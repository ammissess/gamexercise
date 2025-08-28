package com.example.simplegamedemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class MainScreen implements Screen {
    private float squareX = 0f;
    private float squareY = 0f;
    private float speed = 200f;
    private float velocityY = 0f;
    private static final float GRAVITY = 1000f;
    private static final float JUMP_VELOCITY = -650f;
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

        // Cập nhật nhảy với giới hạn trên/dưới
        velocityY += GRAVITY * deltaTime;
        squareY += velocityY * deltaTime;

        // Giới hạn di chuyển trong phạm vi giữa màn hình (ví dụ: ±200 từ vị trí giữa)
        float centerY = gameView.getHeight() / 2;
        if (squareY < centerY - 200) {
            squareY = centerY - 200;
            velocityY = 0;
        } else if (squareY > centerY + 200) {
            squareY = centerY + 200;
            velocityY = 0;
        }
        Log.d("Game", "Update: squareX=" + squareX + ", squareY=" + squareY + ", velocityY=" + velocityY);
    }

    @Override
    public void render(Canvas canvas) {
        if (canvas != null) {
            canvas.drawRect(squareX, squareY, squareX + 100, squareY + 100, paint); // Vẽ khối 100x100
            canvas.drawText("Speed: " + String.format("%.0f", speed), 20, 50, textPaint);
        }
    }

    public void jump() {
        if (velocityY == 0) {
            velocityY = JUMP_VELOCITY;
            Log.d("Game", "Jump initiated, velocityY: " + velocityY);
        }
    }

    public float getSquareX() { return squareX; }
    public void setSquareX(float x) { this.squareX = x; }
    public float getSquareY() { return squareY; }
    public void setSquareY(float y) { this.squareY = y; }
    public float getSpeed() { return speed; }
    public void setSpeed(float speed) {
        this.speed = Math.max(0, Math.min(speed, 1000f));
        Log.d("Game", "Speed set to: " + this.speed);
    }
}