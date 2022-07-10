package com.example.efaideleon.super_mario_bros;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class QuestionBlock {
    private int posX = 0;
    private int posY = 0;
    private Bitmap frame;
    private Bitmap[] Frames;
    private int width = 120;
    private int height = 120;
    private boolean touch = false;
    Items item;
    private boolean collision;
    public QuestionBlock(int x, int y, Bitmap[] cF,Items item) {
        posX = x;
        posY = y;
        Frames = cF;
        frame = cF[0];
        this.item = item;
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
    public void setLocationY(int y){
        posY = y;
    }
    public void setLocationX(int x)
    {
        posX = x;
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

    public Items getItem() {
        return item;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }



    public boolean getCollision(){
        return collision;
    }

    public void setTouch(boolean collision) {
        this.touch = collision;
    }
    public boolean getTouch(){
        return touch;
    }

}
