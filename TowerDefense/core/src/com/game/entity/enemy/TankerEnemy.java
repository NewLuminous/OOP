package com.game.entity.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.BodyFactory;
import com.badlogic.gdx.math.Vector2;
import com.game.entity.tile.GameTile;
import com.game.util.loader.GameLoader;

import java.nio.file.StandardWatchEventKinds;

public class TankerEnemy extends Enemy {
    TankerEnemy(World world, int posx , int posy) {

        super(BodyFactory.getInstance(world).getCircleBody(posx,posy , EnemyConfig.TANKER_ENEMY_RADIUS,true, BodyDef.BodyType.DynamicBody), EnemyConfig.TANKER_ENEMY_HP);
        setArmor(EnemyConfig.TANKER_ENEMY_ARMOR);
        setBounty(EnemyConfig.TANKER_ENEMY_BOUNTY);
        speed = EnemyConfig.TANKER_ENEMY_SPEED;
    }
    public final void targetAt(Vector2 pos) {super.targetAt(pos,EnemyConfig.TANKER_ENEMY_SPEED);}
    @Override
    public Texture[] getTextures() {
        if (textures == null) {
            textures = new Texture[2];
            textures[0] = GameLoader.getManager().get(GameLoader.TANKER_ENEMY_BODY);
            textures[1] = GameLoader.getManager().get(GameLoader.TANKER_ENEMY_HEAD);
        }
        return textures;
    }
}
