package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.controller.GameController;
import com.game.entity.bullet.Bullet;
import com.game.entity.enemy.EnemyFactory;
import com.game.entity.tile.TileFactory;
import com.game.entity.tile.terrain.Mountain;
import com.game.entity.tile.tower.Tower;
import com.game.util.drawer.GameDrawer;
import com.game.entity.IActiveEntity;
import com.game.entity.enemy.Enemy;
import com.game.entity.tile.GameTile;
import com.game.entity.tile.terrain.Road;
import com.game.entity.tile.terrain.Spawner;
import com.game.entity.tile.terrain.Target;
import com.game.util.player.SoundPlayer;
import com.game.view.ScreenType;

import java.util.ArrayList;
import java.util.Iterator;

public class GameField extends Stage implements ContactListener {
    private GameStage stage;
    private World world;
    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private GameDrawer drawer;
    private GameController controller;

    private GameTile[][] map;
    private ArrayList<Spawner> spawners;
    private ArrayList<IActiveEntity> activeEntities;
    private ArrayList<Tower> towers;
    private ArrayList<Bullet> bullets;
    private ArrayList<Body> deadBodies;

    private int newTower = -1;
    private Vector2 itemPos;
    private int bountySum = 0;

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
        towers = new ArrayList<Tower>();
        bullets = new ArrayList<Bullet>();
        deadBodies = new ArrayList<Body>();
    }

    void setMap(int posx, int posy, GameTile.TileType tile) {
        map[posx][posy] = TileFactory.getTile(world, posx, posy, tile);
        stage.saveMap(posx, posy, tile);
        if (map[posx][posy] instanceof Spawner) spawners.add((Spawner) map[posx][posy]);
        if (map[posx][posy] instanceof Tower) {
            activeEntities.add((Tower) map[posx][posy]);
            towers.add((Tower) map[posx][posy]);
        }
        if (map[posx][posy] instanceof Road) connectRoad(posx, posy);
    }

    private void connectRoad(int posx, int posy) {
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

    void spawnEnemy(Enemy.EnemyType enemyType) {
        Vector2 startPos = spawners.get(0).getPosition();
        if (startPos.x == 0) --startPos.x;
        if (startPos.x == GameConfig.VIEWPORT_WIDTH) ++startPos.x;
        if (startPos.y == 0) --startPos.y;
        if (startPos.y == GameConfig.VIEWPORT_HEIGHT) ++startPos.y;
        Enemy enemy = EnemyFactory.getEnemy(world, startPos.x, startPos.y, enemyType);
        assert enemy != null;
        enemy.targetAt(spawners.get(0).getPosition());
        activeEntities.add(enemy);
    }

    boolean allEnemiesDestroyed() {
        for (IActiveEntity activeEntity: activeEntities)
            if (activeEntity instanceof Enemy) return false;
        return true;
    }

    private Vector2 convertScreenPosToWorldPos(Vector2 screenPos) {
        Vector3 screenLocation = new Vector3(screenPos, 0);
        camera.unproject(screenLocation);
        return new Vector2(screenLocation.x + 0.5f, screenLocation.y + 0.5f);
    }

    private boolean isWidgetClicked(Widget widget) {
        if (widget.getX() > controller.mouseLocation.x) return false;
        if (controller.mouseLocation.x > widget.getX() + widget.getWidth()) return false;
        if (widget.getY() > Gdx.graphics.getHeight() - controller.mouseLocation.y) return false;
        if (Gdx.graphics.getHeight() - controller.mouseLocation.y > widget.getY() + widget.getHeight()) return false;
        return true;
    }

    private void handleInputs() {
        if (controller.escape) stage.main.changeScreen(ScreenType.MENU);
        if (controller.space) stage.nextStage();
        if (controller.isMouse1Click()) {
            if (isWidgetClicked(stage.menuButton)) stage.main.changeScreen(ScreenType.MENU);
            if (newTower >= 0) {
                Vector2 tile = convertScreenPosToWorldPos(controller.mouseLocation);
                if (map[(int)tile.x][(int)tile.y] instanceof Mountain) {
                    setMap((int)tile.x, (int)tile.y, GameTile.TileType.values()[5 + newTower]);
                    stage.spendMoney(Tower.getBuildCost(Tower.TowerType.values()[newTower]));
                }
                stage.towers.get(newTower).setPosition(itemPos.x, itemPos.y);
                newTower = -1;
            }
            for (int i = 0; i < Tower.TowerType.values().length; ++i)
                if (isWidgetClicked(stage.buttons.get(i + 1))) {
                    if (stage.canAfford(Tower.getBuildCost(Tower.TowerType.values()[i]))) {
                        newTower = i;
                        itemPos = new Vector2(stage.towers.get(i).getX(), stage.towers.get(i).getY());
                    }
                }
            if (isWidgetClicked(stage.startButton)) stage.nextStage();
            if (isWidgetClicked(stage.autoplayLabel)) stage.switchAutoplayMode();
        }
        for (int i = 1; i <= 3; ++i)
            if (newTower < 0 && controller.num[i] && stage.canAfford(Tower.getBuildCost(Tower.TowerType.values()[i - 1]))) {
                newTower = i - 1;
                itemPos = new Vector2(stage.towers.get(i - 1).getX(), stage.towers.get(i - 1).getY());
            }
        if (newTower >= 0) {
            Vector2 previewPos = convertScreenPosToWorldPos(controller.mouseLocation);
            previewPos.x = ((int)previewPos.x - 0.5f) * GameConfig.GRID_SIZE * GameConfig.getScreenScaleX();
            previewPos.y = ((int)previewPos.y - 0.5f) * GameConfig.GRID_SIZE * GameConfig.getScreenScaleY();
            stage.towers.get(newTower).setPosition(previewPos.x, previewPos.y);
        }
    }

    private void controlTowersAndBullets(float delta) {
        for (Tower tower: towers) {
            Bullet bullet = tower.fire(delta);
            if (bullet != null) {
                bullets.add(bullet);
                activeEntities.add(bullet);
            }
        }

        for (Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
            Bullet bullet = it.next();
            if (bullet.target.isDestroyed()) {
                it.remove();
                activeEntities.remove(bullet);
                deadBodies.add(bullet.destroy());
            }
            else bullet.targetAt(bullet.target.getPosition());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        handleInputs();
        controlTowersAndBullets(delta);

        if (bountySum > 0) {
            stage.receiveBounty(bountySum);
            bountySum = 0;
        }
        for (Body body: deadBodies) world.destroyBody(body);
        deadBodies.clear();
        world.step(delta, 3, 3);
    }

    @Override
    public void beginContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        if (b.getUserData() instanceof Enemy) {
            Body tmp = a; a = b; b = tmp;
        }
        if (a.getUserData() instanceof Enemy) {
            Enemy enemy = (Enemy) a.getUserData();
            if (enemy.isDestroyed()) return;
            if (b.getUserData() instanceof Road) {
                Road road = (Road)b.getUserData();
                enemy.targetAt(road.DFS().getPosition());
            }
            else if (b.getUserData() instanceof Tower) {
                Tower tower = (Tower)b.getUserData();
                tower.addEnemy(enemy);
            }
            else if (b.getUserData() instanceof Bullet) {
                Bullet bullet = (Bullet)b.getUserData();
                if (enemy != bullet.target) return;
                bullets.remove(bullet);
                activeEntities.remove(bullet);
                activeEntities.remove(enemy);
                deadBodies.add(bullet.destroy());
                deadBodies.add(enemy.destroy());
                bountySum += 20;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();
        if (b.getUserData() instanceof Enemy) {
            Body tmp = a; a = b; b = tmp;
        }
        if (a.getUserData() instanceof Enemy) {
            Enemy enemy = (Enemy) a.getUserData();
            if (enemy.isDestroyed()) return;
            if (b.getUserData() instanceof Target) {
                activeEntities.remove(enemy);
                deadBodies.add(enemy.destroy());
                SoundPlayer.play(SoundPlayer.SoundType.BOING);
                stage.damageHP();
            }
            else if (b.getUserData() instanceof Tower) {
                Tower tower = (Tower)b.getUserData();
                tower.removeEnemy(enemy);
            }
        }
    }

    @Override
    public void draw() {
        super.draw();
        renderer.render(world, camera.combined);
        drawer.draw(activeEntities.toArray(new IActiveEntity[activeEntities.size()]));
    }

    private void setupCamera() {
        camera = new OrthographicCamera(GameConfig.VIEWPORT_WIDTH, GameConfig.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}