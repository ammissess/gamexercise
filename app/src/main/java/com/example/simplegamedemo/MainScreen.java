package com.example.simplegamedemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class MainScreen implements Screen {
    private float squareX = 0f;
    private float speed = 200f;
    private GameView gameView;
    private Paint paint;
    private Paint textPaint;

    public MainScreen(GameView gameView) {
        this.gameView = gameView;
        paint = new Paint();
        paint.setColor(Color.RED);
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(50);
    }

    @Override
    public void update(float deltaTime) {
        squareX += speed * deltaTime;
        if (squareX > gameView.getWidth()) {
            squareX = 0f;
        }
        Log.d("Game", "Update: squareX=" + squareX + ", speed=" + speed);
    }

    @Override
    public void render(Canvas canvas) {
        canvas.drawRect(squareX, 100, squareX + 100, 200, paint);
        canvas.drawText("Speed: " + String.format("%.0f", speed), 20, 50, textPaint);
    }

    public float getSquareX() {
        return squareX;
    }

    public void setSquareX(float x) {
        this.squareX = x;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = Math.max(0, Math.min(speed, 1000f));
        Log.d("Game", "Speed set to: " + this.speed);
    }
}