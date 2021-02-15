package mindustry.content;

import arc.graphics.*;
import arc.struct.*;
import mindustry.ai.types.*;
import mindustry.annotations.Annotations.*;
import mindustry.ctype.*;
import mindustry.entities.abilities.*;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;

public class UnitTypes implements ContentList{
    //region definitions

    //(the wall of shame - should fix the legacy stuff eventually...)

    //mech
    public static @EntityDef({Unitc.class, Mechc.class}) UnitType mace, dagger, crawler, fortress, scepter, reign;

    //mech
    public static @EntityDef(value = {Unitc.class, Mechc.class}, legacy = true) UnitType nova, pulsar, quasar;

    //mech
    public static @EntityDef({Unitc.class, Mechc.class}) UnitType vela;

    //legs
    public static @EntityDef({Unitc.class, Legsc.class}) UnitType corvus, atrax;

    //legs
    public static @EntityDef(value = {Unitc.class, Legsc.class}, legacy = true) UnitType spiroct, arkyid, toxopid;

    //air
    public static @EntityDef({Unitc.class}) UnitType flare, eclipse, horizon, zenith, antumbra;

    //air
    public static @EntityDef(value = {Unitc.class}, legacy = true) UnitType mono;

    //air
    public static @EntityDef(value = {Unitc.class}, legacy = true) UnitType poly;

    //air + payload
    public static @EntityDef({Unitc.class, Payloadc.class}) UnitType mega;

    //air + payload
    public static @EntityDef(value = {Unitc.class, Payloadc.class}, legacy = true) UnitType quad;

    //air + payload + ammo distribution
    public static @EntityDef({Unitc.class, Payloadc.class, AmmoDistributec.class}) UnitType oct;

    //air
    public static @EntityDef(value = {Unitc.class}, legacy = true) UnitType alpha, beta, gamma;

    //water
    public static @EntityDef({Unitc.class, WaterMovec.class}) UnitType risso, minke, bryde, sei, omura;

    //special block unit type
    public static @EntityDef({Unitc.class, BlockUnitc.class}) UnitType block;

    //endregion

    @Override
    public void load(){
        //region ground attack

        dagger = new UnitType("dagger"){{
            speed = 0.5f;
            hitSize = 8f;
            health = 140;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        mace = new UnitType("mace"){{
            speed = 0.4f;
            hitSize = 9f;
            health = 500;
            armor = 4f;

            immunities.add(StatusEffects.burning);

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        fortress = new UnitType("fortress"){{
            speed = 0.39f;
            hitSize = 13f;
            rotateSpeed = 3f;
            targetAir = false;
            health = 790;
            armor = 9f;
            mechFrontSway = 0.55f;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        scepter = new UnitType("scepter"){{
            speed = 0.35f;
            hitSize = 20f;
            rotateSpeed = 2.1f;
            health = 9000;
            armor = 11f;
            canDrown = false;
            mechFrontSway = 1f;

            mechStepParticles = true;
            mechStepShake = 0.15f;
            singleTarget = true;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        reign = new UnitType("reign"){{
            speed = 0.35f;
            hitSize = 26f;
            rotateSpeed = 1.65f;
            health = 24000;
            armor = 14f;
            mechStepParticles = true;
            mechStepShake = 0.75f;
            canDrown = false;
            mechFrontSway = 1.9f;
            mechSideSway = 0.6f;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        //endregion
        //region ground support

        nova = new UnitType("nova"){{
            canBoost = true;
            boostMultiplier = 1.5f;
            speed = 0.55f;
            hitSize = 8f;
            health = 120f;
            buildSpeed = 0.8f;
            armor = 1f;
            commandLimit = 8;

            abilities.add(new RepairFieldAbility(10f, 60f * 4, 60f));
            ammoType = AmmoTypes.power;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                recoil = 2f;
                shootSound = Sounds.lasershoot;

                bullet = new LaserBoltBulletType(5.2f, 14){{
                    lifetime = 37f;
                    healPercent = 5f;
                    collidesTeam = true;
                    backColor = Pal.heal;
                    frontColor = Color.white;
                }};
            }});
        }};

        pulsar = new UnitType("pulsar"){{
            canBoost = true;
            boostMultiplier = 1.6f;
            speed = 0.7f;
            hitSize = 10f;
            health = 320f;
            buildSpeed = 0.9f;
            armor = 4f;

            mineTier = 2;
            mineSpeed = 5f;
            commandLimit = 9;

            abilities.add(new ShieldRegenFieldAbility(20f, 40f, 60f * 5, 60f));
            ammoType = AmmoTypes.power;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        quasar = new UnitType("quasar"){{
            mineTier = 3;
            hitSize = 12f;
            boostMultiplier = 2f;
            health = 650f;
            buildSpeed = 1.7f;
            canBoost = true;
            armor = 9f;
            landShake = 2f;

            commandLimit = 10;
            mechFrontSway = 0.55f;
            ammoType = AmmoTypes.power;

            speed = 0.4f;
            hitSize = 10f;

            mineSpeed = 6f;
            drawShields = false;

            abilities.add(new ForceFieldAbility(60f, 0.3f, 400f, 60f * 6));

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        vela = new UnitType("vela"){{
            hitSize = 23f;

            rotateSpeed = 1.6f;
            canDrown = false;
            mechFrontSway = 1f;

            mechStepParticles = true;
            mechStepShake = 0.15f;
            ammoType = AmmoTypes.powerHigh;

            speed = 0.35f;
            boostMultiplier = 2.1f;
            engineOffset = 12f;
            engineSize = 6f;
            lowAltitude = true;

            health = 7000f;
            armor = 7f;
            canBoost = true;
            landShake = 4f;
            immunities = ObjectSet.with(StatusEffects.burning);

            commandLimit = 8;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        corvus = new UnitType("corvus"){{
            mineTier = 1;
            hitSize = 29f;
            health = 18000f;
            armor = 9f;
            landShake = 1.5f;
            rotateSpeed = 1.5f;

            commandLimit = 8;

            legCount = 4;
            legLength = 14f;
            legBaseOffset = 11f;
            legMoveSpace = 1.5f;
            legTrns = 0.58f;
            hovering = true;
            visualElevation = 0.2f;
            allowLegStep = true;
            ammoType = AmmoTypes.powerHigh;
            groundLayer = Layer.legUnit;

            speed = 0.3f;

            mineTier = 2;
            mineSpeed = 7f;
            drawShields = false;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        //endregion
        //region ground legs

        crawler = new UnitType("crawler"){{
            defaultController = SuicideAI::new;

            speed = 1f;
            hitSize = 8f;
            health = 180;
            mechSideSway = 0.25f;
            range = 40f;

            weapons.add(new Weapon(){{
                reload = 24f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        atrax = new UnitType("atrax"){{
            speed = 0.5f;
            drag = 0.4f;
            hitSize = 10f;
            rotateSpeed = 3f;
            targetAir = false;
            health = 600;
            immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.melting);

            legCount = 4;
            legLength = 9f;
            legTrns = 0.6f;
            legMoveSpace = 1.4f;
            hovering = true;
            armor = 3f;

            allowLegStep = true;
            visualElevation = 0.2f;
            groundLayer = Layer.legUnit - 1f;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        spiroct = new UnitType("spiroct"){{
            speed = 0.4f;
            drag = 0.4f;
            hitSize = 12f;
            rotateSpeed = 3f;
            health = 900;
            immunities = ObjectSet.with(StatusEffects.burning, StatusEffects.melting);
            legCount = 6;
            legLength = 13f;
            legTrns = 0.8f;
            legMoveSpace = 1.4f;
            legBaseOffset = 2f;
            hovering = true;
            armor = 5f;
            ammoType = AmmoTypes.power;

            buildSpeed = 0.75f;

            allowLegStep = true;
            visualElevation = 0.3f;
            groundLayer = Layer.legUnit;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                recoil = 2f;
                rotate = true;
                shootSound = Sounds.sap;

                x = 8.5f;
                y = -1.5f;

                bullet = new SapBulletType(){{
                    sapStrength = 0.4f;
                    length = 75f;
                    damage = 20;
                    shootEffect = Fx.shootSmall;
                    hitColor = color = Color.valueOf("bf92f9");
                    despawnEffect = Fx.none;
                    width = 0.54f;
                    lifetime = 35f;
                    knockback = -1.24f;
                }};
            }});

            weapons.add(new Weapon("mount-purple-weapon"){{
                reload = 20f;
                rotate = true;
                x = 4f;
                y = 3f;
                shootSound = Sounds.sap;

                bullet = new SapBulletType(){{
                    sapStrength = 0.8f;
                    length = 40f;
                    damage = 16;
                    shootEffect = Fx.shootSmall;
                    hitColor = color = Color.valueOf("bf92f9");
                    despawnEffect = Fx.none;
                    width = 0.4f;
                    lifetime = 25f;
                    knockback = -0.65f;
                }};
            }});
        }};

        arkyid = new UnitType("arkyid"){{
            drag = 0.1f;
            speed = 0.5f;
            hitSize = 21f;
            health = 8000;
            armor = 6f;

            rotateSpeed = 2.7f;

            legCount = 6;
            legMoveSpace = 1f;
            legPairOffset = 3;
            legLength = 30f;
            legExtension = -15;
            legBaseOffset = 10f;
            landShake = 1f;
            legSpeed = 0.1f;
            legLengthScl = 0.96f;
            rippleScale = 2f;
            legSpeed = 0.2f;
            ammoType = AmmoTypes.power;
            buildSpeed = 1f;

            legSplashDamage = 32;
            legSplashRange = 30;

            hovering = true;
            allowLegStep = true;
            visualElevation = 0.65f;
            groundLayer = Layer.legUnit;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        toxopid = new UnitType("toxopid"){{
            drag = 0.1f;
            speed = 0.5f;
            hitSize = 21f;
            health = 22000;
            armor = 13f;

            rotateSpeed = 1.9f;

            legCount = 8;
            legMoveSpace = 0.8f;
            legPairOffset = 3;
            legLength = 75f;
            legExtension = -20;
            legBaseOffset = 8f;
            landShake = 1f;
            legSpeed = 0.1f;
            legLengthScl = 0.93f;
            rippleScale = 3f;
            legSpeed = 0.19f;
            ammoType = AmmoTypes.powerHigh;
            buildSpeed = 1f;

            legSplashDamage = 80;
            legSplashRange = 60;

            hovering = true;
            allowLegStep = true;
            visualElevation = 0.95f;
            groundLayer = Layer.legUnit;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        //endregion
        //region air attack

        flare = new UnitType("flare"){{
            speed = 3f;
            accel = 0.08f;
            drag = 0.01f;
            flying = true;
            health = 75;
            engineOffset = 5.5f;
            range = 140f;
            targetAir = false;
            commandLimit = 4;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        horizon = new UnitType("horizon"){{
            health = 340;
            speed = 1.7f;
            accel = 0.08f;
            drag = 0.016f;
            flying = true;
            hitSize = 9f;
            targetAir = false;
            engineOffset = 7.8f;
            range = 140f;
            faceTarget = false;
            armor = 3f;
            targetFlag = BlockFlag.factory;
            commandLimit = 5;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        zenith = new UnitType("zenith"){{
            health = 700;
            speed = 1.8f;
            accel = 0.04f;
            drag = 0.016f;
            flying = true;
            range = 140f;
            hitSize = 20f;
            lowAltitude = true;
            armor = 5f;

            engineOffset = 12f;
            engineSize = 3f;

            weapons.add(new Weapon("zenith-missiles"){{
                reload = 40f;
                x = 7f;
                rotate = true;
                shake = 1f;
                shots = 2;
                inaccuracy = 5f;
                velocityRnd = 0.2f;
                shootSound = Sounds.missile;

                bullet = new MissileBulletType(3f, 14){{
                    width = 8f;
                    height = 8f;
                    shrinkY = 0f;
                    drag = -0.003f;
                    homingRange = 60f;
                    keepVelocity = false;
                    splashDamageRadius = 25f;
                    splashDamage = 16f;
                    lifetime = 60f;
                    trailColor = Pal.unitBack;
                    backColor = Pal.unitBack;
                    frontColor = Pal.unitFront;
                    hitEffect = Fx.blastExplosion;
                    despawnEffect = Fx.blastExplosion;
                    weaveScale = 6f;
                    weaveMag = 1f;
                }};
            }});
        }};

        antumbra = new UnitType("antumbra"){{
            speed = 0.8f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 1.9f;
            flying = true;
            lowAltitude = true;
            health = 7000;
            armor = 9f;
            engineOffset = 21;
            engineSize = 5.3f;
            hitSize = 56f;
            targetFlag = BlockFlag.battery;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        eclipse = new UnitType("eclipse"){{
            speed = 0.52f;
            accel = 0.04f;
            drag = 0.04f;
            rotateSpeed = 1f;
            flying = true;
            lowAltitude = true;
            health = 20000;
            engineOffset = 38;
            engineSize = 7.3f;
            hitSize = 58f;
            destructibleWreck = false;
            armor = 13f;
            targetFlag = BlockFlag.reactor;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        //endregion
        //region air support

        mono = new UnitType("mono"){{
            defaultController = MinerAI::new;

            flying = true;
            drag = 0.06f;
            accel = 0.12f;
            speed = 1.5f;
            health = 100;
            engineSize = 1.8f;
            engineOffset = 5.7f;
            range = 50f;
            isCounted = false;

            ammoType = AmmoTypes.powerLow;

            mineTier = 1;
            mineSpeed = 2.5f;
        }};

        poly = new UnitType("poly"){{
            defaultController = BuilderAI::new;

            flying = true;
            drag = 0.05f;
            speed = 2.6f;
            rotateSpeed = 15f;
            accel = 0.1f;
            range = 130f;
            health = 400;
            buildSpeed = 0.5f;
            engineOffset = 6.5f;
            hitSize = 8f;
            lowAltitude = true;

            ammoType = AmmoTypes.power;

            mineTier = 2;
            mineSpeed = 3.5f;

            abilities.add(new RepairFieldAbility(5f, 60f * 5, 50f));

            weapons.add(new Weapon("heal-weapon-mount"){{
                top = false;
                y = -2.5f;
                x = 3.5f;
                reload = 30f;
                ejectEffect = Fx.none;
                recoil = 2f;
                shootSound = Sounds.missile;
                shots = 1;
                velocityRnd = 0.5f;
                inaccuracy = 15f;
                alternate = true;

                bullet = new MissileBulletType(4f, 12){{
                    homingPower = 0.08f;
                    weaveMag = 4;
                    weaveScale = 4;
                    lifetime = 56f;
                    keepVelocity = false;
                    shootEffect = Fx.shootHeal;
                    smokeEffect = Fx.hitLaser;
                    hitEffect = despawnEffect = Fx.hitLaser;
                    frontColor = Color.white;
                    hitSound = Sounds.none;

                    healPercent = 5.5f;
                    collidesTeam = true;
                    backColor = Pal.heal;
                    trailColor = Pal.heal;
                }};
            }});
        }};

        mega = new UnitType("mega"){{
            defaultController = RepairAI::new;

            mineTier = 3;
            mineSpeed = 4f;
            health = 460;
            armor = 3f;
            speed = 2.5f;
            accel = 0.06f;
            drag = 0.017f;
            lowAltitude = true;
            flying = true;
            engineOffset = 10.5f;
            rotateShooting = false;
            hitSize = 15f;
            engineSize = 3f;
            payloadCapacity = (2 * 2) * tilePayload;
            buildSpeed = 2.6f;
            isCounted = false;

            ammoType = AmmoTypes.power;

            weapons.add(
            new Weapon("heal-weapon-mount"){{
                shootSound = Sounds.lasershoot;
                reload = 25f;
                x = 8f;
                y = -6f;
                rotate = true;
                bullet = new LaserBoltBulletType(5.2f, 10){{
                    lifetime = 35f;
                    healPercent = 5.5f;
                    collidesTeam = true;
                    backColor = Pal.heal;
                    frontColor = Color.white;
                }};
            }},
            new Weapon("heal-weapon-mount"){{
                shootSound = Sounds.lasershoot;
                reload = 15f;
                x = 4f;
                y = 5f;
                rotate = true;
                bullet = new LaserBoltBulletType(5.2f, 8){{
                    lifetime = 35f;
                    healPercent = 3f;
                    collidesTeam = true;
                    backColor = Pal.heal;
                    frontColor = Color.white;
                }};
            }});
        }};

        quad = new UnitType("quad"){{
            armor = 8f;
            health = 6000;
            speed = 1.4f;
            rotateSpeed = 2f;
            accel = 0.05f;
            drag = 0.017f;
            lowAltitude = false;
            flying = true;
            engineOffset = 12f;
            engineSize = 6f;
            rotateShooting = false;
            hitSize = 32f;
            payloadCapacity = (3 * 3) * tilePayload;
            buildSpeed = 2.5f;
            range = 140f;
            targetAir = false;
            targetFlag = BlockFlag.battery;

            ammoType = AmmoTypes.powerHigh;

            weapons.add(
            new Weapon(){{
                x = y = 0f;
                mirror = false;
                reload = 55f;
                minShootVelocity = 0.01f;

                soundPitchMin = 1f;
                shootSound = Sounds.plasmadrop;

                bullet = new BasicBulletType(){{
                    sprite = "large-bomb";
                    width = height = 120/4f;

                    maxRange = 30f;
                    ignoreRotation = true;

                    backColor = Pal.heal;
                    frontColor = Color.white;
                    mixColorTo = Color.white;

                    hitSound = Sounds.plasmaboom;

                    shootCone = 180f;
                    ejectEffect = Fx.none;
                    despawnShake = 4f;

                    collidesAir = false;

                    lifetime = 70f;

                    despawnEffect = Fx.greenBomb;
                    hitEffect = Fx.massiveExplosion;
                    keepVelocity = false;
                    spin = 2f;

                    shrinkX = shrinkY = 0.7f;

                    speed = 0.001f;
                    collides = false;

                    healPercent = 15f;
                    splashDamage = 230f;
                    splashDamageRadius = 120f;
                }};
            }});
        }};

        oct = new UnitType("oct"){{
            armor = 16f;
            health = 24000;
            speed = 0.8f;
            rotateSpeed = 1f;
            accel = 0.04f;
            drag = 0.018f;
            flying = true;
            engineOffset = 46f;
            engineSize = 7.8f;
            rotateShooting = false;
            hitSize = 60f;
            payloadCapacity = (5.3f * 5.3f) * tilePayload;
            buildSpeed = 4f;
            drawShields = false;
            commandLimit = 6;
            lowAltitude = true;

            ammoCapacity = 1300;
            ammoResupplyAmount = 20;

            abilities.add(new ForceFieldAbility(140f, 4f, 7000f, 60f * 8), new RepairFieldAbility(130f, 60f * 2, 140f));

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        //endregion
        //region naval attack

        risso = new UnitType("risso"){{
            speed = 1.1f;
            drag = 0.13f;
            hitSize = 9f;
            health = 280;
            accel = 0.4f;
            rotateSpeed = 3.3f;
            trailLength = 20;
            rotateShooting = false;

            armor = 2f;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        minke = new UnitType("minke"){{
            health = 600;
            speed = 0.9f;
            drag = 0.15f;
            hitSize = 11f;
            armor = 4f;
            accel = 0.3f;
            rotateSpeed = 2.6f;
            rotateShooting = false;

            trailLength = 20;
            trailX = 5.5f;
            trailY = -4f;
            trailScl = 1.9f;

            abilities.add(new StatusFieldAbility(StatusEffects.overclock, 60f * 6, 60f * 6f, 60f));

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        bryde = new UnitType("bryde"){{
            health = 900;
            speed = 0.85f;
            accel = 0.2f;
            rotateSpeed = 1.8f;
            drag = 0.17f;
            hitSize = 16f;
            armor = 7f;
            rotateShooting = false;

            trailLength = 22;
            trailX = 7f;
            trailY = -9f;
            trailScl = 1.5f;

            abilities.add(new ShieldRegenFieldAbility(20f, 40f, 60f * 4, 60f));

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        sei = new UnitType("sei"){{
            health = 10000;
            armor = 12f;

            speed = 0.73f;
            drag = 0.17f;
            hitSize = 39f;
            accel = 0.2f;
            rotateSpeed = 1.3f;
            rotateShooting = false;

            trailLength = 50;
            trailX = 18f;
            trailY = -21f;
            trailScl = 3f;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shake = 3f;
                shootSound = Sounds.missile;
                xRand = 8f;
                shotDelay = 1f;

                bullet = new MissileBulletType(4.2f, 42){{
                    homingPower = 0.12f;
                    width = 8f;
                    height = 8f;
                    shrinkX = shrinkY = 0f;
                    drag = -0.003f;
                    homingRange = 80f;
                    keepVelocity = false;
                    splashDamageRadius = 35f;
                    splashDamage = 45f;
                    lifetime = 62f;
                    trailColor = Pal.bulletYellowBack;
                    backColor = Pal.bulletYellowBack;
                    frontColor = Pal.bulletYellow;
                    hitEffect = Fx.blastExplosion;
                    despawnEffect = Fx.blastExplosion;
                    weaveScale = 8f;
                    weaveMag = 2f;
                }};
            }});

            weapons.add(new Weapon("large-bullet-mount"){{
                reload = 60f;
                cooldownTime = 90f;
                x = 70f/4f;
                y = -66f/4f;
                rotateSpeed = 4f;
                rotate = true;
                shootY = 7f;
                shake = 2f;
                recoil = 3f;
                occlusion = 12f;
                ejectEffect = Fx.casing3;
                shootSound = Sounds.shootBig;

                shots = 3;
                shotDelay = 4f;
                inaccuracy = 1f;
                bullet = new BasicBulletType(7f, 57){{
                    width = 13f;
                    height = 19f;
                    shootEffect = Fx.shootBig;
                    lifetime = 35f;
                }};
            }});
        }};

        omura = new UnitType("omura"){{
            health = 22000;
            speed = 0.62f;
            drag = 0.18f;
            hitSize = 50f;
            armor = 16f;
            accel = 0.19f;
            rotateSpeed = 0.9f;
            rotateShooting = false;

            float spawnTime = 60f * 15f;

            abilities.add(new UnitSpawnAbility(flare, spawnTime, 19.25f, -31.75f), new UnitSpawnAbility(flare, spawnTime, -19.25f, -31.75f));

            trailLength = 70;
            trailX = 23f;
            trailY = -32f;
            trailScl = 3.5f;

            defaultController = SuicideAI::new;
            weapons.add(new Weapon(){{
                reload = 12f;
                shootCone = 180f;
                ejectEffect = Fx.none;
                shootSound = Sounds.explosion;
                bullet = new BombBulletType(0f, 0f, "clear"){{
                    hitEffect = Fx.pulverize;
                    lifetime = 10f;
                    speed = 1f;
                    splashDamageRadius = 70f;
                    instantDisappear = true;
                    splashDamage = 80f;
                    killShooter = true;
                    hittable = false;
                    collidesAir = true;
                }};
            }});
        }};

        //endregion
        //region core

        alpha = new UnitType("alpha"){{
            defaultController = BuilderAI::new;
            isCounted = false;

            flying = true;
            mineSpeed = 6.5f;
            mineTier = 1;
            buildSpeed = 0.5f;
            drag = 0.05f;
            speed = 3f;
            rotateSpeed = 15f;
            accel = 0.1f;
            itemCapacity = 30;
            health = 150f;
            engineOffset = 6f;
            hitSize = 8f;
            commandLimit = 3;
            alwaysUnlocked = true;

            weapons.add(new Weapon("small-basic-weapon"){{
                reload = 17f;
                x = 2.75f;
                y = 1f;
                top = false;
                ejectEffect = Fx.casing1;

                bullet = new BasicBulletType(2.5f, 11){{
                    width = 7f;
                    height = 9f;
                    lifetime = 60f;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    tileDamageMultiplier = 0.5f;
                }};
            }});
        }};

        beta = new UnitType("beta"){{
            defaultController = BuilderAI::new;
            isCounted = false;

            flying = true;
            mineSpeed = 7f;
            mineTier = 1;
            buildSpeed = 0.75f;
            drag = 0.05f;
            speed = 3.3f;
            rotateSpeed = 17f;
            accel = 0.1f;
            itemCapacity = 50;
            health = 170f;
            engineOffset = 6f;
            hitSize = 9f;
            rotateShooting = false;
            lowAltitude = true;
            commandLimit = 4;

            weapons.add(new Weapon("small-mount-weapon"){{
                top = false;
                reload = 20f;
                x = 3f;
                y = 0.5f;
                rotate = true;
                shots = 2;
                shotDelay = 4f;
                spacing = 0f;
                ejectEffect = Fx.casing1;

                bullet = new BasicBulletType(3f, 11){{
                    width = 7f;
                    height = 9f;
                    lifetime = 60f;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    tileDamageMultiplier = 2f;
                }};
            }});
        }};

        gamma = new UnitType("gamma"){{
            defaultController = BuilderAI::new;
            isCounted = false;

            flying = true;
            mineSpeed = 8f;
            mineTier = 2;
            buildSpeed = 1f;
            drag = 0.05f;
            speed = 3.55f;
            rotateSpeed = 19f;
            accel = 0.11f;
            itemCapacity = 70;
            health = 220f;
            engineOffset = 6f;
            hitSize = 11f;
            commandLimit = 5;

            weapons.add(new Weapon("small-mount-weapon"){{
                top = false;
                reload = 15f;
                x = 1f;
                y = 2f;
                shots = 2;
                spacing = 2f;
                inaccuracy = 3f;
                shotDelay = 3f;
                ejectEffect = Fx.casing1;

                bullet = new BasicBulletType(3.5f, 11){{
                    width = 6.5f;
                    height = 11f;
                    lifetime = 70f;
                    shootEffect = Fx.shootSmall;
                    smokeEffect = Fx.shootSmallSmoke;
                    tileDamageMultiplier = 2f;
                    homingPower = 0.04f;
                }};
            }});
        }};

        //endregion
        //region internal

        block = new UnitType("block"){
            {
                speed = 0f;
                hitSize = 0f;
                health = 1;
                rotateSpeed = 360f;
                itemCapacity = 0;
                commandLimit = 0;
            }

            @Override
            public boolean isHidden(){
                return true;
            }
        };

        //endregion
    }
}
