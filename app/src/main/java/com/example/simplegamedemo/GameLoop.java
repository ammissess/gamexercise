package com.example.simplegamedemo;

import android.graphics.Canvas;

public class GameLoop extends Thread {
    private boolean running = true;
    private boolean paused = false;
    private Screen screen;
    private GameView gameView;

    public GameLoop(Screen screen, GameView gameView) {
        this.screen = screen;
        this.gameView = gameView;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        while (running) {
            if (!paused) {
                float deltaTime = (System.nanoTime() - lastTime) / 1_000_000_000f;  // Tính deltaTime
                lastTime = System.nanoTime();

                // Cập nhật trạng thái
                screen.update(deltaTime);

                // Render
                Canvas canvas = gameView.lockCanvas();
                if (canvas != null) {
                    gameView.doDraw(canvas);  // Vẽ nền
                    screen.render(canvas);    // Vẽ màn hình
                    gameView.unlockCanvasAndPost(canvas);
                }
            } else {
                try {
                    Thread.sleep(100);  // Ngủ khi pause để tiết kiệm CPU
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void pauseGame() {
        paused = true;
    }

    public void resumeGame() {
        paused = false;
    }

    public void stopGame() {
        running = false;
    }
}