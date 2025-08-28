package com.example.simplegamedemo;

import android.graphics.Canvas;
import android.util.Log;

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
                float deltaTime = (System.nanoTime() - lastTime) / 1_000_000_000f;
                lastTime = System.nanoTime();

                screen.update(deltaTime);

                Canvas canvas = gameView.lockCanvas();
                if (canvas != null) {
                    gameView.doDraw(canvas);
                    screen.render(canvas);
                    gameView.unlockCanvasAndPost(canvas);
                }
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d("Game", "GameLoop: Loop stopped");
    }

    public void pauseGame() {
        paused = true;
        Log.d("Game", "GameLoop: Paused");
    }

    public void resumeGame() {
        paused = false;
        Log.d("Game", "GameLoop: Resumed");
    }

    public void stopGame() {
        running = false;
        Log.d("Game", "GameLoop: Stopped");
    }
}