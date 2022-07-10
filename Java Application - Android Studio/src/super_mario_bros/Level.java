package com.example.efaideleon.super_mario_bros;

import java.util.ArrayList;

public abstract class Level {

    public abstract void gravity();
    public abstract void animation();
    public abstract void goombaAI();
    public abstract void mushroomAI();
    public abstract void piranhaPlantAI();
    public abstract void questionBlockAI();
    public abstract void moveBackgroundLeft();
    public abstract void moveBackgroundRight();
    public abstract void collision();
    public abstract ArrayList<Floor> getPlatforms();
    public abstract ArrayList<Block> getBlockArrList();
    public abstract ArrayList<Boo> getBooArrList();
    public abstract ArrayList<BrickBlock> getBrickBlockArrList();
    public abstract ArrayList<Coin> getCoinArrList();
    public abstract ArrayList<FireFlower> getFireFlowerArrList();
    public abstract ArrayList<Goomba> getGoombaArrList();
    public abstract ArrayList<com.example.efaideleon.super_mario_bros.Mushroom> getMushroomArrList();
    public abstract ArrayList<Pipe> getPipeArrList();
    public abstract ArrayList<com.example.efaideleon.super_mario_bros.PiranhaPlant> getPiranhaPlantArrList();
    public abstract ArrayList<com.example.efaideleon.super_mario_bros.QuestionBlock> getQuestionBlockArrList();
    public abstract ArrayList<Cloud> getCloudArrList();
    public abstract ArrayList<ColorBlocks> getColorBlocksArrList();
    public abstract ArrayList<CastleFloor> getCastleFloorArrList();
    public abstract ArrayList<Goal> getGoalArrList();
    public abstract ArrayList<Bush> getBushArrList();
    public abstract ArrayList<EyeBushes> getEyeBushesArrList();
    public abstract ArrayList<GrassFloor> getGrassFloorArrList();
    public abstract com.example.efaideleon.super_mario_bros.Mario getMario();
    public abstract Block getBlock();
    public abstract int getRed();
    public abstract int getGreen();
    public abstract int getBlue();
    public abstract int getAlpha();
}
