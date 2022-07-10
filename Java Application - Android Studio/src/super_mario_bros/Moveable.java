package com.example.efaideleon.super_mario_bros;

import android.graphics.Bitmap;

public interface Moveable {
    int getPosX();
    int getPosY();
    Bitmap getCurrentFrame();
    void setPosX(int x);
    void setPosY(int y);
    void setCurrentFrame(int index);
}
