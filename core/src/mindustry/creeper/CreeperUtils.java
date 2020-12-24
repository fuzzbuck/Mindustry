package mindustry.creeper;

import mindustry.content.Blocks;
import mindustry.game.Team;
import mindustry.world.Block;

import java.util.HashMap;


public class CreeperUtils {
    public static HashMap<Integer, Block> creeperLevelBlocks = new HashMap<>();
    public static HashMap<Block, Integer> creeperBlockLevels = new HashMap<>();

    public static HashMap<Block, CreeperSpawner> creeperSpawnerBlocks = new HashMap<>();
    public static Team creeperTeam = Team.blue;
    public static int max_level = 8;

    public static void init(){
        creeperLevelBlocks.put(1, Blocks.conveyor);
        creeperLevelBlocks.put(2, Blocks.titaniumConveyor);
        creeperLevelBlocks.put(3, Blocks.armoredConveyor);
        creeperLevelBlocks.put(4, Blocks.plastaniumConveyor);
        creeperLevelBlocks.put(5, Blocks.scrapWall);
        creeperLevelBlocks.put(6, Blocks.titaniumWall);
        creeperLevelBlocks.put(7, Blocks.thoriumWall);
        creeperLevelBlocks.put(8, Blocks.plastaniumWall);

        for(var kvp : creeperLevelBlocks.entrySet()){
            creeperBlockLevels.put(kvp.getValue(), kvp.getKey());
        }

        creeperSpawnerBlocks.put(Blocks.groundFactory, new CreeperSpawner(5, 1f));
        creeperSpawnerBlocks.put(Blocks.airFactory, new CreeperSpawner(1, 0.1f));
        creeperSpawnerBlocks.put(Blocks.navalFactory, new CreeperSpawner(15, 5f));
        creeperSpawnerBlocks.put(Blocks.additiveReconstructor, new CreeperSpawner(8, 1f));
        creeperSpawnerBlocks.put(Blocks.multiplicativeReconstructor, new CreeperSpawner(5, 0.5f));
        creeperSpawnerBlocks.put(Blocks.exponentialReconstructor, new CreeperSpawner(15, 0.5f));
        creeperSpawnerBlocks.put(Blocks.tetrativeReconstructor, new CreeperSpawner(15, 0.2f));
    }
}
