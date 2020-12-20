package mindustry.creeper;

import arc.Events;
import arc.func.Boolf;
import arc.func.Cons;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.struct.IntSeq;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.editor.MapEditor;
import mindustry.game.EventType;
import mindustry.world.Block;
import mindustry.world.Tile;

import java.util.HashMap;

import static mindustry.Vars.world;

public class CreeperUtils {
    public static HashMap<Integer, Block> creeperLevelBlocks = new HashMap<>();
    public static int max_level = 9;

    public static void init(){
        creeperLevelBlocks.put(1, Blocks.conveyor);
        creeperLevelBlocks.put(2, Blocks.titaniumConveyor);
        creeperLevelBlocks.put(3, Blocks.armoredConveyor);
        creeperLevelBlocks.put(4, Blocks.plastaniumConveyor);
        creeperLevelBlocks.put(5, Blocks.scrapWall);
        creeperLevelBlocks.put(6, Blocks.titaniumWall);
        creeperLevelBlocks.put(7, Blocks.thoriumWall);
        creeperLevelBlocks.put(8, Blocks.plastaniumWall);
        creeperLevelBlocks.put(9, Blocks.scrapWall);

        Events.on(EventType.CreeperDestroyEvent.class, e -> {
            if(e.tile.creeper == null)
                return;
            if(e.tile.creeper.level > 1)
                e.tile.creeper.downgrade();
        });
    }

    public static boolean isCreeper(Block block){
        return creeperLevelBlocks.containsValue(block);
    }

    public static void fill(int x, int y, Boolf<Tile> tester, Cons<Creeper> filler, int amount, int spawnpercent){

        int maxamount = amount;
        int width = world.width(), height = world.height();
        int x1;

        IntSeq stack = new IntSeq();
        stack.add(Point2.pack(x, y));

        try{
            while(stack.size > 0 && stack.size < width*height){
                int popped = stack.pop();
                x = Point2.x(popped);
                y = Point2.y(popped);

                x1 = x;
                while(x1 >= 0 && tester.get(world.tile(x1, y))) x1--;
                x1++;
                boolean spanAbove = false, spanBelow = false;
                amount = maxamount;

                while(x1 < width && tester.get(world.tile(x1, y)) && amount > 0){
                    if(Mathf.random(0, 100) <= spawnpercent) {
                        amount--;
                        filler.get(new Creeper(world.tile(x1, y)));
                    }

                    if(!spanAbove && y > 0 && tester.get(world.tile(x1, y - 1))){
                        stack.add(Point2.pack(x1, y - 1));
                        spanAbove = true;
                    }else if(spanAbove && !tester.get(world.tile(x1, y - 1))){
                        spanAbove = false;
                    }

                    if(!spanBelow && y < height - 1 && tester.get(world.tile(x1, y + 1))){
                        stack.add(Point2.pack(x1, y + 1));
                        spanBelow = true;
                    }else if(spanBelow && y < height - 1 && !tester.get(world.tile(x1, y + 1))){
                        spanBelow = false;
                    }
                    x1++;
                }
            }
            stack.clear();
        }catch(OutOfMemoryError e){
            System.gc();
            e.printStackTrace();
        }
    }
}
