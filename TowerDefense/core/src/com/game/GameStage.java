package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.entity.enemy.Enemy;
import com.game.entity.tile.GameTile;
import com.game.entity.tile.tower.TowerConfig;
import com.game.util.loader.GameLoader;
import com.game.view.ScreenType;

import java.util.ArrayList;

public class GameStage {
    private static final int STAGE_COUNT = 7;

    private class SpawnTimer {
        private Enemy.EnemyType enemy;
        private float countdown;

        private SpawnTimer(Enemy.EnemyType enemy, float countdown) {
            this.enemy = enemy;
            this.countdown = countdown;
        }
    }

    public Main main;
    private GameField field;
    public Image mapImg, startButton;
    private Image barImg, hpImg;
    private Label bountyImg, stageLabel, hpLabel;
    public ArrayList<Image> buttons, labels, towers;
    private ArrayList<Label> moneyAndCost;

    private Queue<SpawnTimer> enemyQueue;
    private ArrayList<SpawnTimer>[] stages;
    private int stageId;

    private int hp = 100;
    private int money = 1000;

    public GameStage(Main main) {
        this.main = main;
        field = new GameField(this);
        loadMap();
        setupUI();
        showUI();
        setupStages();
        enemyQueue = new Queue<SpawnTimer>();
    }

    private void loadMap() {
        mapImg = new Image((Texture) GameLoader.manager.get(GameLoader.MAP));
        mapImg.setWidth(Gdx.graphics.getWidth());
        mapImg.setHeight(Gdx.graphics.getHeight());
        field.addActor(mapImg);

        FileHandle handle = Gdx.files.internal(GameConfig.MAP1);
        String[] content = handle.readString().split("\\W+");
        for (int i = 0, k = 0; i <= GameConfig.VIEWPORT_HEIGHT; ++i)
            for (int j = 0; j <= GameConfig.VIEWPORT_WIDTH; ++j) {
                GameTile.TileType tile = GameTile.TileType.values()[Integer.parseInt(content[k++])];
                field.setMap(j, GameConfig.VIEWPORT_HEIGHT - i, tile);
            }
    }

    private void setupUI() {
        barImg = new Image((Texture) GameLoader.manager.get(GameLoader.ITEM_BAR));
        field.addActor(barImg);

        Image bountyButton = new Image((Texture) GameLoader.manager.get(GameLoader.BOUNTY));
        Image normalTowerButton = new Image((Texture) GameLoader.manager.get(GameLoader.NORMAL_TOWER_BUTTON));
        Image sniperTowerButton = new Image((Texture) GameLoader.manager.get(GameLoader.SNIPER_TOWER_BUTTON));
        Image machineTowerButton = new Image((Texture) GameLoader.manager.get(GameLoader.MACHINE_GUN_TOWER_BUTTON));
        buttons = new ArrayList<Image>();
        buttons.add(bountyButton);
        buttons.add(normalTowerButton);
        buttons.add(sniperTowerButton);
        buttons.add(machineTowerButton);
        for (int i = 0; i < buttons.size(); ++i) field.addActor(buttons.get(i));

        Image bountyLabel = new Image((Texture) GameLoader.manager.get(GameLoader.BOUNTY_SUM));
        Image normalTowerLabel = new Image((Texture) GameLoader.manager.get(GameLoader.NORMAL_TOWER_COST));
        Image sniperTowerLabel = new Image((Texture) GameLoader.manager.get(GameLoader.SNIPER_TOWER_COST));
        Image machineTowerLabel = new Image((Texture) GameLoader.manager.get(GameLoader.MACHINE_GUN_TOWER_COST));
        labels = new ArrayList<Image>();
        labels.add(bountyLabel);
        labels.add(normalTowerLabel);
        labels.add(sniperTowerLabel);
        labels.add(machineTowerLabel);
        for (int i = 0; i < labels.size(); ++i) field.addActor(labels.get(i));

        Image normalTowerImg = new Image((Texture) GameLoader.manager.get(GameLoader.NORMAL_TOWER_HEAD));
        Image sniperTowerImg = new Image((Texture) GameLoader.manager.get(GameLoader.SNIPER_TOWER_HEAD));
        Image machineTowerImg = new Image((Texture) GameLoader.manager.get(GameLoader.MACHINE_GUN_TOWER_HEAD));
        towers = new ArrayList<Image>();
        towers.add(normalTowerImg);
        towers.add(sniperTowerImg);
        towers.add(machineTowerImg);
        for (int i = 0; i < towers.size(); ++i) field.addActor(towers.get(i));

        BitmapFont pixelFont = GameLoader.getManager().get(GameLoader.KENNEY_PIXEL_72);
        BitmapFont blocksFont = GameLoader.getManager().get(GameLoader.KENNEY_BLOCKS_60);

        Label money = new Label(getMoney() + "", new Label.LabelStyle(pixelFont, Color.BLACK));
        Label normalTowerCost = new Label(TowerConfig.NORMAL_TOWER_COST + "", new Label.LabelStyle(pixelFont, Color.BLACK));
        moneyAndCost = new ArrayList<Label>();
        moneyAndCost.add(money);
        moneyAndCost.add(normalTowerCost);
        for (int i = 0; i < moneyAndCost.size(); ++i) field.addActor(moneyAndCost.get(i));

        bountyImg = new Label("$", new Label.LabelStyle(pixelFont, Color.WHITE));
        field.addActor(bountyImg);

        hpImg = new Image((Texture) GameLoader.manager.get(GameLoader.HP));
        field.addActor(hpImg);
        hpLabel = new Label("x" + hp, new Label.LabelStyle(pixelFont, Color.WHITE));
        field.addActor(hpLabel);

        stageLabel = new Label("Level 0", new Label.LabelStyle(blocksFont, Color.GOLD));
        field.addActor(stageLabel);

        startButton = new Image((Texture) GameLoader.manager.get(GameLoader.START));
        field.addActor(startButton);
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
            moneyAndCost.get(i).setPosition(posx, labels.get(i).getY() - pad * (1.5f - GameConfig.getScreenScaleY()));
        }

        float bountyImgX = buttons.get(0).getX() + (buttons.get(0).getWidth() - bountyImg.getWidth()) / 2;
        float bountyImgY = buttons.get(0).getY() + (buttons.get(0).getHeight() - bountyImg.getHeight()) / 2;
        bountyImg.setPosition(bountyImgX, bountyImgY);

        hpImg.setHeight(tileSize);
        hpImg.setWidth(tileSize);
        hpImg.setPosition(tileSize * 21.5f, tileSize / 2);
        hpLabel.setFontScale(GameConfig.getScreenScaleY() * 0.75f);
        hpLabel.setPosition(hpImg.getX() + hpImg.getWidth(), hpImg.getY());

        stageLabel.setFontScale(GameConfig.getScreenScaleY() / 2);
        stageLabel.setPosition(tileSize * 25, (GameConfig.getScreenScaleY() - 1) * pad);
        startButton.setPosition(stageLabel.getX() + stageLabel.getWidth() * stageLabel.getFontScaleX() + tileSize / 2, tileSize / 2);
    }

    private void setupStages() {
        stages = new ArrayList[STAGE_COUNT];
        for (int i = 0; i < STAGE_COUNT; ++i) stages[i] = new ArrayList<SpawnTimer>();
        stageId = 0;

        stages[0].add(new SpawnTimer(Enemy.EnemyType.NORMAL, 1));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.NORMAL, 0.1f));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.NORMAL, 2));
        stages[0].add(new SpawnTimer(Enemy.EnemyType.NORMAL, 0.5f));

        for (int i = 0; i < 100; ++i)
            stages[1].add(new SpawnTimer(Enemy.EnemyType.NORMAL, 0.2f));
    }

    public void nextStage() {
        if (stageId == STAGE_COUNT) return;
        for (SpawnTimer spawnTimer: stages[stageId]) enemyQueue.addLast(spawnTimer);
        ++stageId;
        stageLabel.setText("Level " + stageId);
    }

    public void act(float delta) {
        if (enemyQueue.notEmpty()) {
            enemyQueue.first().countdown -= delta;
            if (enemyQueue.first().countdown <= 0) {
                field.spawnEnemy(enemyQueue.first().enemy);
                enemyQueue.removeFirst();
            }
        }
        field.act(delta);
    }

    public void damageHP() {
        if (--hp == 0) main.changeScreen(ScreenType.ENDGAME);
        hpLabel.setText("x" + hp);
    }

    public int getMoney() {
        return money;
    }

    public void spendMoney(int cost) {
        if (cost > money) return;
        money -= cost;
        moneyAndCost.get(0).setText(money + "");
    }

    public void draw() {
        field.draw();
    }

    public Viewport getViewport() {
        return field.getViewport();
    }

    public void dispose() {
        field.dispose();
    }
}
