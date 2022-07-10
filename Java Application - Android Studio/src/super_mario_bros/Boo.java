package com.example.efaideleon.super_mario_bros;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

public class Boo {

    private int posX = 0;
    private int posY = 0;
    private Bitmap frame;
    private Bitmap[] Frames;
    private int width = 90;
    private int height = 90;
    public Boo(int x, int y, Bitmap[] cF) {
        posX = x;
        posY = y;
        Frames = cF;
        frame = cF[0];
    }

    public int getPosX() {
        return posX;
    }
    public void setLocationY(int y){
        posY = y;
    }
    public void setLocationX(int x)
    {
        posX = x;
    }
    public int getPosY() {
        return posY;
    }

    public Bitmap getCurrentFrame() {
        return frame;
    }

    public void setPosX(int x) {
        posX += x;
    }

    public void setPosY(int y) {
        posY += y;
    }
    public void setCurrentFrame(int index) {
        frame = Frames[index];
    }

    public void setFrames(Bitmap[] f){
        Frames = f;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Rect bounds(){
        return (new Rect(posX,posY,posX+ width,posY+ height));
    }
}
