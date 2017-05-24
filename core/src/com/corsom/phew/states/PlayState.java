package com.corsom.phew.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.corsom.phew.Game;
import com.corsom.phew.sprites.Phew;
import com.corsom.phew.sprites.Branch;

public class PlayState extends State {
    private static final int BranchSpacing = 200;
    private static final int BranchCount = 4;
    private static final int WaterYOffset = -50;
    private static float movement = 0;
    private static float movementCounter = 0;

    private Phew phew;
    private Texture background;
    private Texture water;
    private Vector2 waterPos1, waterPos2;
    private Vector2 fontPos;
    private BitmapFont font;

    private Array<Branch> branches;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Game.screenWidth / 2, Game.screenHeight / 2);

        phew = new Phew(50, 300);
        background = new Texture("gamebg.png");
        water = new Texture("water.png");
        waterPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, WaterYOffset);
        waterPos2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + water.getWidth(), WaterYOffset);
        fontPos = new Vector2(phew.getPosition().x, phew.getPosition().y / 4);
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(1);

        branches = new Array<Branch>();

        for(int i = 1; i <= BranchCount; i++){
            branches.add(new Branch(i * (BranchSpacing + Branch.BranchWidth)));
        }
    }

    @Override
    protected void handleInput()
    {
        if(Gdx.input.justTouched() && phew.getPosition().y < cam.viewportHeight - phew.getBounds().getHeight())
            phew.jump();
    }

    @Override
    public void update(float dt)
    {
        handleInput();
        updateWater();
        updateScore();
        updateFont();
        phew.update(dt);
        cam.position.x = phew.getPosition().x + 80;

        for(int i = 0; i < branches.size; i++)
        {
            Branch branch = branches.get(i);

            if(cam.position.x - (cam.viewportWidth / 2) > branch.getPosTopBranch().x + branch.getTopBranch().getWidth())
            {
                branch.reposition(branch.getPosTopBranch().x  + ((Branch.BranchWidth + BranchSpacing) * BranchCount));
            }

            if(branch.collides(phew.getBounds()))
            {
                Game.saveHighscores();
                Game.score = 0;
                gsm.set(new MenuState(gsm));
            }
        }

        if(phew.getPosition().y <= water.getHeight() + WaterYOffset)
        {
            Game.saveHighscores();
            Game.score = 0;
            gsm.set(new MenuState(gsm));
        }

        cam.update();

    }

    @Override
    public void render(SpriteBatch sb)
    {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(phew.getTexture(), phew.getPosition().x, phew.getPosition().y);
        for(Branch branch : branches) {
            sb.draw(branch.getTopBranch(), branch.getPosTopBranch().x, branch.getPosTopBranch().y);
            sb.draw(branch.getBottomBranch(), branch.getPosBotBranch().x, branch.getPosBotBranch().y);
        }
        sb.draw(water, waterPos1.x, waterPos1.y);
        sb.draw(water, waterPos2.x, waterPos2.y);
        font.draw(sb, "Score: " + String.valueOf(Game.score), fontPos.x, fontPos.y);
        sb.end();
    }

    @Override
    public void dispose()
    {
        background.dispose();
        phew.dispose();
        water.dispose();
        font.dispose();
        for(Branch branch : branches)
            branch.dispose();
    }

    private void updateWater()
    {
        if(cam.position.x - (cam.viewportWidth / 2) > waterPos1.x + water.getWidth())
            waterPos1.add(water.getWidth() * 2, 0);
        if(cam.position.x - (cam.viewportWidth / 2) > waterPos2.x + water.getWidth())
            waterPos2.add(water.getWidth() * 2, 0);
    }

    private void updateFont()
    {
        fontPos.x = cam.position.x - 110;
        fontPos.y = cam.position.y + 190;
    }

    private void updateScore()
    {
        Game.score++;

        if (Game.score > Game.highscore1)
        {
            Game.highscore1 = Game.score;
        } else if (Game.score > Game.highscore2)
        {
            Game.highscore2 = Game.score;
        } else if (Game.score > Game.highscore3)
        {
            Game.highscore3 = Game.score;
        } else if (Game.score > Game.highscore4)
        {
            Game.highscore4 = Game.score;
        } else if (Game.score > Game.highscore5)
        {
            Game.highscore5 = Game.score;
        }
    }
}
