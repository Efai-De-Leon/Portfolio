package com.example.efaideleon.candy_crush;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    BoardView b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        b = new BoardView(this);
        setContentView(b);

    }

   /* protected void onStart(){

    }
    protected void onRestart(){

    }*/
    @Override
    protected void onResume(){
        super.onResume();
        b.resume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        b.pause();

    }
        /*  protected void onStop(){

    }
    protected void onDestroy(){

    }*/

}
