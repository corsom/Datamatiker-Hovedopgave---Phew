package com.corsom.phew.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.corsom.phew.Game;

public class HighscoreState extends State
{

    private Texture background;
    private Texture backButton;

    private BitmapFont font;

    private Vector3 touchVector;
    private Rectangle backButtonBounds;


    public HighscoreState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Game.screenWidth / 2, Game.screenHeight / 2);

        background = new Texture("highscorebg.png");
        backButton = new Texture("backbutton.png");

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1);

        backButtonBounds = new Rectangle
                (cam.viewportWidth / 2 - backButton.getWidth() / 2, cam.viewportHeight / 10, backButton.getWidth(), backButton.getHeight());
    }

    @Override
    public void handleInput()
    {
        if (Gdx.input.isTouched())
        {
            touchVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchVector);

            if (backButtonBounds.contains(touchVector.x, touchVector.y))
            {
                gsm.set(new MenuState(gsm));
            }
        }
    }

    @Override
    public void update(float dt)
    {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        font.draw(sb, "1. " + String.valueOf(Game.highscore1), cam.viewportWidth / 2 - backButton.getWidth() / 2, cam.viewportHeight - 100);
        font.draw(sb, "2. " + String.valueOf(Game.highscore2), cam.viewportWidth / 2 - backButton.getWidth() / 2, cam.viewportHeight - 130);
        font.draw(sb, "3. " + String.valueOf(Game.highscore3), cam.viewportWidth / 2 - backButton.getWidth() / 2, cam.viewportHeight - 160);
        font.draw(sb, "4. " + String.valueOf(Game.highscore4), cam.viewportWidth / 2 - backButton.getWidth() / 2, cam.viewportHeight - 190);
        font.draw(sb, "5. " + String.valueOf(Game.highscore5), cam.viewportWidth / 2 - backButton.getWidth() / 2, cam.viewportHeight - 220);
        sb.draw(backButton, cam.viewportWidth / 2 - backButton.getWidth() / 2, cam.viewportHeight / 10);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        font.dispose();
    }
}