package com.example.efaideleon.candy_crush;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import java.util.ArrayList;
import java.util.Random;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    Bitmap mybitmap;
    Bitmap redcandy;
    Bitmap orangecandy;
    Bitmap purplecandy;
    Bitmap yellowcandy;
    Bitmap cyancandy;
    Bitmap greencandy;
    Bitmap temp;
    private Bitmap [] fire;
    Canvas canvas;
    Integer score = 0;
    int roworcolumn;
    int idofboxmovedwith;
    int boxid;
    float xDown = 0;
    float yDown = 0;
    float xMove = 0;
    float yMove = 0;
    int  tempc = 0; // colorid
    int  utmepc = 0;
    int  dtempc = 0;
    int  rtempc = 0;
    int  ltempc = 0;
    boolean solution = false;
    int candy1;
    int candy2;
    int candy3;
    Bitmap currentBitamp;
    Bitmap downBitmap; //adjacent images
    Bitmap rightBitmap;
    Bitmap leftBitmap;
    Bitmap upBitmap;
    boolean oneswipe = true;
    int numberofcandiesinsequence = 0;
    SurfaceHolder surfaceHolder;
    int colorid = 0;
    float x = -1;
    float y = -1;
    Thread thread = null;
    boolean draw = false;
    int numberofrowsfromthetop = 1;
    int numberOfCombos = 0;
    Integer numberOfMoves = 16;
    String scoretext = "score: 0";
    String gameoverText ="COMPLETED";
    String movestext = "moves: 16";
    int fireIndex = 0;
    private Paint textPaint;
    int tempnumberinsequence;
    //int sizeOfNumberOfCandiesInSolution = 0;
    int [] numberOfCandiesInSolution = new int[10];
    ArrayList<Candies> CandyList = new ArrayList<>();
    MainActivity o = new MainActivity();
    Boolean gameOver = false;

    public BoardView(Context context) {
        super(context) ;
        surfaceHolder =getHolder();
        mybitmap = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.backgroundfornow) ;
        //board = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.image) ;
        orangecandy = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.orangecandy) ;
        purplecandy = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.purplecandy) ;
        yellowcandy = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.yellowcandy) ;
        cyancandy = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.cyancandy) ;
        greencandy = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.greencandy) ;
        redcandy = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.redcandy) ;
        temp = BitmapFactory.decodeResource(getResources(), R.drawable.greencandy);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(150);
        textPaint.setTypeface(Typeface.MONOSPACE);
        getHolder().addCallback(this) ;
        setFocusable(true) ;
        fire = new Bitmap[21];
        fire[0] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0001);
        fire[1] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0002);
        fire[2] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0003);
        fire[3] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0004);
        fire[4] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0005);
        fire[5] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0006);
        fire[6] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0007);
        fire[7] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0008);
        fire[8] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0009);
        fire[9] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0010);
        fire[10] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0011);
        fire[11] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0012);
        fire[12] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0013);
        fire[13] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0014);
        fire[14] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0015);
        fire[15] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0016);
        fire[16] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0017);
        fire[17] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0018);
        fire[18] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0019);
        fire[19] = BitmapFactory.decodeResource (getResources ( ) ,R.drawable.fire0020);

        for(int  i = 0; i< 20; i++){
            fire[i]= getResizeBitmap(fire[i],160,160);
        }

    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Canvas c = holder.lockCanvas();
        //this.onDraw(c);
        //holder.unlockCanvasAndPost(c);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    @Override
    public boolean onTouchEvent(MotionEvent e){
        //draw a rectangle at the pos you click on the screen

        switch (e.getAction()){
            case MotionEvent.ACTION_UP:{
                if(!isThereSolution()) {
                    getCandy(boxid).setColorid(tempc);
                    if(!(boxid >= 73 && boxid <= 79)&&boxid!=80&&boxid!=72) {
                        getCandy(boxid + 9).setColorid(dtempc);
                    }
                    if(!(boxid== 17 ||
                            boxid == 26 ||
                            boxid == 35 ||
                            boxid == 44 ||
                            boxid == 53 ||
                            boxid == 62 ||
                            boxid == 71
                    )&&boxid!=8&&boxid!=80) {
                        getCandy(boxid + 1).setColorid(rtempc);
                    }
                    if(!(boxid >= 1 && boxid <= 7)&&boxid!=0&&boxid!=8) {
                        getCandy(boxid - 9).setColorid(utmepc);
                    }

                    if(!(boxid%9==0&& boxid != 0 && boxid != 72)&&boxid!=0&&boxid!=72) {
                        getCandy(boxid - 1).setColorid(ltempc);
                    }

                    getCandy(boxid).setCandy(currentBitamp);
                    if(!(boxid >= 73 && boxid <= 79)&&boxid!=80&&boxid!=72) {
                        getCandy(boxid + 9).setCandy(downBitmap);
                    }
                    if(!(boxid== 17 ||
                            boxid == 26 ||
                            boxid == 35 ||
                            boxid == 44 ||
                            boxid == 53 ||
                            boxid == 62 ||
                            boxid == 71
                    )&&boxid!=8&&boxid!=80) {
                        getCandy(boxid + 1).setCandy(rightBitmap);
                    }
                    if(!(boxid >= 1 && boxid <= 7)&&boxid!=0&&boxid!=8) {
                        getCandy(boxid - 9).setCandy(upBitmap);
                    }
                    if(!(boxid%9==0&& boxid != 0 && boxid != 72)&&boxid!=0&&boxid!=72) {
                        getCandy(boxid - 1).setCandy(leftBitmap);
                    }
                    oneswipe = true;
                }
                else{
                    System.out.println("Solution");
                    solution = true;
                    numberOfMoves--;
                    movestext = "moves: "+ numberOfMoves.toString();
                    if(roworcolumn == 0) {
                        shiftRowCandy();
                        while(isThereSolution()){
                            solution = true;
                            if(roworcolumn == 0){
                                shiftRowCandy();
                                numberOfCombos++;
                            }
                            if(roworcolumn ==1){
                                shiftColumnCandy();
                                numberOfCombos++;
                            }
                        }
                        oneswipe = true;
                        System.out.println("Number of Combos: " + numberOfCombos);
                        System.out.println("Your score is: " + scoretext);
                        System.out.println("Number of Moves: " + numberOfMoves);
                        if(numberOfMoves == 0){
                            System.out.println("GAME OVER!");
                            gameOver = true;
                        }
                        numberOfCombos = 0;
                        break;

                    }
                    if(roworcolumn == 1){
                       shiftColumnCandy();
                       while(isThereSolution()){
                           solution = true;
                           if(roworcolumn == 0) {
                               shiftRowCandy();
                               numberOfCombos++;
                           }
                           if(roworcolumn ==1){
                               shiftColumnCandy();
                               numberOfCombos++;
                           }
                       }
                       oneswipe = true;
                        System.out.println("Number of Combos " + numberOfCombos);
                       System.out.println("Your score is: " + scoretext);
                       System.out.println("Number of Moves: " + numberOfMoves);
                       if(numberOfMoves ==0){
                           System.out.println("GAME OVER!");
                           gameOver = true;
                       }
                       numberOfCombos = 0;
                       break;
                    }

                }
            }
            case MotionEvent.ACTION_DOWN: {

                xDown = e.getX();
                yDown = e.getY();
                boxid = checkBoxID();
                tempc = getCandy(boxid).getColorid();
                if(!(boxid >= 73 && boxid <= 79)&&boxid!=80&&boxid!=72) {
                    dtempc = getCandy(boxid + 9).getColorid();
                }
                if(!(boxid== 17 ||
                        boxid == 26 ||
                        boxid == 35 ||
                        boxid == 44 ||
                        boxid == 53 ||
                        boxid == 62 ||
                        boxid == 71
                )&&boxid!=8&&boxid!=80) {
                    rtempc = getCandy(boxid + 1).getColorid();
                }

                if(!(boxid >= 1 && boxid <= 7)&&boxid!=0&&boxid!=8) {
                    utmepc = getCandy(boxid - 9).getColorid();
                }
                if(!(boxid%9==0&& boxid != 0 && boxid != 72)&&boxid!=0&&boxid!=72) {
                    ltempc = getCandy(boxid - 1).getColorid();
                }




                currentBitamp = getCandy(boxid).getCandy();
                if(!(boxid >= 73 && boxid <= 79)&&boxid!=80&&boxid!=72) {
                    downBitmap = getCandy(boxid + 9).getCandy();
                }
                if(!(boxid== 17 ||
                        boxid == 26 ||
                        boxid == 35 ||
                        boxid == 44 ||
                        boxid == 53 ||
                        boxid == 62 ||
                        boxid == 71
                )&&boxid!=8&&boxid!=80) {
                    rightBitmap = getCandy(boxid + 1).getCandy();
                }
                if(!(boxid >= 1 && boxid <= 7)&&boxid!=0&&boxid!=8) {
                    upBitmap = getCandy(boxid - 9).getCandy();
                }
                if(!(boxid%9==0&& boxid != 0 && boxid != 72)&&boxid!=0&&boxid!=72) {
                    leftBitmap = getCandy(boxid - 1).getCandy();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                xMove = e.getX();
                yMove = e.getY();
                if (boxid == 0) {

                    if (oneswipe) {
                        if (xMove > xDown) {
                            swapRight();
                            oneswipe = false;
                            break;

                        } else {
                           swapDown();
                           oneswipe = false;
                           break;
                        }
                    }
                }
                if (boxid >= 10 && boxid <= 16 ||
                        boxid >= 19 && boxid <= 25 ||
                        boxid >= 28 && boxid <= 34 ||
                        boxid >= 37 && boxid<= 43 ||
                        boxid >= 46 && boxid <= 52 ||
                        boxid >= 55 && boxid <= 61 ||
                        boxid >= 64 && boxid <= 70)
                {
                    if (oneswipe) {
                        if (xMove > xDown) {
                            swapRight();
                            oneswipe = false;
                            break;

                        }
                        if(yMove > yDown){
                            swapDown();
                            oneswipe = false;
                            break;
                        }
                        if (yMove < yDown) {
                            swapUp();
                            oneswipe = false;
                            break;

                        }
                        if(xMove < xDown){
                            swapLeft();
                            oneswipe = false;
                            break;
                        }
                    }
                }
                if(boxid >= 1 && boxid <= 7){
                    if (oneswipe) {
                        if (xMove > xDown) {
                            swapRight();
                            oneswipe = false;
                            break;

                        }
                        if (yMove > yDown) {
                            swapDown();
                            oneswipe = false;
                            break;
                        }
                        if(xMove < xDown){
                            swapLeft();
                            oneswipe = false;
                            break;
                        }

                    }

                }
                if(boxid >= 73 && boxid <= 79){
                    if (oneswipe) {
                        if (xMove > xDown) {
                            swapRight();
                            oneswipe = false;
                            break;

                        }
                        if (yMove < yDown) {
                            swapUp();
                            oneswipe = false;
                            break;

                        }
                        if(xMove < xDown){
                            swapLeft();
                            oneswipe = false;
                            break;
                        }
                    }
                }
                if(boxid%9==0&& boxid != 0 && boxid != 72){
                    if(oneswipe){
                        if (xMove > xDown) {
                            swapRight();
                            oneswipe = false;
                            break;

                        }
                        if (yMove < yDown) {
                            swapUp();
                            oneswipe = false;
                            break;

                        }
                        if (yMove > yDown) {
                            swapDown();
                            oneswipe = false;
                            break;
                        }
                    }
                }
                if(boxid== 17 ||
                        boxid == 26 ||
                        boxid == 35 ||
                        boxid == 44 ||
                        boxid == 53 ||
                        boxid == 62 ||
                        boxid == 71
                        ){
                    if(oneswipe){
                        if (yMove > yDown) {
                            swapDown();
                            oneswipe = false;
                            break;
                        }
                        if (yMove < yDown) {
                            swapUp();
                            oneswipe = false;
                            break;

                        }
                        if(xMove < xDown){
                            swapLeft();
                            oneswipe = false;
                            break;
                        }


                    }

                }

                if(boxid == 8){
                    if(oneswipe){
                        if(xMove < xDown){
                            swapLeft();
                            oneswipe = false;
                            break;
                        }
                        if (yMove > yDown) {
                            swapDown();
                            oneswipe = false;
                            break;
                        }

                    }

                }
                if(boxid == 72){
                    if(oneswipe){
                        if (yMove < yDown) {
                            swapUp();
                            oneswipe = false;
                            break;
                        }
                        if (xMove > xDown) {
                            swapRight();
                            oneswipe = false;
                            break;

                        }

                    }
                }
                if(boxid == 80){
                    if(oneswipe){
                        if (yMove < yDown) {
                            swapUp();
                            oneswipe = false;
                            break;
                        }
                        if(xMove < xDown){
                            swapLeft();
                            oneswipe = false;
                            break;
                        }
                    }
                }
                break;
            }

        }

        return true;
    }
    public void shiftRowCandy(){
        canvas = surfaceHolder.lockCanvas();
        if(solution)
        {
            for(int t = 0; t<10; t++) {
                for (int j = 0; j < numberofcandiesinsequence; j++) {
                    for (int i = 0; i < 20; i++) {
                        canvas.drawBitmap(fire[i], getCandy(numberOfCandiesInSolution[j]).getXCoordinates() - 160 / 2,
                                getCandy(numberOfCandiesInSolution[j]).getYCoordinates() - 160 / 2, null);
                        if (i == 19) {
                            solution = false;
                        }
                    }
                }
            }
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
        score += numberofcandiesinsequence;
        scoretext = "score: "+score.toString();
        int [] current =  new int[10];
        int [] dropping =  new int [10];
        int numbersbehind = 0;
        for(int rd = 0; rd < numberofrowsfromthetop + 1; rd++) {
            for (int u = 0; u < numberofcandiesinsequence; u++) {
                if(rd == numberofrowsfromthetop){
                    generateRandomCandyColor();
                    current[u] = numberOfCandiesInSolution[u] - numbersbehind;
                    getCandy(current[u]).setColorid(colorid);
                    getCandy(current[u]).setCandy(temp);
                }
                else {
                    current[u] = numberOfCandiesInSolution[u] - numbersbehind;
                    dropping[u] = numberOfCandiesInSolution[u] - 9 - numbersbehind;
                    getCandy(current[u]).setColorid(getCandy(dropping[u]).getColorid());
                    getCandy(current[u]).setCandy(getCandy(dropping[u]).getCandy());
                }
            }

            numbersbehind += 9;
        }
        //for(int clear = 0; clear < numberofcandiesinsequence; clear++){
        //    numberOfCandiesInSolution[clear] = 0;
        //}
        numberofrowsfromthetop = 1;
        tempnumberinsequence = numberofcandiesinsequence;
        numberofcandiesinsequence =0;

    }
    public void shiftColumnCandy(){
        canvas = surfaceHolder.lockCanvas();
        if(solution)
        {
            for(int t = 0; t<2; t++) {
                for (int j = 0; j < numberofcandiesinsequence; j++) {
                    for (int i = 0; i < 20; i++) {
                        canvas.drawBitmap(fire[i], getCandy(numberOfCandiesInSolution[j]).getXCoordinates() - 160 / 2,
                                getCandy(numberOfCandiesInSolution[j]).getYCoordinates() - 160 / 2, null);
                        if (i == 19) {
                            solution = false;
                        }
                    }
                }
            }
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
        score += numberofcandiesinsequence;
        scoretext = "score: "+score.toString();
        int [] current =  new int[10];
        int [] dropping =  new int [10];
        int numbersbehind = 0;
        int u = numberofcandiesinsequence -1;
        for(int rd = 0; rd < numberofrowsfromthetop + 1; rd++) {
                if(rd > numberofrowsfromthetop-numberofcandiesinsequence){
                    generateRandomCandyColor();
                    current[u] = numberOfCandiesInSolution[u] - numbersbehind;
                    getCandy(current[u]).setColorid(colorid);
                    getCandy(current[u]).setCandy(temp);
                }
                else {
                    current[u] = numberOfCandiesInSolution[u] - numbersbehind;
                    dropping[u] = numberOfCandiesInSolution[u] - (9*numberofcandiesinsequence) - numbersbehind;
                    getCandy(current[u]).setColorid(getCandy(dropping[u]).getColorid());
                    getCandy(current[u]).setCandy(getCandy(dropping[u]).getCandy());
                }

            numbersbehind += 9;
        }
        //for(int clear = 0; clear < numberofcandiesinsequence; clear++){
         //   numberOfCandiesInSolution[clear] = 0;
        //}
        numberofrowsfromthetop = 1;
        tempnumberinsequence = numberofcandiesinsequence;
        numberofcandiesinsequence =0;
    }

    public void swapRight(){
        getCandy(boxid).setColorid(rtempc);
        getCandy(boxid+1).setColorid(tempc);
        getCandy(boxid).setCandy(rightBitmap);
        getCandy(boxid+1).setCandy(currentBitamp);
        idofboxmovedwith = boxid+1;

    }
    public void swapLeft(){
        getCandy(boxid).setColorid(ltempc);
        getCandy(boxid-1).setColorid(tempc);
        getCandy(boxid).setCandy(leftBitmap);
        getCandy(boxid-1).setCandy(currentBitamp);
        idofboxmovedwith = boxid -1;
    }
    public void swapDown(){
        getCandy(boxid).setColorid(dtempc);
        getCandy(boxid + 9).setColorid(tempc);
        getCandy(boxid).setCandy(downBitmap);
        getCandy(boxid + 9).setCandy(currentBitamp);
        idofboxmovedwith = boxid + 9;

    }
    public void swapUp(){
        getCandy(boxid).setColorid(utmepc);
        getCandy(boxid-9).setColorid(tempc);
        getCandy(boxid).setCandy(upBitmap);
        getCandy(boxid-9).setCandy(currentBitamp);
        idofboxmovedwith = boxid - 9;
    }

    protected void onDraw (Canvas c1){
        super.onDraw(c1);
        Rect dst = new Rect();
        dst.set(0,0,1440,2120);
        c1.drawBitmap(mybitmap, null, dst, null);
        c1.drawText(scoretext, 10, 250, textPaint);
        c1.drawText(movestext, 10, 2000, textPaint);
        if(gameOver) {
            c1.drawText(gameoverText, 300, 1100, textPaint);
        }
        else
        {
            for (int i = 0; i < 81; i++) {
                c1.drawBitmap(getCandy(i).getCandy(), getCandy(i).getXCoordinates() - (getCandy(i).getCandy().getWidth() / 2),
                        getCandy(i).getYCoordinates() - (getCandy(i).getCandy().getHeight() / 2), null);

            }
        }


    }

    @Override
    public void run() {
        resizeALLCandies();
        generateAllInitialCandies();
        sortCandyList();
        reRandomize();
        while(draw){
            if(!surfaceHolder.getSurface().isValid()){
                continue;
            }
            canvas = surfaceHolder.lockCanvas();
            onDraw(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);

        }

    }
    public void pause(){
        draw = false;
        while(true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        thread = null;

    }

    public void resume(){
        draw = true;
        thread = new Thread(this);
        thread.start();

    }

    public Bitmap getResizeBitmap(Bitmap bm, int newWidth, int newHeight){
        Bitmap resizeBitmap = Bitmap.createScaledBitmap(bm, newWidth,newHeight, false);
        return resizeBitmap;
    }

    public float [][] getCoordinates(){
        float [][]coordinates = new float[2][9];
        float init = 10 + (1420/9)/2;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 9; j++){
                    coordinates[i][j] = init ;
                    init  += (1420 / 9);

            }
            init = 350 + (1420/9)/2;
        }
        return coordinates;
    }

    public void addCandy(Candies newCandy){
        CandyList.add(newCandy);

    }

    public void generateAllInitialCandies(){
        int random_x = 0;
        int random_y = 0;
        int i = 0;
        int ID;
        float x1;
        float y1;
        int hi;
        boolean first = true;

        while(i<81) {
            hi = 1;
            Random r = new Random();
            generateRandomCandyColor();
            random_x = r.nextInt(9);
            random_y = r.nextInt(9);
            ID = (random_x*(1)) + (random_y *9);
                while(true){
                    if(!first) {
                        for (int t = 0; t < CandyList.size(); t++) {
                            if (candyListContains(ID)) {
                                random_x = r.nextInt(9);
                                random_y = r.nextInt(9);
                                ID = (random_x * 1) + (random_y * 9);
                                hi = 0;
                                break;
                            }
                            else{
                                hi = 1;
                            }

                        }
                        if(hi == 1) {
                            x1 = getCoordinates()[0][random_x];
                            y1 = getCoordinates()[1][random_y];
                            Candies c = new Candies(temp, x1, y1, ID,colorid);
                            addCandy(c);
                            break;
                        }
                    }
                    else{
                        x1 = getCoordinates()[0][random_x];
                        y1 = getCoordinates()[1][random_y];
                        Candies c = new Candies(temp, x1, y1, ID,colorid);
                        addCandy(c);
                        break;
                    }

                }
            first = false;
            i++;
        }
    }
    public Candies getCandy(int i)
    {
        return CandyList.get(i);
    }

    public void resizeALLCandies(){
        redcandy = getResizeBitmap(redcandy,160,160);
        cyancandy = getResizeBitmap(cyancandy,160,160);
        orangecandy = getResizeBitmap(orangecandy,160,160);
        greencandy = getResizeBitmap(greencandy,160,160);
        yellowcandy= getResizeBitmap(yellowcandy,160,160);
        purplecandy = getResizeBitmap(purplecandy,160,160);
    }

    public void generateRandomCandyColor(){
        Random r = new Random();

        switch (r.nextInt(6)) {
            case 0: {
                temp = redcandy;
                colorid =0;
                break;
            }
            case 1: {
                temp = cyancandy;
                colorid =1;
                break;
            }
            case 2: {
                temp = yellowcandy;
                colorid =2;
                break;
            }
            case 3: {
                temp = orangecandy;
                colorid =3;
                break;
            }
            case 4: {
                temp = purplecandy;
                colorid =4;
                break;
            }
            case 5: {
                temp = greencandy;
                colorid =5;
                break;
            }

        }


    }

    public boolean candyListContains(int id2){
        boolean idInthelist = false;
        for(int i = 0; i < CandyList.size(); i++){
            if (getCandy(i).getId() == id2){

                idInthelist = true;
            }

        }
        return idInthelist;
    }

    public boolean isThereCandyHere(int index, int x){


            if ((CandyList.contains(getCandy(index).getId() + x))) {

                return true;
            }

        return false;
    }

    public void reRandomize(){

        for(int i = 0; i < CandyList.size(); i++) {
            if (i >= 10 && i <= 16 ||
                i >= 19 && i <= 25 ||
                i >= 28 && i <= 34 ||
                i >= 37 && i <= 43 ||
                i >= 46 && i <= 52 ||
                i >= 55 && i <= 61 ||
                i >= 64 && i <= 70)
            {

                while (true) {
                    if((getCandy(i).getColorid() == getCandy(i-1).getColorid() && getCandy(i).getColorid() == getCandy(i+1).getColorid())||
                            (getCandy(i).getColorid()== getCandy(i-9).getColorid() && getCandy(i).getColorid() == getCandy(i+9).getColorid()))
                    {
                        generateRandomCandyColor();
                        getCandy(i).setColorid(colorid);
                        getCandy(i).setCandy(temp);
                    }
                    else {
                        break;
                    }
                }
            }

           if(i >= 1 && i <= 7){
                while(true){
                    if((getCandy(i).getColorid() == getCandy(i-1).getColorid() && getCandy(i).getColorid() == getCandy(i+1).getColorid()))
                    {
                        generateRandomCandyColor();
                        getCandy(i).setColorid(colorid);
                        getCandy(i).setCandy(temp);
                    }
                    else {
                        break;
                    }
                }

            }
            if(i >= 73 && i <= 79){
                while(true){
                    if((getCandy(i).getColorid() == getCandy(i-1).getColorid() && getCandy(i).getColorid() == getCandy(i+1).getColorid()))
                    {
                        generateRandomCandyColor();
                        getCandy(i).setColorid(colorid);
                        getCandy(i).setCandy(temp);
                    }
                    else {
                        break;
                    }
                }

            }

            if(i%9==0&& i != 0 && i != 72){
                while(true){
                    if((getCandy(i).getColorid() == getCandy(i-9).getColorid() && getCandy(i).getColorid() == getCandy(i+9).getColorid()))
                    {
                        generateRandomCandyColor();
                        getCandy(i).setColorid(colorid);
                        getCandy(i).setCandy(temp);
                    }
                    else {
                        break;
                    }
                }

            }
            if(i == 17 ||
                i == 26 ||
                i == 35 ||
                i == 44 ||
                i == 53 ||
                i == 62 ||
                i == 71
                    ){
                while(true){
                    if((getCandy(i).getColorid() == getCandy(i-9).getColorid() && getCandy(i).getColorid() == getCandy(i+9).getColorid()))
                    {
                        generateRandomCandyColor();
                        getCandy(i).setColorid(colorid);
                        getCandy(i).setCandy(temp);
                    }
                    else {
                        break;
                    }
                }

            }
        }
    }

    public boolean isThereSolution(){

        for(int i = 0; i < CandyList.size(); i++) {
            if (i >= 10 && i <= 16 ||
                    i >= 19 && i <= 25 ||
                    i >= 28 && i <= 34 ||
                    i >= 37 && i <= 43 ||
                    i >= 46 && i <= 52 ||
                    i >= 55 && i <= 61 ||
                    i >= 64 && i <= 70) {


                if ((getCandy(i).getColorid() == getCandy(i - 1).getColorid() && getCandy(i).getColorid() == getCandy(i + 1).getColorid()) ||
                        (getCandy(i).getColorid() == getCandy(i - 9).getColorid() && getCandy(i).getColorid() == getCandy(i + 9).getColorid())) {
                    if (getCandy(i).getColorid() == getCandy(i - 1).getColorid() && getCandy(i).getColorid() == getCandy(i + 1).getColorid()) {
                        numberofcandiesinsequence = 3;
                        numberOfCandiesInSolution[0] = i - 1;
                        numberOfCandiesInSolution[1] = i;
                        numberOfCandiesInSolution[2] = i + 1;
                        int nl = 10;
                        int nr = 16;
                        int noff = 1;
                        while ((ColorMatches(getCandy(i + 1).getColorid(), getCandy(i + 1 + noff).getColorid()))
                                && getCandy(i + 1).getId() != 8 && getCandy(i + 1).getId() != 17 && getCandy(i + 1).getId() != 26
                                && getCandy(i + 1).getId() != 35 && getCandy(i + 1).getId() != 44 && getCandy(i + 1).getId() != 53
                                && getCandy(i + 1).getId() != 62 && getCandy(i + 1).getId() != 71 && getCandy(i + 1).getId() != 80) {
                            numberofcandiesinsequence++;
                            numberOfCandiesInSolution[numberofcandiesinsequence - 1] = i + 1 + noff;
                            noff++;
                        }
                        while (true) {
                            if (i >= nl && i <= nr) {
                                break;
                            } else {
                                nl += 9;
                                nr += 9;
                                numberofrowsfromthetop++;
                            }
                        }
                        roworcolumn = 0;
                    } else {
                        numberofcandiesinsequence = 3;
                        numberOfCandiesInSolution[0] = i - 9;
                        numberOfCandiesInSolution[1] = i;
                        numberOfCandiesInSolution[2] = i + 9;
                        int nl = 10;
                        int nr = 16;
                        int noff = 9;

                        while (getCandy(i + 9).getId() != 73 && getCandy(i + 9).getId() != 74 && getCandy(i + 9).getId() != 75
                                && getCandy(i + 9).getId() != 76 && getCandy(i + 9).getId() != 77 && getCandy(i + 9).getId() != 78
                                && (i + 9) != 79 && (i + 9) != 80 && (i + 9) != 72 && (ColorMatches(getCandy(i + 9).getColorid(), getCandy(i + 9 + noff).getColorid()))) {
                            numberofcandiesinsequence++;
                            numberOfCandiesInSolution[numberofcandiesinsequence - 1] = i + 9 + noff;
                            if (((i + 9 + noff) == 73 || (i + 9 + noff) == 74 || (i + 9 + noff) == 75
                                    || (i + 9 + noff) == 76 || (i + 9 + noff) == 77 || (i + 9 + noff) == 78
                                    || (i + 9 + noff) == 79 || (i + 9 + noff) == 80 || (i + 9 + noff) == 72)) {
                                break;
                            }
                            noff += 9;
                        }
                        while (true) {
                            if ((i + noff) >= nl && (i + noff) <= nr) {
                                break;
                            } else {
                                nl += 9;
                                nr += 9;
                                numberofrowsfromthetop++;
                            }
                        }
                        roworcolumn = 1;
                    }

                    return true;
                }

            }

            if (i >= 1 && i <= 7) {
                while (true) {
                    if ((getCandy(i).getColorid() == getCandy(i - 1).getColorid() && getCandy(i).getColorid() == getCandy(i + 1).getColorid())) {
                        numberofcandiesinsequence = 3;
                        numberOfCandiesInSolution[0] = i - 1;
                        numberOfCandiesInSolution[1] = i;
                        numberOfCandiesInSolution[2] = i + 1;
                        int noff = 1;
                        while (getCandy(i + 1).getId() != 8 && (ColorMatches(getCandy(i + 1).getColorid(), getCandy(i + 1 + noff).getColorid()))) {
                            numberofcandiesinsequence++;
                            numberOfCandiesInSolution[numberofcandiesinsequence - 1] = i + 1 + noff;
                            noff++;
                        }
                        numberofrowsfromthetop = 0;

                        roworcolumn = 0;
                        return true;
                    } else {
                        break;
                    }
                }

            }
            if (i >= 73 && i <= 79) {
                while (true) {
                    if ((getCandy(i).getColorid() == getCandy(i - 1).getColorid() && getCandy(i).getColorid() == getCandy(i + 1).getColorid())) {
                        numberofcandiesinsequence = 3;
                        numberOfCandiesInSolution[0] = i - 1;
                        numberOfCandiesInSolution[1] = i;
                        numberOfCandiesInSolution[2] = i + 1;
                        int noff = 1;
                        while (getCandy(i + 1).getId() != 80 && (ColorMatches(getCandy(i + 1).getColorid(), getCandy(i + 1 + noff).getColorid()))) {
                            numberofcandiesinsequence++;
                            numberOfCandiesInSolution[numberofcandiesinsequence - 1] = i + 1 + noff;
                            noff++;
                        }
                        numberofrowsfromthetop = 8;

                        roworcolumn = 0;
                        return true;
                    } else {
                        break;
                    }
                }

            }

            if (i % 9 == 0 && i != 0 && i != 72) {
                while (true) {
                    if ((getCandy(i).getColorid() == getCandy(i - 9).getColorid() && getCandy(i).getColorid() == getCandy(i + 9).getColorid()))

                    {
                        numberofcandiesinsequence = 3;
                        numberOfCandiesInSolution[0] = i - 9;
                        numberOfCandiesInSolution[1] = i;
                        numberOfCandiesInSolution[2] = i + 9;
                        int nl = 9;
                        int noff = 9;
                        while ((i + 9 + noff) != 72 && (ColorMatches(getCandy(i + 9).getColorid(), getCandy(i + 9 + noff).getColorid()))) {
                            numberofcandiesinsequence++;
                            numberOfCandiesInSolution[numberofcandiesinsequence - 1] = i + 9 + noff;
                            noff += 9;
                        }
                        while (true) {
                            if ((i + noff) == nl) {
                                break;
                            } else {
                                nl += 9;
                                numberofrowsfromthetop++;
                            }
                        }
                        roworcolumn = 1;
                        return true;
                    } else {
                        break;
                    }
                }

            }
            if (i == 17 ||
                    i == 26 ||
                    i == 35 ||
                    i == 44 ||
                    i == 53 ||
                    i == 62 ||
                    i == 71
                    ) {
                while (true) {
                    if ((getCandy(i).getColorid() == getCandy(i - 9).getColorid() && getCandy(i).getColorid() == getCandy(i + 9).getColorid())) {
                        roworcolumn = 1;
                        numberofcandiesinsequence = 3;
                        numberOfCandiesInSolution[0] = i - 9;
                        numberOfCandiesInSolution[1] = i;
                        numberOfCandiesInSolution[2] = i + 9;
                        int nr = 17;
                        int noff = 9;
                        while ((i + 9 + noff) != 80 && (ColorMatches(getCandy(i + 9).getColorid(), getCandy(i + 9 + noff).getColorid()))) {
                            numberofcandiesinsequence++;
                            numberOfCandiesInSolution[numberofcandiesinsequence - 1] = i + 9 + noff;
                            noff += 9;
                        }
                        while (true) {
                            if ((i + noff) == nr) {
                                break;
                            } else {
                                nr += 9;
                                numberofrowsfromthetop++;
                            }
                        }
                        return true;
                    } else {
                        break;
                    }
                }

            }
        }
        return false;
    }


    public boolean ColorMatches(int c1, int c2){
        boolean flag = false;
        if(c1 == c2){
            flag = true;
        }
        return flag;
    }
    public void sortCandyList(){
        Candies temp;
        for(int i = 0; i < CandyList.size(); i++){
            for (int j = 0; j < CandyList.size()-1-i; j++) {
                if(getCandy(j).getId() > getCandy(j+1).getId()) {
                    temp = getCandy(j);
                    CandyList.set(j,getCandy(j+1));
                    CandyList.set(j+1, temp);
                }
            }
        }
    }

    public int checkBoxID(){
        int offsetx = 0;
        int offsety = 0;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j< 9; j++) {
                if (xDown > offsetx + 10 && xDown < offsetx + 10 + 1420 / 9 && yDown > offsety + 350 && yDown < offsety + 350 + 1420 / 9) {
                    return offsetx / 157 + (offsety * 9) / 157;
                } else {
                    offsetx += 157;
                }
            }
            offsetx = 0;
            offsety += 157;
        }

        return 200;
    }

}

