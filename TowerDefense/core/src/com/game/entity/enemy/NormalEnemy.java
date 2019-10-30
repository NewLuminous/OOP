package com.game.entity.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.BodyFactory;
import com.game.util.loader.GameLoader;

class NormalEnemy extends Enemy {
    NormalEnemy(World world, int posx, int posy) {
        super(BodyFactory.getInstance(world).getCircleBody(posx, posy, EnemyConfig.NORMAL_ENEMY_RADIUS, false, BodyDef.BodyType.DynamicBody), EnemyConfig.NORMAL_ENEMY_HP);
        setArmor(EnemyConfig.NORMAL_ENEMY_ARMOR);
        setBounty(EnemyConfig.NORMAL_ENEMY_BOUNTY);
        speed = EnemyConfig.NORMAL_ENEMY_SPEED;
    }

    public final void targetAt(Vector2 pos) {
        super.targetAt(pos, EnemyConfig.NORMAL_ENEMY_SPEED);
    }

    @Override
    public Texture[] getTextures() {
        if (textures == null) {
            textures = new Texture[1];
            textures[0] = GameLoader.getManager().get(GameLoader.NORMAL_ENEMY);
        }
        return textures;
    }
}
