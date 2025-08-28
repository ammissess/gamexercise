package com.example.simplegamedemo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class GameActivity extends Activity {
    private GameView gameView;
    private GameLoop gameLoop;
    private MainScreen mainScreen;
    private float screenHeight; // Thêm biến thành viên để lưu chiều cao màn hình
    private float screenWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        gameView = findViewById(R.id.game_view);
        mainScreen = new MainScreen(gameView);
        gameView.setMainScreen(mainScreen);

        // Lấy kích thước màn hình và lưu vào biến thành viên
        updateScreenDimensions();
        mainScreen.setSquareY(screenHeight / 2); // Đặt khối ở giữa chiều cao màn hình

        gameLoop = new GameLoop(mainScreen, gameView);
        gameLoop.start();

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

        // Khôi phục trạng thái nếu có
        restoreState();
        Log.d("Game", "onCreate: GameActivity initialized");
    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateScreenDimensions();
        mainScreen.setSquareY(screenHeight / 2); // Cập nhật vị trí giữa khi xoay
    }

    private void updateScreenDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
        Log.d("Game", "Screen dimensions updated: " + screenWidth + "x" + screenHeight);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            mainScreen.jump();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameLoop.pauseGame();
        saveState();
        Log.d("Game", "onPause: Game paused");
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreState();
        mainScreen.setSquareY(screenHeight / 2); // Đảm bảo khối ở giữa khi resume
        gameLoop.resumeGame();
        Log.d("Game", "onResume: Game resumed");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameLoop.stopGame();
        Log.d("Game", "onDestroy: Game destroyed");
    }

    private void saveState() {
        SharedPreferences prefs = getSharedPreferences("GameState", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat("squareX", mainScreen.getSquareX());
        editor.putFloat("squareY", mainScreen.getSquareY());
        editor.putFloat("speed", mainScreen.getSpeed());
        editor.apply();
        Log.d("Game", "saveState: Saved squareX=" + mainScreen.getSquareX() + ", squareY=" + mainScreen.getSquareY() + ", speed=" + mainScreen.getSpeed());
    }

    private void restoreState() {
        SharedPreferences prefs = getSharedPreferences("GameState", MODE_PRIVATE);
        float savedX = prefs.getFloat("squareX", 0f);
        float savedY = prefs.getFloat("squareY", screenHeight / 2); // Giữ giữa chiều cao
        float savedSpeed = prefs.getFloat("speed", 200f);
        mainScreen.setSquareX(savedX);
        mainScreen.setSquareY(savedY);
        mainScreen.setSpeed(savedSpeed);
        Log.d("Game", "restoreState: Restored squareX=" + savedX + ", squareY=" + savedY + ", speed=" + savedSpeed);
    }
}