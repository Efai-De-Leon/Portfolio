package com.example.efaideleon.super_mario_bros;

import android.graphics.Bitmap;

public class TransferPoint {
    private int transferPX = 1225;
    public TransferPoint()
    {
    }
    public int getTransferPX() {
        return transferPX;
    }

    public void setTransferPX(int transferPX) {
        this.transferPX += transferPX;
    }
    public void setLocationPX(int t)
    {
        transferPX = t;
    }

}
