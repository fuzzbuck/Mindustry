package mindustry.world.blocks.production;

import arc.func.Cons;
import arc.func.Prov;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effects;
import mindustry.entities.Effects.Effect;
import mindustry.entities.type.TileEntity;
import mindustry.gen.Sounds;
import mindustry.type.HeatStack;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.consumers.ConsumeLiquidBase;
import mindustry.world.consumers.ConsumeType;
import mindustry.world.meta.BlockStat;
import mindustry.world.meta.StatUnit;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class GenericHeatCrafter extends GenericCrafter{

    public GenericHeatCrafter(String name){
        super(name);
        entityType = GenericCrafterEntity::new;
    }


    @Override
    public boolean shouldConsume(Tile tile){
        return outputHeat != null && tile.entity.heatmod.currentAmount() < heatCapacity;
    }

    @Override
    public void update(Tile tile){
        GenericCrafterEntity entity = tile.ent();

        if(entity.cons.valid()){

            entity.progress += getProgressIncrease(entity, craftTime);
            entity.totalProgress += entity.delta();
            entity.warmup = Mathf.lerpDelta(entity.warmup, 1f, 0.02f);

            handleHeat(tile, tile, outputHeat.amount);

            if(Mathf.chance(Time.delta() * updateEffectChance)){
                Effects.effect(updateEffect, entity.x + Mathf.range(size * 4f), entity.y + Mathf.range(size * 4));
            }
        }else{
            entity.warmup = Mathf.lerp(entity.warmup, 0f, 0.02f);
        }

        if(entity.progress >= 1f){
            entity.cons.trigger();
            Effects.effect(craftEffect, tile.drawx(), tile.drawy());
            entity.progress = 0f;
        }

        if(outputItem != null && tile.entity.timer.get(timerDump, dumpTime)){
            tryDump(tile, outputItem.item);
        }

        if(outputLiquid != null){
            tryDumpLiquid(tile, outputLiquid.liquid);
        }
        tryDumpHeat(tile);
        entity.heatmod.update();
    }
}
