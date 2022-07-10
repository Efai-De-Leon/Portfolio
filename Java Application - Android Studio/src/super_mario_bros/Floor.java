package com.example.efaideleon.super_mario_bros;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Floor
{
    private int floorPosX;
    private int floorPosY;
    private Bitmap floorFrame;
    private int width;
    private int height;
    public Floor(int x, int y, Bitmap b,int width,int height)
    {
        floorPosX = x;
        floorPosY = y;
        floorFrame = b;
        this.width = width;
        this.height = height;
    }

    public void setFloorFrame(Bitmap floorFrame) {
        this.floorFrame = floorFrame;
    }

    public void setFloorPosX(int floorPosX) {
        this.floorPosX += floorPosX;
    }

    public void setFloorPosY(int floorPosY) {
        this.floorPosY = floorPosY;
    }

    public Bitmap getFloorFrame() {
        return floorFrame;
    }
    public void setLocationY(int y){
        floorPosY = y;
    }
    public void setLocationX(int x)
    {
        floorPosX = x;
    }
    public int getFloorPosX() {
        return floorPosX;
    }

    public int getFloorPosY() {
        return floorPosY;
    }

    public Rect bounds(){
        return (new Rect(floorPosX,floorPosY,floorPosX+width,floorPosY+ height));
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
