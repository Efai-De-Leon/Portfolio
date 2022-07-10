package com.example.efaideleon.super_mario_bros;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MarioThread extends Thread {
    private com.example.efaideleon.super_mario_bros.WorldView worldView;
    public MarioThread(com.example.efaideleon.super_mario_bros.WorldView wv)
    {
        worldView = wv;
    }
    public void run()
    {
        while(true) {
            if(worldView.lOne.isLevelOneRunning()) {
                worldView.lOne.mario.setMarioPosX();
            }
            if(worldView.levelTwo.isLevelTwoRunning())
            {
                worldView.levelTwo.mario.setMarioPosX();
            }
            if(worldView.levelThree.isLevelThreeRunning())
            {
                worldView.levelThree.mario.setMarioPosX();
            }
            try{
                sleep(15);
            }
            catch(InterruptedException e) {
                System.out.println("Exception occured");
            }
        }
    }
}
