package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.entity.enemy.Enemy;
import com.game.entity.tile.GameTile;
import com.game.entity.tile.tower.Tower;
import com.game.util.loader.GameLoader;
import com.game.view.ScreenType;

import java.util.ArrayList;

public class GameStage {
    private static final int STAGE_COUNT = 30;
    private static final String savePath = "save.dat";

    private static class SpawnTimer {
        private Enemy.EnemyType enemy;
        private float countdown;

        private SpawnTimer(Enemy.EnemyType enemy, float countdown) {
            this.enemy = enemy;
            this.countdown = countdown;
        }
    }

    public Main main;
    private GameField field;
    public Image mapImg;
    public GameUI gameUI;
    private GameBot gameBot;

    private Queue<SpawnTimer> enemyQueue;
    private ArrayList<SpawnTimer>[] stages;
    private int stageId = 0;

    private int[][] map;
    private int hp = 100;
    private int money = 150;
    private boolean autoplayMode = false;

    public GameStage(Main main) {
        this.main = main;
        field = new GameField(this);
        if (!main.mainScreenContinued) loadMap(Gdx.files.internal(GameConfig.MAP1)); else loadGame();
        gameUI = new GameUI(field, stageId, hp, money);
        setupStages();
        enemyQueue = new Queue<SpawnTimer>();
    }

    private void loadMap(FileHandle mapFile) {
        mapImg = new Image((Texture) GameLoader.manager.get(GameLoader.MAP));
        mapImg.setWidth(Gdx.graphics.getWidth());
        mapImg.setHeight(Gdx.graphics.getHeight());
        field.addActor(mapImg);

        map = new int[GameConfig.VIEWPORT_HEIGHT + 1][GameConfig.VIEWPORT_WIDTH + 1];
        String[] content = mapFile.readString().split("\\W+");
        for (int i = 0, k = 0; i <= GameConfig.VIEWPORT_HEIGHT; ++i)
            for (int j = 0; j <= GameConfig.VIEWPORT_WIDTH; ++j) {
                int id = Integer.parseInt(content[k++]);
                map[i][j] = id;
                GameTile.TileType tile = GameTile.TileType.values()[id];
                field.setMap(j, GameConfig.VIEWPORT_HEIGHT - i, tile);
            }
        gameBot = new GameBot(map);
    }

    void saveMap(int x, int y, GameTile.TileType tile) {
        map[GameConfig.VIEWPORT_HEIGHT - y][x] = tile.ordinal();
    }

    private void loadGame() {
        loadMap(Gdx.files.local(savePath));
        FileHandle handle = Gdx.files.local(savePath);
        String[] content = handle.readString().split("\\W+");
        setHP(Integer.parseInt(content[content.length - 3]));
        setMoney(Integer.parseInt(content[content.length - 2]));
        stageId = Integer.parseInt(content[content.length - 1]);
    }

    private void saveGame() {
        if (!(field.allEnemiesDestroyed() && noEnemiesLeft() && hp > 0)) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= GameConfig.VIEWPORT_HEIGHT; ++i) {
            for (int j = 0; j <= GameConfig.VIEWPORT_WIDTH; ++j) {
                sb.append(map[i][j]);sb.append(" ");
            }
            sb.append('\n');
        }
        sb.append(hp);sb.append('\n');
        sb.append(money);sb.append('\n');
        sb.append(stageId);sb.append('\n');
        Gdx.files.local(savePath).writeString(sb.toString(), false);
    }

    private void setupStages() {
        stages = new ArrayList[STAGE_COUNT];
        for (int i = 0; i < STAGE_COUNT; ++i) stages[i] = new ArrayList<SpawnTimer>();
        stages[0].add(new SpawnTimer(Enemy.EnemyType.NORMAL, 1));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.TANKER, 1));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.NORMAL, 0.1f));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.SMALLER, 0.2f));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.TANKER, 1.8f));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.SMALLER, 1.9f));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.NORMAL, 0.5f));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.TANKER, 0.6f));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.BOSS, 0.7f));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.BOSS, 1f));
        for (int i = 0; i < 7; ++i)
            stages[1].add(new SpawnTimer(Enemy.EnemyType.NORMAL, 0.5f));
        for (int i = 0; i < 4; ++i)
            stages[1].add(new SpawnTimer(Enemy.EnemyType.TANKER, 0.7f));
        for (int i = 0; i < 3; ++i)
            stages[1].add(new SpawnTimer(Enemy.EnemyType.SMALLER, 0.5f));
        for (int i = 0; i < 1; ++i)
            stages[1].add(new SpawnTimer(Enemy.EnemyType.BOSS, 0.8f));
        for (int i = 2; i < STAGE_COUNT; ++i)
            for (int j = 0; j < i * i; ++j) stages[i].add(new SpawnTimer(Enemy.EnemyType.NORMAL, 0.85f/(i * i)));
        for (int i = 2; i < STAGE_COUNT; ++i)
            for (int j = 0; j < i * i; ++j) stages[i].add(new SpawnTimer(Enemy.EnemyType.TANKER, 0.95f/(i * i)));
        for (int i = 2; i < STAGE_COUNT; ++i)
            for (int j = 0; j < i * i; ++j) stages[i].add(new SpawnTimer(Enemy.EnemyType.SMALLER, 1.45f/(i * i)));
        for (int i = 2; i < STAGE_COUNT; ++i)
            for (int j = 0; j < i * i; ++j) stages[i].add(new SpawnTimer(Enemy.EnemyType.BOSS, 1.25f/(i * i)));
    }

    void nextStage() {
        if (!(field.allEnemiesDestroyed() && noEnemiesLeft())) return;
        if (stageId >= STAGE_COUNT) {
            main.endScreenLose = false;
            main.changeScreen(ScreenType.ENDGAME);
        }
        for (SpawnTimer spawnTimer: stages[stageId]) enemyQueue.addLast(spawnTimer);
        gameUI.setStage(++stageId);
    }

    private boolean noEnemiesLeft() {
        return enemyQueue.isEmpty();
    }

    public void act(float delta) {
        if (!noEnemiesLeft()) {
            enemyQueue.first().countdown -= delta;
            if (enemyQueue.first().countdown <= 0) {
                field.spawnEnemy(enemyQueue.first().enemy);
                enemyQueue.removeFirst();
            }
        }
        field.act(delta);
    }

    private void setHP(int hp) {
        if (hp <= 0) throw new IllegalArgumentException("HP must be a positive value");
        this.hp = hp;
    }

    void damageHP() {
        if (--hp == 0) {
            main.endScreenLose = true;
            main.changeScreen(ScreenType.ENDGAME);
        }
        gameUI.setHp(hp);
        saveGame();
        if (autoplayMode) autoplay();
    }

    private void setMoney(int money) {
        this.money = money;
        if (gameUI != null) gameUI.setMoney(money);
    }

    boolean canAfford(int cost) {
        if (cost < 0) throw new IllegalArgumentException("Cost must not be a negative value");
        return cost <= money;
    }

    void spendMoney(int cost) {
        if (cost < 0) throw new IllegalArgumentException("Cannot spend a negative value of money");
        if (!canAfford(cost)) return;
        setMoney(money - cost);
        saveGame();
    }

    void receiveBounty(int bounty) {
        if (bounty <= 0) throw new IllegalArgumentException("Bounty must be a positive value");
        setMoney(money + bounty);
        saveGame();
        if (autoplayMode) autoplay();
    }

    void switchAutoplayMode() {
        autoplayMode = !autoplayMode;
        gameUI.setAutoplayMode(autoplayMode);
        if (autoplayMode) autoplay();
    }

    private void autoplay() {
        Position pos = new Position(0, 0);
        Tower.TowerType tower = gameBot.query(money, pos);
        if (tower != null) {
            spendMoney(Tower.getBuildCost(tower));
            field.setMap(pos.col, GameConfig.VIEWPORT_HEIGHT - pos.row, GameTile.TileType.values()[5 + tower.ordinal()]);
        }
        nextStage();
    }

    public void draw() {
        field.draw();
    }

    public Viewport getViewport() {
        return field.getViewport();
    }

    public void dispose() {
        saveGame();
        field.dispose();
    }
}