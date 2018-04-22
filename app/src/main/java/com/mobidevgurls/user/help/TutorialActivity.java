package com.mobidevgurls.user.help;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends AppCompatActivity {
    private ViewPager mPager;
    private int currentPage = 0;
    private Integer[] images = {R.drawable.home, R.drawable.helper, R.drawable.book, R.drawable.projecthover, R.drawable.project,
            R.drawable.declinedhover, R.drawable.declined, R.drawable.pendinghover, R.drawable.request,
            R.drawable.decide, R.drawable.userhover, R.drawable.profile, R.drawable.accounthover, R.drawable.edit};

     /*R.drawable.projecthover,*/
     /*R.drawable.declinedhover,*/
     /*R.drawable.pendinghover,*/
     /*R.drawable.userhover,*/
     /*R.drawable.accounthover,*/

    private ArrayList<Integer> imagesArray = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        init();
    }
    private void init() {
        for(int i=0;i<images.length;i++)
            imagesArray.add(images[i]);

        Log.d("LENGTH", images.length + "");

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new ImageAdapter(TutorialActivity.this, imagesArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == images.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 100000, 100000);
    }
}
