package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.controller.GameController;
import com.game.entity.IActiveEntity;
import com.game.entity.enemy.Enemy;
import com.game.entity.enemy.EnemyType;
import com.game.entity.enemy.NormalEnemy;
import com.game.entity.tile.GameTile;
import com.game.entity.tile.TileType;
import com.game.entity.tile.terrain.Mountain;
import com.game.entity.tile.terrain.Road;
import com.game.entity.tile.terrain.Spawner;
import com.game.entity.tile.terrain.Target;

import java.util.ArrayList;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.Color;

public class GameField extends Stage implements ContactListener {
    private final float TIME_STEP = 1 / 300f;
    private float accumulator = 0f;

    private GameStage stage;
    private World world;
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private GameController controller;
    private SpriteBatch sb;

    private GameTile[][] map;
    private ArrayList<Spawner> spawners;
    private ArrayList<IActiveEntity> activeEntities;
    private ArrayList<Enemy> deadEnemies;

    public GameField(GameStage stage) {
        super(new ScreenViewport());
        this.stage = stage;
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(this);

        renderer = new Box2DDebugRenderer(false,true,false,true,true,true);
        setupCamera();

        controller = new GameController();
        Gdx.input.setInputProcessor(controller);
        sb = new SpriteBatch();
        sb.setProjectionMatrix(camera.combined);

        map = new GameTile[GameConfig.VIEWPORT_WIDTH + 1][GameConfig.VIEWPORT_HEIGHT + 1];
        spawners = new ArrayList<Spawner>();
        activeEntities = new ArrayList<IActiveEntity>();
        deadEnemies = new ArrayList<Enemy>();
    }

    public void setMap(int posx, int posy, TileType tile) {
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

    public void spawnEnemy(EnemyType enemyType) {
        Vector2 startPos = spawners.get(0).getPosition();
        if (startPos.x == 0) --startPos.x;
        if (startPos.x == GameConfig.VIEWPORT_WIDTH) ++startPos.x;
        if (startPos.y == 0) --startPos.y;
        if (startPos.y == GameConfig.VIEWPORT_HEIGHT) ++startPos.y;
        Enemy enemy;
        switch (enemyType) {
            default:
                enemy = new NormalEnemy(world, (int)startPos.x, (int)startPos.y);
        }
        enemy.targetAt(spawners.get(0).getPosition());
        activeEntities.add(enemy);
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        if (b.getUserData() instanceof NormalEnemy) {
            NormalEnemy enemy = (NormalEnemy)b.getUserData();
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
        if (b.getUserData() instanceof NormalEnemy) {
            NormalEnemy enemy = (NormalEnemy)b.getUserData();
            if (a.getUserData() instanceof Target) {
                deadEnemies.add(enemy);
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

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
        for (Enemy enemy: deadEnemies) enemy.die();
        deadEnemies.clear();
        renderer.render(world, camera.combined);
        sb.begin();
        for (IActiveEntity entity: activeEntities) {
            Texture[] textures = entity.getTextures();
            Vector2 pos = new Vector2(entity.getPosition().x - entity.getTextureWidth() / 2, entity.getPosition().y - entity.getTextureHeight() / 2);
            Vector2 center = new Vector2(entity.getTextureWidth() / 2, entity.getTextureHeight() / 2);
            for (Texture texture: textures) {
                TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
                sb.draw(region, pos.x, pos.y, center.x, center.y, entity.getTextureWidth(), entity.getTextureHeight(), 1, 1, 90 + entity.getDirection(), true);
            }
        }
        sb.end();
    }
}