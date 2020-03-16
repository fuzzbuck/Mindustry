package mindustry.world.modules;

import arc.math.Mathf;
import mindustry.type.Liquid;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

import static mindustry.Vars.content;

public class HeatModule extends BlockModule{
    private float total;
    private float smoothTotal;

    public void update(){
        smoothTotal = Mathf.lerpDelta(smoothTotal, currentAmount(), 0.1f);
    }

    public float smoothAmount(){
        return smoothTotal;
    }

    /** Returns total amount of liquids. */
    public float total(){
        return total;
    }

    public void reset(float amount){
        total = amount;
    }

    public void add(float amount){
        total = total + amount;
    }

    public float currentAmount(){
        return total;
    }

    public void clear(){
        total = 0;
    }

    public void remove(float amount){
        total = total - amount;
    }

    @Override
    public void write(DataOutput stream) throws IOException{
        stream.writeFloat(total);; //amount of heat
    }

    @Override
    public void read(DataInput stream) throws IOException{
        this.total = stream.readFloat();
    }

    public interface HeatConsumer{
        void accept(float amount);
    }

    public interface HeatCalculator{
        float get(float amount);
    }
}
