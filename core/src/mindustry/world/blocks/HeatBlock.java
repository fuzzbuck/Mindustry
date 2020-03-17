package mindustry.world.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.BlockGroup;
import mindustry.world.modules.HeatModule;

public class HeatBlock extends Block{
    protected TextureRegion bottomRegion, topRegion;

    public HeatBlock(String name){
        super(name);
        update = true;
        solid = true;
        hasHeat = true;
        group = BlockGroup.power;
    }

    @Override
    public void load(){
        super.load();

        topRegion = Core.atlas.find(name + "-top");
        bottomRegion = Core.atlas.find(name + "-bottom");
    }

    @Override
    public TextureRegion[] generateIcons(){
        return new TextureRegion[]{Core.atlas.find(name + "-bottom"), Core.atlas.find(name + "-top")};
    }

    @Override
    public void draw(Tile tile){
        HeatModule mod = tile.entity.heatmod;

        int rotation = rotate ? tile.rotation() * 90 : 0;

        Draw.rect(bottomRegion, tile.drawx(), tile.drawy(), rotation);

        if(mod.total() > 0.001f){
            Draw.color(Pal.lightOrange);
            Draw.alpha(mod.total() / liquidCapacity);
            Draw.rect(topRegion, tile.drawx(), tile.drawy(), rotation);
            Draw.color();
        }
    }
}
