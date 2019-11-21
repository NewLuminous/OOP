package com.game.entity.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.BodyFactory;
import com.game.util.loader.GameLoader;

public class SmallerEnemy extends Enemy{
    SmallerEnemy(World world , int posx , int posy) {
        super(BodyFactory.getInstance(world).getCircleBody(posx, posy, EnemyConfig.SMALLER_ENEMY_RADIUS, true, BodyDef.BodyType.DynamicBody), EnemyConfig.SMALLER_ENRMY_HP);
        setArmor(EnemyConfig.SMALLER_ENEMY_ARMOR);
        setBounty(EnemyConfig.SMALLER_ENEMY_BOUNTY);
        speed = EnemyConfig.SMALLER_ENEMY_SPEED;
    }
    public final void targetAt(Vector2 pos) { super.targetAt(pos, EnemyConfig.SMALLER_ENEMY_SPEED) ;}
    @Override
    public Texture[] getTextures() {
        if (textures == null) {
            textures = new Texture[1];
            textures[0] = GameLoader.getManager().get(GameLoader.SMALLER_ENEMY);
        }
        return textures;
    }
}
