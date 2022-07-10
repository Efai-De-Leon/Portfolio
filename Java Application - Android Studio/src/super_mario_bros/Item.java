package com.example.efaideleon.super_mario_bros;

import com.example.efaideleon.super_mario_bros.GameItems;

/**
 * Created by yashaswiniamaresh on 5/24/17.
 */

public abstract class Item implements GameItems {

    int value;
    int x;
    int y;

    /* Write Constructor here, if necessary */

    public int getValue(){
        return value;
    }

    public int getPoints(com.example.efaideleon.super_mario_bros.SuperMarioVisitor visitor){
        return visitor.visit(this);
    }
}
