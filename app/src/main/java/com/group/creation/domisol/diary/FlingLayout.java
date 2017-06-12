package com.group.creation.domisol.diary;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FlingLayout extends FrameLayout {
    private GestureDetector gestureDetector;
    private FrameLayout content;
//    private ImageView pageCurlView;

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
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        pageCurlView = (ImageView) findViewById(R.id.transition_view);
        content = (FrameLayout) findViewById(R.id.main_content);
//        content.setDrawingCacheEnabled(true);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
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
//                            pageCurlView.setVisibility(INVISIBLE);
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

            @Override
            public boolean onDown(MotionEvent e) {
//                content.setDrawingCacheEnabled(true);
//                content.buildDrawingCache();
//                pageCurlView.setImageBitmap(Bitmap.createBitmap(content.getDrawingCache(true)));
//                pageCurlView.setVisibility(VISIBLE);
//                content.destroyDrawingCache();
                return super.onDown(e);
            }

            private void onSwipeLeft() {
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
