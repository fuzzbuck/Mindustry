package mindustry.world.blocks.power;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Log;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.entities.Effects;
import mindustry.entities.Effects.Effect;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import mindustry.world.consumers.ConsumeLiquidFilter;
import mindustry.world.meta.BlockStat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.*;

/**
 * Power generation block which can use items, liquids or both as input sources for power production.
 * Liquids will take priority over items.
 */
public class LiquidGenerator extends PowerGenerator{

    /** Maximum liquid used per frame. */
    public float maxLiquidGenerate = 0.4f;

    /** How fast it warms up to rotate faster */
    public float rotateWarmupSpeed = 0.001f;

    /** Maximum degrees per frame it can rotate */
    public float maxRotateSpeed = 4.2f;

    public Effect generateEffect = Fx.steam;

    public TextureRegion spinRegion;

    public boolean defaults = false;

    public LiquidGenerator(String name){
        super(name);
        this.entityType = LiquidGeneratorEntity::new;
    }

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void load(){
        super.load();
        spinRegion = Core.atlas.find(name + "-spinner");
    }

    @Override
    public TextureRegion[] generateIcons(){
        return new TextureRegion[]{Core.atlas.find(name), Core.atlas.find(name + "-spinner")};
    }

    @Override
    public void setStats(){
        super.setStats();
    }

    @Override
    public boolean productionValid(Tile tile){
        LiquidGeneratorEntity entity = tile.ent();
        return entity.rotateSpeed > 0;
    }

    @Override
    public void update(Tile tile){
        LiquidGeneratorEntity entity = tile.ent();

        //Note: Do not use this delta when calculating the amount of power or the power efficiency, but use it for resource consumption if necessary.
        //Power amount is delta'd by PowerGraph class already.
        float calculationDelta = entity.delta();

        Liquid liquid = entity.liquids.current();

        if(hasLiquids && liquid != null && entity.liquids.get(liquid) >= 0.1f){
            float maximumPossible = maxLiquidGenerate * calculationDelta;
            float used = Math.min(entity.liquids.get(liquid) * calculationDelta, maximumPossible);

            entity.rotateSpeed = Mathf.lerpDelta(entity.rotateSpeed, maxRotateSpeed, rotateWarmupSpeed);
            entity.liquids.remove(liquid, used * entity.power.graph.getUsageFraction());

            if(used > 0.001f && Mathf.chance(0.2 * entity.delta())){
                Effects.effect(generateEffect, tile.drawx() + Mathf.range(4f), tile.drawy() + Mathf.range(4f));
            }
        } else{
            entity.rotateSpeed = Mathf.lerpDelta(entity.rotateSpeed, 0f, rotateWarmupSpeed / 2); // slow down 2x slower
        }
        entity.productionEfficiency = entity.rotateSpeed / maxRotateSpeed;
        entity.rotation += entity.rotateSpeed;
    }

    @Override
    public void draw(Tile tile){
        super.draw(tile);
        LiquidGeneratorEntity entity = tile.ent();

        Draw.rect(region, tile.drawx(), tile.drawy());
        Draw.rect(spinRegion, tile.drawx(), tile.drawy(), entity.rotation);
    }

    @Override
    public void drawLight(Tile tile){
        LiquidGeneratorEntity entity = tile.ent();

        renderer.lights.add(tile.drawx(), tile.drawy(), (20f + Mathf.absin(10f, 5f)) * entity.productionEfficiency * size, Color.white, 0.5f);
    }

    public static class LiquidGeneratorEntity extends GeneratorEntity{
        public float rotateSpeed;
        public float rotation;
    }
}
