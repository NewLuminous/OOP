package com.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameStage extends Stage {
    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private World world;

    public GameStage() {
        world = new World(new Vector2(0, -10f), true);

        BodyFactory bodyFactory = BodyFactory.getInstance(world);
        bodyFactory.getBoxBody(0, 1, 100, 2, BodyDef.BodyType.StaticBody);
        bodyFactory.getBoxBody(20, 20, 2, 2, BodyDef.BodyType.DynamicBody);
        bodyFactory.getBoxBody(20, 3, 2, 2, BodyDef.BodyType.KinematicBody).setLinearVelocity(0, 1f);
        bodyFactory.getCircleBody(2, 10, 2, BodyFactory.Materials.RUBBER, BodyDef.BodyType.DynamicBody);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void act(float delta) {

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        //TODO: Implement interpolation

    }
}
