package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.entity.enemy.Enemy;
import com.game.entity.tile.GameTile;
import com.game.entity.tile.tower.Tower;
import com.game.util.loader.GameLoader;
import com.game.view.ScreenType;

import java.util.ArrayList;
import java.util.Collections;

public class GameStage {
    private static final int STAGE_COUNT = 30;
    private static final String savePath = "save.dat";

    private static class Position {
        private int row, col;

        private Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

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
    private Image barImg, hpImg;
    Image menuButton, startButton;
    public Image mapImg;
    private Label bountyImg, stageLabel, hpLabel;
    Label autoplayLabel;
    private ArrayList<Image> labels;
    ArrayList<Image> buttons, towers;
    private ArrayList<Label> moneyAndCost;

    private Queue<SpawnTimer> enemyQueue;
    private ArrayList<SpawnTimer>[] stages;
    private int stageId = 0;

    private int[][] map;
    private int hp = 100;
    private int money = 100;
    private boolean autoplayMode = false;

    public GameStage(Main main) {
        this.main = main;
        field = new GameField(this);
        if (!main.mainScreenContinued) loadMap(Gdx.files.internal(GameConfig.MAP1)); else loadGame();
        setupUI();
        showUI();
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

    private void setupUI() {
        barImg = new Image((Texture) GameLoader.manager.get(GameLoader.ITEM_BAR));
        field.addActor(barImg);

        Image bountyButton = new Image((Texture) GameLoader.manager.get(GameLoader.BOUNTY));
        Image normalTowerButton = new Image((Texture) GameLoader.manager.get(GameLoader.NORMAL_TOWER_BUTTON));
        Image machineTowerButton = new Image((Texture) GameLoader.manager.get(GameLoader.MACHINE_GUN_TOWER_BUTTON));
        Image sniperTowerButton = new Image((Texture) GameLoader.manager.get(GameLoader.SNIPER_TOWER_BUTTON));
        buttons = new ArrayList<Image>();
        buttons.add(bountyButton);
        buttons.add(normalTowerButton);
        buttons.add(machineTowerButton);
        buttons.add(sniperTowerButton);
        for (Image button : buttons) field.addActor(button);

        Image bountyLabel = new Image((Texture) GameLoader.manager.get(GameLoader.BOUNTY_SUM));
        Image normalTowerLabel = new Image((Texture) GameLoader.manager.get(GameLoader.NORMAL_TOWER_COST));
        Image machineTowerLabel = new Image((Texture) GameLoader.manager.get(GameLoader.MACHINE_GUN_TOWER_COST));
        Image sniperTowerLabel = new Image((Texture) GameLoader.manager.get(GameLoader.SNIPER_TOWER_COST));
        labels = new ArrayList<Image>();
        labels.add(bountyLabel);
        labels.add(normalTowerLabel);
        labels.add(machineTowerLabel);
        labels.add(sniperTowerLabel);
        for (Image label : labels) field.addActor(label);

        Image normalTowerImg = new Image((Texture) GameLoader.manager.get(GameLoader.NORMAL_TOWER_HEAD));
        Image machineTowerImg = new Image((Texture) GameLoader.manager.get(GameLoader.MACHINE_GUN_TOWER_HEAD));
        Image sniperTowerImg = new Image((Texture) GameLoader.manager.get(GameLoader.SNIPER_TOWER_HEAD));
        towers = new ArrayList<Image>();
        towers.add(normalTowerImg);
        towers.add(machineTowerImg);
        towers.add(sniperTowerImg);
        for (Image tower : towers) field.addActor(tower);

        BitmapFont pixelFont = GameLoader.getManager().get(GameLoader.KENNEY_PIXEL_72);
        BitmapFont blocksFont = GameLoader.getManager().get(GameLoader.KENNEY_BLOCKS_60);
        BitmapFont futureFont = GameLoader.getManager().get(GameLoader.KENNEY_FUTURE_NARROW_36);

        Label moneyLabel = new Label(String.format("%5d",money), new Label.LabelStyle(pixelFont, Color.BLACK));
        Label normalTowerCost = new Label(Tower.getBuildCost(Tower.TowerType.NORMAL) + "", new Label.LabelStyle(pixelFont, Color.BLACK));
        Label machineGunTowerCost = new Label(Tower.getBuildCost(Tower.TowerType.MACHINE_GUN) + "", new Label.LabelStyle(pixelFont, Color.BLACK));
        Label sniperTowerCost = new Label(Tower.getBuildCost(Tower.TowerType.SNIPER) + "", new Label.LabelStyle(pixelFont, Color.BLACK));
        moneyAndCost = new ArrayList<Label>();
        moneyAndCost.add(moneyLabel);
        moneyAndCost.add(normalTowerCost);
        moneyAndCost.add(machineGunTowerCost);
        moneyAndCost.add(sniperTowerCost);
        for (Label label : moneyAndCost) field.addActor(label);

        bountyImg = new Label("$", new Label.LabelStyle(pixelFont, Color.WHITE));
        field.addActor(bountyImg);

        hpImg = new Image((Texture) GameLoader.manager.get(GameLoader.HP));
        field.addActor(hpImg);
        hpLabel = new Label("x" + hp, new Label.LabelStyle(pixelFont, Color.WHITE));
        field.addActor(hpLabel);

        stageLabel = new Label("Level " + stageId, new Label.LabelStyle(blocksFont, Color.GOLD));
        field.addActor(stageLabel);

        startButton = new Image((Texture) GameLoader.manager.get(GameLoader.START));
        field.addActor(startButton);

        menuButton = new Image((Texture) GameLoader.manager.get(GameLoader.MENU));
        field.addActor(menuButton);

        autoplayLabel = new Label("MANUAL", new Label.LabelStyle(futureFont, Color.CHARTREUSE));
        field.addActor(autoplayLabel);
    }

    public void showUI() {
        float tileSize = GameConfig.GRID_SIZE * GameConfig.getScreenScaleX();
        float pad = tileSize / 5;
        Vector2 pos = new Vector2(tileSize / 2, Gdx.graphics.getHeight() - tileSize / 2);

        barImg.setHeight(tileSize * 3);
        barImg.setWidth(tileSize * 8);
        barImg.setPosition(pos.x, pos.y - barImg.getHeight());

        for (int i = 0; i < buttons.size(); ++i) {
            buttons.get(i).setHeight(tileSize * 2 - pad * 2);
            buttons.get(i).setWidth(tileSize * 2 - pad * 2);
            buttons.get(i).setPosition(barImg.getX() + pad + tileSize * 2 * i, barImg.getY() + tileSize + pad);
        }

        for (int i = 0; i < labels.size(); ++i) {
            labels.get(i).setHeight(tileSize * 1.25f - pad * 2);
            labels.get(i).setWidth(tileSize * 2 - pad * 2);
            labels.get(i).setPosition(buttons.get(i).getX(), buttons.get(i).getY() - tileSize);
        }

        for (int i = 0; i < towers.size(); ++i) {
            towers.get(i).setHeight(tileSize);
            towers.get(i).setWidth(tileSize);
            towers.get(i).setPosition(barImg.getX() + tileSize * (2.5f + 2 * i), barImg.getY() + tileSize * 1.5f);
        }

        for (int i = 0; i < moneyAndCost.size(); ++i) {
            moneyAndCost.get(i).setFontScale(GameConfig.getScreenScaleX() / 2);
            float posx = labels.get(i).getX() + (labels.get(i).getWidth() - moneyAndCost.get(i).getWidth() * moneyAndCost.get(i).getFontScaleX()) / 2;
            moneyAndCost.get(i).setAlignment(Align.bottomLeft);
            moneyAndCost.get(i).setPosition(posx, labels.get(i).getY() + pad / 2);
        }
        moneyAndCost.get(0).setX(labels.get(0).getX());

        float bountyImgX = buttons.get(0).getX() + (buttons.get(0).getWidth() - bountyImg.getWidth()) / 2;
        float bountyImgY = buttons.get(0).getY() + (buttons.get(0).getHeight() - bountyImg.getHeight()) / 2;
        bountyImg.setPosition(bountyImgX, bountyImgY);

        hpImg.setHeight(tileSize);
        hpImg.setWidth(tileSize);
        hpImg.setPosition(Gdx.graphics.getWidth() - tileSize * 10.5f, tileSize / 2);
        hpLabel.setFontScale(GameConfig.getScreenScaleY() * 0.75f);
        hpLabel.setPosition(hpImg.getX() + hpImg.getWidth(), hpImg.getY());

        startButton.setPosition(Gdx.graphics.getWidth() - tileSize * 3, tileSize / 2);
        stageLabel.setFontScale(GameConfig.getScreenScaleY() / 2);
        stageLabel.setAlignment(Align.bottomLeft);
        stageLabel.setPosition(Gdx.graphics.getWidth() - tileSize * 7.25f, startButton.getY());

        menuButton.setPosition(Gdx.graphics.getWidth() - tileSize * 2, Gdx.graphics.getHeight() - tileSize * 2);
        autoplayLabel.setPosition(menuButton.getX() - autoplayLabel.getWidth() - tileSize / 2, menuButton.getY());
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
        if (stageId >= STAGE_COUNT) return;
        for (SpawnTimer spawnTimer: stages[stageId]) enemyQueue.addLast(spawnTimer);
        ++stageId;
        stageLabel.setText("Level " + stageId);
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
        if (--hp == 0) main.changeScreen(ScreenType.ENDGAME);
        hpLabel.setText("x" + hp);
        saveGame();
        if (autoplayMode) autoplay();
    }

    private void setMoney(int money) {
        this.money = money;
        if (moneyAndCost != null) moneyAndCost.get(0).setText(String.format("%5d",money));
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
        if (autoplayMode) autoplayLabel.setText("AUTO"); else autoplayLabel.setText("MANUAL");
        if (autoplayMode) autoplay();
    }

    private void autoplay() {
        ArrayList<Position>[] positionList = new ArrayList[5];
        for (int i = 0; i < positionList.length; ++i) positionList[i] = new ArrayList<Position>();
        for (int j = GameConfig.VIEWPORT_WIDTH; j >= 0; --j)
            for (int i = 0; i <= GameConfig.VIEWPORT_HEIGHT; ++i) {
                if (map[i][j] != GameTile.TileType.MOUNTAIN.ordinal()) continue;
                int adjacentRoadCount = 0;
                for (int di = -2; di <= 2; ++di)
                    for (int dj = Math.abs(di) - 2; dj <= 2 - Math.abs(di); dj += 2) {
                        int adji = i + di;
                        int adjj = j + dj;
                        if (GameConfig.insideViewport(adjj, adji) && map[adji][adjj] == GameTile.TileType.ROAD.ordinal()) ++adjacentRoadCount;
                    }
                positionList[adjacentRoadCount].add(new Position(i, j));
            }
        for (int i = positionList.length - 1; i > 1; --i) {
            //int buildCost = Tower.getBuildCost(Tower.TowerType.MACHINE_GUN);
            int buildCost = Tower.getBuildCost(Tower.TowerType.NORMAL);
            for (Position position: positionList[i]) {
                if (canAfford(buildCost)) {
                    spendMoney(buildCost);
                    //field.setMap(position.col, GameConfig.VIEWPORT_HEIGHT - position.row, GameTile.TileType.MACHINE_GUN_TOWER);
                    field.setMap(position.col, GameConfig.VIEWPORT_HEIGHT - position.row, GameTile.TileType.NORMAL_TOWER);
                }
            }
        }

        Collections.shuffle(positionList[1]);
        for (Position position: positionList[1]) {
            int buildCost = Tower.getBuildCost(Tower.TowerType.SNIPER);
            if (canAfford(buildCost)) {
                spendMoney(buildCost);
                field.setMap(position.col, GameConfig.VIEWPORT_HEIGHT - position.row, GameTile.TileType.SNIPER_TOWER);
            }
            else {
                buildCost = Tower.getBuildCost(Tower.TowerType.NORMAL);
                if (canAfford(buildCost)) {
                    spendMoney(buildCost);
                    field.setMap(position.col, GameConfig.VIEWPORT_HEIGHT - position.row, GameTile.TileType.NORMAL_TOWER);
                }
                else {
                    nextStage();
                    return;
                }
            }
        }

        Collections.shuffle(positionList[0]);
        for (Position position: positionList[0]) {
            //int buildCost = Tower.getBuildCost(Tower.TowerType.SNIPER);
            int buildCost = Tower.getBuildCost(Tower.TowerType.NORMAL);
            if (canAfford(buildCost)) {
                spendMoney(buildCost);
                //field.setMap(position.col, GameConfig.VIEWPORT_HEIGHT - position.row, GameTile.TileType.SNIPER_TOWER);
                field.setMap(position.col, GameConfig.VIEWPORT_HEIGHT - position.row, GameTile.TileType.NORMAL_TOWER);
            }
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
