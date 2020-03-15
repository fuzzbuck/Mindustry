package mindustry.world.blocks.liquid;

public class LiquidTank extends LiquidRouter{

    public LiquidTank(String name){
        super(name);
    }

    public LiquidTank(String name, Boolean acidResistant){
        super(name);
        acidResistance = acidResistant;
    }
}
