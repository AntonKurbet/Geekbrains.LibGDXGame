package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Projectile extends MyTexture{
    private final BattleField battleField;
    private float vx;
    private float vy;
    private float speed;
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public Projectile(BattleField battleField) {
        super("projectile.png");
        this.speed = 600.0f;
        this.battleField = battleField;
        this.scale = 2f;
    }

    public void shoot(float x, float y, float angle) {
        this.x = x;
        this.y = y;
        this.vx = speed * MathUtils.cosDeg(angle);
        this.vy = speed * MathUtils.sinDeg(angle);
        this.active = true;
    }

    public void update(float dt) {
        x += vx * dt;
        y += vy * dt;
        if (x < 0 || x > battleField.getSizeX() || y < 0 || y > battleField.getSizeY()) {
            deactivate();
        }
        if (battleField.getTarget().getCircle().contains(x,y)) {
            battleField.getTarget().deactivate();
            deactivate();
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x - halfX, y - halfY, halfX, halfY, sizeX, sizeY, scale, scale,
                0, 0, 0, sizeX, sizeY, false, false);
    }

    @Override
    public void renderShape(ShapeRenderer shapeRenderer) {

    }

    public void dispose() {
        texture.dispose();
    }
}
