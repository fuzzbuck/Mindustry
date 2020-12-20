package mindustry.creeper;

import arc.math.Mathf;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Call;
import mindustry.world.Tile;

import static mindustry.Vars.*;
import static mindustry.creeper.CreeperUtils.*;

public class Creeper {
    public int x, y;
    public float worldx, worldy;
    public int level;
    public Tile tile;
    public Building build;

    public Creeper(int _x, int _y){
        x = _x;
        y = _y;
        tile = world.tile(x, y);
        tile.creeper = this;
    }
    public Creeper(Tile t){
        x = t.x;
        y = t.y;
        tile = t;
        tile.creeper = this;
    }

    public void set(int _level){
        level = _level;
        world.tile(x, y).setNet(creeperLevelBlocks.get(_level), Team.crux, Mathf.random(0, 3));
        build = world.tile(x, y).build;
    }
    public void upgrade(){
        if(level >= max_level)
            return;
        set(level+1);
    }
    public void downgrade(){
        if(level <= 1) {
            remove();
            return;
        }
        set(level-1);
    }
    public void remove(){
        tile.creeper = null;
    }
}
