package com.example.simplegamedemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GameActivity extends Activity {
    private GameView gameView;
    private GameLoop gameLoop;
    private MainScreen mainScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Sử dụng layout XML
        setContentView(R.layout.activity_game);

        // Khởi tạo GameView
        gameView = findViewById(R.id.game_view);
        mainScreen = new MainScreen(gameView);
        gameView.setMainScreen(mainScreen);

        // Khởi động GameLoop
        gameLoop = new GameLoop(mainScreen, gameView);
        gameLoop.start();

        // Xử lý sự kiện nút
        Button buttonIncrease = findViewById(R.id.button_increase);
        Button buttonDecrease = findViewById(R.id.button_decrease);

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.setSpeed(mainScreen.getSpeed() + 100f);
                Log.d("Game", "Increase button clicked, speed: " + mainScreen.getSpeed());
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainScreen.setSpeed(mainScreen.getSpeed() - 100f);
                Log.d("Game", "Decrease button clicked, speed: " + mainScreen.getSpeed());
            }
        });

        Log.d("Game", "GameActivity created");
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameLoop.pauseGame();
        saveState();
        Log.d("Game", "Game paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameLoop.resumeGame();
        restoreState();
        Log.d("Game", "Game resumed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameLoop.stopGame();
        Log.d("Game", "Game destroyed");
    }

    private void saveState() {
        SharedPreferences prefs = getSharedPreferences("GameState", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("squareX", mainScreen.getSquareX());
        editor.putFloat("speed", mainScreen.getSpeed());
        editor.apply();
    }

    private void restoreState() {
        SharedPreferences prefs = getSharedPreferences("GameState", MODE_PRIVATE);
        float savedX = prefs.getFloat("squareX", 0f);
        float savedSpeed = prefs.getFloat("speed", 200f);
        mainScreen.setSquareX(savedX);
        mainScreen.setSpeed(savedSpeed);
    }
}