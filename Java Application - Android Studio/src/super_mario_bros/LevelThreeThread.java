package com.example.efaideleon.super_mario_bros;

public class LevelThreeThread extends Thread{
    private WorldView worldView;
    public LevelThreeThread(WorldView wv)
    {
        worldView = wv;
    }
    public void run()
    {
        while(worldView.levelThree.isLevelThreeRunning()) {
            //worldView.lOne.mario.setMarioPosX();
            if (worldView.levelThree.mario.isWalkRightGround() &&
                    worldView.levelThree.mario.getPosX() < 1225)
            {
                worldView.levelThree.moveBackgroundLeft();

            }

            if (worldView.levelThree.mario.isWalkLeftGround() &&
                    worldView.levelThree.mario.getPosX() < 1225)
            {
                worldView.levelThree.moveBackgroundRight();
            }
            //worldView.levelThree.gravity();
            //worldView.levelThree.collision();
            worldView.levelThree.goombaAI();
            worldView.levelThree.mushroomAI();
            worldView.levelThree.piranhaPlantAI();
            worldView.levelThree.questionBlockAI();
            try{
                sleep(15);
            }
            catch(InterruptedException e) {
                System.out.println("Exception occured");
            }
        }
    }
}
