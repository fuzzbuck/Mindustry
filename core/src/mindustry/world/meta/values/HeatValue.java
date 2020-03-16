package mindustry.world.meta.values;

import arc.scene.ui.layout.Table;
import mindustry.ui.HeatDisplay;
import mindustry.world.meta.StatValue;

public class HeatValue implements StatValue{
    private final float amount;
    private final boolean perSecond;

    public HeatValue(float amount, boolean perSecond){
        this.amount = amount;
        this.perSecond = perSecond;
    }

    @Override
    public void display(Table table){
        table.add(new HeatDisplay(amount, perSecond));
    }
}
