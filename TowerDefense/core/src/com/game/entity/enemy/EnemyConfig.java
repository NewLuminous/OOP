package com.game.entity.enemy;

final class EnemyConfig {
    static float NORMAL_ENEMY_RADIUS = 0.4f;
    static int NORMAL_ENEMY_HP = 7;
    static int NORMAL_ENEMY_ARMOR = 10;
    static int NORMAL_ENEMY_BOUNTY=10;
    static float NORMAL_ENEMY_SPEED = 7;

    static float TANKER_ENEMY_RADIUS = 0.4f;
    static int TANKER_ENEMY_HP = 20;
    static int TANKER_ENEMY_ARMOR = 15;
    static int TANKER_ENEMY_BOUNTY = 20;
    static float TANKER_ENEMY_SPEED = 8;

    static float SMALLER_ENEMY_RADIUS = 0.6f;
    static int SMALLER_ENEMY_HP = 60;
    static int SMALLER_ENEMY_ARMOR = 25;
    static int SMALLER_ENEMY_BOUNTY = 25;
    static float SMALLER_ENEMY_SPEED = 10;

    static float BOSSS_ENEMY_RADIUS = 1f;
    static int BOSS_ENEMY_HP = 100;
    static int BOSS_ENEMY_ARMOR = 30;
    static int BOSS_ENEMY_BOUNTY = 30;
    static float BOSS_ENEMY_SPEED = 6;

    private EnemyConfig() {}
}
