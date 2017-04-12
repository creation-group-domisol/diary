package com.group.creation.domisol.diary;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.group.creation.domisol.diary.calendar.CalendarFragment;
import com.group.creation.domisol.diary.contacts.ContactFragment;
import com.group.creation.domisol.diary.memo.MemoFragment;

public class MainActivity extends AppCompatActivity {
    private CalendarFragment calendarFragment = new CalendarFragment();
    private MemoFragment memoFragment = new MemoFragment();
    private ContactFragment contactFragment = new ContactFragment();
    private GestureDetector gestureDetector;
    private FrameLayout mainContent;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image_preview);
        mainContent = (FrameLayout) findViewById(R.id.main_content);
        mainContent.setDrawingCacheEnabled(true);



        getSupportFragmentManager().beginTransaction().add(R.id.main_content, memoFragment).commit();


        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){

            public static final float SWIPE_THRESHOLD = 0;
            public static final float SWIPE_VELOCITY_THRESHOLD = 0;

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

                Bitmap mainContentCapture = mainContent.getDrawingCache();

                imageView.setImageBitmap(mainContentCapture);



                Swipable currentFragment = (Swipable) getSupportFragmentManager().findFragmentById(R.id.main_content);
                currentFragment.swipeLeft();
            }

            private void onSwipeRight() {
                Swipable currentFragment = (Swipable) getSupportFragmentManager().findFragmentById(R.id.main_content);
                currentFragment.swipeRight();
            }
        });

    }


    public void onToCalendar(View view) {
        replaceView(calendarFragment);
    }

    public void onToMemo(View view) {
        replaceView(memoFragment);
    }

    public void onToContacts(View view) {
        replaceView(contactFragment);
    }

    private void replaceView(Fragment fragment) {
        if (fragment.isVisible())
            return;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }
}
