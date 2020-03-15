package mindustry.world.blocks.liquid;

import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.Liquids;
import mindustry.entities.Effects;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import mindustry.world.blocks.LiquidBlock;

public class LiquidRouter extends LiquidBlock{

    public LiquidRouter(String name){
        super(name);
    }
    public boolean acidResistance = false;

    @Override
    public void update(Tile tile){

        if(tile.entity.liquids.total() > 0.01f){
            tryDumpLiquid(tile, tile.entity.liquids.current());
            if(tile.entity.liquids.current() == Liquids.acid){
                tile.entity.damage(Mathf.random(0.001f, 0.1f));
                if(Mathf.chance(0.05f)){
                    Effects.effect(Fx.radiating, tile.drawx(), tile.drawy());
                }
            }
        }
    }

    @Override
    public boolean acceptLiquid(Tile tile, Tile source, Liquid liquid, float amount){
        return tile.entity.liquids.get(liquid) + amount < liquidCapacity && (tile.entity.liquids.current() == liquid || tile.entity.liquids.get(tile.entity.liquids.current()) < 0.2f);
    }
}
