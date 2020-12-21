package mindustry.creeper;

import arc.Events;
import arc.func.Boolf;
import arc.func.Cons;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.StaticWall;

import java.util.HashMap;

public class CreeperGraph {
    // all creepers
    public HashMap<Point2, Creeper> creepers = new HashMap();

    // creepers that are near a border to an empty (no creeper) tile
    public HashMap<Point2, Creeper> activeCreepers = new HashMap();

    public CreeperGraph(){
        Events.on(EventType.CreeperDestroyEvent.class, e -> {
            if(e.tile.creeper == null)
                return;

            if(e.tile.creeper.level > 1) {
                e.tile.creeper.downgrade(1);
            }else{
                Point2 point = new Point2(e.tile.x, e.tile.y);
                if(creepers.containsValue(e.tile.creeper))
                    creepers.remove(point);

                if(activeCreepers.containsValue(e.tile.creeper))
                    activeCreepers.remove(point);
            }
        });
    }

    public Creeper addCreeper(Point2 point, int amt){
        if(creepers.containsKey(point)){
            creepers.get(point).upgrade(amt);
        }else{
            creepers.put(point, new Creeper(point.x, point.y, amt));
        }
        return creepers.get(point);
    }

    public void putCreeper(int x, int y, int amt, int radius, int chanceSpread){
        Geometry.circle(x, y, radius, (cx, cy) -> {
            if(Mathf.random(0, 100) > chanceSpread)
                return;

            Tile t = Vars.world.tile(cx, cy);
            if(t.block() instanceof StaticWall && (t.build != null && !t.isCreeper()))
                return;

            Point2 point = new Point2(cx, cy);
            Creeper c = addCreeper(point, amt);

            if(c.isBorderingWithLand()) {
                activeCreepers.put(point, c);
            }else{
                if(activeCreepers.containsKey(point)){
                    activeCreepers.remove(point);
                }
            }
        });
    }

    public void updateBorders(int amt){
        for(Creeper c : activeCreepers.values()){
            if(amt <= 0)
                return;

            Tile t = c.findAvailableLand();
            if(t != null){
                amt--;
                addCreeper(new Point2(t.x, t.y), c.level == 1 ? 1 : c.level -1);
            }

            // check if this should still be active
            if(!c.isBorderingWithLand())
                activeCreepers.remove(new Point2(c.x, c.y));
        }
    }

}
