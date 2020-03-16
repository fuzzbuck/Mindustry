package mindustry.entities.type.base;

import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.math.geom.Intersector;
import arc.math.geom.Vec2;
import arc.util.Time;
import mindustry.Vars;
import mindustry.ai.Pathfinder.PathTarget;
import mindustry.content.Fx;
import mindustry.entities.Effects;
import mindustry.entities.Predict;
import mindustry.entities.Units;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.traits.AbsorbTrait;
import mindustry.entities.type.BaseUnit;
import mindustry.entities.type.TileEntity;
import mindustry.entities.type.Unit;
import mindustry.entities.units.UnitCommand;
import mindustry.entities.units.UnitState;
import mindustry.game.Team;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.world.Tile;
import mindustry.world.blocks.Floor;
import mindustry.world.blocks.defense.ForceProjector;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.pathfinder;
import static mindustry.Vars.world;

public class GroundHealingUnit extends GroundUnit{
    public Unit target;
    public float radius = 75f;
    public float repairSpeed = 0.3f;
    public float strength = 0f;
    @Override
    public void update(){
        super.update();

        if(target != null && (target.isDead() || target.dst(lastPosition()) > radius || target.health >= target.maxHealth())){
            target = null;
        }else if(target != null){
            target.health += repairSpeed * Time.delta() * strength;
            target.clampHealth();
        }

        if(target != null){
            strength = Mathf.lerpDelta(strength, 1f, 0.08f * Time.delta());
        }else{
            strength = Mathf.lerpDelta(strength, 0f, 0.07f * Time.delta());
        }

        if(timer.get(timerTarget, 20)){
            target = Units.closest(getTeam(), x, y, radius,
                    unit -> unit.health < unit.maxHealth());
        }
    }

}
