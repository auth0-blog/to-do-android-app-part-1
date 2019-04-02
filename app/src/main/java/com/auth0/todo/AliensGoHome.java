package com.auth0.todo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Scene;

public class AliensGoHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliens_go_home);

        // defining the background
        Bitmap bg = Bitmap.createBitmap(480, 600, Bitmap.Config.ARGB_8888);

        // defining what color to use on the square (green)
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#00ff00"));

        // defining the square
        Canvas canvas = new Canvas(bg);
        canvas.drawRect(50, 50, 100, 100, paint);

        // configuring the layout with the background
        LinearLayout ll = findViewById(R.id.rect);
        ll.setBackground(new BitmapDrawable(bg));

        Scene mScene = new Scene(ll, mViewHierarchy);
    }
}
