package com.game.entity.tile.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.game.loader.GameLoader;

public class NormalTower extends Tower {
    public NormalTower(World world, int posx, int posy) {
        super(world, posx, posy, TowerType.NORMAL);
        setRateOfFire(TowerConfig.NORMAL_TOWER_RATE_OF_FIRE);
        setRange(TowerConfig.NORMAL_TOWER_RANGE);
        setDamage(TowerConfig.NORMAL_TOWER_DAMAGE);
    }

    @Override
    public Texture[] getTextures() {
        if (textures == null) {
            textures = new Texture[2];
            textures[0] = GameLoader.getManager().get(GameLoader.NORMAL_TOWER_BODY);
            textures[1] = GameLoader.getManager().get(GameLoader.NORMAL_TOWER_HEAD);
        }
        return textures;
    }
}
