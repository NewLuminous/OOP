package com.game.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class GameController implements InputProcessor {
    public boolean left, right, up, down;
    public boolean escape, space;
    public boolean[] num = new boolean[10];

    public boolean isMouse1Down, isMouse2Down, isMouse3Down;
    private boolean isMouse1Up, isMouse2Up, isMouse3Up;
    public boolean isDragged;
    public Vector2 mouseLocation = new Vector2();

    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = true;
        switch (keycode) {
            case Input.Keys.LEFT:
                left = true;
                break;
            case Input.Keys.RIGHT:
                right = true;
                break;
            case Input.Keys.UP:
                up = true;
                break;
            case Input.Keys.DOWN:
                down = true;
                break;
            case Input.Keys.ESCAPE:
                escape = true;
                break;
            case Input.Keys.SPACE:
                space = true;
                break;
            case Input.Keys.NUM_1:
                num[1] = true;
                break;
            case Input.Keys.NUM_2:
                num[2] = true;
                break;
            case Input.Keys.NUM_3:
                num[3] = true;
                break;
            default:
                keyProcessed = false;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = true;
        switch (keycode) {
            case Input.Keys.LEFT:
                left = false;
                break;
            case Input.Keys.RIGHT:
                right = false;
                break;
            case Input.Keys.UP:
                up = false;
                break;
            case Input.Keys.DOWN:
                down = false;
                break;
            case Input.Keys.ESCAPE:
                escape = false;
                break;
            case Input.Keys.SPACE:
                space = false;
                break;
            case Input.Keys.NUM_1:
                num[1] = false;
                break;
            case Input.Keys.NUM_2:
                num[2] = false;
                break;
            case Input.Keys.NUM_3:
                num[3] = false;
                break;
            default:
                keyProcessed = false;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == 0) {
            isMouse1Down = true;
            isMouse1Up = false;
        } else if (button == 1) {
            isMouse2Down = true;
            isMouse2Up = false;
        } else if (button == 2) {
            isMouse3Down = true;
            isMouse3Up = false;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragged = false;
        if (button == 0) {
            isMouse1Down = false;
            isMouse1Up = true;
        } else if (button == 1) {
            isMouse2Down = false;
            isMouse2Up = true;
        } else if (button == 2) {
            isMouse3Down = false;
            isMouse3Up = true;
        }
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isDragged = true;
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseLocation.x = screenX;
        mouseLocation.y = screenY;
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isMouse1Click() {
        if (isMouse1Up) {
            isMouse1Up = false;
            return true;
        }
        return false;
    }

    public boolean isMouse2Click() {
        if (isMouse2Up) {
            isMouse2Up = false;
            return true;
        }
        return false;
    }
}
