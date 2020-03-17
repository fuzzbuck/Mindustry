package mindustry.world.blocks.liquid;

import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.traits.BuilderTrait.*;
import mindustry.entities.type.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.*;

import static mindustry.Vars.tilesize;

public class HeatRouter extends HeatBlock implements Autotiler{

    private TextureRegion[] blendbits = new TextureRegion[4], blendbitsTop = new TextureRegion[4];

    public static final int[] blendresult = new int[4];

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
            blendbitsTop[i] = Core.atlas.find(name + "-" + i + "-top");
        }
    }

    @Override
    public TextureRegion[] generateIcons(){
        return new TextureRegion[]{Core.atlas.find(name + "-3")};
    }

    @Override
    public boolean acceptHeat(Tile tile, Tile source, float amount){
        return tile.entity.heatmod.currentAmount() + amount < heatCapacity;
    }

    @Override
    public void onProximityUpdate(Tile tile){
        HeatPipeEntity entity = tile.ent();

        int[] bits = buildBlending(tile, 0, null, true);
        entity.blendbits = bits[0];
        entity.blendsclx = bits[1];
        entity.blendscly = bits[2];
        entity.blendsclr = bits[3];
    }

    public int[] buildBlending(Tile tile, int rotation, BuildRequest[] directional, boolean world){
        blendresult[0] = blendresult[3] = 0;
        blendresult[1] = blendresult[2] = 1;

        boolean right = blends(tile, 0, directional, 0, true),
                up    = blends(tile, 0, directional, 1, true),
                left  = blends(tile, 0, directional, 2, true),
                down  = blends(tile, 0, directional, 3, true);

        int num = -1;

        // straight
        if((right || left) && !(up || down)) num = 0;
        if((up || down) && !(right || left)) num = 1;

        // turns
        if(up && right && !(down || left)) num = 2;
        if(down && right && !(up || left)) num = 3;
        if(down && left && !(up || right)) num = 4;
        if(up && left && !(down || right)) num = 5;

        // junctions
        if(up && left && right && !down) num = 6;
        if(down && left && right && !up) num = 7;
        if(down && up && right && !left) num = 8;
        if(down && up && left && !right) num = 9;
        if(up && right && down && left) num = 10;

        transformCase(num, blendresult);
        return blendresult;
    }

    @Override
    public void transformCase(int num, int[] bits){
        if(num == 1){
            bits[0] = 0;
            bits[3] = 1;
        }else if(num == 2){
            bits[0] = 1;
            bits[2] = -1;
        }else if(num == 3){
            bits[0] = 1;
        }else if(num == 4){
            bits[0] = 1;
            bits[1] = -1;
        }else if(num == 5){
            bits[0] = 1;
            bits[1] = -1;
            bits[2] = -1;
        }else if(num == 6){
            bits[0] = 2;
            bits[3] = 0;
        }else if(num == 7){
            bits[0] = 2;
            bits[3] = 2;
        }else if(num == 8){
            bits[0] = 2;
            bits[3] = 1;
        }else if(num == 9){
            bits[0] = 2;
            bits[3] = 3;
        }else if(num == 10){
            bits[0] = 3;
        }
    }

    @Override
    public void draw(Tile tile){
        HeatPipeEntity entity = tile.ent();

        Draw.rect(blendbits[entity.blendbits], tile.drawx(), tile.drawy(), tilesize * entity.blendsclx, tilesize * entity.blendscly, entity.blendsclr * 90);
        if(tile.entity.heatmod.smoothAmount() > 0.01f) {
            Draw.color(Pal.lightOrange);
            Draw.alpha(entity.heatmod.smoothAmount() / heatCapacity);
            Draw.rect(blendbitsTop[entity.blendbits], tile.drawx(), tile.drawy(), tilesize * entity.blendsclx, tilesize * entity.blendscly, entity.blendsclr * 90);
            Draw.color();
        }
    }

    @Override
    public void drawRequestRegion(BuildRequest req, Eachable<BuildRequest> list){
        int[] bits = getTiling(req, list);

        if(bits == null) return;

        TextureRegion region = blendbits[bits[0]];
        Draw.rect(region, req.drawx(), req.drawy(), region.getWidth() * bits[1] * Draw.scl * req.animScale, region.getHeight() * bits[2] * Draw.scl * req.animScale, bits[3] * 90);
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock){
        return otherblock.hasHeat;
    }

    public static class HeatPipeEntity extends TileEntity{
        int blendbits;
        int blendsclx, blendscly, blendsclr;
    }
}
