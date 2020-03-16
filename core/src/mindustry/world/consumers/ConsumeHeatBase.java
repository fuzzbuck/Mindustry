package mindustry.world.consumers;

import mindustry.entities.type.TileEntity;

public abstract class ConsumeHeatBase extends Consume{
    /** amount used per frame */
    public final float amount;
    /**
     * How much time is taken to use this heat, in ticks. Used only for visual purposes.
     * Example: a normal ConsumeHeat with 10/s and a 10 second timePeriod would display as "100 seconds".
     * Without a time override, it would display as "10 liquid/second".
     * This is used for generic crafters.
     */
    public float timePeriod = 60;

    public ConsumeHeatBase(float amount){
        this.amount = amount;
    }

    @Override
    public ConsumeType type(){
        return ConsumeType.heat;
    }

    protected float use(TileEntity entity){
        return Math.min(amount * entity.delta(), entity.block.heatCapacity);
    }
}
