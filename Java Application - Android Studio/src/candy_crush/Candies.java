package com.example.efaideleon.candy_crush;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

class Candies {
    private float x;
    private float y;
    private Bitmap Candy;
    private int id;
    private int colorid;

    public Candies(Bitmap c, float x, float y, int id, int colorid){
        Candy = c;
        this.x = x;
        this.y =  y;
        this.id = id;
        this.colorid = colorid;
    }

    public float  getXCoordinates(){

        return x;
    }
    public float getYCoordinates(){
        return y;
    }

    public Bitmap getCandy() {
        return Candy;
    }
    public int getId(){
        return id;
    }
    public int getColorid(){

        return  colorid;
    }

    public void setCandy (Bitmap bmp){
        Candy = bmp;
    }
    public void setColorid(int cid){
        colorid = cid;

    }
    public void setXCoordinates(float xc){
        x = xc;
    }
    public void setYCoordinates(float yc){
        y = yc;
    }
}
