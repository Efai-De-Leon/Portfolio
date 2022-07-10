package com.example.efaideleon.super_mario_bros;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class Mario extends Character {
    private boolean right = false;
    private int left = 0;
    private boolean canTakeExtraStep = false;
    private int time = 10;
    private int firecount =0;
    private int mushroomMarioIndex = 0;
    private float marioPosX = 2000;
    private float marioPosY = 950;
    private boolean walkRightAir = false;
    private boolean walkRightGround = false;
    private boolean walkLeftAir = false;
    private boolean walkLeftGround = false;
    private boolean marioJump = false;
    private boolean hasfireflower = false;
    private float altitude  = 0;
    private float maximumJump = 500;
    private boolean reachedMaximum = false;
    private int joystickIndex;
    private float changeInPosition;
    private int marioIndexRight = 2;
    private int marioIndexLeft = 2;
    private int marioIndex = 3;
    private int newmarioIndexRight = 0;
    private int marioHeight = 0;
    private int marioWidth = 0;
    private int fireBallPosX;
    private int fireBallPosY;
    private int fireBallIndex = 0;
    private int existenceOfFire = 0;
    private boolean fired = false;
    private boolean spawnFire = false;
    private boolean fireAnimation = false;
    private int tempMarioIndex = 0;
    private double changeInPosXFire = 0;
    private int tempfirechange = 0;
    private int fireoffset = 0;
    private int tempfirehigh = 0;
    private int fireBallRate = 0;
    private int numFireTouchGround = 0;
    private boolean mushroom = false;
    private int GROUND = 950;
    private boolean isJumping = false;
    private int fireImageIndex = 0;
    private int left2 = 0;
    private boolean canJump = false;
    private boolean collisionWithFloor = false;
    private boolean fireBallTouchedFloor =false;
    com.example.efaideleon.super_mario_bros.TransferPoint transferPoint = new com.example.efaideleon.super_mario_bros.TransferPoint();
    private int verticalVelocity = 10;
    public Mario(int x, int y, Bitmap[] cF) {
        super(x, y, cF);

    }

    public void marioFalls(){
        setPosY(verticalVelocity);
    }
    public void stopFalling(){
        verticalVelocity = 0;

    }
    public void startFalling(){
        verticalVelocity = 10;
    }

    public void setCollisionWithFloor(boolean c){
        collisionWithFloor = c;
    }
    public boolean getCollisionWithFloor(){
        return collisionWithFloor;
    }

    private com.example.efaideleon.super_mario_bros.WorldView.marioPowerUp[] marioPowerUps = new com.example.efaideleon.super_mario_bros.WorldView.marioPowerUp[]{
            new com.example.efaideleon.super_mario_bros.WorldView.marioPowerUp() {
                @Override
                public boolean powerUp() {
                    return hasMushroom();
                }
            },
            new com.example.efaideleon.super_mario_bros.WorldView.marioPowerUp() {
                @Override
                public boolean powerUp() {
                    return hasFireFlower();
                }
            },
            new com.example.efaideleon.super_mario_bros.WorldView.marioPowerUp() {
                @Override
                public boolean powerUp() {
                    return hasNoPowerUp();
                }
            }

    };


    public void startWalkingLeft(){
        walkLeftAir = true;
        walkLeftGround = true;
    }
    public void startWalkingRight(){
        walkRightAir = true;
        walkRightGround = true;
    }
    public void stopWalkingLeft(){
        walkLeftAir = false;
        walkLeftGround = false;
    }
    public void stopWalkingRight(){
        walkRightGround = false;
        walkRightAir = false;
    }
    public void giveFireFlower(boolean fireFlower){
        if(fireFlower){
            giveMushroom(true);
            mushroomMarioIndex = 6;
            hasfireflower = true;
        }
        if(!fireFlower){
            hasfireflower = false;
            mushroomMarioIndex = 0;
        }
    }
    public boolean hasFireFlower(){
        return hasfireflower;
    }
    public void giveMushroom(boolean mushroom){
        if(mushroom){
            marioWidth = 10;
            marioHeight = 70;
            this.mushroom = true;
        }
        else{
            mushroomMarioIndex = 0;
            marioWidth = 0;
            marioHeight = 0;
            this.mushroom = false;
        }

    }
    public boolean hasMushroom(){
        return mushroom;
    }
    public boolean hasNoPowerUp(){
        boolean noPowerUp = false;
        if(!hasFireFlower() && !hasMushroom()){
            noPowerUp = true;
        }
        return noPowerUp;
    }

    public void setWalkingDirection(boolean right){
        int walkingLeft = 0;
        if(!right) {
            walkingLeft = 3;
        }

        marioIndexRight++;
        if(marioIndexRight == 3){
            marioIndex = 1 + mushroomMarioIndex + walkingLeft;
            setCurrentFrame(marioIndex);

        }
        if(marioIndexRight == 6){
            marioIndex = 2 + mushroomMarioIndex + walkingLeft;
            setCurrentFrame(marioIndex);

        }
        if(marioIndexRight == 9){
            marioIndex = mushroomMarioIndex + walkingLeft;
            setCurrentFrame(marioIndex);
            marioIndexRight =0;

        }
    }


    public void takeExtraStep(){
        if(Math.abs(changeInPosition)> 250&& !walkLeftGround &&!walkRightGround){
            if(isMarioFacingRight()){
                right = true;
                canTakeExtraStep = true;
                setMarioIndex(0);
                setCurrentFrame(marioIndex);
            }
            if(!isMarioFacingRight()){
                right = false;
                canTakeExtraStep = true;
                setMarioIndex(0);
                setCurrentFrame(marioIndex);
            }
        }
        if(Math.abs(changeInPosition)< 250&& !walkLeftGround &&!walkRightGround){
            if(isMarioFacingRight()){
                right = true;
                canTakeExtraStep = false;
                setMarioIndex(0);
                setCurrentFrame(marioIndex);
            }
            if(!isMarioFacingRight()){
                right = false;
                canTakeExtraStep = false;
                setMarioIndex(0);
                setCurrentFrame(marioIndex);
            }
        }
        if(!walkLeftGround &&!walkRightGround) {
            if (canTakeExtraStep) {
                time--;
                newmarioIndexRight++;
                if (newmarioIndexRight >= 0 && newmarioIndexRight <= 3) {
                    setMarioIndex(1);
                    setCurrentFrame(marioIndex);

                }
                if (newmarioIndexRight > 3 && newmarioIndexRight <= 6) {
                    setMarioIndex(2);
                    setCurrentFrame(marioIndex);
                }
                if (newmarioIndexRight > 6 && newmarioIndexRight <= 9) {
                    setMarioIndex(0);
                    newmarioIndexRight = 0;
                    setCurrentFrame(marioIndex);
                }

                if (transferPoint.getTransferPX() <= 1225) {
                    if (right) {
                        moveMarioRight();
                    }
                    if (!right) {
                        moveMarioLeft();
                    }
                }

                if (time == 0) {
                    setMarioIndex(0);
                    setCurrentFrame(marioIndex);
                    newmarioIndexRight = 0;
                    changeInPosition = 0;
                    canTakeExtraStep = false;
                    time = 9;
                }
                System.out.println(marioIndex);
            }
        }
    }

    public void setMarioOnGround(){
        isJumping = false;
        //setPosY(950);
        marioJump = false;
        altitude = 0;
        reachedMaximum= false;
        walkLeftAir = false;
        walkRightAir = false;
    }
    public boolean isMarioFacingRight(){
        boolean direction = false;
        if((marioIndex)>=mushroomMarioIndex &&(marioIndex)<=2 + mushroomMarioIndex
                ||(marioIndex == 12)||(marioIndex==16 || marioIndex == 14)) {
            direction = true;
            left = 0;
        }
        if ((marioIndex <= 5 + mushroomMarioIndex && marioIndex >= 3 + mushroomMarioIndex)
                || marioIndex == 13 || marioIndex == 17 || marioIndex == 15) {
            direction = false;
            left = 3;
        }
        return direction;
    }

    public void marioJumps(){
        boolean direction = isMarioFacingRight();
        int index = 12;
        for(int j = 1; j<3; j++) {
            for (int i = 0; i < 2; i++) {
                if (marioPowerUps[j].powerUp() && direction) {
                    marioIndex = index;
                    setCurrentFrame(marioIndex);
                    j = 3;
                    break;

                }else{
                    direction = !isMarioFacingRight();
                    index++;
                }

            }
            direction = isMarioFacingRight();
        }
        if(!canJump) {
            altitude = 500;
        }
            altitude += 15;


            if (altitude < maximumJump) {
                setPosY(-25);
            }
            if (altitude >= maximumJump) {
                reachedMaximum = true;
                setPosY(10);
            }

        isJumping = true;
    }

    public void setMarioIndex(int marioIndex) {
        this.marioIndex = marioIndex + mushroomMarioIndex + left;
    }

    public boolean isMarioJumping(){
        return isJumping;
    }

    public void moveMarioRight(){
        setPosX(5);

    }
    public void moveMarioLeft(){
        setPosX(-5);
    }

    public void setMarioPosX()
    {
        if(walkRightGround)
        {
            setWalkingDirection(true);
            if(transferPoint.getTransferPX() <= 1225)
            {
                moveMarioRight();
            }
            if(time < 10){
                changeInPosition = 0;
                time = 10;
            }
            changeInPosition +=10;
        }
        if(walkLeftGround)
        {
            setWalkingDirection(false);
            if( transferPoint.getTransferPX() <= 1225){
                moveMarioLeft();
            }
            if(time < 10){
                changeInPosition = 0;
                time  = 10;
            }
            changeInPosition -=10;
        }
        takeExtraStep();

        if(marioJump)
        {
            marioJumps();
        }

        if(hasFireFlower()) {
            if (fireAnimation) {
                firecount++;
                if (isMarioFacingRight()) {
                    if (firecount <= 7) {
                        tempMarioIndex = marioIndex;
                        marioIndex = 16;
                        setCurrentFrame(marioIndex);
                    }
                }
                if (!isMarioFacingRight()) {
                    if (firecount <= 7) {
                        tempMarioIndex = marioIndex;
                        marioIndex = 17;
                        setCurrentFrame(marioIndex);
                    }
                }
                if (firecount == 7) {
                    marioIndex = tempMarioIndex;
                    setCurrentFrame(marioIndex);
                    firecount = 0;
                    fireAnimation = false;

                }
            }
        }


    }

    public void setFireBallPosition(){
        if(isMarioFacingRight()&&hasFireFlower()) {
            if (!spawnFire) {
                fireBallPosX = (int) getPosX() + 90;
                fireBallPosY = (int) getPosY() - 50;
                spawnFire = true;
                tempfirechange = fireBallPosX;
                tempfirehigh = fireBallPosY;
            } else {
                changeInPosXFire = fireBallPosX - tempfirechange;
                fireBallPosY = ((int) (((Math.pow((changeInPosXFire - fireoffset), 2)) / (100)) + tempfirehigh));
                if (fireBallTouchedFloor) {
                    if (numFireTouchGround == 0) {
                        fireoffset = (int) (changeInPosXFire + 100);
                    }
                    if (numFireTouchGround >= 1) {
                        fireoffset += 200;
                    }
                    numFireTouchGround++;
                    fireBallTouchedFloor = false;
                }
                fireBallPosX += 7;
                System.out.println("fireball Y position"+fireBallPosY);
            }
        }
        if(!isMarioFacingRight()&&hasFireFlower()) {
            if (!spawnFire) {
                fireBallPosX = (int) getPosX() - 90;
                fireBallPosY = (int) getPosY() - 50;
                spawnFire = true;
                tempfirechange = fireBallPosX;
                tempfirehigh = fireBallPosY;
            } else {
                changeInPosXFire = (fireBallPosX - tempfirechange);
                fireBallPosY = ((int) (((Math.pow((changeInPosXFire + fireoffset), 2)) / (100)) + tempfirehigh));
                if (fireBallTouchedFloor) {
                    if (numFireTouchGround == 0) {
                        fireoffset = (int) (changeInPosXFire + 100);
                    }
                    if (numFireTouchGround >= 1) {
                        fireoffset += 199;
                    }
                    numFireTouchGround++;
                    fireBallTouchedFloor = false;
                }
                fireBallPosX -= 7;

            }
        }
        fireBallRate++;
        existenceOfFire++;

        if(fireBallRate == 0){
            fireBallIndex = 0;
        }
        if(fireBallRate == 4){
            fireBallIndex = 1;
        }
        if(fireBallRate == 8){
            fireBallIndex = 2;
        }
        if(fireBallRate == 12){
            fireBallIndex = 3;
            fireBallRate = 0;
        }

        if(existenceOfFire >= 200){
            fired = false;
            spawnFire = false;
            existenceOfFire = 0;
            fireAnimation = false;
            fireoffset = 0;
            //fireball posx and y to be out screen
        }

    }
    public boolean isFire(){
        return fired;
    }
    public boolean getfireMario(){
        return hasfireflower;
    }

    public void marioJump()
    {
        marioJump = true;
    }

    public boolean isWalkLeftGround() {
        return walkLeftGround;
    }

    public boolean isWalkRightGround() {
        return walkRightGround;
    }
    public int getMarioWidth(){
        return marioWidth;
    }
    public int getMarioHeight(){
        return marioHeight;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public Rect fireBallBounds()
    {
        return (new Rect(fireBallPosX,fireBallPosY,fireBallPosX+40,fireBallPosY+50));
    }

    public Rect bounds(){
         return (new Rect(getPosX()-50-marioWidth,getPosY()-50-marioHeight,getPosX()+50+marioWidth,getPosY()+50));
    }

    public Rect leftBounds()
    {
        return (new Rect(getPosX()-50-marioWidth,getPosY()-40-marioHeight,getPosX()-44 -getMarioWidth(),getPosY()+43));
    }
    public Rect rightBounds()
    {
        return (new Rect(getPosX()+50+marioWidth,getPosY()-40-marioHeight,getPosX() +45 + getMarioWidth(),getPosY()+43));
    }
    public Rect topBounds()
    {
        return (new Rect(getPosX()-48-marioWidth,getPosY()- 50 - marioHeight,getPosX() + 48 + getMarioWidth(),getPosY()-48 - getMarioHeight()));
    }
    public Rect bottomBounds()
    {
        return (new Rect(getPosX()-43 -marioWidth,getPosY()+50,getPosX() +43 + getMarioWidth(),getPosY()+44));
    }



    public void setCanJump(boolean canJump) {
        this.canJump = canJump;
    }

    public int getMarioIndex() {
        return marioIndex;
    }

    public void setFired(boolean a){
        fired = a;
    }
    public void setFireAnimation(boolean a){
        fireAnimation = a;
    }
    public boolean getFired(){
        return fired;
    }

    public int getFireBallIndex() {
        return fireBallIndex;
    }

    public int getFireBallPosX() {
        return fireBallPosX;
    }

    public int getFireBallPosY() {
        return fireBallPosY;
    }

    public boolean isSpawnFire() {
        return spawnFire;
    }
    public void setFireBallTouchedFloor(boolean a){
        fireBallTouchedFloor = a;
    }
    public void setTempfirehigh(int a){
        tempfirehigh = a;
    }
    public void setExistenceOfFire(int a){
        existenceOfFire =a;
    }
}
