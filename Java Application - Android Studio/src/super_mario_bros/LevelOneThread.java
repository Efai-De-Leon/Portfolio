package com.example.efaideleon.super_mario_bros;
public class LevelOneThread extends Thread {
    private WorldView worldView;
    public LevelOneThread(WorldView wv)
    {
        worldView = wv;
    }
    public void run()
    {
        while(worldView.lOne.isLevelOneRunning()) {
            //worldView.lOne.mario.setMarioPosX();
            if (worldView.lOne.mario.isWalkRightGround() &&
                    worldView.lOne.mario.getPosX() < 1225)
            {
                worldView.lOne.moveBackgroundLeft();
            }

            if (worldView.lOne.mario.isWalkLeftGround() &&
                   worldView.lOne.mario.getPosX() < 1225)
            {
                worldView.lOne.moveBackgroundRight();
            }
            //worldView.lOne.gravity();
            //worldView.lOne.collision();
            worldView.lOne.goombaAI();
            worldView.lOne.mushroomAI();
            worldView.lOne.piranhaPlantAI();
            worldView.lOne.questionBlockAI();


            try{
                sleep(20);
            }
            catch(InterruptedException e) {
                System.out.println("Exception occured");
            }
        }
    }
}
