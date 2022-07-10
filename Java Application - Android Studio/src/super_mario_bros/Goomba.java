package com.example.efaideleon.super_mario_bros;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Goomba{

    private int posX = 0;
    private int posY = 0;
    private Bitmap frame;
    private Bitmap[] Frames;
    private int width = 100;
    private int height = 100;
    private boolean walkRight = true;
    private boolean walkLeft = false;
    public Goomba(int x, int y, Bitmap[] cF) {
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
    public void setLocationY(int y){
        posY = y;
    }
    public void setLocationX(int x)
    {
        posX = x;
    }
    public void goombaAi(){
        if(walkRight){
            posX +=3;
        }
        if(walkLeft){
            posX -=3;
        }
    }

    public void changeDirection(){
        System.out.println("CHANGING GOOMBA DIRECTION");
        if(walkRight)
        {
            walkLeft = true;
            walkRight = false;
        }
        else{
            walkRight = true;
            walkLeft = false;
        }
    }



}
