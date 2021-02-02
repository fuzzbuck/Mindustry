package mindustry.entities.abilities;

import arc.*;
import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import arc.scene.ui.layout.*;
import arc.util.*;
import mindustry.Vars;
import mindustry.content.*;
import mindustry.creeper.CreeperUtils;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.Tile;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.ui.*;

public class ForceFieldAbility extends Ability{
    /** Shield radius. */
    public float radius = 60f;
    /** Shield regen speed in damage/tick. */
    public float regen = 0.1f;
    /** Maximum shield. */
    public float max = 200f;
    /** Cooldown after the shield is broken, in ticks. */
    public float cooldown = 60f * 5;

    /** State. */
    protected float radiusScale, alpha;

    private static float realRad;
    private static Unit paramUnit;
    private static ForceFieldAbility paramField;
    private static final Cons<Bullet> shieldConsumer = trait -> {
        if(trait.team != paramUnit.team && trait.type.absorbable && Intersector.isInsideHexagon(paramUnit.x, paramUnit.y, realRad * 2f, trait.x(), trait.y()) && paramUnit.shield > 0){
            trait.absorb();
            Fx.absorb.at(trait);

            //break shield
            if(paramUnit.shield <= trait.damage()){
                paramUnit.shield -= paramField.cooldown * paramField.regen;

                Fx.shieldBreak.at(paramUnit.x, paramUnit.y, paramField.radius, paramUnit.team.color);
            }

            paramUnit.shield -= trait.damage();
            paramField.alpha = 1f;
        }
    };

    private static final Cons<Tile> creeperConsumer = tile -> {
        if((tile.creep >= 1f || (CreeperUtils.creeperBlocks.containsValue(tile.block()) && tile.team() == CreeperUtils.creeperTeam)) && Intersector.isInsideHexagon(paramUnit.x, paramUnit.y, realRad * 2f, tile.worldx(), tile.worldy()) && paramUnit.shield > 0){

            if(paramUnit.shield <= CreeperUtils.creeperDamage * CreeperUtils.creeperLevels.getOrDefault(tile.block(), 1)){
                paramUnit.shield -= paramField.cooldown * paramField.regen;

                Call.effect(Fx.shieldBreak, paramUnit.x, paramUnit.y, paramField.radius, paramUnit.team.color);
            }

            paramUnit.shield -= CreeperUtils.creeperDamage * CreeperUtils.unitShieldDamageMultiplier * (tile.creep / 2f) + (tile.block() instanceof CoreBlock && tile.team() == CreeperUtils.creeperTeam ? 2 : 0);
            paramField.alpha = 1f;

            var dmg = Blocks.conveyor.health / 10;
            if(tile.build != null && tile.build.team == CreeperUtils.creeperTeam)
                tile.build.damage(dmg);

            if(tile.build != null && tile.build.health <= dmg)
                Call.effect(Fx.absorb, tile.worldx(), tile.worldy(), 1, Color.blue);
        }
    };

    public ForceFieldAbility(float radius, float regen, float max, float cooldown){
        this.radius = radius;
        this.regen = regen;
        this.max = max;
        this.cooldown = cooldown;
    }

    ForceFieldAbility(){}

    @Override
    public void update(Unit unit){
        if(unit.shield < max){
            unit.shield += Time.delta * regen;
        }

        alpha = Math.max(alpha - Time.delta/10f, 0f);

        if(unit.shield > 0){
            radiusScale = Mathf.lerpDelta(radiusScale, 1f, 0.06f);
            paramUnit = unit;
            paramField = this;
            checkRadius(unit);

            Groups.bullet.intersect(unit.x - realRad, unit.y - realRad, realRad * 2f, realRad * 2f, shieldConsumer);
            Geometry.circle(unit.tileX(), unit.tileY(), (int) (((int) realRad/ Vars.tilesize) * 3), (cx, cy) -> {
                if(Intersector.isInsideHexagon(paramUnit.x, paramUnit.y, realRad * 2f, cx * Vars.tilesize, cy * Vars.tilesize) && Vars.world.tile(cx, cy) != null)
                    creeperConsumer.get(Vars.world.tile(cx, cy));
            });
        }else{
            radiusScale = 0f;
        }
    }

    @Override
    public void draw(Unit unit){
        checkRadius(unit);

        if(unit.shield > 0){
            Draw.z(Layer.shields);

            Draw.color(unit.team.color, Color.white, Mathf.clamp(alpha));

            if(Core.settings.getBool("animatedshields")){
                Fill.poly(unit.x, unit.y, 6, realRad);
            }else{
                Lines.stroke(1.5f);
                Draw.alpha(0.09f);
                Fill.poly(unit.x, unit.y, 6, radius);
                Draw.alpha(1f);
                Lines.poly(unit.x, unit.y, 6, radius);
            }
        }
    }

    @Override
    public void displayBars(Unit unit, Table bars){
        bars.add(new Bar("stat.shieldhealth", Pal.accent, () -> unit.shield / max)).row();
    }

    public void checkRadius(Unit unit){
        //timer2 is used to store radius scale as an effect
        realRad = radiusScale * radius;
    }
}
