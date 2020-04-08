package com.example.circularreveal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.transition.Transition;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout layoutMain;
    private ConstraintLayout layoutButtons;

    //check for layoutButtons is open
    private boolean isOpen = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutMain = findViewById(R.id.layoutMain);
        layoutButtons = findViewById(R.id.layoutButtons);



        layoutMain.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                // getX and getY give us touch coordinates(where user touches by X and Y)
                viewMenu((int) motionEvent.getX(), (int) motionEvent.getY());

                return false;
            }
        });
    }

    //Handle view visibility witch circular animation
    private void viewMenu(int x, int y) {

        if (!isOpen) {
            //start from center
            int startRadius = 0;

            // get the final radius for the clipping circle which is layoutMain whole size
            int finalRadius = Math.max(layoutMain.getWidth(), layoutMain.getHeight());

            // create the animator for this view (the start radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, finalRadius);

            // make the view visible and start the animation
            layoutButtons.setVisibility(View.VISIBLE);

            anim.start();

            isOpen = true;

        } else {

            int startRadius = Math.max(layoutMain.getWidth(), layoutMain.getHeight());
            int endRadius = 0;

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(layoutButtons, x, y, startRadius, endRadius);

            // make the view invisible when the animation is done
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layoutButtons.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
            anim.start();

            isOpen = false;
        }
    }

}
