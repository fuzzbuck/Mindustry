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
    public Creeper(int _x, int _y, int _level){
        x = _x;
        y = _y;
        level = _level;
        tile = world.tile(x, y);
        tile.creeper = this;
        set(_level);
    }
    public Creeper(Tile t){
        x = t.x;
        y = t.y;
        tile = t;
        tile.creeper = this;
    }

    // whether this creeper is bordering with normal land
    public boolean isBorderingWithLand(){
        return (!world.tile(x+1, y).isCreeper()||
                !world.tile(x-1, y).isCreeper()||
                !world.tile(x, y+1).isCreeper()||
                !world.tile(x, y-1).isCreeper()
                );
    }

    // whether this creeper is bordering with a creeper
    public boolean isBorderingWithCreeper(){
        return (tile.isNearCreeper());
    }

    // finds free land ready to be claimed (or null if theres no free land)
    public Tile findAvailableLand(int level){
        if(world.tile(x+1, y).canBeClaimedByCreeper(level)) return world.tile(x+1, y);
        if(world.tile(x-1, y).canBeClaimedByCreeper(level)) return world.tile(x-1, y);

        if(world.tile(x, y+1).canBeClaimedByCreeper(level)) return world.tile(x, y+1);
        if(world.tile(x, y-1).canBeClaimedByCreeper(level)) return world.tile(x, y-1);

        return null;
    }

    public void set(int _level){
        level = _level;
        world.tile(x, y).setNet(creeperLevelBlocks.get(_level), Team.crux, Mathf.random(0, 3));
        build = world.tile(x, y).build;
    }
    public void upgrade(int amt){
        if(level+amt >= max_level)
            return;
        set(level+amt);
    }
    public void downgrade(int amt){
        if(level-amt <= 1) {
            remove();
            return;
        }
        set(level-amt);
    }
    public void remove(){
        tile.creeper = null;
    }
}
