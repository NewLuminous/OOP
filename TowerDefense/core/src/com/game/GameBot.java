package com.game;

import com.game.entity.tile.GameTile;
import com.game.entity.tile.tower.Tower;

import java.util.ArrayList;
import java.util.Collections;

public class GameBot {
    private int[][] map;
    private int nextChoice = -1;

    public GameBot(int[][] map) {
        this.map = map;
    }

    private ArrayList<Position>[] scanMap() {
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
        return positionList;
    }

    public Tower.TowerType query(int money, Position pos) {
        ArrayList<Position>[] positionList = scanMap();
        for (int i = positionList.length - 1; i > 1; --i) {
            for (Position position: positionList[i]) {
                if (money >= Tower.getBuildCost(Tower.TowerType.MACHINE_GUN)) {
                    pos.set(position);
                    return Tower.TowerType.MACHINE_GUN;
                }
                return null;
            }
        }
        if (nextChoice < 0) {
            double randNum = Math.random();
            if (randNum <= 0.5f) nextChoice = 1; else nextChoice = 0;
            if (positionList[1].size() == 0) nextChoice = 0;
            if (positionList[0].size() == 0) nextChoice = 1;
            if (positionList[1].size() == 0 && positionList[0].size() == 0) nextChoice = -1;
        }
        if (nextChoice == 1) {
            if (money < Tower.getBuildCost(Tower.TowerType.NORMAL)) return null;
            nextChoice = -1;
            Collections.shuffle(positionList[1]);
            pos.set(positionList[1].get(0));
            return Tower.TowerType.NORMAL;
        }
        else if (nextChoice == 0) {
            if (money < Tower.getBuildCost(Tower.TowerType.SNIPER)) return null;
            nextChoice = -1;
            Collections.shuffle(positionList[0]);
            pos.set(positionList[0].get(0));
            return Tower.TowerType.SNIPER;
        }
        return null;
    }
}
