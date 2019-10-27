package com.game.entity.tile.terrain;

import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.tile.GameTile;

import java.util.ArrayList;

public class Road extends GameTile {
    private ArrayList<Road> adjacents;
    private boolean onWay = false;
    private Road parent;

    public Road(World world, int posx, int posy) {
        super(world, posx, posy);
        adjacents = new ArrayList<Road>();
    }

    public void addAdjacent(Road adj) {
        adjacents.add(adj);
    }

    public Road DFS() {
        if (this instanceof Target) return this;
        onWay = true;
        for (Road adj: adjacents)
            if (!adj.onWay && adj != parent) {
                if (adj.DFS() != null) {
                    onWay = false;
                    adj.parent = this;
                    return adj;
                }
            }
        onWay = false;
        return null;
    }
}
