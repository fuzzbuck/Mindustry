package mindustry.entities.traits;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.math.geom.Position;
import mindustry.game.Team;

/**
 * Base interface for targetable entities.
 */
public interface TargetTrait extends Position, VelocityTrait{

    TextureRegion laser = Core.atlas.find("laser");
    TextureRegion laserEnd = Core.atlas.find("laser-end");

    boolean isDead();

    Team getTeam();

    default float getTargetVelocityX(){
        if(this instanceof SolidTrait){
            return ((SolidTrait)this).getDeltaX();
        }
        return velocity().x;
    }

    default float getTargetVelocityY(){
        if(this instanceof SolidTrait){
            return ((SolidTrait)this).getDeltaY();
        }
        return velocity().y;
    }

    /**
     * Whether this entity is a valid target.
     */
    default boolean isValid(){
        return !isDead();
    }
}
