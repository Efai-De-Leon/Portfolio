package com.example.efaideleon.super_mario_bros;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Pipe {
    private int posX = 0;
    private int posY = 0;
    private Bitmap frame;
    private int width = 180;
    private int height = 200;
    public Pipe(int x, int y, Bitmap f){
        posX = x;
        posY = y;
        frame = f;

    }

    public void setFrame(Bitmap frame) {
        this.frame = frame;
    }

    public void setPosX(int posX) {
        this.posX += posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    public void setLocationY(int y){
        posY = y;
    }
    public void setLocationX(int x)
    {
        posX = x;
    }
    public Bitmap getFrame() {
        return frame;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
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
