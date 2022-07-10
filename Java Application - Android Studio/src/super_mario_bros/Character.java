package com.example.efaideleon.super_mario_bros;

import android.graphics.Bitmap;

public class Character implements com.example.efaideleon.super_mario_bros.Moveable {
    private int posX = 0;
    private int posY = 0;
    private Bitmap frame;
    private Bitmap[] Frames;
    public Character(int x, int y, Bitmap[] cF) {
        posX = x;
        posY = y;
        Frames = cF;
        frame = cF[3];
    }
    @Override
    public int getPosX() {
        return posX;
    }
    @Override
    public int getPosY() {
        return posY;
    }
    @Override
    public Bitmap getCurrentFrame() {
        return frame;
    }
    @Override
    public void setPosX(int x) {
        posX += x;
    }
    @Override
    public void setPosY(int y) {
        posY += y;
    }
    public void setLocationX(int x) {
        posX = x;
    }
    public void setLocationY(int y) {
        posY = y;
    }
    @Override
    public void setCurrentFrame(int index) {
        frame = Frames[index];
    }

    public void setFrames(Bitmap[] f){
        Frames = f;
    }
}
