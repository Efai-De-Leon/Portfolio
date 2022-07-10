package com.example.efaideleon.super_mario_bros;

public class GoombaThread extends Thread {
    private WorldView worldview;

    public GoombaThread(WorldView wv){
        worldview = wv;
    }
    public void run() {
        while(true) {
           // worldview.setGoomba();
            try{
                sleep(15);

            }
            catch(InterruptedException e) {
                System.out.println("Exception occured");
            }
        }
    }
}
