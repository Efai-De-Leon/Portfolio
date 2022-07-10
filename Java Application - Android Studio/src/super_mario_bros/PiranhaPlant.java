package com.example.efaideleon.super_mario_bros;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class PiranhaPlant  {
    private int posX = 0;
    private int posY = 0;
    private Bitmap frame;
    private Bitmap[] Frames;
    private int width = 100;
    private int height = 210;
    private int changeInPosition = 0;
    private int time = 0;
    public PiranhaPlant(int x, int y, Bitmap[] cF) {
        posX = x;
        posY = y;
        Frames = cF;
        frame = cF[0];
    }

    public int getPosX() {
        return posX;
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

    public void setLocationY(int y){
        posY = y;
    }
    public void setLocationX(int x)
    {
        posX = x;
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

    public void piranhaPlantAi(){

        if(changeInPosition <210) {
            posY -= 5;
            changeInPosition += 5;
        }
        if(changeInPosition>=210){

            posY +=5;
            time +=5;
            if(time == 210) {
                changeInPosition = 0;
                time = 0;
            }
        }

    }


}
