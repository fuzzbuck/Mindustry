package mindustry.world.consumers;

import arc.scene.ui.layout.Table;
import mindustry.entities.type.TileEntity;
import mindustry.world.Tile;
import mindustry.world.meta.BlockStat;
import mindustry.world.meta.BlockStats;

public class ConsumeHeat extends ConsumeHeatBase{

    public ConsumeHeat(float amount){
        super(amount);
    }

    protected ConsumeHeat(){
        this(0f);
    }

    @Override
    public void build(Tile tile, Table table){

    }

    @Override
    public String getIcon(){
        return "icon-heat-consume";
    }

    @Override
    public void update(TileEntity entity){
        entity.heatmod.remove(Math.min(use(entity), entity.heatmod.currentAmount()));
    }

    @Override
    public boolean valid(TileEntity entity){
        return entity != null && entity.heatmod != null && entity.heatmod.currentAmount() >= use(entity);
    }

    @Override
    public void display(BlockStats stats){
        stats.add(booster ? BlockStat.booster : BlockStat.input,amount * timePeriod, true);
    }
}
