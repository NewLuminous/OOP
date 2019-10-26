package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.entity.GameEntity;
import com.game.entity.enemy.NormalEnemy;

public class GameField extends Stage {
    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private World world;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    public GameField() {
        super(new ScreenViewport());
        world = new World(new Vector2(0, -10f), true);
        renderer = new Box2DDebugRenderer(true,true,true,true,true,true);
        setupCamera();
        Gdx.input.setInputProcessor(this);

        //BodyFactory bodyFactory = BodyFactory.getInstance(world);
        //Body ground = bodyFactory.getBoxBody(0, 1, 100, 2, BodyDef.BodyType.StaticBody);
        //bodyFactory.getBoxBody(20, 20, 2, 2, BodyDef.BodyType.DynamicBody);
        //bodyFactory.getBoxBody(20, 3, 2, 2, BodyDef.BodyType.KinematicBody).setLinearVelocity(0, 1f);
        //Body circle = bodyFactory.getCircleBody(1, 10, 1, BodyFactory.Materials.RUBBER, BodyDef.BodyType.DynamicBody);

        //GameEntity groundEntity = new NormalEnemy(ground);
        //GameEntity circleEntity = new NormalEnemy(circle);
        //addActor(groundEntity);
        //addActor(circleEntity);
    }

    private void setupCamera() {
        camera = new OrthographicCamera(GameConfig.VIEWPORT_WIDTH, GameConfig.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // Fixed timestep
        accumulator += delta;

        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }

        //TODO: Implement interpolation

    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
    }
}