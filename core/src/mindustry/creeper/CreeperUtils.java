package mindustry.creeper;

import arc.Core;
import arc.Events;
import arc.func.Cons;
import arc.func.Intc;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Vec2;
import arc.math.geom.Vector;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.Timer;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Bullets;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.bullet.BulletType;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.net.Administration;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.ForceProjector;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.environment.Cliff;
import mindustry.world.blocks.environment.StaticWall;
import mindustry.world.blocks.environment.TreeBlock;
import mindustry.world.blocks.storage.CoreBlock;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import static mindustry.Vars.*;

public class CreeperUtils {
    public static float updateInterval = 0.025f; // Base update interval in seconds
    public static float transferRate = 0.249f; // Base transfer rate NOTE: keep below 0.25f
    public static float evaporationRate = 0f; // Base creeper evaporation
    public static float creeperDamage = 2.5f; // Base creeper damage
    public static float creeperEvaporationDamageMultiplier = 20f; // Creeper percentage that will remain upon damaging something
    public static float creeperUnitDamage = 4f;
    public static float minCreeper = 0.9f; // Minimum amount of creeper required for transfer

    public static BulletType sporeType = Bullets.artilleryDense;
    public static float sporeMaxRangeMultiplier = 30f;
    public static float sporeAmount = 30f;
    public static float sporeRadius = 3f;
    public static float sporeSpeedMultiplier = 0.15f;
    public static float sporeHealthMultiplier = 10f;
    public static float sporeTargetOffset = 256f;

    public static float unitShieldDamageMultiplier = 0.5f;
    public static float buildShieldDamageMultiplier = 1f;
    public static float shieldBoostProtectionMultiplier = 0.5f;
    public static float shieldCreeperDropAmount = 5f;
    public static float shieldCreeperDropRadius = 3f;

    public static float nullifierRange = 10f;


    public static float nullifyDamage = 1500f; // Damage that needs to be applied for the core to be suspended
    public static float nullifyTimeout = 360f; // The amount of time a core remains suspended (resets upon enough damage applied)

    public static float nullificationPeriod = 10f; // How many seconds all cores have to be nullified (suspended) in order for the game to end
    private static int nullifiedCount = 0;

    public static Team creeperTeam = Team.blue;

    public static HashMap<Integer, Block> creeperBlocks = new HashMap<>();
    public static HashMap<Block, Integer> creeperLevels = new HashMap<>();

    public static HashMap<Item, Vec2> creeperItemWeights = new HashMap<>();

    public static HashMap<Block, Emitter> emitterBlocks = new HashMap<>();

    public static Seq<Emitter> creeperEmitters = new Seq<>();
    public static Seq<ForceProjector.ForceBuild> shields = new Seq<>();

    public static Timer.Task runner;
    public static Timer.Task fixedRunner;

    public static String getTrafficlightColor(double value){
        return "#"+Integer.toHexString(java.awt.Color.HSBtoRGB((float)value/3f, 1f, 1f)).substring(2);
    }


    public static float[] targetSpore(){
        float[] ret = null;
        int iterations = 0;

        while(ret == null && iterations < 100 && Groups.player.size() > 0){
            iterations++;
            Player player = Groups.player.index(Mathf.random(0, Groups.player.size()-1));
            if(player.team() == creeperTeam || player.unit() == null || player.x == 0 && player.y == 0)
                continue;

            Unit unit = player.unit();
            ret = new float[]{unit.x + Mathf.random(-sporeTargetOffset, sporeTargetOffset), unit.y + Mathf.random(-sporeTargetOffset, sporeTargetOffset)};
        }

        return (ret != null ? ret : new float[]{0,0});
    }

    public static void sporeCollision(Bullet bullet, float x, float y){
        Tile tile = world.tileWorld(x, y);
        if(!validTile(tile))
            return;

        Call.effect(Fx.sapExplosion, x, y, sporeRadius, Color.blue);

        depositCreeper(tile, sporeRadius, sporeAmount);
    }

    public static void init(){
        sporeType.isCreeper = true;


        creeperBlocks.put(0, Blocks.air);
        creeperBlocks.put(1, Blocks.conveyor);
        creeperBlocks.put(2, Blocks.titaniumConveyor);
        creeperBlocks.put(3, Blocks.armoredConveyor);
        creeperBlocks.put(4, Blocks.plastaniumConveyor);
        creeperBlocks.put(5, Blocks.scrapWall);
        creeperBlocks.put(6, Blocks.titaniumWall);
        creeperBlocks.put(7, Blocks.thoriumWall);
        creeperBlocks.put(8, Blocks.plastaniumWall);
        creeperBlocks.put(9, Blocks.phaseWall);
        creeperBlocks.put(10, Blocks.surgeWall);

        // this is purely for damage multiplication
        creeperBlocks.put(12, Blocks.thoriumReactor);
        creeperBlocks.put(20, Blocks.coreShard);
        creeperBlocks.put(35, Blocks.coreFoundation);
        creeperBlocks.put(50, Blocks.coreNucleus);

        // for creeper pvp
        // Vec2 : <radius, amt>
        creeperItemWeights.put(Items.copper, new Vec2(1, 2));
        creeperItemWeights.put(Items.lead, new Vec2(1, 2));
        creeperItemWeights.put(Items.coal, new Vec2(1, 3));
        creeperItemWeights.put(Items.sand, new Vec2(1, 1));
        creeperItemWeights.put(Items.titanium, new Vec2(2, 1));
        creeperItemWeights.put(Items.graphite, new Vec2(2, 1));
        creeperItemWeights.put(Items.silicon, new Vec2(2, 2));
        creeperItemWeights.put(Items.thorium, new Vec2(2, 1));
        creeperItemWeights.put(Items.surgeAlloy, new Vec2(2, 8));
        creeperItemWeights.put(Items.phaseFabric, new Vec2(2, 10));
        creeperItemWeights.put(Items.metaglass, new Vec2(2, 2));
        creeperItemWeights.put(Items.plastanium, new Vec2(2, 7));
        creeperItemWeights.put(Items.blastCompound, new Vec2(2, 8));
        creeperItemWeights.put(Items.pyratite, new Vec2(2, 3));
        creeperItemWeights.put(Items.scrap, new Vec2(1, 1));
        creeperItemWeights.put(Items.sporePod, new Vec2(2, 2));

        for(var set : creeperBlocks.entrySet()){
            creeperLevels.put(set.getValue(), set.getKey());
        }

        emitterBlocks.put(Blocks.launchPadLarge, new Emitter(1, 20));

        // todo: add "spore launchers", etc. (yes creeper world ripoff i know)

        netServer.admins.addActionFilter(new Administration.ActionFilter() {
            @Override
            public boolean allow(Administration.PlayerAction action) {
                if (action.type == Administration.ActionType.breakBlock){
                    if(action.player != null && action.player.team() == CreeperUtils.creeperTeam && action.block == Blocks.launchPadLarge)
                        return false;
                }
                if (action.type == Administration.ActionType.placeBlock){
                    if(action.player != null && action.player.team() == CreeperUtils.creeperTeam){
                        Block block = action.block;
                        if(block instanceof Turret && block != Blocks.hail && block != Blocks.scatter){
                            action.player.sendMessage("[#202020]You can only build hails & scatters as defensive blocks.");
                            return false;
                        }
                        if(block == Blocks.navalFactory){
                            return false;
                        }
                        if(block == Blocks.forceProjector){
                            return false;
                        }
                    }
                }

                return action.type != Administration.ActionType.rotate;
            }
        });

        Events.on(EventType.GameOverEvent.class, e -> {
            if(runner != null)
                runner.cancel();
            if(fixedRunner != null)
                fixedRunner.cancel();

            creeperEmitters.clear();
            shields.clear();
        });

        Events.on(EventType.WorldLoadEvent.class, e -> {
            // DOES NOT SIGNIFY WORLD IS LOADED, need to wait
            try {
                Thread.sleep(2000);
                creeperEmitters.clear();

                Seq<Building> iterated = new Seq<>();
                for(Tile tile : world.tiles){
                    if(tile.block() != null && emitterBlocks.containsKey(tile.block()) && !iterated.contains(tile.build) && tile.build.team == creeperTeam){
                        iterated.add(tile.build);
                        creeperEmitters.add(new Emitter(tile.build));
                    }
                }

                Log.info(creeperEmitters.size + " emitters");

                runner = Timer.schedule(CreeperUtils::updateCreeper, 0, updateInterval);
                fixedRunner = Timer.schedule(CreeperUtils::fixedUpdate, 0, 1);

            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        Events.on(EventType.BlockDestroyEvent.class, e -> {
            if(CreeperUtils.creeperBlocks.containsValue(e.tile.block()))
                onCreeperDestroy(e.tile);
            
            e.tile.creep = 0;
            e.tile.newCreep = 0;
        });

        /*
        Timer.schedule(() -> {

            Call.infoPopup("\uE88B [" + getTrafficlightColor(Mathf.clamp((CreeperUtils.nullifiedCount / Math.max(1.0, creeperEmitters.size)), 0f, 1f)) + "]" + CreeperUtils.nullifiedCount + "/" + CreeperUtils.creeperEmitters.size + "[] emitters suspended", 10f, 20, 50, 20, 450, 0);
            // check for gameover
            if(CreeperUtils.nullifiedCount == CreeperUtils.creeperEmitters.size){
                Timer.schedule(() -> {
                    if(CreeperUtils.nullifiedCount == CreeperUtils.creeperEmitters.size){
                        // gameover
                        state.gameOver = true;
                        Events.fire(new EventType.GameOverEvent(Team.sharded));
                    }
                    // failed to win, core got unsuspended
                }, nullificationPeriod);
            }

            }, 0, 10);
         */

    }

    public static void depositCreeper(Tile tile, float radius, float amount){
        Geometry.circle(tile.x, tile.y, (int) radius, (cx, cy) -> {
            Tile ct = world.tile(cx, cy);
            if(!validTile(ct) || (tile.block() instanceof StaticWall || (tile.floor() != null && !tile.floor().placeableOn || tile.floor().isDeep() || tile.block() instanceof Cliff)))
                return;

            if(tile.floor() == Blocks.metalFloor3)
                return;

            ct.creep = ct.creep + amount;
            ct.newCreep = ct.creep;
        });
    }

    private static void onCreeperDestroy(Tile tile) {
        tile.creep = 0;
        tile.newCreep = 0;
    }

    public static void fixedUpdate(){
        int newcount = 0;
        /*
        for(Emitter emitter : creeperEmitters){
            emitter.fixedUpdate();
            if(emitter.nullified)
                newcount++;
        }
         */
        for(ForceProjector.ForceBuild shield : shields){
            if(shield == null || shield.dead || shield.health <= 0f || shield.healthLeft <= 0f) {
                shields.remove(shield);
                Core.app.post(shield::kill);

                float percentage = 1f - shield.healthLeft / ((ForceProjector) shield.block).shieldHealth;
                depositCreeper(shield.tile, shieldCreeperDropRadius, shieldCreeperDropAmount * percentage);

                continue;
            }

            double percentage = shield.healthLeft / ((ForceProjector) shield.block).shieldHealth;
            Call.label("[" + getTrafficlightColor(percentage) + "]" + (int) (percentage*100) + "%" + (shield.phaseHeat > 0.1f ? " [#f4ba6e]\uE86B +" + ((int) ((1f - CreeperUtils.shieldBoostProtectionMultiplier) * 100f)) + "%" : ""), 1f, shield.x, shield.y);
        }

        nullifiedCount = newcount;
    }

    public static void updateCreeper(){
        // launchpad is auto updated in @update()
        /*
        for(Emitter emitter : creeperEmitters){
            if(!emitter.update())
                creeperEmitters.remove(emitter);
        }
         */

        // update creeper flow
        for(Tile tile : world.tiles){
            transferCreeper(tile, world.tile(tile.x+1, tile.y));
            transferCreeper(tile, world.tile(tile.x-1, tile.y));
            transferCreeper(tile, world.tile(tile.x, tile.y+1));
            transferCreeper(tile, world.tile(tile.x, tile.y-1));
        }

        // clamp creeper
        for(Tile tile : world.tiles){
            if(tile.newCreep > 10)
                tile.newCreep = 10;
            else if (tile.newCreep < 0.01)
                tile.newCreep = 0;
            tile.creep = tile.newCreep;
            drawCreeper(tile);
        }

    }

    // creates appropiate blocks for creeper OR damages the tile that it wants to take
    public static void drawCreeper(Tile tile){

        // check if can transfer anyway because weird
        if(tile.creep >= 1f) {

            if(tile.creep < 10f && tile.block() == creeperBlocks.get(10))
                tile.removeNet();

                // deal continuous damage
            if (tile.build != null && tile.build.team != creeperTeam && tile.touchingCreeper()) {

                Core.app.post(() -> {
                    if (tile.build != null) {

                        if (Mathf.chance(0.05f))
                            Call.effect(Fx.bubble, tile.build.x, tile.build.y, 0, Color.blue);

                        tile.build.damageContinuous(creeperDamage * tile.creep);
                        tile.creep -= creeperDamage / creeperEvaporationDamageMultiplier;
                        tile.newCreep -= creeperDamage / creeperEvaporationDamageMultiplier;
                    }
                });
            }

        }
        if (tile != null && tile.x < world.width() && tile.y < world.height() && tile.creep >= 1f &&
                !(tile.block() == Blocks.launchPadLarge) &&
                (creeperLevels.getOrDefault(tile.block(), 10)) < Math.round(tile.creep) || tile.block() instanceof TreeBlock){

                tile.setNet(creeperBlocks.get(Mathf.clamp(Math.round(tile.creep), 0, 10)), creeperTeam, Mathf.random(0, 3));
        }
    }

    public static boolean canTransfer(Tile source, Tile target){
        boolean amountValid = source.creep > minCreeper;

        if(!validTile(source) || !validTile(target))
            return false;

        if(target.block() instanceof StaticWall || (target.floor() != null && !target.floor().placeableOn || target.floor().isDeep() || target.block() instanceof Cliff))
            return false;

        if(source.floor() == Blocks.metalFloor3 || target.floor() == Blocks.metalFloor3)
            return false;

        if(target.block() instanceof TreeBlock && amountValid)
            return true;

        if(source.build != null && source.build.team != creeperTeam) {
            // wall or something, decline transfer but damage the wall
            drawCreeper(source);
            return false;
        }

        return amountValid;
    }

    public static boolean validTile(Tile tile){
        if(tile == null)
            return false;

        return true;
    }

    public static void transferCreeper(Tile source, Tile target) {
        if(canTransfer(source, target)){
            float sourceCreeper = source.creep;

            if (sourceCreeper > 0){
                float sourceTotal = source.creep;
                float targetTotal = target.creep;
                float delta;

                if (sourceTotal > targetTotal) {
                    delta = sourceTotal - targetTotal;
                    if (delta > sourceCreeper)
                        delta = sourceCreeper;

                    float adjustedDelta = delta * transferRate;
                    source.newCreep -= adjustedDelta;
                    target.newCreep += adjustedDelta - evaporationRate;
                }
            }
        }
    }
}
