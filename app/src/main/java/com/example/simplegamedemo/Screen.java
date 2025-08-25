package com.example.simplegamedemo;

import android.graphics.Canvas;

public interface Screen {
    void update(float deltaTime);
    void render(Canvas canvas);
}