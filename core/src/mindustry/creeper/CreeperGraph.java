package mindustry.creeper;

import arc.Events;
import arc.func.Boolf;
import arc.func.Cons;
import arc.struct.Seq;
import mindustry.game.EventType;
import mindustry.world.Tile;

public class CreeperGraph {
    // all creepers, including "inactive" ones
    public Seq<Creeper> creepers;

    // creepers that are near a border and are updated very frequently
    public Seq<Creeper> activeCreepers;

    public CreeperGraph(){
        Events.on(EventType.CreeperDestroyEvent.class, e -> {
            if(e.tile.creeper == null)
                return;

            if(e.tile.creeper.level > 1) {
                e.tile.creeper.downgrade();
            }else{

                if(creepers.contains(e.tile.creeper))
                    creepers.remove(e.tile.creeper);

                if(activeCreepers.contains(e.tile.creeper))
                    activeCreepers.remove(e.tile.creeper);
            }
        });
    }

    // upgrades existing creeper
    public void addOnto(int amount, int chance, int spawnerx, int spawnery){
        Boolf<Tile> tester = t -> t.isCreeper();
        Cons<Creeper> setter = c -> {
            c.upgrade();
        };

        CreeperUtils.fill(spawnerx, spawnery, tester, setter, amount, chance);
    }

    // creates new creeper
    public void updateBorders(int amount, int chance, int spawnerx, int spawnery){
        Boolf<Tile> tester = t -> !t.solid() && !t.isCreeper() && t.isNearCreeper();
        Cons<Creeper> setter = c -> {
            activeCreepers.add(c);
            creepers.add(c);
            c.set(1);
        };

        CreeperUtils.fill(spawnerx, spawnery, tester, setter, amount, chance);
    }
}
