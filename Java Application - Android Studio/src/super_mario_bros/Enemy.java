package com.example.efaideleon.super_mario_bros;

/**
 * Created by yashaswiniamaresh on 5/24/17.
 */

public abstract class Enemy /*implements GameItems*/ {
    //int reward;
    int posx;
    int posy;

   // public int getReward(){
    //    return reward;
    //}

    public int getPositionX(){
        return posx;
    }
    public int getPositionY(){
        return posy;
    }

    //public int getPoints(SuperMarioVisitor visitor){
    //    return visitor.visit(this);
    //}
}
