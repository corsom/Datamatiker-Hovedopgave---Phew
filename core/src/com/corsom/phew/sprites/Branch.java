package com.corsom.phew.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Branch {
    public static final int BranchWidth= 52;

    private static final int Fluctuation = 130;
    private static final int BranchGap = 130;
    private static final int LowestOpening = 120;
    private Texture topBranch, bottomBranch;
    private Vector2 posTopBranch, posBotBranch;
    private Rectangle boundsTop, boundsBot;
    private Random rand;


    public Branch(float x){
        topBranch = new Texture("topbranch.png");
        bottomBranch = new Texture("bottombranch.png");
        rand = new Random();


        posTopBranch = new Vector2(x, rand.nextInt(Fluctuation) + BranchGap + LowestOpening);
        posBotBranch = new Vector2(x, posTopBranch.y - BranchGap - bottomBranch.getHeight());

        boundsTop = new Rectangle(posTopBranch.x, posTopBranch.y, topBranch.getWidth(), topBranch.getHeight());
        boundsBot = new Rectangle(posBotBranch.x, posBotBranch.y, bottomBranch.getWidth(), bottomBranch.getHeight());


    }

    public Texture getBottomBranch() {
        return bottomBranch;
    }

    public Texture getTopBranch() {
        return topBranch;
    }

    public Vector2 getPosTopBranch() {
        return posTopBranch;
    }

    public Vector2 getPosBotBranch() {
        return posBotBranch;
    }

    public void reposition(float x){
        posTopBranch.set(x, rand.nextInt(Fluctuation) + BranchGap+ LowestOpening);
        posBotBranch.set(x, posTopBranch.y - BranchGap- bottomBranch.getHeight());
        boundsTop.setPosition(posTopBranch.x, posTopBranch.y);
        boundsBot.setPosition(posBotBranch.x, posBotBranch.y);
    }

    public boolean collides(Rectangle player){
        return player.overlaps(boundsTop) || player.overlaps(boundsBot);
    }

    public void dispose(){
        topBranch.dispose();
        bottomBranch.dispose();
    }
}
