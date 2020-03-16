package mindustry.world.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import mindustry.graphics.Layer;
import mindustry.world.Block;
import mindustry.world.Tile;

public class RuinsBlock extends Block{
    public TextureRegion botRegion;
    public TextureRegion topRegion;

    public RuinsBlock(String name){
        super(name);
        solid = true;
        layer = Layer.high;
        expanded = true;
    }

    @Override
    public void load(){
        botRegion = Core.atlas.find(name + "-bottom");
        topRegion = Core.atlas.find(name + "-top");
    }

    @Override
    public TextureRegion[] generateIcons(){
        return new TextureRegion[]{Core.atlas.find(name + "-top")};
    }

    @Override
    public void draw(Tile tile){
        Draw.rect(botRegion, tile.drawx(), tile.drawy());
    }

    @Override
    public void drawLayer(Tile tile){
        Draw.rect(topRegion, tile.drawx(), tile.drawy());
    }
}
