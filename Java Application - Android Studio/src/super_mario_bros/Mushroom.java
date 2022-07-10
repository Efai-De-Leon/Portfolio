package com.example.efaideleon.super_mario_bros;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class Mushroom implements Items{
    private int posX = 0;
    private int posY = 0;
    private Bitmap frame;
    private int width = 80;
    private int height = 80;
    private boolean walkRight = true;
    private boolean walkLeft = false;
    private int changeInPosition;
    private int velocityDown = 10;
    private int distanceAfterSpawning = 0;
    private boolean collisionWithFloor = false;
    private boolean collisionWithBrickBlock = false;
    public Mushroom(int x, int y, Bitmap f){
        posX = x;
        posY = y;
        frame = f;

    }

    public void setFrame(Bitmap frame) {
        this.frame = frame;
    }

    public void setPosX(int posX) {
        this.posX += posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Bitmap getFrame() {
        return frame;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Rect bounds(){
        return (new Rect(posX,posY,posX+ width,posY+ height));
    }

    public void mushroomGravity(){
        posY += velocityDown;
    }

    public void stopMushroomFalling(){
        velocityDown = 0;
    }

    public void startMushroomFalling(){
        velocityDown = 10;
    }

    public boolean getCollisionWithFloor(){
        return collisionWithFloor;
    }
    public void setCollisionWithFloor(boolean collisionWithFloor){
        this.collisionWithFloor = collisionWithFloor;
    }
    public void setLocationY(int y){
        posY = y;
    }
    public void setLocationX(int x)
    {
        posX = x;
    }


    public boolean getCollisionWithBrickBlock(){
        return collisionWithBrickBlock;
    }
    public void setCollisionWithBrickBlock(boolean collisionWithBrickBlock){
        this.collisionWithBrickBlock = collisionWithBrickBlock;
    }

    public void mushroomAi(){
        if(walkRight){
            posX +=4;
            distanceAfterSpawning +=4;
        }
        if(walkLeft){
            posX -=4;
        }



        if(!getCollisionWithFloor()&&!getCollisionWithBrickBlock()&&distanceAfterSpawning>=80){
            mushroomGravity();
        }



    }

    public void changeDirection(){
        if(walkRight)
        {
            walkLeft = true;
            walkRight = false;
        }
        else{
            walkRight = true;
            walkLeft = false;
        }
    }
    public void moveUp(){
        if(changeInPosition == 0) {
            posY = posY - 70;
        }
       changeInPosition = 1;
    }
    public int getChangeInPosition(){
        return changeInPosition;
    }
}
