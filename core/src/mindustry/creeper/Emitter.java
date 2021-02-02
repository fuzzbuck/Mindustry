package mindustry.creeper;

import arc.Events;
import arc.graphics.Color;
import arc.util.Log;
import mindustry.content.Fx;
import mindustry.game.EventType;
import mindustry.gen.Building;
import mindustry.gen.Call;

public class Emitter {
    public int interval;
    public int amt;
    public Building build;
    public boolean nullified;

    protected int counter;

    // updates every interval in CreeperUtils
    public boolean update(){
        if(build == null || build != null && build.health <= 1f)
            return false;

        // no nullification for team blue
        // nullified = build.nullifyTimeout > 0f;

        if(counter >= interval && !nullified) {
            counter = 0;
            build.tile.creep += amt;
        }
        counter++;

        return true;
    }

    // updates every 1 second
    public void fixedUpdate(){
        if(nullified) {
            Call.label("[red]*[] SUSPENDED [red]*[]", 1f, build.x, build.y);
            Call.effect(Fx.placeBlock, build.x, build.y, build.block.size, Color.yellow);
        }
    }

    public Emitter(int _interval, int _amt){
        interval = _interval;
        amt = _amt;
    }

    public Emitter(Building _build){
        build = _build;

        var ref = CreeperUtils.emitterBlocks.get(build.block);
        interval = ref.interval;
        amt = ref.amt;
    }
}
