package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.controller.GameController;
import com.game.entity.enemy.EnemyFactory;
import com.game.util.drawer.GameDrawer;
import com.game.entity.IActiveEntity;
import com.game.entity.enemy.Enemy;
import com.game.entity.tile.GameTile;
import com.game.entity.tile.terrain.Mountain;
import com.game.entity.tile.terrain.Road;
import com.game.entity.tile.terrain.Spawner;
import com.game.entity.tile.terrain.Target;
import com.game.util.player.SoundPlayer;

import java.util.ArrayList;

public class GameField extends Stage implements ContactListener {
    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private GameStage stage;
    private World world;
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private GameDrawer drawer;
    private GameController controller;

    private GameTile[][] map;
    private ArrayList<Spawner> spawners;
    private ArrayList<IActiveEntity> activeEntities;
    private ArrayList<Body> deadBodies;

    public GameField(GameStage stage) {
        super(new ScreenViewport());
        this.stage = stage;
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(this);

        renderer = new Box2DDebugRenderer(false,true,false,true,true,true);
        setupCamera();

        controller = new GameController();
        Gdx.input.setInputProcessor(controller);
        drawer = new GameDrawer(camera.combined);

        map = new GameTile[GameConfig.VIEWPORT_WIDTH + 1][GameConfig.VIEWPORT_HEIGHT + 1];
        spawners = new ArrayList<Spawner>();
        activeEntities = new ArrayList<IActiveEntity>();
        deadBodies = new ArrayList<Body>();
    }

    public void setMap(int posx, int posy, GameTile.TileType tile) {
        switch (tile) {
            case MOUNTAIN:
                map[posx][posy] = new Mountain(world, posx, posy);
                break;
            case ROAD:
                map[posx][posy] = new Road(world, posx, posy);
                break;
            case SPAWNER:
                map[posx][posy] = new Spawner(world, posx, posy);
                spawners.add((Spawner) map[posx][posy]);
                break;
            case TARGET:
                map[posx][posy] = new Target(world, posx, posy);
                break;
        }
        if (map[posx][posy] instanceof Road) {
            Road current = (Road)map[posx][posy];
            for (int dx = -1; dx <= 1; ++dx)
                for (int dy = Math.abs(dx) - 1; dy <= 1; dy += 2) {
                    int adjx = posx + dx;
                    int adjy = posy + dy;
                    if (GameConfig.insideViewport(adjx, adjy) && map[adjx][adjy] instanceof Road) {
                        Road adj = (Road)map[adjx][adjy];
                        current.addAdjacent(adj);
                        adj.addAdjacent(current);
                    }
                }
        }
    }

    public void spawnEnemy(Enemy.EnemyType enemyType) {
        Vector2 startPos = spawners.get(0).getPosition();
        if (startPos.x == 0) --startPos.x;
        if (startPos.x == GameConfig.VIEWPORT_WIDTH) ++startPos.x;
        if (startPos.y == 0) --startPos.y;
        if (startPos.y == GameConfig.VIEWPORT_HEIGHT) ++startPos.y;
        Enemy enemy = EnemyFactory.getEnemy(world, startPos.x, startPos.y, Enemy.EnemyType.NORMAL);
        enemy.targetAt(spawners.get(0).getPosition());
        activeEntities.add(enemy);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (controller.isMouse1Click()) spawnEnemy(Enemy.EnemyType.NORMAL);

        // Fixed timestep
        accumulator += delta;
        while (accumulator >= delta) {
            world.step(TIME_STEP, 6, 2);
            accumulator -= TIME_STEP;
        }
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        if (b.getUserData() instanceof Enemy) {
            Enemy enemy = (Enemy) b.getUserData();
            if (a.getUserData() instanceof Road) {
                Road road = (Road)a.getUserData();
                enemy.targetAt(road.DFS().getPosition());
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        if (b.getUserData() instanceof Enemy) {
            Enemy enemy = (Enemy) b.getUserData();
            if (a.getUserData() instanceof Target) {
                deadBodies.add(enemy.die());
                activeEntities.remove(enemy);
                SoundPlayer.play(SoundPlayer.SoundType.BOING);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void draw() {
        super.draw();
        for (Body body: deadBodies) world.destroyBody(body);
        deadBodies.clear();
        renderer.render(world, camera.combined);
        drawer.draw(activeEntities.toArray(new IActiveEntity[activeEntities.size()]));
    }

    private void setupCamera() {
        camera = new OrthographicCamera(GameConfig.VIEWPORT_WIDTH, GameConfig.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }
}