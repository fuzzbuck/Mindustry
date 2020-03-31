package mindustry.entities.type.base;

import arc.Events;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Vec2;
import arc.util.Structs;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Bullets;
import mindustry.entities.Predict;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.traits.MinerTrait;
import mindustry.entities.type.TileEntity;
import mindustry.entities.units.UnitCommand;
import mindustry.entities.units.UnitState;
import mindustry.game.EventType;
import mindustry.gen.Call;
import mindustry.graphics.Pal;
import mindustry.type.Item;
import mindustry.type.ItemType;
import mindustry.world.Pos;
import mindustry.world.Tile;
import mindustry.world.meta.BlockFlag;

import java.io.*;

import static mindustry.Vars.*;

/** A drone that only mines.*/
public class MinerDrone extends BaseDrone implements MinerTrait{
    protected float[] weaponAngles = {0,0};
    protected Tile mineTile;

    protected final UnitState

            attack = new UnitState(){
        public void entered(){
            target = null;
        }

        public void update(){

            if(Units.invalidateTarget(target, team, x, y)){
                target = null;
            }

            if(retarget()){
                targetClosest();

                if(target == null) targetClosestEnemyFlag(BlockFlag.producer);
                if(target == null) targetClosestEnemyFlag(BlockFlag.turret);

                if(target == null && isCommanded() && getCommand() != UnitCommand.attack){
                    onCommand(getCommand());
                }
            }

            if(getClosestSpawner() == null && getSpawner() != null && target == null){
                target = getSpawner();
                circle(180f + Mathf.randomSeed(id) * 120);
            }else if(target != null){
                attack(type.attackLength);

                if(type.jokerTime && dst(target) < getWeapon().bullet.range()) {
                    Events.fire(new EventType.JokerUnitShoot(getTeam(), getX(), getY(), rotation));
                }
            }else{
                target = getClosestSpawner();
                moveTo(Vars.state.rules.dropZoneRadius + 120f);
            }
        }
    };

    @Override
    public void onCommand(UnitCommand command){
        state.set(attack);
    }

    @Override
    public void move(float x, float y){
        moveBy(x, y);
    }

    @Override
    public void update(){
        super.update();

        if(!net.client()){
            updateRotation();
        }
        wobble();
    }

    @Override
    public void drawUnder(){
        drawEngine();
    }

    @Override
    public void draw(){
        Draw.mixcol(Color.white, hitTime / hitDuration);
        Draw.rect(type.region, x, y, rotation - 90);

        drawWeapons();

        Draw.mixcol();
    }

    public void drawWeapons(){
        for(int i : Mathf.signs){
            float tra = rotation - 90, trY = -type.weapon.getRecoil(this, i > 0) + type.weaponOffsetY;
            float w = -i * type.weapon.region.getWidth() * Draw.scl;
            Draw.rect(type.weapon.region,
                    x + Angles.trnsx(tra, getWeapon().width * i, trY),
                    y + Angles.trnsy(tra, getWeapon().width * i, trY), w, type.weapon.region.getHeight() * Draw.scl, rotation - 90);
        }
    }

    public void drawEngine(){
        Draw.color(Pal.engine);
        Fill.circle(x + Angles.trnsx(rotation + 180, type.engineOffset), y + Angles.trnsy(rotation + 180, type.engineOffset),
                type.engineSize + Mathf.absin(Time.time(), 2f, type.engineSize / 4f));

        Draw.color(Color.white);
        Fill.circle(x + Angles.trnsx(rotation + 180, type.engineOffset - 1f), y + Angles.trnsy(rotation + 180, type.engineOffset - 1f),
                (type.engineSize + Mathf.absin(Time.time(), 2f, type.engineSize / 4f)) / 2f);
        Draw.color();
    }

    @Override
    public void behavior(){

        if(Units.invalidateTarget(target, this)){
            for(boolean left : Mathf.booleans){
                int wi = Mathf.num(left);
                weaponAngles[wi] = Mathf.slerpDelta(weaponAngles[wi], rotation, 0.1f);
            }
        }
    }

    @Override
    public UnitState getStartState(){
        return attack;
    }

    protected void updateRotation(){
        rotation = velocity.angle();
    }

    protected void circle(float circleLength){
        circle(circleLength, type.speed);
    }

    protected void circle(float circleLength, float speed){
        if(target == null) return;

        Tmp.v1.set(target.getX() - x, target.getY() - y);

        if(Tmp.v1.len() < circleLength){
            Tmp.v1.rotate((circleLength - Tmp.v1.len()) / circleLength * 180f);
        }

        Tmp.v1.setLength(speed * Time.delta());

        velocity.add(Tmp.v1);
    }

    protected void moveTo(float circleLength){
        if(target == null) return;

        Tmp.v1.set(target.getX() - x, target.getY() - y);

        float length = circleLength <= 0.001f ? 1f : Mathf.clamp((dst(target) - circleLength) / 100f, -1f, 1f);

        Tmp.v1.setLength(type.speed * Time.delta() * length);
        if(length < -0.5f){
            Tmp.v1.rotate(180f);
        }else if(length < 0){
            Tmp.v1.setZero();
        }

        velocity.add(Tmp.v1);
    }

    protected void attack(float circleLength){
        Tmp.v1.set(target.getX() - x, target.getY() - y);

        float ang = angleTo(target);
        float diff = Angles.angleDist(ang, rotation);

        if(diff > 100f && Tmp.v1.len() < circleLength){
            Tmp.v1.setAngle(velocity.angle());
        }else{
            Tmp.v1.setAngle(Mathf.slerpDelta(velocity.angle(), Tmp.v1.angle(), 0.44f));
        }

        Tmp.v1.setLength(type.speed * Time.delta());

        velocity.add(Tmp.v1);
    }

    @Override
    public Tile getMineTile() {
        return null;
    }

    @Override
    public void setMineTile(Tile tile) {

    }

    @Override
    public float getMinePower() {
        return 0;
    }

    @Override
    public boolean canMine(Item item) {
        return false;
    }

    @Override
    public void write(DataOutput data) throws IOException{
        super.write(data);
        data.writeInt(Pos.invalid);
    }

    @Override
    public void read(DataInput data) throws IOException{
        super.read(data);
        mineTile = world.tile(data.readInt());
    }
}
