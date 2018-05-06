package com.morristaedt.mirror;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.morristaedt.mirror.configuration.ConfigurationSettings;


import com.morristaedt.mirror.modules.DayModule;
import com.morristaedt.mirror.modules.MoodModule;



import com.morristaedt.mirror.receiver.AlarmReceiver;

import com.morristaedt.mirror.utils.WeekUtil;


import java.lang.ref.WeakReference;

public class MirrorActivity extends ActionBarActivity {

    @NonNull
    private ConfigurationSettings mConfigSettings;


    private TextView mDayText;
    private TextView mMoodText;


    private MoodModule mMoodModule;




    private MoodModule.MoodListener mMoodListener = new MoodModule.MoodListener() {
        @Override
        public void onShouldGivePositiveAffirmation(final String affirmation) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMoodText.setVisibility(affirmation == null ? View.GONE : View.VISIBLE);
                    mMoodText.setText(affirmation);
                }
            });
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mirror);
        mConfigSettings = new ConfigurationSettings(this);
        AlarmReceiver.startMirrorUpdates(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
            decorView.setSystemUiVisibility(uiOptions);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        mDayText = (TextView) findViewById(R.id.day_text);





        mMoodText = (TextView) findViewById(R.id.mood_text);



        setViewState();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mMoodModule != null) {
            mMoodModule.release();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setViewState();
    }

    private void colorTextViews(ViewGroup mview){
        for (int i = 0; i < mview.getChildCount(); i++) {
            View view = mview.getChildAt(i);
            if (view instanceof ViewGroup)
                colorTextViews((ViewGroup) view);
            else if (view instanceof TextView) {
                ((TextView) view).setTextColor(mConfigSettings.getTextColor());
            }
        }
    }

    private void setViewState() {
        colorTextViews((ViewGroup) findViewById(R.id.main_layout));


        mDayText.setText(DayModule.getDay());



        if (mConfigSettings.showMoodDetection()) {
            mMoodModule = new MoodModule(new WeakReference<Context>(this));
            mMoodModule.getCurrentMood(mMoodListener);
        } else {
            mMoodText.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlarmReceiver.stopMirrorUpdates(this);
        Intent intent = new Intent(this, SetUpActivity.class);
        startActivity(intent);
    }
}
