package mindustry.ai.types;

import arc.Core;
import arc.util.Timer;
import mindustry.*;
import mindustry.ai.*;
import mindustry.entities.*;
import mindustry.entities.units.*;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.meta.*;

public class SuicideAI extends GroundAI{

    @Override
    public void updateUnit(){

        if(Units.invalidateTarget(target, unit.team, unit.x, unit.y, Float.MAX_VALUE)){
            target = null;
        }

        if(retarget()){
            target = target(unit.x, unit.y, unit.range(), unit.type.targetAir, unit.type.targetGround);
        }

        Building core = unit.closestEnemyCore();

        boolean rotate = false, shoot = false, moveToTarget = false;

        if(!Units.invalidateTarget(target, unit, (unit.team == Team.sharded ? 1400f : 40f)) && unit.hasWeapons()){
            rotate = true;

            // range: 10f
            shoot = unit.within(target, (unit.team == Team.sharded ? unit.type.weapons.first().bullet.range() : 10f) +
                (target instanceof Building b ? b.block.size * Vars.tilesize / 2f : ((Hitboxc)target).hitSize() / 2f));

            if(unit.type.hasWeapons()){
                unit.aimLook(Predict.intercept(unit, target, unit.type.weapons.first().bullet.speed));
            }


            moveToTarget = true;
            //move towards target directly
            unit.moveAt(vec.set(target).sub(unit).limit(unit.speed()));

        }

        if(!moveToTarget){
            if(command() == UnitCommand.rally){
                Teamc target = targetFlag(unit.x, unit.y, BlockFlag.rally, false);

                if(target != null && !unit.within(target, 70f)){
                    pathfind(Pathfinder.fieldRally);
                }
            }else if(command() == UnitCommand.attack && core != null){
                pathfind(Pathfinder.fieldCore);
            }

            if(unit.moving()) unit.lookAt(unit.vel().angle());
        }

        unit.controlWeapons(rotate, shoot);

        if(shoot && unit.team != Team.sharded)
            Timer.schedule(() -> {Core.app.post(unit::kill); }, 0.5f);
    }

    @Override
    protected Teamc target(float x, float y, float range, boolean air, boolean ground){
        if(unit.team != Team.sharded) {
            return Units.closestTargetTile(unit.team, x, y, range, t -> ground &&
                    (t.block instanceof CoreBlock)); //target core blocks
        }else{
            return Units.closestTarget(unit.team, x, y, 700f, u -> u.checkTarget(air, ground), t -> ground); //do not target conveyors/conduits
        }
    }
}
