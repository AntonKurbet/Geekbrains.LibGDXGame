package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

abstract class MyTexture {
    Texture texture;
    float x;
    float y;
    int sizeX;
    int sizeY;
    float halfX;
    float halfY;
    float scale;

    boolean debug = false;

    MyTexture() {
    }

    MyTexture(String filename) {
        super();
        this.texture = new Texture(filename);
        this.sizeX = texture.getWidth();
        this.sizeY = texture.getHeight();
        this.halfX = this.sizeX / 2f ;
        this.halfY = this.sizeY / 2f;
    }

    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);
    public abstract void renderShape(ShapeRenderer shapeRenderer);
    public abstract void dispose();
}
