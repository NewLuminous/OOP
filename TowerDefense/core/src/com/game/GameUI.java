package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Align;
import com.game.entity.tile.tower.Tower;
import com.game.util.loader.GameLoader;

import java.util.ArrayList;

public class GameUI {
    public enum WidgetType {
        MENU, START, AUTOPLAY, NORMAL_TOWER, MACHINE_GUN_TOWER, SNIPER_TOWER
    }

    private Image barImg, hpImg, menuButton, startButton;
    private Label bountyImg, stageLabel, hpLabel, autoplayLabel;
    private ArrayList<Image> labels, buttons;
    public ArrayList<Image> towers;
    private ArrayList<Label> moneyAndCost;

    public GameUI(GameField field, int stage, int hp, int money) {
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

        stageLabel = new Label("Level " + stage, new Label.LabelStyle(blocksFont, Color.GOLD));
        field.addActor(stageLabel);

        startButton = new Image((Texture) GameLoader.manager.get(GameLoader.START));
        field.addActor(startButton);

        menuButton = new Image((Texture) GameLoader.manager.get(GameLoader.MENU));
        field.addActor(menuButton);

        autoplayLabel = new Label("MANUAL", new Label.LabelStyle(futureFont, Color.CHARTREUSE));
        field.addActor(autoplayLabel);

        show();
    }

    public void show() {
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

    public void setStage(int stage) {
        stageLabel.setText("Level " + stage);
    }

    public void setHp(int hp) {
        hpLabel.setText("x" + hp);
    }

    public void setMoney(int money) {
        if (moneyAndCost != null) moneyAndCost.get(0).setText(String.format("%5d",money));
    }

    public void setAutoplayMode(boolean autoplayMode) {
        if (autoplayMode) autoplayLabel.setText("AUTO"); else autoplayLabel.setText("MANUAL");
    }

    public boolean isWidgetClicked(WidgetType widgetType, Vector2 mouseLocation) {
        Widget widget = null;
        switch (widgetType) {
            case MENU:
                widget = menuButton;
                break;
            case START:
                widget = startButton;
                break;
            case AUTOPLAY:
                widget = autoplayLabel;
                break;
            case NORMAL_TOWER:
                widget = buttons.get(1);
                break;
            case MACHINE_GUN_TOWER:
                widget = buttons.get(2);
                break;
            case SNIPER_TOWER:
                widget = buttons.get(3);
                break;
            default:
                return false;
        }
        if (widget.getX() > mouseLocation.x) return false;
        if (mouseLocation.x > widget.getX() + widget.getWidth()) return false;
        if (widget.getY() > Gdx.graphics.getHeight() - mouseLocation.y) return false;
        if (Gdx.graphics.getHeight() - mouseLocation.y > widget.getY() + widget.getHeight()) return false;
        return true;
    }

    public boolean isTowerClicked(Tower.TowerType tower, Vector2 mouseLocation) {
        switch (tower) {
            case NORMAL:
                return isWidgetClicked(WidgetType.NORMAL_TOWER, mouseLocation);
            case MACHINE_GUN:
                return isWidgetClicked(WidgetType.MACHINE_GUN_TOWER, mouseLocation);
            case SNIPER:
                return isWidgetClicked(WidgetType.SNIPER_TOWER, mouseLocation);
            default:
                return false;
        }
    }
}
