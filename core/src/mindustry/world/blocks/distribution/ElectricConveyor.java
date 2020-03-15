package mindustry.world.blocks.distribution;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.util.Log;
import mindustry.entities.type.TileEntity;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.Tile;

import static mindustry.Vars.tilesize;

public class ElectricConveyor extends Conveyor{
    public float maxSpeed = 0f;

    public ElectricConveyor(String name){
        super(name);
        this.consumesPower = true;
        this.outputsPower = true;
    }

    @Override
    public void update(Tile tile){
        ElectricConveyorEntity entity = tile.ent();
        entity.speed = maxSpeed * entity.power.status;
        super.update(tile);
        entity.cons.trigger();
    }

    @Override
    public boolean acceptItem(Item item, Tile tile, Tile source){
        return super.acceptItem(item, tile, source);
    }

    @Override
    public boolean blends(Tile tile, int rotation, int otherx, int othery, int otherrot, Block otherblock) {
        return blendsElectric(tile, rotation, otherx, othery, otherrot, otherblock);
    }


    public static class ElectricConveyorEntity extends ConveyorEntity {
        float speed;
    }

}
