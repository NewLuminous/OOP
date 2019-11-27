package com.game.entity.enemy;

final class EnemyConfig {
    static float NORMAL_ENEMY_RADIUS = 0.4f;
    static int NORMAL_ENEMY_HP = 7;
    static int NORMAL_ENEMY_ARMOR = 10;
    static int NORMAL_ENEMY_BOUNTY = 10;
    static float NORMAL_ENEMY_SPEED = 7;

    static float TANKER_ENEMY_RADIUS = 0.6f;
    static int TANKER_ENEMY_HP = 30;
    static int TANKER_ENEMY_ARMOR = 15;
    static int TANKER_ENEMY_BOUNTY = 20;
    static float TANKER_ENEMY_SPEED = 5;

    static float SMALLER_ENEMY_RADIUS = 0.4f;
    static int SMALLER_ENEMY_HP = 60;
    static int SMALLER_ENEMY_ARMOR = 20;
    static int SMALLER_ENEMY_BOUNTY = 25;
    static float SMALLER_ENEMY_SPEED = 9;

    static float BOSS_ENEMY_RADIUS = 0.6f;
    static int BOSS_ENEMY_HP = 150;
    static int BOSS_ENEMY_ARMOR = 30;
    static int BOSS_ENEMY_BOUNTY = 30;
    static float BOSS_ENEMY_SPEED = 3;

    private EnemyConfig() {}
}
