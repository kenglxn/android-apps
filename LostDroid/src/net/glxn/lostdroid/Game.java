package net.glxn.lostdroid;

import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class Game implements ApplicationListener {


    private Random random;

    @Override
    public void create() {
        random = new Random();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());

    }

    @Override
    public void resize(int width, int height) {
        
    }


    @Override
    public void resume() {
    }
}
