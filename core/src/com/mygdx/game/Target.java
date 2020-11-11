package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.*;

public class Target extends MyTexture {
    private float radius;
    private Circle circle;
    private BattleField battleField;
    private boolean active;

    Target(BattleField battleField) {
        this.x = 300;
        this.y = 300;
        this.radius = 50;
        this.battleField = battleField;
        circle = new Circle(x,y,radius);
        this.active = true;
    }
    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {
        if (active) {
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.circle(x, y, radius);
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.circle(x, y, radius / 2f);
            shapeRenderer.setColor(Color.BLUE);
            shapeRenderer.circle(x, y, radius / 4f);
            shapeRenderer.end();
        }
        if (debug) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.ORANGE);
            shapeRenderer.circle(x, y, radius);
            shapeRenderer.end();
        }

    }

    @Override
    public void dispose() {
        if (texture != null) texture.dispose();
    }

    public Circle getCircle() {return circle;}

    public void deactivate() {
        this.active = false;
        x = (float) (radius + (battleField.getSizeX() - 2 * radius) * Math.random());
        y = (float) (radius + (battleField.getSizeY() - 2 * radius) * Math.random());
        circle = new Circle(x,y,radius);
        this.active = true;
    }
}
