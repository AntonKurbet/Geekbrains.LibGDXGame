package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class Tank extends MyTexture{
    private Texture textureWeapon;
    private float speedForward;
    private float speedBack;
    private float angle;
    private float angleWeapon;
    private Projectile projectile;
    private BitmapFont font;
    private float borderAngle;
    private BattleField battleField;
    private Rectangle br;
    private Polygon bp;

    public Tank(BattleField battleField) {
        super("tank.png");
        this.textureWeapon = new Texture("weapon.png");
        this.projectile = new Projectile(battleField);
        this.x = 100.0f;
        this.y = 100.0f;
        this.speedForward = 240.0f;
        this.speedBack = this.speedForward * 0.2f;
        this.scale = 3.0f;

        this.borderAngle = MathUtils.atan2(halfY, halfX) * MathUtils.radiansToDegrees;

        this.battleField = battleField;
        this.font = new BitmapFont();
        this.br = new Sprite(texture).getBoundingRectangle();
    }

    public void update(float dt) {
        float newX = x;
        float newY = y;
        int direction = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            angle -= 90.0f * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            angle += 90.0f * dt;
        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
//            angle -= 90.0f;
//        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
//            angle += 90.0f;
//        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newX += speedForward * MathUtils.cosDeg(angle) * dt;
            newY += speedForward * MathUtils.sinDeg(angle) * dt;
            direction = +1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newX -= speedBack * MathUtils.cosDeg(angle) * dt;
            newY -= speedBack * MathUtils.sinDeg(angle) * dt;
            direction = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            angleWeapon -= 90.0f * dt;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            angleWeapon += 90.0f * dt;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !projectile.isActive()) {
            projectile.shoot(x + 16 * scale * MathUtils.cosDeg(angle), y + 16 * scale * MathUtils.sinDeg(angle), angle + angleWeapon);
        }
        if (projectile.isActive()) {
            projectile.update(dt);
        }
        if (updateBorders(newX,newY,direction)) {
            x = newX;
            y = newY;
        }
//        if (checkBorders(newX,newY)) {
//            x = newX;
//            y = newY;
//        }

    }

// Вариант 1 - так работает
    private boolean updateBorders(float newX, float newY, int direction) {
        boolean result = true;
        if (direction != 0) {
            result = !((checkOneRay(newX, newY, direction,borderAngle)) ||
               (checkOneRay(newX, newY, direction,- borderAngle)));
        }
        return result;
    }

    private boolean checkOneRay(float newX, float newY, int direction, float borderAngle) {
        float borderX = newX + direction * MathUtils.cosDeg(angle + borderAngle) * halfX * scale;
        float borderY = newY + direction * MathUtils.sinDeg(angle + borderAngle) * halfY * scale;
        return ((borderX < 0) | (borderX > battleField.getSizeX()) | (borderY < 0) | (borderY > battleField.getSizeY())
                | battleField.getTarget().getCircle().contains(borderX, borderY));
    }

// Вариант 2 - так не работает, если не тема урока,
// можно уточнить как работает Intersector.overlapConvexPolygons ?

//    private boolean checkBorders(float newX, float newY) {
//        bp = new Polygon(new float[]{0,0,0,br.height,br.width,br.height,br.width,0});
//        bp.setScale(scale,scale);
//        bp.setOrigin(halfX,halfY);
//        bp.setPosition(newX,newY);
//        bp.setRotation(angle);
//        boolean a = Intersector.overlapConvexPolygons(new Polygon(battleField.getRegion().getTransformedVertices()),
//                                                      new Polygon(bp.getTransformedVertices()));
//        boolean b = battleField.getTarget().getCircle().contains(newX,newY);
//        if (a)
//            debug = true;
//        return !a | !b;
//    }

    public void render(SpriteBatch batch) {
        if (debug) {
            font.draw(batch, String.valueOf(angle), 500, 100);
            font.draw(batch, String.valueOf(borderAngle), 500, 130);
        }
        batch.draw(texture, x - halfX, y - halfY, halfX, halfY,
                sizeX, sizeY, scale, scale, angle, 0, 0, sizeX, sizeY,
                false, false);
        batch.draw(textureWeapon, x - halfX, y - halfY, halfX, halfY,
                sizeX, sizeY, scale, scale, angle + angleWeapon, 0, 0, sizeX, sizeY,
                false, false);
        if (projectile.isActive()) {
            projectile.render(batch);
        }
    }

    public void renderShape(ShapeRenderer shapeRenderer) {
        if (debug) {
            shapeRenderer.begin(ShapeType.Line);
            shapeRenderer.setColor(Color.ORANGE);
            shapeRenderer.polygon(bp.getTransformedVertices());
            shapeRenderer.end();
        }
    }

    public void dispose() {
        texture.dispose();
        projectile.dispose();
    }
}
