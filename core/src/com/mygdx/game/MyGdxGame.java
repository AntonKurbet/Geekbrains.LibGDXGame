package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BattleField battleField;

    public MyGdxGame() {

    }

    // Домашнее задание:
    // 1. Не дайте танку уехать за пределы экрана
    // 2. * Попробуйте добавить мишень и попадание по ней

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        battleField = new BattleField(1280,720);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);
        Gdx.gl.glClearColor(0.5f, 1.0f, 0.5f, 0.8f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        battleField.render(batch);
        batch.end();

        battleField.renderShape(shapeRenderer);
    }

    public void update(float dt) {
        battleField.update(dt);
//        if (Gdx.input.justTouched()) {
//            System.out.println(Gdx.input.getX());
//            System.out.println(Gdx.graphics.getHeight() - Gdx.input.getY());
//        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        battleField.dispose();
    }
}
