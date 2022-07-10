package com.example.efaideleon.super_mario_bros;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.efaideleon.super_mario_bros.LevelOne;
import com.example.efaideleon.super_mario_bros.LevelThreeThread;
import com.example.efaideleon.super_mario_bros.LevelTwo;
import com.example.efaideleon.super_mario_bros.LevelTwoThread;
import com.example.efaideleon.super_mario_bros.R;

import java.util.ArrayList;
import java.util.List;

class WorldView extends SurfaceView implements SurfaceHolder.Callback {
    private Paint paint;
    private float clickX;
    private float clickY;
    private float joystickPosX = 350 + 100;
    private float joystickPosY = 900 + 100;
    private float distanceFromJoystick;
    private boolean joystickOutOfBoundary = false;
    private float altitude  = 0;
    private boolean reachedMaximum = false;
    private int joystickIndex;
    private Bitmap floor;
    private Bitmap[] mario = new Bitmap[26];
    private Bitmap fireFlower;
    private Bitmap [] fireBall = new Bitmap[4];
    private float distanceFromButton = 0;
    private int buttonAposX = 2350;
    private int buttonAposY = 850;
    private int buttonBposX = 2050;
    private int buttonBposY = 1150;
    private Bitmap block;
    private Bitmap []coin = new Bitmap[4];
    private Bitmap []piranhaPlant = new Bitmap[4];
    private Bitmap pipe;
    private Bitmap redMushroom;
    private Bitmap []questionBlock = new Bitmap[5];
    private Bitmap []brickBlock = new Bitmap[4];
    private Bitmap []boo = new Bitmap[4];
    private Bitmap []goomba = new Bitmap[4];
    private Bitmap cloud;
    private Bitmap bush;
    private Bitmap eye_bushes;
    private Bitmap goal;
    private Bitmap castleFloor;
    private Bitmap colorBlocks;
    private Bitmap grassFloor;
    private ArrayList<Integer> tracks = new ArrayList<Integer>();

    private ArrayList<Level> levels = new ArrayList<Level>();

    LevelOne lOne;
    LevelTwo levelTwo;
    LevelThree levelThree;
    private int n = 0; // level iterator
    LevelTwoThread levelTwoThread;
    LevelThreeThread levelThreeThread;

    private int lives = 3;
    private int points = 0;
    private int coinPoints = 200;
    private int mushPoints = 1000;
    private int fireFlowerPooints = 100;
    public WorldView(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
        paint = new Paint();
        floor = BitmapFactory.decodeResource(getResources(), R.drawable.floor);
        mario[0] =BitmapFactory.decodeResource(getResources(), R.drawable.small_mario_standing_right);
        mario[1] =BitmapFactory.decodeResource(getResources(), R.drawable.small_mario_walking_right_one);
        mario[2] =BitmapFactory.decodeResource(getResources(), R.drawable.small_mario_walking_right_two);
        mario[3] =BitmapFactory.decodeResource(getResources(), R.drawable.small_mario_standing_left);
        mario[4] =BitmapFactory.decodeResource(getResources(), R.drawable.small_mario_walking_left_one);
        mario[5] =BitmapFactory.decodeResource(getResources(), R.drawable.small_mario_walking_left_two);

        mario[6] = BitmapFactory.decodeResource(getResources(), R.drawable.big_fire_mario_standing_right);
        mario[7] = BitmapFactory.decodeResource(getResources(), R.drawable.big_fire_mario_walking_right_one);
        mario[8] = BitmapFactory.decodeResource(getResources(), R.drawable.big_fire_mario_walking_right_two);
        mario[9] = BitmapFactory.decodeResource(getResources(), R.drawable.big_fire_mario_standing_left);
        mario[10] = BitmapFactory.decodeResource(getResources(), R.drawable.big_fire_mario_walking_left_one);
        mario[11] = BitmapFactory.decodeResource(getResources(), R.drawable.big_fire_mario_walking_left_two);
        mario[12] = BitmapFactory.decodeResource(getResources(), R.drawable.big_fire_mario_jumping_right);
        mario[13] = BitmapFactory.decodeResource(getResources(), R.drawable.big_fire_mario_jumping_left);
        mario[14] = BitmapFactory.decodeResource(getResources(), R.drawable.small_jumping_right);
        mario[15] = BitmapFactory.decodeResource(getResources(), R.drawable.small_jumping_left);
        mario[16] = BitmapFactory.decodeResource(getResources(), R.drawable.fire_mario_throw_right);
        mario[17] = BitmapFactory.decodeResource(getResources(), R.drawable.fire_mario_throw_left);
        mario[18] = BitmapFactory.decodeResource(getResources(), R.drawable.big_mario_standing_right);
        mario[19] = BitmapFactory.decodeResource(getResources(), R.drawable.big_mario_walking_right_one);
        mario[20] = BitmapFactory.decodeResource(getResources(), R.drawable.big_mario_walking_right_two);
        mario[21] = BitmapFactory.decodeResource(getResources(), R.drawable.big_mario_standing_left);
        mario[22] = BitmapFactory.decodeResource(getResources(), R.drawable.big_mario_walking_left_one);
        mario[23] = BitmapFactory.decodeResource(getResources(), R.drawable.big_mario_walking_left_two);
        mario[24] = BitmapFactory.decodeResource(getResources(), R.drawable.big_mario_jumping_right);
        mario[25] = BitmapFactory.decodeResource(getResources(), R.drawable.big_mario_jumping_left);

        fireFlower = BitmapFactory.decodeResource(getResources(), R.drawable.fire_flower);
        fireBall[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fire_ball);
        fireBall[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fire_ball_rotated_one);
        fireBall[2] = BitmapFactory.decodeResource(getResources(), R.drawable.fire_ball_rotated_two);
        fireBall[3] = BitmapFactory.decodeResource(getResources(), R.drawable.fire_ball_rotated_three);

        block =  BitmapFactory.decodeResource(getResources(), R.drawable.static_block);

        goomba[0] =  BitmapFactory.decodeResource(getResources(), R.drawable.goomba);
        goomba[1] =  BitmapFactory.decodeResource(getResources(), R.drawable.goomba1);

        brickBlock[0]  =  BitmapFactory.decodeResource(getResources(), R.drawable.brick1);
        brickBlock[1]  =  BitmapFactory.decodeResource(getResources(), R.drawable.brick2);
        brickBlock[2]  =  BitmapFactory.decodeResource(getResources(), R.drawable.brick3);
        brickBlock[3]  =  BitmapFactory.decodeResource(getResources(), R.drawable.brick4);

        questionBlock[0]  =  BitmapFactory.decodeResource(getResources(), R.drawable.qblock1);
        questionBlock[1]  =  BitmapFactory.decodeResource(getResources(), R.drawable.qblock2);
        questionBlock[2]  =  BitmapFactory.decodeResource(getResources(), R.drawable.qblock3);
        questionBlock[3]  =  BitmapFactory.decodeResource(getResources(), R.drawable.qblock4);
        questionBlock[4]  =  BitmapFactory.decodeResource(getResources(), R.drawable.qblock5);
        pipe  =  BitmapFactory.decodeResource(getResources(), R.drawable.pipe_small);
        boo[0]  =  BitmapFactory.decodeResource(getResources(), R.drawable.boo);
        boo[1]  =  BitmapFactory.decodeResource(getResources(), R.drawable.boo);
        piranhaPlant [0]  =  BitmapFactory.decodeResource(getResources(), R.drawable.piranha1);
        piranhaPlant [1]  =  BitmapFactory.decodeResource(getResources(), R.drawable.piranha2);
        redMushroom  =  BitmapFactory.decodeResource(getResources(), R.drawable.mushroom);
        coin[0]  =  BitmapFactory.decodeResource(getResources(), R.drawable.coin1);
        coin[1]  =  BitmapFactory.decodeResource(getResources(), R.drawable.coin2);
        coin[2]  =  BitmapFactory.decodeResource(getResources(), R.drawable.coin3);

        cloud =  BitmapFactory.decodeResource(getResources(), R.drawable.cloud);
        bush =  BitmapFactory.decodeResource(getResources(), R.drawable.bushes);
        eye_bushes =  BitmapFactory.decodeResource(getResources(), R.drawable.eyes_bush);
        goal =  BitmapFactory.decodeResource(getResources(), R.drawable.goal);
        castleFloor =  BitmapFactory.decodeResource(getResources(), R.drawable.castle_floor);
        colorBlocks =  BitmapFactory.decodeResource(getResources(), R.drawable.color_blocks);
        grassFloor = BitmapFactory.decodeResource(getResources(), R.drawable.grass_floor);

        lOne = new LevelOne(this);
        levelTwo = new LevelTwo(this);
        levelThree = new LevelThree(this);
        levels.add(lOne);
        levels.add(levelTwo);
        levels.add(levelThree);



    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (lives > 0) {
            Rect dst = new Rect();
            Rect marioRect = new Rect();
            Rect fireFlowerRect = new Rect();
            Rect goombaRect = new Rect();
            Rect blockRect = new Rect();
            Rect brickBlockRect = new Rect();
            Rect questionBlockRect = new Rect();
            Rect pipeRect = new Rect();
            Rect coinRect = new Rect();
            Rect booRect = new Rect();
            Rect redMushroomRect = new Rect();
            Rect piranhaRect = new Rect();
            Rect cloudRect = new Rect();
            Rect colorBlocksRect = new Rect();
            Rect bushRect = new Rect();
            Rect eyeBushesRect = new Rect();
            Rect castleFloorRect = new Rect();
            Rect goalRect = new Rect();
            Rect grassFloorRect = new Rect();
            Rect fireBallRect = new Rect();

            // brickBlockRect.set(lOne.brickBlock.getPosX(), lOne.brickBlock.getPosY(), lOne.brickBlock.getPosX() + lOne.brickBlock.getWidth(), lOne.brickBlock.getPosY() + lOne.brickBlock.getHeight() );
        /*questionBlockRect.set(lOne.questionBlock.getPosX(), lOne.questionBlock.getPosY(), lOne.questionBlock.getPosX() + lOne.questionBlock.getWidth(), lOne.questionBlock.getPosY() + lOne.questionBlock.getHeight() );
        pipeRect.set(lOne.pipe.getPosX(), lOne.pipe.getPosY(), lOne.pipe.getPosX() + lOne.pipe.getWidth(), lOne.pipe.getPosY() + lOne.pipe.getHeight() );
        coinRect.set(lOne.coin.getPosX(), lOne.coin.getPosY(), lOne.coin.getPosX() + lOne.coin.getWidth(), lOne.coin.getPosY() + lOne.coin.getHeight() );
        booRect.set(lOne.boo.getPosX(), lOne.boo.getPosY(), lOne.boo.getPosX() + lOne.boo.getWidth(), lOne.boo.getPosY() + lOne.boo.getHeight() );
        redMushroomRect.set(lOne.mushroom.getPosX(), lOne.mushroom.getPosY(), lOne.mushroom.getPosX() + lOne.mushroom.getWidth(), lOne.mushroom.getPosY() + lOne.mushroom.getHeight() );
        piranhaRect.set(lOne.piranhaPlant.getPosX(), lOne.piranhaPlant.getPosY(), lOne.piranhaPlant.getPosX() + lOne.piranhaPlant.getWidth(), lOne.piranhaPlant.getPosY() + lOne.piranhaPlant.getHeight() );
        goombaRect.set(lOne.goomba.getPosX(), lOne.goomba.getPosY(), lOne.goomba.getPosX() + lOne.goomba.getWidth(), lOne.goomba.getPosY() + lOne.goomba.getHeight() );
        blockRect.set(lOne.block.getPosX(), lOne.block.getPosY(), lOne.block.getPosX() + lOne.block.getWidth(), lOne.block.getPosY() + lOne.block.getHeight() );
        fireFlowerRect.set(lOne.fireFlower.getPosX(), lOne.fireFlower.getPosY() , lOne.fireFlower.getPosX() + lOne.fireFlower.getWidth(), lOne.fireFlower.getPosY() + lOne.fireFlower.getHeight());*/

            for (int i = 0; i < levels.get(n).getCloudArrList().size(); i++) {
                canvas.drawARGB(levels.get(n).getAlpha(), levels.get(n).getRed(), levels.get(n).getGreen(), levels.get(n).getBlue());
            }


            if (!levels.get(n).getCloudArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getCloudArrList().size(); i++) {
                    cloudRect.set(levels.get(n).getCloudArrList().get(i).getPosX(), levels.get(n).getCloudArrList().get(i).getPosY(),
                            levels.get(n).getCloudArrList().get(i).getPosX() + levels.get(n).getCloudArrList().get(i).getWidth(), levels.get(n).getCloudArrList().get(i).getPosY() + levels.get(n).getCloudArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getCloudArrList().get(i).getFrame(), null, cloudRect, null);
                }
            }
            if (!levels.get(n).getGrassFloorArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getGrassFloorArrList().size(); i++) {
                    grassFloorRect.set(levels.get(n).getGrassFloorArrList().get(i).getPosX(), levels.get(n).getGrassFloorArrList().get(i).getPosY(),
                            levels.get(n).getGrassFloorArrList().get(i).getPosX() + levels.get(n).getGrassFloorArrList().get(i).getWidth(), levels.get(n).getGrassFloorArrList().get(i).getPosY() + levels.get(n).getGrassFloorArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getGrassFloorArrList().get(i).getFrame(), null, grassFloorRect, null);
                }
            }


            if (!levels.get(n).getBushArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getBushArrList().size(); i++) {
                    bushRect.set(levels.get(n).getBushArrList().get(i).getPosX(), levels.get(n).getBushArrList().get(i).getPosY(),
                            levels.get(n).getBushArrList().get(i).getPosX() + levels.get(n).getBushArrList().get(i).getWidth(), levels.get(n).getBushArrList().get(i).getPosY() + levels.get(n).getBushArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getBushArrList().get(i).getFrame(), null, bushRect, null);
                }
            }

            if (!levels.get(n).getColorBlocksArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getColorBlocksArrList().size(); i++) {
                    colorBlocksRect.set(levels.get(n).getColorBlocksArrList().get(i).getPosX(), levels.get(n).getColorBlocksArrList().get(i).getPosY(),
                            levels.get(n).getColorBlocksArrList().get(i).getPosX() + levels.get(n).getColorBlocksArrList().get(i).getWidth(), levels.get(n).getColorBlocksArrList().get(i).getPosY() + levels.get(n).getColorBlocksArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getColorBlocksArrList().get(i).getFrame(), null, colorBlocksRect, null);
                }
            }

            if (!levels.get(n).getCastleFloorArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getCastleFloorArrList().size(); i++) {
                    castleFloorRect.set(levels.get(n).getCastleFloorArrList().get(i).getPosX(), levels.get(n).getCastleFloorArrList().get(i).getPosY(),
                            levels.get(n).getCastleFloorArrList().get(i).getPosX() + levels.get(n).getCastleFloorArrList().get(i).getWidth(), levels.get(n).getCastleFloorArrList().get(i).getPosY() + levels.get(n).getCastleFloorArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getCastleFloorArrList().get(i).getFrame(), null, castleFloorRect, null);
                }
            }

            if (!levels.get(n).getEyeBushesArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getEyeBushesArrList().size(); i++) {
                    eyeBushesRect.set(levels.get(n).getEyeBushesArrList().get(i).getPosX(), levels.get(n).getEyeBushesArrList().get(i).getPosY(),
                            levels.get(n).getEyeBushesArrList().get(i).getPosX() + levels.get(n).getEyeBushesArrList().get(i).getWidth(), levels.get(n).getEyeBushesArrList().get(i).getPosY() + levels.get(n).getEyeBushesArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getEyeBushesArrList().get(i).getFrame(), null, eyeBushesRect, null);
                }
            }

            if (!levels.get(n).getGoalArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getGoalArrList().size(); i++) {
                    goalRect.set(levels.get(n).getGoalArrList().get(i).getPosX(), levels.get(n).getGoalArrList().get(i).getPosY(),
                            levels.get(n).getGoalArrList().get(i).getPosX() + levels.get(n).getGoalArrList().get(i).getWidth(), levels.get(n).getGoalArrList().get(i).getPosY() + levels.get(n).getGoalArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getGoalArrList().get(i).getFrame(), null, goalRect, null);
                }
            }
            if (!levels.get(n).getCoinArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getCoinArrList().size(); i++) {
                    coinRect.set(levels.get(n).getCoinArrList().get(i).getPosX(), levels.get(n).getCoinArrList().get(i).getPosY(),
                            levels.get(n).getCoinArrList().get(i).getPosX() + levels.get(n).getCoinArrList().get(i).getWidth(), levels.get(n).getCoinArrList().get(i).getPosY() + levels.get(n).getCoinArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getCoinArrList().get(i).getCurrentFrame(), null, coinRect, null);
                }

            }


            if (!levels.get(n).getFireFlowerArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getFireFlowerArrList().size(); i++) {
                    fireFlowerRect.set(levels.get(n).getFireFlowerArrList().get(i).getPosX(), levels.get(n).getFireFlowerArrList().get(i).getPosY(),
                            levels.get(n).getFireFlowerArrList().get(i).getPosX() + levels.get(n).getFireFlowerArrList().get(i).getWidth(), levels.get(n).getFireFlowerArrList().get(i).getPosY() + levels.get(n).getFireFlowerArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getFireFlowerArrList().get(i).getFrame(), null, fireFlowerRect, null);
                }
            }

            if (!levels.get(n).getMushroomArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getMushroomArrList().size(); i++) {
                    redMushroomRect.set(levels.get(n).getMushroomArrList().get(i).getPosX(), levels.get(n).getMushroomArrList().get(i).getPosY(),
                            levels.get(n).getMushroomArrList().get(i).getPosX() + levels.get(n).getMushroomArrList().get(i).getWidth(), levels.get(n).getMushroomArrList().get(i).getPosY() + levels.get(n).getMushroomArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getMushroomArrList().get(i).getFrame(), null, redMushroomRect, null);
                }
            }


            if (!levels.get(n).getBlockArrList().isEmpty()) {

                for (int i = 0; i < levels.get(n).getBlockArrList().size(); i++) {
                    blockRect.set(levels.get(n).getBlockArrList().get(i).getPosX(), levels.get(n).getBlockArrList().get(i).getPosY(),
                            levels.get(n).getBlockArrList().get(i).getPosX() + levels.get(n).getBlockArrList().get(i).getWidth(), levels.get(n).getBlockArrList().get(i).getPosY() + levels.get(n).getBlockArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getBlockArrList().get(i).getFrame(), null, blockRect, null);
                }
            }


            if (!levels.get(n).getBrickBlockArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getBrickBlockArrList().size(); i++) {
                    brickBlockRect.set(levels.get(n).getBrickBlockArrList().get(i).getPosX(), levels.get(n).getBrickBlockArrList().get(i).getPosY(),
                            levels.get(n).getBrickBlockArrList().get(i).getPosX() + levels.get(n).getBrickBlockArrList().get(i).getWidth(), levels.get(n).getBrickBlockArrList().get(i).getPosY() + levels.get(n).getBrickBlockArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getBrickBlockArrList().get(i).getCurrentFrame(), null, brickBlockRect, null);

                }
            }
            if (!levels.get(n).getQuestionBlockArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getQuestionBlockArrList().size(); i++) {
                    questionBlockRect.set(levels.get(n).getQuestionBlockArrList().get(i).getPosX(), levels.get(n).getQuestionBlockArrList().get(i).getPosY(),
                            levels.get(n).getQuestionBlockArrList().get(i).getPosX() + levels.get(n).getQuestionBlockArrList().get(i).getWidth(), levels.get(n).getQuestionBlockArrList().get(i).getPosY() + levels.get(n).getQuestionBlockArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getQuestionBlockArrList().get(i).getCurrentFrame(), null, questionBlockRect, null);
                }

            }

            if (!levels.get(n).getPiranhaPlantArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getPiranhaPlantArrList().size(); i++) {
                    piranhaRect.set(levels.get(n).getPiranhaPlantArrList().get(i).getPosX(), levels.get(n).getPiranhaPlantArrList().get(i).getPosY(),
                            levels.get(n).getPiranhaPlantArrList().get(i).getPosX() + levels.get(n).getPiranhaPlantArrList().get(i).getWidth(), levels.get(n).getPiranhaPlantArrList().get(i).getPosY() + levels.get(n).getPiranhaPlantArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getPiranhaPlantArrList().get(i).getCurrentFrame(), null, piranhaRect, null);
                }


            }
            if (!levels.get(n).getPipeArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getPipeArrList().size(); i++) {
                    pipeRect.set(levels.get(n).getPipeArrList().get(i).getPosX(), levels.get(n).getPipeArrList().get(i).getPosY(),
                            levels.get(n).getPipeArrList().get(i).getPosX() + levels.get(n).getPipeArrList().get(i).getWidth(), levels.get(n).getPipeArrList().get(i).getPosY() + levels.get(n).getPipeArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getPipeArrList().get(i).getFrame(), null, pipeRect, null);
                }

            }


            if (!levels.get(n).getBooArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getBooArrList().size(); i++) {
                    booRect.set(levels.get(n).getBooArrList().get(i).getPosX(), levels.get(n).getBooArrList().get(i).getPosY(),
                            levels.get(n).getBooArrList().get(i).getPosX() + levels.get(n).getBooArrList().get(i).getWidth(), levels.get(n).getBooArrList().get(i).getPosY() + levels.get(n).getBooArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getBooArrList().get(i).getCurrentFrame(), null, booRect, null);
                }

            }


            if (!levels.get(n).getGoombaArrList().isEmpty()) {
                for (int i = 0; i < levels.get(n).getGoombaArrList().size(); i++) {
                    goombaRect.set(levels.get(n).getGoombaArrList().get(i).getPosX(), levels.get(n).getGoombaArrList().get(i).getPosY(),
                            levels.get(n).getGoombaArrList().get(i).getPosX() + levels.get(n).getGoombaArrList().get(i).getWidth(), levels.get(n).getGoombaArrList().get(i).getPosY() + levels.get(n).getGoombaArrList().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getGoombaArrList().get(i).getCurrentFrame(), null, goombaRect, null);
                }


            }





       /* canvas.drawBitmap(levels.get(n).brickBlock.getCurrentFrame(),null, brickBlockRect,null);
        canvas.drawBitmap(levels.get(n).questionBlock.getCurrentFrame(),null, questionBlockRect,null);
        canvas.drawBitmap(levels.get(n).pipe.getFrame(),null, pipeRect,null);
        canvas.drawBitmap(levels.get(n).coin.getCurrentFrame(),null, coinRect,null);
        canvas.drawBitmap(levels.get(n).boo.getCurrentFrame(),null, booRect,null);
        canvas.drawBitmap(levels.get(n).mushroom.getFrame(),null, redMushroomRect,null);
        canvas.drawBitmap(levels.get(n).piranhaPlant.getCurrentFrame(),null, piranhaRect,null);
        canvas.drawBitmap(levels.get(n).goomba.getCurrentFrame(),null, goombaRect,null);
        canvas.drawBitmap(levels.get(n).block.getFrame(),null, blockRect,null);*/


            ////////////FLOOR
            if (!levels.get(n).getPlatforms().isEmpty()) {
                for (int i = 0; i < levels.get(n).getPlatforms().size(); i++) {
                    dst.set(levels.get(n).getPlatforms().get(i).getFloorPosX(), levels.get(n).getPlatforms().get(i).getFloorPosY(),
                            levels.get(n).getPlatforms().get(i).getFloorPosX() + levels.get(n).getPlatforms().get(i).getWidth(), levels.get(n).getPlatforms().get(i).getFloorPosY() + levels.get(n).getPlatforms().get(i).getHeight());
                    canvas.drawBitmap(levels.get(n).getPlatforms().get(i).getFloorFrame(), null, dst, null);
                }
            }
            /////////////FIREBALL
            if (levels.get(n).getMario().isSpawnFire()) {
                fireBallRect.set(levels.get(n).getMario().getFireBallPosX(), levels.get(n).getMario().getFireBallPosY(),levels.get(n).getMario().getFireBallPosX()+40, levels.get(n).getMario().getFireBallPosY()+40);
                canvas.drawBitmap(fireBall[levels.get(n).getMario().getFireBallIndex()], null, fireBallRect, null);
            }
            //paint.setColor(Color.WHITE);
            //canvas.drawRect(cloudPosX, cloudPosY, cloudPosX+ 500, cloudPosY+ 200, paint);

            blockRect.set(levels.get(n).getBlock().getPosX(), levels.get(n).getBlock().getPosY(), levels.get(n).getBlock().getPosX() + levels.get(n).getBlock().getWidth(), levels.get(n).getBlock().getPosY() + levels.get(n).getBlock().getHeight());
            canvas.drawBitmap(levels.get(n).getBlock().getFrame(), null, blockRect, null);


            /////////////////MARIO
            paint.setColor(Color.RED);
            marioRect.set((int) levels.get(n).getMario().getPosX() - 50 - levels.get(n).getMario().getMarioWidth(), (int) levels.get(n).getMario().getPosY() - 50 - levels.get(n).getMario().getMarioHeight(),
                    (int) levels.get(n).getMario().getPosX() + 50 + levels.get(n).getMario().getMarioWidth(), (int) levels.get(n).getMario().getPosY() + 50); //MARIO
            canvas.drawBitmap(levels.get(n).getMario().getCurrentFrame(), null, marioRect, null);
            ///////////////////GOOMBA
            ///////////////////////JOYSTICK
            paint.setColor(Color.WHITE);
            canvas.drawOval(buttonAposX - 150, buttonAposY - 150, buttonAposX + 150, buttonAposY + 150, paint);
            canvas.drawOval(buttonBposX - 150, buttonBposY - 150, buttonBposX + 150, buttonBposY + 150, paint);
            paint.setColor(Color.GRAY);
            paint.setAlpha(40);
            canvas.drawOval(200, 750, 700, 1250, paint);
            paint.setColor(Color.LTGRAY);
            paint.setAlpha(150);
            canvas.drawOval(joystickPosX - 100, joystickPosY - 100, joystickPosX + 100, joystickPosY + 100, paint);

            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(levels.get(n).getMario().getPosX()-50-levels.get(n).getMario().getMarioWidth(),levels.get(n).getMario().getPosY()-44-levels.get(n).getMario().getMarioHeight(),levels.get(n).getMario().getPosX()-48 -levels.get(n).getMario().getMarioWidth(),levels.get(n).getMario().getPosY()+44/*+ levels.get(n).getMario().getMarioHeight()*/,paint);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(levels.get(n).getMario().getPosX()+50+levels.get(n).getMario().getMarioWidth(),levels.get(n).getMario().getPosY()-44-levels.get(n).getMario().getMarioHeight(),levels.get(n).getMario().getPosX()+48 +levels.get(n).getMario().getMarioWidth(),levels.get(n).getMario().getPosY()+44/*+ levels.get(n).getMario().getMarioHeight()*/,paint);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(levels.get(n).getMario().getPosX()-48-levels.get(n).getMario().getMarioWidth(),levels.get(n).getMario().getPosY()-50-levels.get(n).getMario().getMarioHeight(),levels.get(n).getMario().getPosX()+48 +levels.get(n).getMario().getMarioWidth(),levels.get(n).getMario().getPosY()-48- levels.get(n).getMario().getMarioHeight(),paint);
            paint.setColor(Color.CYAN);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(levels.get(n).getMario().getPosX()-48-levels.get(n).getMario().getMarioWidth(),levels.get(n).getMario().getPosY()+50/*+levels.get(n).getMario().getMarioHeight()*/,levels.get(n).getMario().getPosX()+48+ levels.get(n).getMario().getMarioWidth(),levels.get(n).getMario().getPosY()+44/*+ levels.get(n).getMario().getMarioHeight()*/,paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(70);
            String stringLives = "LIVES: " + lives;
            String stringPoints = "POINTS: " + points;
            canvas.drawText(stringLives,100,100,paint);
            canvas.drawText(stringPoints,2100,100,paint);
        }
        else
        {
            paint.setColor(Color.GREEN);
            paint.setTextSize(100);
            canvas.drawText("GAME OVER", 1000,800,paint);
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //levels.get(n) = new LevelOne(this);
        //levels.add(levels.get(n));
        WorldThread worldThread = new WorldThread(this);
        MarioThread marioThread = new MarioThread(this);
        FireBallThread fireBallThread = new FireBallThread(this);
        LevelOneThread levelOneThread = new LevelOneThread(this);
        levelTwoThread = new LevelTwoThread(this);
        levelThreeThread = new LevelThreeThread(this);
        levelOneThread.start();
        fireBallThread.start();
        worldThread.start();
        marioThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public boolean onTouchEvent(MotionEvent e) {
        int pointerIndex;
        int action = e.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            {
                pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

                tracks.remove(Integer.valueOf(e.getPointerId(pointerIndex)));
                pointerIndex =Integer.valueOf(e.getPointerId(pointerIndex));
                if (pointerIndex == joystickIndex)
                {
                    joystickPosY = 1000;
                    joystickPosX = 450;
                    levels.get(n).getMario().stopWalkingRight();
                    levels.get(n).getMario().stopWalkingLeft();
                }
                if(!reachedMaximum)
                {
                    altitude = 450;
                }
                break;
            }
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:{
                pointerIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                tracks.add(Integer.valueOf(e.getPointerId(pointerIndex)));

                clickX = e.getX(pointerIndex);
                clickY = e.getY(pointerIndex);
                distanceFromJoystick = (float) Math.sqrt(Math.pow((clickX - 450), 2) + Math.pow((clickY - 1000), 2));
                if (distanceFromJoystick < 250) {
                    joystickIndex = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    joystickIndex = (Integer.valueOf(e.getPointerId(joystickIndex)));
                }
                distanceFromButton = (float) Math.sqrt(Math.pow((clickX - buttonAposX), 2) + Math.pow((clickY - buttonAposY), 2));

                if (distanceFromButton < 150)
                {
                    levels.get(n).getMario().marioJump();
                    levels.get(n).getMario().setCanJump(true);
                    //marioJump = true;
                }
                distanceFromButton = (float) Math.sqrt(Math.pow((clickX - buttonBposX), 2) + Math.pow((clickY - buttonBposY), 2));
                if (distanceFromButton < 150)
                {
                    levels.get(n).getMario().setFireAnimation(true);
                    levels.get(n).getMario().setFired(true);
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                for (Integer i:tracks)
                {
                    pointerIndex = e.findPointerIndex(i.intValue());
                    for (int j = 0; j < e.getPointerCount(); j++)
                    {
                        clickX = e.getX(pointerIndex);
                        clickY = e.getY(pointerIndex);
                        distanceFromJoystick = (float) Math.sqrt(Math.pow((clickX - 450), 2) + Math.pow((clickY - 1000), 2));
                        if (distanceFromJoystick >= 250) {
                            joystickOutOfBoundary = true;
                        }else{
                            joystickOutOfBoundary = false;
                        }
                        if (!joystickOutOfBoundary) {
                            joystickPosX = clickX;//e.getX();
                            joystickPosY = clickY;//e.getY();
                            if (joystickPosX > 500) {
                                levels.get(n).getMario().startWalkingRight();
                                levels.get(n).getMario().stopWalkingLeft();
                            }
                            if (joystickPosX < 400) {
                                levels.get(n).getMario().stopWalkingRight();
                                levels.get(n).getMario().startWalkingLeft();
                            }
                            if(joystickPosX >420 &&joystickPosX<480){
                                levels.get(n).getMario().stopWalkingLeft();
                                levels.get(n).getMario().stopWalkingRight();
                            }
                        }
                    }
                }

                break;
            }
        }
        return true;
    }
   interface marioPowerUp{
        boolean powerUp();
    }


    public boolean isFire(){
        boolean a = levels.get(n).getMario().getFired();
        return a;
    }

    public Bitmap[] getMario()
    {
        return mario;
    }
    public Bitmap getFireFlower()
    {
        return fireFlower;
    }

    public Bitmap[] getFireBall() {
        return fireBall;
    }

    public Bitmap getFloor() {
        return floor;
    }
    public Bitmap getBlock(){
        return block;
    }
    public Bitmap[] getQuestionBlock(){
        return questionBlock;
    }
    public Bitmap [] getCoin(){
        return coin;
    }
    public Bitmap[] getPiranhaPlant(){
        return piranhaPlant;
    }
    public Bitmap[] getBrickBlock(){
        return brickBlock;
    }
    public Bitmap[] getBoo(){
        return boo;
    }
    public Bitmap[] getGoomba(){
        return goomba;
    }
    public Bitmap getPipe(){
        return pipe;
    }
    public Bitmap getMushroom(){
        return redMushroom;
    }
    public Bitmap getCloud(){
        return cloud;
    }
    public Bitmap getBush(){
        return bush;
    }
    public Bitmap getEye_bushes(){
        return eye_bushes;
    }
    public Bitmap getGoal(){
        return goal;
    }
    public Bitmap getCastleFloor(){
        return castleFloor;
    }
    public Bitmap getColorBlocks(){
        return colorBlocks;
    }

    public Bitmap getGrassFloor(){
        return grassFloor;
    }
    public void incrementN(int itr)
    {
        n += itr;
    }

    public void updateLives()
    {
        lives--;
    }
    public void addPoints(int p)
    {
        points += p;
    }



}