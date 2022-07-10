package com.example.efaideleon.super_mario_bros;

public class LevelTwoThread extends Thread {
    private WorldView worldView;
    public LevelTwoThread(WorldView wv)
    {
        worldView = wv;
    }
    public void run()
    {
        while(worldView.levelTwo.isLevelTwoRunning()) {
            //worldView.lOne.mario.setMarioPosX();
            if (worldView.levelTwo.mario.isWalkRightGround() &&
                    worldView.levelTwo.mario.getPosX() < 1225)
            {
                worldView.levelTwo.moveBackgroundLeft();

            }

            if (worldView.levelTwo.mario.isWalkLeftGround() &&
                    worldView.levelTwo.mario.getPosX() < 1225)
            {
                worldView.levelTwo.moveBackgroundRight();
            }
            //worldView.levelTwo.gravity();
            //worldView.levelTwo.collision();
            worldView.levelTwo.goombaAI();
            worldView.levelTwo.mushroomAI();
            worldView.levelTwo.piranhaPlantAI();
            worldView.levelTwo.questionBlockAI();
            try{
                sleep(15);
            }
            catch(InterruptedException e) {
                System.out.println("Exception occured");
            }
        }
    }
}
