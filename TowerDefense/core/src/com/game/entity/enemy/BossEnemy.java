package com.game.entity.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.BodyFactory;
import com.game.util.loader.GameLoader;

public class BossEnemy extends Enemy {
    BossEnemy(World world, int posx , int posy) {
        super(BodyFactory.getInstance(world).getCircleBody(posx , posy, EnemyConfig.BOSSS_ENEMY_RADIUS,true, BodyDef.BodyType.DynamicBody),EnemyConfig.BOSS_ENEMY_HP);
        setArmor(EnemyConfig.BOSS_ENEMY_ARMOR);
        setBounty(EnemyConfig.BOSS_ENEMY_BOUNTY);
        speed = EnemyConfig.BOSS_ENEMY_SPEED;
    }
     public final void targetAt(Vector2 pos) { super.targetAt(pos , EnemyConfig.BOSS_ENEMY_SPEED ); }
    @Override
    public Texture[] getTextures() {
        if (textures == null) {
            textures = new Texture[1];
            textures[0] = GameLoader.getManager().get(GameLoader.BOSS_ENEMY_BODY);
        }
        return textures;
    }

    }

