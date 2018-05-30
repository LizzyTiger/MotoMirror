package com.morristaedt.mirror;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.morristaedt.mirror.configuration.ConfigurationSettings;

import com.morristaedt.mirror.modules.EncouragementModule;
import com.morristaedt.mirror.modules.MoodModule;
import com.morristaedt.mirror.receiver.AlarmReceiver;

import java.lang.ref.WeakReference;
/**
 * Created by HannahMitt on 8/22/15. edited by Sally and Mary to be more responsive
 * to include Encouragment statements, remove extra features not applicable to our project
 * added encouragement listener, custom fonts,
 */
public class MirrorActivity extends ActionBarActivity {

    @NonNull
    private ConfigurationSettings mConfigSettings;
    private TextView mMoodText;
    private TextView smileView;
    private TextView mEncStatement;
    private TextView customtv1;
    private TextView customtv2;
    private TextView customtv3;
    private Typeface typefacec1;
    private Typeface typefacec2;
    private Typeface typefacec3;
    private MoodModule mMoodModule;
    private double counter = 0;


    private MoodModule.MoodListener mMoodListener = new MoodModule.MoodListener() {
        @Override
        public void onShouldGivePositiveAffirmation(final String affirmation) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMoodText.setVisibility(affirmation == null ? View.GONE : View.VISIBLE);
                    mMoodText.setText(affirmation);
                    if (affirmation .equalsIgnoreCase("Wow Nice Smile")||affirmation .equalsIgnoreCase("You look happy."))
                        counter = mMoodModule.getSmile();
                        smileView.setText("We've seen " + (int)Math.round(counter)+" smiles today! ") ;
                        smileView.setTextColor(0xFFFF69B4);


                }
            });
        }
    };

    private EncouragementModule.EncListener mEncListener = new EncouragementModule.EncListener() {
        @Override
        public void ongetStatements(final String result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mEncStatement.setText(result);
                    //mEncStatement.setTextColor(0xFFFF4500);
                    mEncStatement.setTextColor(0xFFFFFFFF);
                }
            });
        }
    };




    private Class<MoodModule> moodModuleClass;


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

        mMoodText = (TextView) findViewById(R.id.mood_text);
        mEncStatement = (TextView) findViewById(R.id.Encouraging_summary);
        smileView = (TextView) findViewById(R.id.Smile_counter);

        customtv1 = (TextView) findViewById(R.id.Encouraging_summary);
        typefacec1 = Typeface.createFromAsset(this.getAssets(), "fonts/italic.otf");
        customtv1.setTypeface(typefacec1);

        customtv2 = (TextView) findViewById(R.id.Smile_counter);
        typefacec2 = Typeface.createFromAsset(this.getAssets(), "fonts/circular.otf");
        customtv2.setTypeface(typefacec2);

        customtv3 = (TextView) findViewById(R.id.mood_text);
        typefacec3 = Typeface.createFromAsset(this.getAssets(), "fonts/circular.otf");
        customtv3.setTypeface(typefacec3);

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

        EncouragementModule.getStatement(mEncListener);
        if (mConfigSettings.showMoodDetection()) {
            mMoodModule = new MoodModule(new WeakReference<Context>(this));
            mMoodModule.getCurrentMood(mMoodListener);
        } else {
            mMoodText.setVisibility(View.GONE);
        }

        smileView.setText("We've seen " + (int)Math.round(counter)+" smiles today!") ;
        smileView.setTextColor(0xFFFF69B4);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AlarmReceiver.stopMirrorUpdates(this);
        Intent intent = new Intent(this, SetUpActivity.class);
        startActivity(intent);
    }
}
