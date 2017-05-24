package com.corsom.phew.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import org.w3c.dom.css.Rect;

public class Phew {
    private static final int Gravity = -15;
    private static final int Movement = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;
    private Animation phewAnimation;
    private Texture texture;
    private Sound flap;

    public Phew(int x, int y){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        texture = new Texture("phewanimation.png");
        phewAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x, y, texture.getWidth() / 3, texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("wind.ogg"));
    }

    public void update(float dt){
        phewAnimation.update(dt);
        if(position.y > 0)
            velocity.add(0, Gravity, 0);
        velocity.scl(dt);
        position.add(Movement * dt, velocity.y, 0);
        if(position.y < 0)
            position.y = 0;

        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return phewAnimation.getFrame();
    }

    public void jump(){
        velocity.y = 250;
        flap.play(0.5f);
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        texture.dispose();
        flap.dispose();
    }

}