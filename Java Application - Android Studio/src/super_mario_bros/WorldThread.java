package com.example.efaideleon.super_mario_bros;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import java.util.ArrayList;

public class WorldThread extends Thread{

    private com.example.efaideleon.super_mario_bros.WorldView worldView;
    private boolean twoStarted = false;
    private boolean threeStarted = false;
    public WorldThread(com.example.efaideleon.super_mario_bros.WorldView wv)
    {
        worldView = wv;
    }
    public void run()
    {
        SurfaceHolder holder;
        Canvas canvas;
        while(true) {
            holder = worldView.getHolder();
            canvas = holder.lockCanvas();
            if(worldView.lOne.isLevelOneRunning()) {
                worldView.lOne.animation();
                worldView.lOne.gravity();
                worldView.lOne.collision();

                if(worldView.lOne.isResetLevelOne())
                {
                    worldView.lOne.resetPositions();
                    worldView.lOne.setResetLevelOne(false);
                }
            }
            else {
                if (!twoStarted){
                worldView.levelTwo.setLevelTwoRunning(true);
                worldView.incrementN(1);
                twoStarted = true;
                worldView.levelTwoThread.start();
                }

                if(worldView.levelTwo.isLevelTwoRunning())
                {
                    worldView.levelTwo.animation();
                    worldView.levelTwo.gravity();
                    worldView.levelTwo.collision();
                    if(worldView.levelTwo.isResetLevelTwo())
                    {
                        worldView.levelTwo.resetPositions();
                        worldView.levelTwo.setResetLevelTwo(false);
                    }
                }
                else
                {
                    if(!threeStarted)
                    {
                        worldView.levelThree.setLevelThreeRunning(true);
                        worldView.incrementN(1);
                        threeStarted = true;
                        worldView.levelThreeThread.start();
                    }
                    if(worldView.levelThree.isLevelThreeRunning())
                    {
                        worldView.levelThree.animation();
                        worldView.levelThree.gravity();
                        worldView.levelThree.collision();
                        if(worldView.levelTwo.isResetLevelTwo())
                        {
                            worldView.levelThree.resetPositions();
                            worldView.levelThree.setResetLevelThree(false);
                        }
                    }
                }
            }

            if(canvas != null) {

                worldView.draw(canvas);
                holder.unlockCanvasAndPost(canvas);
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
