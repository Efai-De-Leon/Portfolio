package com.example.efaideleon.super_mario_bros;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class FireBallThread extends Thread {
    private com.example.efaideleon.super_mario_bros.WorldView worldView;
    public FireBallThread(com.example.efaideleon.super_mario_bros.WorldView wv)
    {
        worldView = wv;
    }
    public void run()
    {
        while(true) {
            if(worldView.isFire())
            {
                if(worldView.lOne.isLevelOneRunning())
                    worldView.lOne.mario.setFireBallPosition();
                if(worldView.levelTwo.isLevelTwoRunning()) {
                    worldView.levelTwo.mario.setFireBallPosition();
                }
                if(worldView.levelThree.isLevelThreeRunning()) {
                    worldView.levelThree.mario.setFireBallPosition();
                }
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