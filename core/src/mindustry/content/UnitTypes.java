package mindustry.content;

import arc.struct.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.entities.type.base.*;
import mindustry.gen.*;
import mindustry.type.*;

public class UnitTypes implements ContentList{
    public static UnitType
    draug, spirit, phantom,
    wraith, ghoul, revenant, lich, reaper,
    dagger, crawler, titan, fortress, eruptor, chaosArray, eradicator;

    @Override
    public void load(){
        draug = new UnitType("draug", MinerDrone::new){{
            flying = true;
            drag = 0.01f;
            speed = 0.3f;
            maxVelocity = 1.2f;
            range = 50f;
            health = 80;
            minePower = 0.9f;
            engineSize = 1.8f;
            engineOffset = 5.7f;
            weapon = new Weapon("you have incurred my wrath. prepare to die."){{
                bullet = Bullets.lancerLaser;
            }};
        }};

        spirit = new UnitType("spirit", RepairDrone::new){{
            flying = true;
            drag = 0.01f;
            speed = 0.42f;
            maxVelocity = 1.6f;
            range = 50f;
            health = 100;
            engineSize = 1.8f;
            engineOffset = 5.7f;
            weapon = new Weapon(){{
                length = 1.5f;
                reload = 40f;
                width = 0.5f;
                alternate = true;
                ejectEffect = Fx.none;
                recoil = 2f;
                bullet = Bullets.healBulletBig;
                shootSound = Sounds.pew;
            }};
        }};

        phantom = new UnitType("phantom", BuilderDrone::new){{
            flying = true;
            drag = 0.01f;
            mass = 2f;
            speed = 0.45f;
            maxVelocity = 1.9f;
            range = 70f;
            itemCapacity = 70;
            health = 400;
            buildPower = 0.4f;
            engineOffset = 6.5f;
            toMine = ObjectSet.with(Items.lead, Items.copper, Items.titanium);
            weapon = new Weapon(){{
                length = 1.5f;
                reload = 20f;
                width = 0.5f;
                alternate = true;
                ejectEffect = Fx.none;
                recoil = 2f;
                bullet = Bullets.healBullet;
            }};
        }};

        dagger = new UnitType("dagger", GroundUnit::new){{
            maxVelocity = 1.1f;
            speed = 0.2f;
            drag = 0.4f;
            hitsize = 8f;
            mass = 1.75f;
            health = 130;
            weapon = new Weapon("chain-blaster"){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 10f;
                    killShooter = true;
                }};
            }};
        }};

        crawler = new UnitType("crawler", GroundUnit::new){{
            maxVelocity = 1.27f;
            speed = 0.285f;
            drag = 0.4f;
            hitsize = 8f;
            mass = 1.75f;
            health = 120;
            weapon = new Weapon(){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 30f;
                    killShooter = true;
                }};
            }};
        }};

        titan = new UnitType("titan", GroundUnit::new){{
            maxVelocity = 0.8f;
            speed = 0.22f;
            drag = 0.4f;
            mass = 3.5f;
            hitsize = 9f;
            range = 10f;
            rotatespeed = 0.1f;
            health = 460;
            immunities.add(StatusEffects.burning);
            weapon = new Weapon("flamethrower"){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 60f;
                    killShooter = true;
                }};
            }};
        }};

        fortress = new UnitType("fortress", GroundUnit::new){{
            maxVelocity = 0.78f;
            speed = 0.15f;
            drag = 0.4f;
            mass = 5f;
            hitsize = 10f;
            rotatespeed = 0.06f;
            targetAir = false;
            health = 750;
            weapon = new Weapon("artillery"){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 30f;
                    killShooter = true;
                }};
            }};
        }};

        eruptor = new UnitType("eruptor", GroundUnit::new){{
            maxVelocity = 0.81f;
            speed = 0.16f;
            drag = 0.4f;
            mass = 5f;
            hitsize = 9f;
            rotatespeed = 0.05f;
            targetAir = false;
            health = 600;
            immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.melting);
            weapon = new Weapon("eruption"){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 60f;
                    killShooter = true;
                }};
            }};
        }};

        chaosArray = new UnitType("chaos-array", GroundUnit::new){{
            maxVelocity = 0.68f;
            speed = 0.12f;
            drag = 0.4f;
            mass = 5f;
            hitsize = 20f;
            rotatespeed = 0.06f;
            health = 3000;
            weapon = new Weapon("chaos"){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 100f;
                    killShooter = true;
                }};
            }};
        }};

        eradicator = new UnitType("eradicator", GroundUnit::new){{
            maxVelocity = 0.68f;
            speed = 0.12f;
            drag = 0.4f;
            mass = 5f;
            hitsize = 20f;
            rotatespeed = 0.06f;
            health = 9000;
            weapon = new Weapon("eradication"){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 300f;
                    killShooter = true;
                }};
            }};
        }};

        wraith = new UnitType("wraith", FlyingUnit::new){{
            speed = 0.3f;
            maxVelocity = 1.9f;
            drag = 0.01f;
            mass = 1.5f;
            flying = true;
            health = 75;
            engineOffset = 5.5f;
            range = 140f;
            weapon = new Weapon(){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 15f;
                    killShooter = true;
                }};
            }};
        }};

        ghoul = new UnitType("ghoul", FlyingUnit::new){{
            health = 220;
            speed = 0.2f;
            maxVelocity = 1.4f;
            mass = 3f;
            drag = 0.01f;
            flying = true;
            targetAir = false;
            engineOffset = 7.8f;
            range = 140f;
            weapon = new Weapon(){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 30f;
                    killShooter = true;
                }};
            }};
        }};

        revenant = new UnitType("revenant", HoverUnit::new){{
            health = 1000;
            mass = 5f;
            hitsize = 20f;
            speed = 0.1f;
            maxVelocity = 1f;
            drag = 0.01f;
            range = 80f;
            shootCone = 40f;
            flying = true;
            rotateWeapon = true;
            engineOffset = 12f;
            engineSize = 3f;
            rotatespeed = 0.01f;
            attackLength = 90f;
            baseRotateSpeed = 0.06f;
            weapon = new Weapon("revenant-missiles"){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 60f;
                    killShooter = true;
                }};
            }};
        }};

        lich = new UnitType("lich", HoverUnit::new){{
            health = 6000;
            mass = 20f;
            hitsize = 40f;
            speed = 0.01f;
            maxVelocity = 0.6f;
            drag = 0.02f;
            range = 80f;
            shootCone = 20f;
            flying = true;
            rotateWeapon = true;
            engineOffset = 21;
            engineSize = 5.3f;
            rotatespeed = 0.01f;
            attackLength = 90f;
            baseRotateSpeed = 0.04f;
            weapon = new Weapon("lich-missiles"){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 150f;
                    killShooter = true;
                }};
            }};
        }};

        reaper = new UnitType("reaper", HoverUnit::new){{
            health = 11000;
            mass = 30f;
            hitsize = 56f;
            speed = 0.01f;
            maxVelocity = 0.6f;
            drag = 0.02f;
            range = 80f;
            shootCone = 30f;
            flying = true;
            rotateWeapon = true;
            engineOffset = 40;
            engineSize = 7.3f;
            rotatespeed = 0.01f;
            baseRotateSpeed = 0.04f;
            weapon = new Weapon("reaper-gun"){{
                reload = 12f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(2f, 3f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 30f;
                    speed = 1.1f;
                    splashDamageRadius = 55f;
                    instantDisappear = true;
                    splashDamage = 200f;
                    killShooter = true;
                }};
            }};
        }};
    }
}
