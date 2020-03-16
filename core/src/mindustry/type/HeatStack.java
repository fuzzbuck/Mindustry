package mindustry.type;

public class HeatStack {
    public float amount;

    public HeatStack(float amount){
        this.amount = amount;
    }

    /** serialization only*/
    protected HeatStack(){

    }

    @Override
    public String toString(){
        return "HeatStack{" +
        "amount=" + amount +
        '}';
    }
}
