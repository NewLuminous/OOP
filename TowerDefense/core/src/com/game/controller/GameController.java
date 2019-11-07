package com.game.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class GameController implements InputProcessor {
    public boolean left, right, up, down;
    public boolean escape;

    public boolean isMouse1Down, isMouse2Down, isMouse3Down;
    private boolean isMouse1Up, isMouse2Up, isMouse3Up;
    public boolean isDragged;
    public Vector2 mouseLocation = new Vector2();

    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.LEFT:
                left = true;
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                right = true;
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                up = true;
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                down = true;
                keyProcessed = true;
                break;
            case Input.Keys.ESCAPE:
                escape = true;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode) {
            case Input.Keys.LEFT:
                left = false;
                keyProcessed = true;
                break;
            case Input.Keys.RIGHT:
                right = false;
                keyProcessed = true;
                break;
            case Input.Keys.UP:
                up = false;
                keyProcessed = true;
                break;
            case Input.Keys.DOWN:
                down = false;
                keyProcessed = true;
                break;
            case Input.Keys.ESCAPE:
                escape = false;
                keyProcessed = true;
                break;
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
