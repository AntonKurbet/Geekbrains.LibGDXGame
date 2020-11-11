package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class BattleField {
    private int sizeX;
    private int sizeY;
    private Tank tank;
    private Target target;
    private Polygon region;
    private boolean debug = false;

    public BattleField(int sizeX, int sizeY) {
        tank = new Tank(this);
        target = new Target(this);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.region = new Polygon(new float[]{1,1,1,sizeY - 1,sizeX - 1,sizeY - 1,sizeX - 1,1});
    }
    public Tank getTank() {
        return tank;
    }

    public Target getTarget() {
        return target;
    }

    public Polygon getRegion() {
        return region;
    }

    public void render(SpriteBatch batch) {
        tank.render(batch);
        target.render(batch);
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        tank.renderShape(shapeRenderer);
        target.renderShape(shapeRenderer);
        if (debug) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.ORANGE);
            shapeRenderer.polygon(region.getVertices());
            shapeRenderer.end();
        }
    }

    public void update(float dt) {
        tank.update(dt);
    }

    public void dispose() {
        tank.dispose();
        target.dispose();
    }

    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }

}
