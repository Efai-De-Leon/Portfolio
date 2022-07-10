package com.example.efaideleon.super_mario_bros;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class LevelThree extends Level{
    private boolean collision = false;
    private int index = 0;
    private int indexTwoDArray=0;
    private int indexThreeDArray = 0;
    private int time = 0;
    Gravity gravity;
    private int alpha = 255;
    private int red = 0;
    private int green = 0;
    private int blue = 0;
    com.example.efaideleon.super_mario_bros.Mario mario;
    Floor floor1;
    Floor floor2;
    Floor floor3;
    Floor floor4;
    Floor floor5;
    WorldView worldView;
    FireFlower fireFlower;

    Block block;
    BrickBlock brickBlock;
    com.example.efaideleon.super_mario_bros.QuestionBlock questionBlock;
    com.example.efaideleon.super_mario_bros.QuestionBlock questionBlock2;
    Pipe pipe;
    Boo boo;
    Boo boo2;
    Boo boo3;
    Boo boo4;
    Boo boo5;
    com.example.efaideleon.super_mario_bros.PiranhaPlant piranhaPlant;
    com.example.efaideleon.super_mario_bros.Mushroom mushroom;
    com.example.efaideleon.super_mario_bros.Mushroom mushroom2;
    Coin coin;
    Coin coin1;
    Coin coin2;
    Coin coin3;
    Coin coin4;
    Coin coin5;
    Goomba goomba;
    BrickBlock brickBlock2;
    Goal goal1;
    private boolean resetLevelThree = false;
    private boolean levelThreeRunning = false;
    private ArrayList<Floor> platforms = new ArrayList<Floor>();
    private ArrayList<FireFlower> fireFlowerArrList = new ArrayList<FireFlower>();
    private ArrayList<Block> blockArrList = new ArrayList<Block>();
    private ArrayList<BrickBlock> brickBlockArrList = new ArrayList<BrickBlock>();
    private ArrayList<com.example.efaideleon.super_mario_bros.QuestionBlock> questionBlockArrList = new ArrayList<com.example.efaideleon.super_mario_bros.QuestionBlock>();
    private ArrayList<Pipe> pipeArrList = new ArrayList<Pipe>();
    private ArrayList<Boo> booArrList = new ArrayList<Boo>();
    private ArrayList<com.example.efaideleon.super_mario_bros.PiranhaPlant> piranhaPlantArrList = new ArrayList<com.example.efaideleon.super_mario_bros.PiranhaPlant>();
    private ArrayList<com.example.efaideleon.super_mario_bros.Mushroom> mushroomArrList = new ArrayList<com.example.efaideleon.super_mario_bros.Mushroom>();
    private ArrayList<Coin> coinArrList = new ArrayList<Coin>();
    private ArrayList<Goomba> goombaArrList = new ArrayList<Goomba>();
    private ArrayList<Cloud> cloudArrList = new ArrayList<Cloud>();
    private ArrayList<ColorBlocks> colorBlocksArrList = new ArrayList<ColorBlocks>();
    private ArrayList<CastleFloor> castleFloorArrList = new ArrayList<CastleFloor>();
    private ArrayList<Bush> bushArrList = new ArrayList<Bush>();
    private ArrayList<EyeBushes> eyeBushesArrList = new ArrayList<EyeBushes>();
    private ArrayList<Goal> goalArrList = new ArrayList<Goal>();
    private ArrayList<GrassFloor> grassFloorArrList = new ArrayList<GrassFloor>();
    private int coinPoints = 200;
    //private int goombaPoints = 1000;
    private int mushPoints = 1000;
    private int fireFlowerPoints = 1000;
    Timer timer = new Timer();
    private boolean transforming = false;
    public LevelThree(WorldView wv)
    {

        worldView = wv;
        floor1 = new Floor(1780,1235,worldView.getCastleFloor(), 780,205);
        floor2 = new Floor(1000,1235,worldView.getCastleFloor(), 780,205);
        floor3 = new Floor(220,1235,worldView.getCastleFloor(), 780,205);
        floor4 = new Floor(-760,1235,worldView.getCastleFloor(), 780,205);
        floor5 = new Floor(-1540,1235,worldView.getCastleFloor(), 780,205);

        platforms.add(floor1);
        platforms.add(floor2);
        platforms.add(floor3);
        platforms.add(floor4);
        platforms.add(floor5);
        mario = new com.example.efaideleon.super_mario_bros.Mario(1700, 950,worldView.getMario());

        fireFlower = new FireFlower(-90,780,worldView.getFireFlower());
        gravity = new Gravity();

        block = new Block(-500,800,worldView.getBlock());
        goomba = new Goomba(500,1145,worldView.getGoomba());
        coin = new Coin(-200,1140,worldView.getCoin());
        coin1 = new Coin(-300,1140,worldView.getCoin());
        coin2 = new Coin(-400,1140,worldView.getCoin());
        coin3 = new Coin(-500,1140,worldView.getCoin());
        coin4 = new Coin(-600,1140,worldView.getCoin());
        coin5 = new Coin(-700,1140,worldView.getCoin());
        //mushroom = new com.example.efaideleon.super_mario_bros.Mushroom(-300,900,worldView.getMushroom());
        mushroom2 = new com.example.efaideleon.super_mario_bros.Mushroom(1020,800,worldView.getMushroom());
        boo = new Boo(-700,1100,worldView.getBoo());
        boo2 = new Boo(500,500,worldView.getBoo());
        boo3 = new Boo(0,800,worldView.getBoo());
        boo4 = new Boo(150,400,worldView.getBoo());
        boo5 = new Boo(1400,700,worldView.getBoo());
        questionBlock = new com.example.efaideleon.super_mario_bros.QuestionBlock(-120,780,worldView.getQuestionBlock(),fireFlower);

        questionBlock2 = new com.example.efaideleon.super_mario_bros.QuestionBlock(1000,800,worldView.getQuestionBlock(),mushroom2);
        brickBlock = new BrickBlock(880,800,worldView.getBrickBlock());
        brickBlock2 = new BrickBlock(1120,800,worldView.getBrickBlock());
        goal1 = new Goal(-1100,800, worldView.getGoal());

        fireFlowerArrList.add(fireFlower);;

        goalArrList.add(goal1);

        booArrList.add(boo2);
        booArrList.add(boo3);
        booArrList.add(boo4);
        booArrList.add(boo5);
        questionBlockArrList.add(questionBlock2);
        mushroomArrList.add(mushroom2);
        blockArrList.add(block);
        brickBlockArrList.add(brickBlock);
        brickBlockArrList.add(brickBlock2);
        questionBlockArrList.add(questionBlock);
        booArrList.add(boo);
        //mushroomArrList.add(mushroom);
        coinArrList.add(coin);
        coinArrList.add(coin1);
        coinArrList.add(coin2);
        coinArrList.add(coin3);
        coinArrList.add(coin4);
        coinArrList.add(coin5);
        goombaArrList.add(goomba);
    }

    public void gravity(){
        if(!mario.getCollisionWithFloor()) {
            mario.startFalling();
            mario.marioFalls();

        }
    }
    public void animation(){
        if(time%6 == 0) {
            if (!questionBlockArrList.isEmpty()) {
                for (int i = 0; i < questionBlockArrList.size(); i++) {
                    questionBlockArrList.get(i).setCurrentFrame(index);
                    if(questionBlockArrList.get(i).getCollision()){
                        questionBlockArrList.get(i).setCurrentFrame(4);
                    }
                }
            }
            if (!brickBlockArrList.isEmpty()) {
                for (int i = 0; i < brickBlockArrList.size(); i++) {
                    brickBlockArrList.get(i).setCurrentFrame(index);
                    /*if(questionBlockArrList.get(i).getCollision()){
                        questionBlockArrList.get(i).setCurrentFrame(4);
                    }*/
                }
            }


            index++;
            if(index == 4){
                index = 0;
            }
        }

        if(time%6==0){
            if (!goombaArrList.isEmpty()) {
                for (int i = 0; i < goombaArrList.size(); i++) {
                    goombaArrList.get(i).setCurrentFrame(indexTwoDArray);
                    /*if(questionBlockArrList.get(i).getCollision()){
                        questionBlockArrList.get(i).setCurrentFrame(4);
                    }*/
                }

            }
            if (!piranhaPlantArrList.isEmpty()) {
                for (int i = 0; i < piranhaPlantArrList.size(); i++) {
                    piranhaPlantArrList.get(i).setCurrentFrame(indexTwoDArray);
                    /*if(questionBlockArrList.get(i).getCollision()){
                        questionBlockArrList.get(i).setCurrentFrame(4);
                    }*/
                }

            }
            indexTwoDArray++;
            if(indexTwoDArray==2){
                indexTwoDArray = 0;
            }
        }
        if(time%4==0) {
            if (!coinArrList.isEmpty()) {
                for (int i = 0; i < coinArrList.size(); i++) {
                    coinArrList.get(i).setCurrentFrame(indexThreeDArray);
                    /*if(questionBlockArrList.get(i).getCollision()){
                        questionBlockArrList.get(i).setCurrentFrame(4);
                    }*/
                }

                indexThreeDArray++;
                if (indexThreeDArray == 3) {
                    indexThreeDArray = 0;
                }
            }
        }
        time++;

        if(time == 18){
            time = 0;
        }
    }

    public void goombaAI(){
        for(int i = 0; i < goombaArrList.size(); i++){
            goombaArrList.get(i).goombaAi();
        }
    }
    public void mushroomAI(){
        if(!mushroomArrList.isEmpty()) {
            for (int i = 0; i < mushroomArrList.size(); i++) {
                if(mushroomArrList.get(i).getChangeInPosition()== 1) {
                    mushroomArrList.get(i).mushroomAi();
                }
            }
        }
    }

    public void piranhaPlantAI(){
        for(int i = 0; i<piranhaPlantArrList.size(); i++){
            piranhaPlantArrList.get(i).piranhaPlantAi();
        }
    }

    public void questionBlockAI(){
        if(!questionBlockArrList.isEmpty()) {
            for (int i = 0; i < questionBlockArrList.size(); i++) {
                if(questionBlockArrList.get(i).getCollision()) {
                    questionBlockArrList.get(i).getItem().moveUp();
                }
            }
        }
    }



    public void moveBackgroundLeft(){
        //cloudPosX -=10;
        //floorPosX -=10;
        mario.transferPoint.setTransferPX(-5);
        //floor1.setFloorPosX(-5);
        //block.setPosX(-5);
        // fireFlower.setPosX(-5);

        if(!getPlatforms().isEmpty()) {
            for(int i=0; i < getPlatforms().size(); i++) {
                getPlatforms().get(i).setFloorPosX(-5);
            }
        }

        if(!cloudArrList.isEmpty()) {
            for(int i=0; i < cloudArrList.size(); i++) {
                cloudArrList.get(i).setPosX(-5);
            }
        }
        if(!colorBlocksArrList.isEmpty()) {
            for(int i=0; i < colorBlocksArrList.size(); i++) {
                colorBlocksArrList.get(i).setPosX(-5);
            }
        }
        if(!castleFloorArrList.isEmpty()) {
            for(int i=0; i < castleFloorArrList.size(); i++) {
                castleFloorArrList.get(i).setPosX(-5);
            }
        }
        if(!bushArrList.isEmpty()) {
            for(int i=0; i < bushArrList.size(); i++) {
                bushArrList.get(i).setPosX(-5);
            }
        }
        if(!eyeBushesArrList.isEmpty()) {
            for(int i=0; i < eyeBushesArrList.size(); i++) {
                eyeBushesArrList.get(i).setPosX(-5);
            }
        }
        if(!goalArrList.isEmpty()) {
            for(int i=0; i < goalArrList.size(); i++) {
                goalArrList.get(i).setPosX(-5);
            }
        }

        if(!fireFlowerArrList.isEmpty()) {
            for(int i=0; i < fireFlowerArrList.size(); i++) {
                fireFlowerArrList.get(i).setPosX(-5);
            }
        }

        if(!brickBlockArrList.isEmpty()) {
            for(int i=0; i < brickBlockArrList.size(); i++) {
                brickBlockArrList.get(i).setPosX(-5);
            }
        }
        if(!blockArrList.isEmpty()) {
            for(int i=0; i < blockArrList.size(); i++) {
                blockArrList.get(i).setPosX(-5);
            }
        }
        if(!questionBlockArrList.isEmpty()) {
            for(int i=0; i < questionBlockArrList.size(); i++) {
                questionBlockArrList.get(i).setPosX(-5);
            }
        }
        if(!pipeArrList.isEmpty()) {
            for(int i=0; i < pipeArrList.size(); i++) {
                pipeArrList.get(i).setPosX(-5);
            }
        }
        if(!booArrList.isEmpty()) {
            for(int i=0; i < booArrList.size(); i++) {
                booArrList.get(i).setPosX(-5);
            }
        }
        if(!piranhaPlantArrList.isEmpty()) {
            for(int i=0; i < piranhaPlantArrList.size(); i++) {
                piranhaPlantArrList.get(i).setPosX(-5);
            }
        }
        if(!mushroomArrList.isEmpty()) {
            for(int i=0; i < mushroomArrList.size(); i++) {
                mushroomArrList.get(i).setPosX(-5);
            }
        }
        if(!goombaArrList.isEmpty()) {
            for(int i=0; i < goombaArrList.size(); i++) {
                goombaArrList.get(i).setPosX(-5);
            }
        }
        if(!coinArrList.isEmpty()) {
            for(int i=0; i < coinArrList.size(); i++) {
                coinArrList.get(i).setPosX(-5);
            }
        }

        /*brickBlock.setPosX(-5);
        questionBlock.setPosX(-5);
        pipe.setPosX(-5);
        boo.setPosX(-5);
        piranhaPlant.setPosX(-5);
        mushroom.setPosX(-5);
        coin.setPosX(-5);
        goomba.setPosX(-5);*/
        //fireBallPosX -= 10;
    }
    public void moveBackgroundRight(){
        // cloudPosX += 10;
        //floorPosX +=10;
        mario.transferPoint.setTransferPX(5);
        ///floor1.setFloorPosX(5);
        if(!getPlatforms().isEmpty()) {
            for(int i=0; i < getPlatforms().size(); i++) {
                getPlatforms().get(i).setFloorPosX(5);
            }
        }
        if(!cloudArrList.isEmpty()) {
            for(int i=0; i < cloudArrList.size(); i++) {
                cloudArrList.get(i).setPosX(5);
            }
        }
        if(!colorBlocksArrList.isEmpty()) {
            for(int i=0; i < colorBlocksArrList.size(); i++) {
                colorBlocksArrList.get(i).setPosX(5);
            }
        }
        if(!castleFloorArrList.isEmpty()) {
            for(int i=0; i < castleFloorArrList.size(); i++) {
                castleFloorArrList.get(i).setPosX(5);
            }
        }
        if(!bushArrList.isEmpty()) {
            for(int i=0; i < bushArrList.size(); i++) {
                bushArrList.get(i).setPosX(5);
            }
        }
        if(!eyeBushesArrList.isEmpty()) {
            for(int i=0; i < eyeBushesArrList.size(); i++) {
                eyeBushesArrList.get(i).setPosX(5);
            }
        }
        if(!goalArrList.isEmpty()) {
            for(int i=0; i < goalArrList.size(); i++) {
                goalArrList.get(i).setPosX(5);
            }
        }

        if(!fireFlowerArrList.isEmpty()) {
            for(int i=0; i < fireFlowerArrList.size(); i++) {
                fireFlowerArrList.get(i).setPosX(5);
            }
        }

        if(!brickBlockArrList.isEmpty()) {
            for(int i=0; i < brickBlockArrList.size(); i++) {
                brickBlockArrList.get(i).setPosX(5);
            }
        }

        if(!blockArrList.isEmpty()) {
            for(int i=0; i < blockArrList.size(); i++) {
                blockArrList.get(i).setPosX(5);
            }
        }
        if(!questionBlockArrList.isEmpty()) {
            for(int i=0; i < questionBlockArrList.size(); i++) {
                questionBlockArrList.get(i).setPosX(5);
            }
        }

        if(!pipeArrList.isEmpty()) {
            for(int i=0; i < pipeArrList.size(); i++) {
                pipeArrList.get(i).setPosX(5);
            }
        }
        if(!booArrList.isEmpty()) {
            for(int i=0; i < booArrList.size(); i++) {
                booArrList.get(i).setPosX(5);
            }
        }
        if(!piranhaPlantArrList.isEmpty()) {
            for(int i=0; i < piranhaPlantArrList.size(); i++) {
                piranhaPlantArrList.get(i).setPosX(5);
            }
        }
        if(!mushroomArrList.isEmpty()) {
            for(int i=0; i < mushroomArrList.size(); i++) {
                mushroomArrList.get(i).setPosX(5);
            }
        }
        if(!goombaArrList.isEmpty()) {
            for(int i=0; i < goombaArrList.size(); i++) {
                goombaArrList.get(i).setPosX(5);
            }
        }
        if(!coinArrList.isEmpty()) {
            for(int i=0; i < coinArrList.size(); i++) {
                coinArrList.get(i).setPosX(5);
            }
        }
        /*block.setPosX(5);
        brickBlock.setPosX(5);
        questionBlock.setPosX(5);
        pipe.setPosX(5);
        boo.setPosX(5);
        piranhaPlant.setPosX(5);
        mushroom.setPosX(5);
        coin.setPosX(5);
        goomba.setPosX(5);*/
        //fireBallPosX += 7;
    }
    public void accomodateBackgroundLeft(){
        //cloudPosX -=10;
        //floorPosX -=10;
        mario.transferPoint.setTransferPX(-10);
        //floor1.setFloorPosX(-10);
        //block.setPosX(-10);
        // fireFlower.setPosX(-10);

        if(!getPlatforms().isEmpty()) {
            for(int i=0; i < getPlatforms().size(); i++) {
                getPlatforms().get(i).setFloorPosX(-10);
            }
        }

        if(!cloudArrList.isEmpty()) {
            for(int i=0; i < cloudArrList.size(); i++) {
                cloudArrList.get(i).setPosX(-10);
            }
        }
        if(!colorBlocksArrList.isEmpty()) {
            for(int i=0; i < colorBlocksArrList.size(); i++) {
                colorBlocksArrList.get(i).setPosX(-10);
            }
        }
        if(!castleFloorArrList.isEmpty()) {
            for(int i=0; i < castleFloorArrList.size(); i++) {
                castleFloorArrList.get(i).setPosX(-10);
            }
        }
        if(!bushArrList.isEmpty()) {
            for(int i=0; i < bushArrList.size(); i++) {
                bushArrList.get(i).setPosX(-10);
            }
        }
        if(!eyeBushesArrList.isEmpty()) {
            for(int i=0; i < eyeBushesArrList.size(); i++) {
                eyeBushesArrList.get(i).setPosX(-10);
            }
        }
        if(!goalArrList.isEmpty()) {
            for(int i=0; i < goalArrList.size(); i++) {
                goalArrList.get(i).setPosX(-10);
            }
        }
        ////////////////////////////////////////////////////////
        if(!fireFlowerArrList.isEmpty()) {
            for(int i=0; i < fireFlowerArrList.size(); i++) {
                fireFlowerArrList.get(i).setPosX(-10);
            }
        }

        if(!brickBlockArrList.isEmpty()) {
            for(int i=0; i < brickBlockArrList.size(); i++) {
                brickBlockArrList.get(i).setPosX(-10);
            }
        }
        if(!blockArrList.isEmpty()) {
            for(int i=0; i < blockArrList.size(); i++) {
                blockArrList.get(i).setPosX(-10);
            }
        }
        if(!questionBlockArrList.isEmpty()) {
            for(int i=0; i < questionBlockArrList.size(); i++) {
                questionBlockArrList.get(i).setPosX(-10);
            }
        }
        if(!pipeArrList.isEmpty()) {
            for(int i=0; i < pipeArrList.size(); i++) {
                pipeArrList.get(i).setPosX(-10);
            }
        }
        if(!booArrList.isEmpty()) {
            for(int i=0; i < booArrList.size(); i++) {
                booArrList.get(i).setPosX(-10);
            }
        }
        if(!piranhaPlantArrList.isEmpty()) {
            for(int i=0; i < piranhaPlantArrList.size(); i++) {
                piranhaPlantArrList.get(i).setPosX(-10);
            }
        }
        if(!mushroomArrList.isEmpty()) {
            for(int i=0; i < mushroomArrList.size(); i++) {
                mushroomArrList.get(i).setPosX(-10);
            }
        }
        if(!goombaArrList.isEmpty()) {
            for(int i=0; i < goombaArrList.size(); i++) {
                goombaArrList.get(i).setPosX(-10);
            }
        }
        if(!coinArrList.isEmpty()) {
            for(int i=0; i < coinArrList.size(); i++) {
                coinArrList.get(i).setPosX(-10);
            }
        }

        /*brickBlock.setPosX(-5);
        questionBlock.setPosX(-5);
        pipe.setPosX(-5);
        boo.setPosX(-5);
        piranhaPlant.setPosX(-5);
        mushroom.setPosX(-5);
        coin.setPosX(-5);
        goomba.setPosX(-5);*/
        //fireBallPosX -= 10;
    }
    public void collision() {
        if (!getPlatforms().isEmpty()) {
            for (int i = 0; i < getPlatforms().size(); i++) {
                if(!mario.leftBounds().intersect(getPlatforms().get(i).bounds())){
                    mario.setCollisionWithFloor(false);
                    mario.startFalling();
                }
                if(!mario.rightBounds().intersect(getPlatforms().get(i).bounds())){
                    mario.setCollisionWithFloor(false);
                    mario.startFalling();
                }
                if (mario.bottomBounds().intersect(getPlatforms().get(i).bounds())) {
                    System.out.println("COLLISION WITH FLOOR");
                    mario.setLocationY(getPlatforms().get(i).getFloorPosY() -50);
                    mario.setCollisionWithFloor(true);
                    mario.stopFalling();
                    if(mario.isMarioJumping()){
                        mario.setMarioIndex(0);
                        mario.setCurrentFrame(mario.getMarioIndex());
                    }

                    mario.setMarioOnGround();
                }
                if(!mushroomArrList.isEmpty()){
                    for(int j =0; j<mushroomArrList.size(); j++){
                        if(getPlatforms().get(i).bounds().intersect(mushroomArrList.get(j).bounds())){
                            mushroomArrList.get(j).setLocationY(getPlatforms().get(j).getFloorPosY() - 80);
                            mushroomArrList.get(j).setCollisionWithFloor(true);
                            mushroomArrList.get(j).stopMushroomFalling();
                        }
                    }
                }
                if(getPlatforms().get(i).bounds().intersect(mario.fireBallBounds())){
                    mario.setFireBallTouchedFloor(true);
                    mario.setTempfirehigh(getPlatforms().get(i).getFloorPosY()-150);
                }

            }
        }

        if (!fireFlowerArrList.isEmpty()) {
            for (int i = 0; i < fireFlowerArrList.size(); i++) {
                if (mario.bounds().intersect(fireFlowerArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH FIRE FLOWER");
                    mario.giveFireFlower(true);
                    fireFlowerArrList.remove(i);
                    worldView.addPoints(fireFlowerPoints);
                }
            }
        }
        if (!brickBlockArrList.isEmpty()) {
            for (int i = 0; i < brickBlockArrList.size(); i++) {
                if (mario.leftBounds().intersect(brickBlockArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH BRICK BLOCK");
                    mario.stopWalkingRight();
                    mario.stopWalkingLeft();
                    mario.setCanJump(false);


                    /* collision = true;*/
                }
                if (mario.rightBounds().intersect(brickBlockArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH BRICK BLOCK");
                    mario.stopWalkingRight();
                    mario.stopWalkingLeft();
                    mario.setCanJump(false);
                    /* collision = true;*/
                }
                if (mario.bottomBounds().intersect(brickBlockArrList.get(i).bounds())) {
                    mario.setLocationY(brickBlockArrList.get(i).getPosY() -50);
                    mario.setCollisionWithFloor(true);
                    mario.stopFalling();
                    if(mario.isMarioJumping()){
                        mario.setMarioIndex(0);
                        mario.setCurrentFrame(mario.getMarioIndex());
                    }

                    mario.setMarioOnGround();
                }

                if(!mushroomArrList.isEmpty()){
                    for(int j =0; j<mushroomArrList.size(); j++){
                        if(brickBlockArrList.get(i).bounds().intersect(mushroomArrList.get(j).bounds())){
                            mushroomArrList.get(j).setLocationY(brickBlockArrList.get(i).getPosY() - mushroomArrList.get(j).getHeight());
                            mushroomArrList.get(j).setCollisionWithBrickBlock(true);
                            mushroomArrList.get(j).stopMushroomFalling();
                        }
                        if(!brickBlockArrList.get(i).bounds().intersect(mushroomArrList.get(j).bounds())){
                            mushroomArrList.get(j).setCollisionWithBrickBlock(false);
                            mushroomArrList.get(j).startMushroomFalling();
                        }
                    }
                }
                if(brickBlockArrList.get(i).bounds().intersect(mario.fireBallBounds())){
                    mario.setExistenceOfFire(200);
                }
                if(mario.hasMushroom()) {
                    if (mario.topBounds().intersect(brickBlockArrList.get(i).bounds())) {
                        brickBlockArrList.remove(i);
                    }
                }
            }

        }
        if (!questionBlockArrList.isEmpty()) {
            for (int i = 0; i <  questionBlockArrList.size(); i++) {
                if(mario.topBounds().intersect(questionBlockArrList.get(i).bounds())){
                    mario.setCanJump(false);
                    questionBlockArrList.get(i).getItem().moveUp();
                    questionBlockArrList.get(i).setCollision(true);
                    if(!coinArrList.isEmpty()) {
                        for (int j = 0; j < coinArrList.size(); j++) {
                            if (questionBlockArrList.get(i).getItem().equals(coinArrList.get(j))) {
                                coinArrList.remove(j);
                                worldView.addPoints(coinPoints);

                            }
                        }
                    }
                }
                if (mario.leftBounds().intersect(questionBlockArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH QUESTION BLOCK");
                    mario.stopWalkingRight();
                    mario.stopWalkingLeft();
                    // mario.setLocationY(questionBlockArrList.get(i).getPosY()+100+50);
                    questionBlockArrList.get(i).setCollision(true);
                }
                if (mario.rightBounds().intersect(questionBlockArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH QUESTION BLOCK");
                    mario.stopWalkingRight();
                    mario.stopWalkingLeft();
                    //mario.setLocationY(questionBlockArrList.get(i).getPosY()+100+50);

                    questionBlockArrList.get(i).setCollision(true);
                }
                if (mario.bottomBounds().intersect(questionBlockArrList.get(i).bounds())) {
                    mario.setLocationY(questionBlockArrList.get(i).getPosY() -50);
                    mario.setCollisionWithFloor(true);
                    mario.stopFalling();
                    if(mario.isMarioJumping()){
                        mario.setMarioIndex(0);
                        mario.setCurrentFrame(mario.getMarioIndex());
                    }

                    mario.setMarioOnGround();
                }
                if(questionBlockArrList.get(i).bounds().intersect(mario.fireBallBounds())){
                    mario.setExistenceOfFire(200);
                }
            }


        }

        if (!pipeArrList.isEmpty()) {

            for (int i = 0; i < pipeArrList.size(); i++) {
                if (mario.leftBounds().intersect(pipeArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH PIPE");
                    collision = true;
                    //mario.transferPoint.setLocationPX(1820);
                    accomodateBackgroundLeft();
                    System.out.println( mario.transferPoint.getTransferPX());
                }
                if (mario.rightBounds().intersect(pipeArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH PIPE");
                    collision = true;
                    //mario.setLocationX(pipeArrList.get(i).getPosX() - 50);
                }
                if (mario.bottomBounds().intersect(pipeArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH PIPE");
                    mario.setLocationY(getPipeArrList().get(i).getPosY() -50);
                    mario.setCollisionWithFloor(true);
                    mario.stopFalling();
                    if(mario.isMarioJumping()){
                        mario.setMarioIndex(0);
                        mario.setCurrentFrame(mario.getMarioIndex());
                    }

                    mario.setMarioOnGround();
                }
                if(pipeArrList.get(i).bounds().intersect(mario.fireBallBounds())){
                    mario.setExistenceOfFire(200);
                }
            }


        }
        if (!goalArrList.isEmpty()) {

            for (int i = 0; i < goalArrList.size(); i++) {
                if (mario.bounds().intersect(goalArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH GOAL");
                    collision = true;
                    levelThreeRunning = false;

                }
            }


        }
        if (!booArrList.isEmpty()) {
            if (transforming) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        transforming = false;
                    }
                }, 3000);
            }
            else {
                for (int i = 0; i < booArrList.size(); i++) {
                    if (mario.bounds().intersect(booArrList.get(i).bounds())) {
                        System.out.println("COLLISION WITH BOO");
                        collision = true;

                        if (!mario.hasMushroom()) {
                            worldView.updateLives();
                            resetLevelThree = true;
                        }
                        if(mario.hasMushroom()&&!mario.hasFireFlower()){
                            mario.giveMushroom(false);
                            transforming = true;
                        }
                        if(mario.hasFireFlower()){
                            mario.giveFireFlower(false);
                            transforming = true;
                        }
                    }
                }
            }



        }

        if (!piranhaPlantArrList.isEmpty()) {
            for (int i = 0; i < piranhaPlantArrList.size(); i++) {
                if (transforming) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            transforming = false;
                        }
                    }, 3000);
                }
                else {
                    if (mario.bounds().intersect(piranhaPlantArrList.get(i).bounds())) {
                        System.out.println("COLLISION WITH PIRANHA PLANT");
                        //mario.giveMushroom(false);
                        //mario.giveFireFlower(false);
                        collision = true;
                        if (!mario.hasMushroom()) {
                            worldView.updateLives();
                            resetLevelThree = true;
                        }
                        if(mario.hasMushroom()&&!mario.hasFireFlower()){
                            mario.giveMushroom(false);
                            transforming = true;
                        }
                        if(mario.hasFireFlower()){
                            mario.giveFireFlower(false);
                            transforming = true;
                        }
                    }
                }
                if(mario.getFired()) {
                    if (piranhaPlantArrList.get(i).bounds().intersect(mario.fireBallBounds())) {
                        mario.setExistenceOfFire(200);
                    }
                    if (piranhaPlantArrList.get(i).bounds().intersect(mario.fireBallBounds())) {
                        piranhaPlantArrList.remove(i);
                    }
                }
            }


        }

        if (!mushroomArrList.isEmpty()) {
            for (int i = 0; i < mushroomArrList.size(); i++) {
                if (mario.bounds().intersect(mushroomArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH MUSHROOM");
                    mushroomArrList.remove(i);
                    mario.giveMushroom(true);
                    collision = true;
                    worldView.addPoints(mushPoints);
                }

            }


        }

        if (!coinArrList.isEmpty()) {
            for (int i = 0; i < coinArrList.size(); i++) {
                if (mario.bounds().intersect(coinArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH COIN");
                    coinArrList.remove(i);
                    collision = true;
                    worldView.addPoints(coinPoints);
                }
            }


        }


        if (!goombaArrList.isEmpty()) {

            for (int i = 0; i < goombaArrList.size(); i++) {

                if (transforming) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            transforming = false;
                        }
                    }, 3000);
                }
                else{
                    if (mario.bottomBounds().intersect(goombaArrList.get(i).bounds())) {
                        goombaArrList.remove(i);
                        //mario.giveFireFlower(false);
                        //mario.giveMushroom(false);
                        System.out.println("SQUASHED");
                        collision = true;

                    }

                    else if (mario.leftBounds().intersect(goombaArrList.get(i).bounds())) {
                        //goombaArrList.get(i).changeDirection();
                        //mario.giveFireFlower(false);
                        //mario.giveMushroom(false);
                        System.out.println("COLLISION WITH GOOMBA FROM LEFT");
                        collision = true;

                        if (!mario.hasMushroom()) {
                            worldView.updateLives();
                            resetLevelThree = true;
                        }
                        if(mario.hasMushroom()&&!mario.hasFireFlower()){
                            mario.giveMushroom(false);
                            transforming = true;
                        }
                        if(mario.hasFireFlower()){
                            mario.giveFireFlower(false);
                            transforming = true;
                            System.out.println("has fireflower lost it to goomba");
                            System.out.println(mario.hasFireFlower());
                            System.out.println(mario.hasMushroom());
                        }
                    } else if (mario.rightBounds().intersect(goombaArrList.get(i).bounds())) {
                        //goombaArrList.get(i).changeDirection();
                        // mario.giveFireFlower(false);
                        // mario.giveMushroom(false);
                        System.out.println("COLLISION WITH GOOMBA FROM RIGHT");
                        collision = true;
                        worldView.updateLives();
                        resetLevelThree = true;

                        if(!mario.hasMushroom()) {
                            worldView.updateLives();
                            resetLevelThree= true;
                        }
                        if(mario.hasMushroom()&&!mario.hasFireFlower()){
                            mario.giveMushroom(false);
                            transforming = true;
                        }
                        if(mario.hasFireFlower()){
                            mario.giveFireFlower(false);
                            transforming = true;
                            System.out.println("has fireflower lost it to goomba");
                            System.out.println(mario.hasFireFlower());
                            System.out.println(mario.hasMushroom());
                        }
                    }

                }
                if(mario.getFired()) {
                    if (goombaArrList.get(i).bounds().intersect(mario.fireBallBounds())) {
                        mario.setExistenceOfFire(200);
                    }
                    if (goombaArrList.get(i).bounds().intersect(mario.fireBallBounds())) {
                        goombaArrList.remove(i);
                    }
                }


            }
        }

        if (!blockArrList.isEmpty()) {
            for (int i = 0; i < blockArrList.size(); i++)
            {
                if(mario.topBounds().intersect(blockArrList.get(i).bounds())) {
                    mario.setCanJump(false);
                }
                if (mario.leftBounds().intersect(blockArrList.get(i).bounds())) {
                System.out.println("COLLISION WITH BLOCK");
                collision = true;
                //mario.transferPoint.setLocationPX(1820);
                accomodateBackgroundLeft();
                System.out.println( mario.transferPoint.getTransferPX());
                }
                if (mario.rightBounds().intersect(blockArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH BLOCK");
                    collision = true;
                    //mario.setLocationX(pipeArrList.get(i).getPosX() - 50);
                }
                if (mario.bottomBounds().intersect(blockArrList.get(i).bounds())) {
                    System.out.println("COLLISION WITH BLOCK");
                    mario.setLocationY(blockArrList.get(i).getPosY() -50);
                    mario.setCollisionWithFloor(true);
                    mario.stopFalling();
                    if(mario.isMarioJumping()){
                        mario.setMarioIndex(0);
                        mario.setCurrentFrame(mario.getMarioIndex());
                    }

                    mario.setMarioOnGround();
                }
                if(blockArrList.get(i).bounds().intersect(mario.fireBallBounds())){
                    mario.setExistenceOfFire(200);
                }
            }

        }

        if(mario.getPosY() > 1600)
        {
            worldView.updateLives();
            resetLevelThree = true;
        }
    }



    public ArrayList<Floor> getPlatforms() {
        return platforms;
    }

    public ArrayList<Block> getBlockArrList() {
        return blockArrList;
    }

    public ArrayList<Boo> getBooArrList() {
        return booArrList;
    }

    public ArrayList<BrickBlock> getBrickBlockArrList() {
        return brickBlockArrList;
    }

    public ArrayList<Coin> getCoinArrList() {
        return coinArrList;
    }

    public ArrayList<FireFlower> getFireFlowerArrList() {
        return fireFlowerArrList;
    }

    public ArrayList<Goomba> getGoombaArrList() {
        return goombaArrList;
    }

    public ArrayList<com.example.efaideleon.super_mario_bros.Mushroom> getMushroomArrList() {
        return mushroomArrList;
    }

    public ArrayList<Pipe> getPipeArrList() {
        return pipeArrList;
    }

    public ArrayList<com.example.efaideleon.super_mario_bros.PiranhaPlant> getPiranhaPlantArrList() {
        return piranhaPlantArrList;
    }

    public ArrayList<com.example.efaideleon.super_mario_bros.QuestionBlock> getQuestionBlockArrList() {
        return questionBlockArrList;
    }
    public ArrayList<Cloud> getCloudArrList() {
        return cloudArrList;
    }

    @Override
    public ArrayList<ColorBlocks> getColorBlocksArrList() {
        return colorBlocksArrList;
    }

    @Override
    public ArrayList<CastleFloor> getCastleFloorArrList() {
        return castleFloorArrList;
    }

    @Override
    public ArrayList<Goal> getGoalArrList() {
        return goalArrList;
    }

    @Override
    public ArrayList<Bush> getBushArrList() {
        return bushArrList;
    }

    @Override
    public ArrayList<EyeBushes> getEyeBushesArrList() {
        return eyeBushesArrList;
    }

    @Override
    public ArrayList<GrassFloor> getGrassFloorArrList() {
        return grassFloorArrList;
    }

    public boolean isLevelThreeRunning() {
        return levelThreeRunning;
    }

    public com.example.efaideleon.super_mario_bros.Mario getMario() {
        return mario;
    }

    public Block getBlock() {
        return block;
    }

    public void setLevelThreeRunning(boolean levelThreeRunning) {
        this.levelThreeRunning = levelThreeRunning;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getAlpha() {
        return alpha;
    }


    public void resetPositions()
    {
        floor1.setLocationX(1780);
        floor1.setLocationY(1235);
        floor2.setLocationX(1000);
        floor2.setLocationY(1235);
        floor3.setLocationX(220);
        floor3.setLocationY(1235);
        floor4.setLocationX(-760);
        floor4.setLocationY(1235);
        floor5.setLocationX(-1540);
        floor5.setLocationY(1235);
        fireFlower.setLocationX(-90);
        fireFlower.setLocationY(780);
        block.setLocationX(-1200);
        block.setLocationY(1145);
        goomba.setLocationX(500);
        goomba.setLocationY(1145);
        coin.setLocationX(-400);
        coin.setLocationY(800);
        coin1.setLocationX(-500);
        coin1.setLocationY(800);
        coin2.setLocationX(-600);
        coin2.setLocationY(800);
        coin3.setLocationX(-700);
        coin3.setLocationY(800);
        coin4.setLocationX(-800);
        coin4.setLocationY(800);
        coin5.setLocationX(-900);
        coin5.setLocationY(800);
        //mushroom.setLocationX(-300);
        //mushroom.setLocationY(900);
        mushroom2.setLocationX(1020);
        mushroom2.setLocationY(800);
        piranhaPlant.setLocationX(440);
        piranhaPlant.setLocationY(1035);
        pipe.setLocationX(400);
        pipe.setLocationY(1035);
        boo.setLocationX(-700);
        boo.setLocationY(1100);
        questionBlock.setLocationX(-120);
        questionBlock.setLocationY(780);
        questionBlock2.setLocationX(1000);
        questionBlock2.setLocationY(800);
        brickBlock.setLocationX(880);
        brickBlock.setLocationY(800);
        brickBlock2.setLocationX(1120);
        brickBlock2.setLocationY(800);
        /*cloud1.setLocationX(100);
        cloud1.setLocationY(300);
        colorBlocks1.setLocationX(300);
        colorBlocks1.setLocationY(535);
        bush1.setLocationX(600);
        bush1.setLocationY(1135);*/
        goal1.setLocationX(-1100);
        goal1.setLocationY(800);
        mario.setLocationX(1700);
        mario.setLocationY(950);
        mario.transferPoint.setLocationPX(1225);
    }

    public boolean isResetLevelThree() {
        return resetLevelThree;
    }

    public void setResetLevelThree(boolean resetLevelThree) {
        this.resetLevelThree = resetLevelThree;
    }
}
