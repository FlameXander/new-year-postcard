package ru.flamexander.newyear.postcard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private class SnowFlake {
        private Vector2 position;
        private Vector2 velocity;
        private float size;
        private float time;

        public SnowFlake() {
            this.position = new Vector2(MathUtils.random(0, 640), MathUtils.random(0, 1200));
            this.velocity = new Vector2(MathUtils.random(-50, 50), MathUtils.random(-250, -100));
            this.size = MathUtils.random(0.02f, 0.04f);
            this.time = MathUtils.random(0.0f, 10.0f);
        }

        public void draw() {
            batch.draw(textureSnowFlake, position.x - 256, position.y - 256, 256, 256, 512, 512, size + 0.02f * (float) Math.sin(time * 2), size, time * 50, 0, 0, 512, 512, false, false);
            if (MathUtils.random(0, 1000) < 2) {
                batch.draw(textureSnowFlake, position.x - 256, position.y - 256, 256, 256, 512, 512, size + 0.02f * (float) Math.sin(time * 2) + 0.01f, size + 0.01f, time * 50, 0, 0, 512, 512, false, false);
            }
        }

        public void update(float dt) {
            position.mulAdd(velocity, dt);
            time += dt;
            if (position.y < -50) {
                position.set(MathUtils.random(0, 640), 850);
                velocity.set(MathUtils.random(-50, 50), MathUtils.random(-250, -100));
                size = MathUtils.random(0.02f, 0.04f);
            }
        }
    }

    private class Cloud {
        private Vector2 position;
        private float speed;
        private float size;

        public Cloud() {
            this.position = new Vector2(MathUtils.random(0, 1200), MathUtils.random(660, 800));
            this.speed = MathUtils.random(50.0f, 140.0f);
            this.size = MathUtils.random(0.6f, 0.9f);
        }

        public void draw() {
            batch.draw(textureCloud, position.x - 160, position.y - 120, 160, 120, 320, 240, size, size, 0, 0, 0, 320, 240, false, false);
        }

        public void update(float dt) {
            position.x -= speed * dt;
            if (position.x < -200) {
                position.set(MathUtils.random(700, 1000), MathUtils.random(660, 800));
                speed = MathUtils.random(50.0f, 140.0f);
                size = MathUtils.random(0.6f, 0.9f);
            }
        }
    }

    private class GroundBlock {
        private float size;
        private float rotation;

        public GroundBlock() {
            this.size = MathUtils.random(0.3f, 0.5f);
            this.rotation = MathUtils.random(0, 360);
        }
    }


    private SpriteBatch batch;
    private Texture textureSnowFlake;
    private Texture textureText;
    private Texture textureCloud;
    private Texture textureGround;
    private SnowFlake[] snowFlakes;
    private Cloud[] clouds;
    private GroundBlock[] ground;

    @Override
    public void create() {
        batch = new SpriteBatch();
        textureSnowFlake = new Texture("flake.png");
        textureText = new Texture("text.png");
        textureCloud = new Texture("cloud.png");
        textureGround = new Texture("ground.png");
        snowFlakes = new SnowFlake[450];
        for (int i = 0; i < snowFlakes.length; i++) {
            snowFlakes[i] = new SnowFlake();
        }
        clouds = new Cloud[40];
        for (int i = 0; i < clouds.length; i++) {
            clouds[i] = new Cloud();
        }
        ground = new GroundBlock[40];
        for (int i = 0; i < ground.length; i++) {
            ground[i] = new GroundBlock();
        }
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        update(dt);

        ScreenUtils.clear(0.0f, 0.0f, 0.2f, 1f);
        batch.begin();


        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        for (int i = 0; i < snowFlakes.length; i++) {
            snowFlakes[i].draw();
        }
        batch.draw(textureText, 80, 200);
        batch.draw(textureText, 80, 200);

        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.setColor(0.6f, 0.6f, 0.8f, 1.0f);
        for (int i = 0; i < clouds.length; i++) {
            clouds[i].draw();
        }
        batch.setColor(Color.WHITE);

        for (int i = 0; i < ground.length; i++) {
            batch.setColor(0.2f, 0.2f, 0.4f, 1.0f);
            batch.draw(textureGround, i * 50 - 256 - 3, 0 - 256 - 3, 256, 256, 512, 512, ground[i].size, ground[i].size, ground[i].rotation, 0, 0, 512, 512, false, false);
            batch.setColor(0.9f, 0.9f, 1.0f, 1.0f);
            batch.draw(textureGround, i * 50 - 256, 0 - 256, 256, 256, 512, 512, ground[i].size, ground[i].size, ground[i].rotation, 0, 0, 512, 512, false, false);
        }
        batch.setColor(Color.WHITE);

        batch.end();
    }

    public void update(float dt) {
        for (int i = 0; i < snowFlakes.length; i++) {
            snowFlakes[i].update(dt);
        }
        for (int i = 0; i < clouds.length; i++) {
            clouds[i].update(dt);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
