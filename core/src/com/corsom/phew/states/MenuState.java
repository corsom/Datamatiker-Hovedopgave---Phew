package com.corsom.phew.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.corsom.phew.Game;

public class MenuState extends State
{
    private Texture background;
    private Texture title;
    private Texture playButton;
    private Texture highscorebutton;
    private Texture facebookbutton;

    private Rectangle playButtonBounds;
    private Rectangle highscoreButtonBounds;
    private Rectangle facebookButtonBounds;

    private Vector3 touchVector;

    public MenuState(GameStateManager gsm)
    {
        super(gsm);
        cam.setToOrtho(false, Game.screenWidth / 2, Game.screenHeight / 2);

        background = new Texture("menubg.png");
        title = new Texture("title.png");
        playButton = new Texture("playbutton.png");
        highscorebutton = new Texture("highscorebutton.png");
        facebookbutton = new Texture("facebookbutton.png");

        playButtonBounds = new Rectangle
                (cam.viewportWidth / 2 - playButton.getWidth() / 2, cam.viewportHeight - 200, playButton.getWidth(), playButton.getHeight());
        highscoreButtonBounds = new Rectangle
                (cam.viewportWidth / 2 - highscorebutton.getWidth() / 2, cam.viewportHeight - 265, highscorebutton.getWidth(), highscorebutton.getHeight());
        facebookButtonBounds = new Rectangle
                (cam.viewportWidth / 2 - facebookbutton.getWidth() / 2, cam.viewportHeight - 330, facebookbutton.getWidth(), facebookbutton.getHeight());
    }

    @Override
    public void handleInput()
    {
        if (Gdx.input.isTouched())
        {
            touchVector = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchVector);

            if (playButtonBounds.contains(touchVector.x, touchVector.y))
            {
                gsm.set(new PlayState(gsm));
            }

            if (highscoreButtonBounds.contains(touchVector.x, touchVector.y))
            {
                gsm.set(new HighscoreState(gsm));
            }

            if (facebookButtonBounds.contains(touchVector.x, touchVector.y))
            {
                Gdx.net.openURI("https://www.facebook.com/Phew-1359824807447118/");
            }
        }
    }

    @Override
    public void update(float dt)
    {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(title, cam.viewportWidth / 2 - title.getWidth() / 2, cam.viewportHeight - 120);
        sb.draw(playButton, cam.viewportWidth / 2 - playButton.getWidth() / 2, cam.viewportHeight - 200);
        sb.draw(highscorebutton, cam.viewportWidth / 2 - highscorebutton.getWidth() / 2, cam.viewportHeight - 265);
        sb.draw(facebookbutton, cam.viewportWidth / 2 - facebookbutton.getWidth() / 2, cam.viewportHeight - 330);
        sb.end();
    }

    @Override
    public void dispose()
    {
        background.dispose();
        title.dispose();
        playButton.dispose();
        highscorebutton.dispose();
        facebookbutton.dispose();
    }
}