package com.example.simplegamedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private MainScreen mainScreen;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void setMainScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("Game", "Surface created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("Game", "Surface changed: " + width + "x" + height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("Game", "Surface destroyed");
    }

    public void doDraw(Canvas canvas) {
        if (canvas != null) {
            canvas.drawColor(0xFFFFFFFF); // Nền trắng
        }
    }

    public Canvas lockCanvas() {
        if (holder.getSurface().isValid()) {
            return holder.lockCanvas();
        }
        return null;
    }

    public void unlockCanvasAndPost(Canvas canvas) {
        if (canvas != null) {
            holder.unlockCanvasAndPost(canvas);
        }
    }
}