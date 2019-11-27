package com.game.entity.tile.tower;

final class TowerConfig {
    static float NORMAL_TOWER_RANGE = 3.5f;
    static double NORMAL_TOWER_DAMAGE = 5;
    static double NORMAL_TOWER_RATE_OF_FIRE = 1;
    static int NORMAL_TOWER_COST = 100;

    static float MACHINE_GUN_TOWER_RANGE = NORMAL_TOWER_RANGE - 1;
    static double MACHINE_GUN_TOWER_DAMAGE = NORMAL_TOWER_DAMAGE;
    static double MACHINE_GUN_TOWER_RATE_OF_FIRE = NORMAL_TOWER_RATE_OF_FIRE * 2;
    static int MACHINE_GUN_TOWER_COST = NORMAL_TOWER_COST * 3 / 2;

    static float SNIPER_TOWER_RANGE = NORMAL_TOWER_RANGE + 1;
    static double SNIPER_TOWER_DAMAGE = NORMAL_TOWER_DAMAGE * 2;
    static double SNIPER_TOWER_RATE_OF_FIRE = NORMAL_TOWER_RATE_OF_FIRE * 3 / 4;
    static int SNIPER_TOWER_COST = NORMAL_TOWER_COST * 2;

    private TowerConfig() {}
}

