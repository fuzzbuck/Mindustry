package mindustry.world.blocks.production;

import arc.math.Mathf;
import mindustry.entities.Effects;
import mindustry.type.Liquid;
import mindustry.world.Tile;
import mindustry.world.consumers.ConsumeLiquidBase;
import mindustry.world.consumers.ConsumeType;
import mindustry.world.meta.BlockStat;

public class LiquidHeatConverter extends LiquidConverter{

    public LiquidHeatConverter(String name){
        super(name);
        hasHeat = true;
    }

    public boolean acceptHeat(Tile tile, Tile source, float amount){
        return tile.entity.heatmod.currentAmount() + amount < heatCapacity;
    }

    @Override
    public void update(Tile tile){
        super.update(tile);
        tile.entity.heatmod.update();
    }
}
