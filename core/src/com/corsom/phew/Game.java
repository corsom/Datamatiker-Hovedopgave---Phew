package com.corsom.phew;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.corsom.phew.states.GameStateManager;
import com.corsom.phew.states.MenuState;

public class Game extends ApplicationAdapter
{
    public static final int screenWidth = 480;
    public static final int screenHeight = 800;
    public static final String title = "Phew";

    private GameStateManager gsm;
    private SpriteBatch batch;

    public static int score;
    public static int highscore1;
    public static int highscore2;
    public static int highscore3;
    public static int highscore4;
    public static int highscore5;

    private Music music;

    public static Preferences preferences;

	@Override
	public void create ()
    {
		batch = new SpriteBatch();
        gsm = new GameStateManager();
        music = Gdx.audio.newMusic(Gdx.files.internal("wind.wav"));
        music.setLooping(true);
        music.setVolume(0.1f);
        music.play();
        preferences = Gdx.app.getPreferences("My Preferences");
        loadHighscores();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new MenuState(gsm));
	}

	@Override
	public void render ()
    {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
	}

    @Override
    public void dispose()
    {
        super.dispose();
        music.dispose();
    }

    public static void loadHighscores()
    {
        highscore1 = preferences.getInteger("highscore1");
        highscore2 = preferences.getInteger("highscore2");
        highscore3 = preferences.getInteger("highscore3");
        highscore4 = preferences.getInteger("highscore4");
        highscore5 = preferences.getInteger("highscore5");
    }

    public static void saveHighscores()
    {
        preferences.putInteger("highscore1", highscore1);
        preferences.putInteger("highscore2", highscore2);
        preferences.putInteger("highscore3", highscore3);
        preferences.putInteger("highscore4", highscore4);
        preferences.putInteger("highscore5", highscore5);
        preferences.flush();
    }
}
