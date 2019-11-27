package com.game;

public class Position {
    public int row, col;

    public Position(int row, int col) {
        set(row, col);
    }

    public void set(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void set(Position that) {
        set(that.row, that.col);
    }
}
