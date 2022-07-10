package com.example.efaideleon.super_mario_bros;

import android.graphics.Rect;
import android.graphics.Bitmap;

class Cloud {

    private int posX = 0;
    private int posY = 0;
    private Bitmap frame;
    private int width = 150;
    private int height = 150;
    private int changeInPosition = 0;
    public Cloud(int x, int y, Bitmap f){
        posX = x;
        posY = y;
        frame = f;

    }

    public void setFrame(Bitmap frame) {
        this.frame = frame;
    }

    public void setLocationY(int y){
        posY = y;
    }
    public void setLocationX(int x)
    {
        posX = x;
    }
    public void setPosX(int posX) {
        this.posX += posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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

    public Rect bounds(){
        return (new Rect(posX,posY,posX+width,posY+ height));
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void moveUp(){
        if(changeInPosition == 0) {
            posY = posY - 70;
            changeInPosition = 1;
        }
    }
    public int getChangeInPosition(){
        return changeInPosition;
    }
}
