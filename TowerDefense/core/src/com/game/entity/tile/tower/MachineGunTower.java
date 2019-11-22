package com.game.entity.tile.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.game.util.loader.GameLoader;

class MachineGunTower extends Tower {
    MachineGunTower (World world, int posx, int posy) {
        super(world, posx, posy, TowerType.MACHINE_GUN, TowerConfig.MACHINE_GUN_TOWER_RANGE);
        setRateOfFire(TowerConfig.MACHINE_GUN_TOWER_RATE_OF_FIRE);
        setDamage(TowerConfig.MACHINE_GUN_TOWER_DAMAGE);
    }

    @Override
    public Texture[] getTextures() {
        if (textures == null) {
            textures = new Texture[2];
            textures[0] = GameLoader.getManager().get(GameLoader.MACHINE_GUN_TOWER_BODY);
            textures[1] = GameLoader.getManager().get(GameLoader.MACHINE_GUN_TOWER_HEAD);
        }
        return textures;
    }
}