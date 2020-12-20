package mindustry.creeper;

import arc.func.Boolf;
import arc.func.Cons;
import arc.struct.Seq;
import mindustry.world.Tile;

public class CreeperGraph {
    // all creepers, including "inactive" ones
    public Seq<Creeper> creepers;

    // creepers that are near a border and are updated very frequently
    public Seq<Creeper> activeCreepers;

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
