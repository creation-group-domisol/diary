package com.group.creation.domisol.diary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FlingLayout extends FrameLayout {
    private GestureDetector gestureDetector;

    public FlingLayout(@NonNull Context context) {
        super(context);
    }

    public FlingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlingLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("!");
        if (gestureDetector == null)
            initGestureDetector(getContext());
        gestureDetector.onTouchEvent(ev);
        return super.onInterceptTouchEvent(ev);
    }

    private void initGestureDetector(final Context context) {
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){

            public static final float SWIPE_THRESHOLD = 0;
            public static final float SWIPE_VELOCITY_THRESHOLD = 0;
            private FragmentManager fragmentManager;

            {
                FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                this.fragmentManager = fragmentActivity.getSupportFragmentManager();
                System.out.println(this.fragmentManager);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                            result = true;
                        }
                    }
                    else{
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }

            private void onSwipeLeft() {
                View fling = findViewById(R.id.main_content);

                fling.setDrawingCacheEnabled(true);
                ImageView imageView = (ImageView) ((Activity) context).findViewById(R.id.image_preview);
                Bitmap bitmap = Bitmap.createBitmap(fling.getDrawingCache());
                fling.setDrawingCacheEnabled(false);
                imageView.setImageBitmap(bitmap);

                Swipable currentFragment = (Swipable) this.fragmentManager.findFragmentById(R.id.main_content);
                currentFragment.swipeLeft();
            }

            private void onSwipeRight() {
                Swipable currentFragment = (Swipable) this.fragmentManager.findFragmentById(R.id.main_content);
                currentFragment.swipeRight();
            }
        });
    }
}
