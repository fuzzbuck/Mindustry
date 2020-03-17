package mindustry.world.blocks.liquid;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.entities.Effects;
import mindustry.entities.type.TileEntity;
import mindustry.graphics.Pal;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import mindustry.world.blocks.BlockPart;
import mindustry.world.blocks.HeatBlock;

public class HeatRouter extends HeatBlock {
    private TextureRegion[] blendbits = new TextureRegion[11];
    private TextureRegion[] blendbitsTop = new TextureRegion[11];

    public HeatRouter(String name){
        super(name);
        hasHeat = true;
        outputsHeat = true;
        entityType = HeatPipeEntity::new;
    }

    @Override
    public void update(Tile tile){
        if(tile.entity.heatmod.currentAmount() > 0.01f){
            if(tile.floor().liquidDrop == Liquids.water){
                tile.entity.heatmod.remove(0.08f);
                if(Mathf.chance(0.1f)){
                    Effects.effect(Fx.steam, tile.drawx(), tile.drawy());
                }
            }
            tile.entity.heatmod.remove(0.001f);
            tryDumpHeat(tile);
        }
        tile.entity.heatmod.update();
    }

    @Override
    public void load(){
        super.load();

        for(int i = 0; i < blendbits.length; i++){
            blendbits[i] = Core.atlas.find(name + "-" + i);
        }
        for(int i = 0; i < blendbitsTop.length; i++){
            blendbitsTop[i] = Core.atlas.find(name + "-" + i + "-top");
        }
    }

    @Override
    public TextureRegion[] generateIcons(){
        return new TextureRegion[]{Core.atlas.find(name + "-10")};
    }

    @Override
    public boolean acceptHeat(Tile tile, Tile source, float amount){
        return tile.entity.heatmod.currentAmount() + amount < heatCapacity;
    }

    @Override
    public void onProximityUpdate(Tile tile){
        HeatPipeEntity ent = tile.ent();
        boolean // which way to face
                up = blendsWith(tile, 0, 1),
                right = blendsWith(tile, 1, 0),
                down = blendsWith(tile, 0, -1),
                left = blendsWith(tile, -1, 0);

        // straight
        if((right || left) && !(up || down)) ent.blendbit = 0;
        if((up || down) && !(right || left)) ent.blendbit = 1;

        // turns
        if(up && right && !(down || left)) ent.blendbit = 2;
        if(down && right && !(up || left)) ent.blendbit = 3;
        if(down && left && !(up || right)) ent.blendbit = 4;
        if(up && left && !(down || right)) ent.blendbit = 5;

        // junctions
        if(up && left && right && !down) ent.blendbit = 6;
        if(down && left && right && !up) ent.blendbit = 7;
        if(down && up && right && !left) ent.blendbit = 8;
        if(down && up && left && !right) ent.blendbit = 9;
        if(up && right && down && left) ent.blendbit = 10;
    }

    @Override
    public void draw(Tile tile){
        HeatPipeEntity entity = tile.ent();

        Draw.rect(blendbits[entity.blendbit], tile.drawx(), tile.drawy());
        if(tile.entity.heatmod.smoothAmount() > 0.01f) {
            Draw.color(Pal.lightOrange);
            Draw.alpha(entity.heatmod.smoothAmount() / heatCapacity);
            Draw.rect(blendbitsTop[entity.blendbit], tile.drawx(), tile.drawy());
            Draw.color();
        }
    }

    private boolean blendsWith(Tile tile, int dx, int dy){
        if(tile.getNearby(dx, dy) == null) return false;
        return tile.getNearby(dx, dy).link().block().hasHeat;
    }

    public static class HeatPipeEntity extends TileEntity {
        public int blendbit;
    }
}
