package mindustry.world.blocks.power;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effects;
import mindustry.entities.Effects.Effect;
import mindustry.entities.type.TileEntity;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BlockStat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import static mindustry.Vars.renderer;

/**
 * Power generation block which can use items, liquids or both as input sources for power production.
 * Liquids will take priority over items.
 */
public class HeatGenerator extends PowerGenerator{

    /** Maximum heat used per frame. */
    public float maxHeatGenerate = 0.1f;

    public float baseFloorHeatGeneration = 0.2f;

    public Attribute attribute = Attribute.heat;

    public Effect generateEffect = Fx.steam;

    public boolean defaults = false;

    public TextureRegion heatRegion;

    public HeatGenerator(String name){
        super(name);
        hasHeat = true;
        consumesHeat = true;
        this.entityType = HeatGeneratorEntity::new;
    }

    @Override
    public void init(){
        super.init();
    }

    @Override
    public void load(){
        super.load();
        heatRegion = Core.atlas.find(name + "-top");
    }

    @Override
    public void onProximityAdded(Tile tile){
        super.onProximityAdded(tile);
        HeatGeneratorEntity entity = tile.ent();
        entity.floorHeatGeneration = baseFloorHeatGeneration * sumAttribute(attribute, tile.x, tile.y);
    }

    @Override
    public TextureRegion[] generateIcons(){
        return new TextureRegion[]{Core.atlas.find(name)};
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(BlockStat.tiles, attribute);
    }

    @Override
    public boolean productionValid(Tile tile){
        HeatGeneratorEntity entity = tile.ent();
        return entity.heatmod.currentAmount() > 0;
    }

    @Override
    public void update(Tile tile){
        HeatGeneratorEntity entity = tile.ent();

        if(entity.floorHeatGeneration > 0) {
            if (entity.heatmod.currentAmount() + baseFloorHeatGeneration < heatCapacity) {
                entity.heatmod.add(baseFloorHeatGeneration);
            } else {
                entity.heatmod.add(heatCapacity - entity.heatmod.currentAmount());
            }
        }

        //Note: Do not use this delta when calculating the amount of power or the power efficiency, but use it for resource consumption if necessary.
        //Power amount is delta'd by PowerGraph class already.
        float calculationDelta = entity.delta();

        if(entity.heatmod.currentAmount() >= 0.1f){
            float maximumPossible = maxHeatGenerate * calculationDelta;
            float used = Math.min(entity.heatmod.currentAmount() * calculationDelta, maximumPossible);

            entity.heatmod.remove(used * entity.power.graph.getUsageFraction());

            if(Mathf.chance(0.1 * entity.delta())){
                Effects.effect(generateEffect, tile.drawx() + Mathf.range(4f), tile.drawy() + Mathf.range(4f));
            }
        }
        tile.entity.heatmod.update();
        entity.productionEfficiency = tile.entity.heatmod.smoothAmount() / heatCapacity;
    }

    @Override
    public void draw(Tile tile){
        super.draw(tile);

        Draw.rect(region, tile.drawx(), tile.drawy());
        Draw.color(Pal.lightOrange);
        Draw.alpha(tile.entity.heatmod.smoothAmount() / heatCapacity);
        Draw.rect(heatRegion, tile.drawx(), tile.drawy());
        Draw.color();
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        drawPlaceText(Core.bundle.formatFloat("bar.efficiency", sumAttribute(attribute, x, y) * 100, 1), x, y, valid);
    }

    @Override
    public void drawLight(Tile tile){
        GeneratorEntity entity = tile.ent();

        renderer.lights.add(tile.drawx(), tile.drawy(), (20f + Mathf.absin(10f, 5f)) * entity.productionEfficiency * size, Color.white, 0.5f);
    }

    public static class HeatGeneratorEntity extends GeneratorEntity {
        public float floorHeatGeneration;

        @Override
        public void write(DataOutput stream) throws IOException {
            super.write(stream);
            stream.writeFloat(floorHeatGeneration);
        }

        @Override
        public void read(DataInput stream, byte revision) throws IOException{
            super.read(stream, revision);
            floorHeatGeneration = stream.readFloat();
        }
    }
}
