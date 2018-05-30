package com.morristaedt.mirror;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.morristaedt.mirror.configuration.ConfigurationSettings;

public class SetUpActivity extends Activity {


    @NonNull
    private ConfigurationSettings mConfigSettings;
    private CheckBox mMoodDetectionCheckbox;
    private View mColorShowView;
    private SeekBar mColorPickerRed;
    private SeekBar mColorPickerGreen;
    private SeekBar mColorPickerBlue;
    private TextView mColorShowerRed;
    private TextView mColorShowerGreen;
    private TextView mColorShowerBlue;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        mConfigSettings = new ConfigurationSettings(this);

        mColorPickerRed = (SeekBar) findViewById(R.id.ColorPickerRed);
        mColorPickerRed.setProgress(Color.red(mConfigSettings.getTextColor()));

        mColorPickerGreen = (SeekBar) findViewById(R.id.ColorPickerGreen);
        mColorPickerGreen.setProgress(Color.green(mConfigSettings.getTextColor()));

        mColorPickerBlue = (SeekBar) findViewById(R.id.ColorPickerBlue);
        mColorPickerBlue.setProgress(Color.blue(mConfigSettings.getTextColor()));

        mColorShowerRed = (TextView) findViewById(R.id.ColorShowerRed);
        mColorShowerRed.setText(String.format("%d", Color.red(mConfigSettings.getTextColor())));

        mColorShowerGreen = (TextView) findViewById(R.id.ColorShowerGreen);
        mColorShowerGreen.setText(String.format("%d", Color.green(mConfigSettings.getTextColor())));

        mColorShowerBlue = (TextView) findViewById(R.id.ColorShowerBlue);
        mColorShowerBlue.setText(String.format("%d", Color.blue(mConfigSettings.getTextColor())));

        mColorShowView = findViewById(R.id.colored_bar);
        mColorShowView.setBackgroundColor(mConfigSettings.getTextColor());

        mColorPickerRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mConfigSettings.setTextColorRed(progress);
                mColorShowerRed.setText(String.format("%d", progress));
                mColorShowView.setBackgroundColor(mConfigSettings.getTextColor());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mColorPickerGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mConfigSettings.setTextColorGreen(progress);
                mColorShowerGreen.setText(String.format("%d", progress));
                mColorShowView.setBackgroundColor(mConfigSettings.getTextColor());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mColorPickerBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mConfigSettings.setTextColorBlue(progress);
                mColorShowerBlue.setText(String.format("%d", progress));
                mColorShowView.setBackgroundColor(mConfigSettings.getTextColor());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mMoodDetectionCheckbox = (CheckBox) findViewById(R.id.mood_detection_checkbox);
        mMoodDetectionCheckbox.setChecked(mConfigSettings.showMoodDetection());

        findViewById(R.id.launch_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFields();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                Intent intent = new Intent(SetUpActivity.this, MirrorActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private void saveFields() {
        mConfigSettings.setShowMoodDetection(mMoodDetectionCheckbox.isChecked());


    }
}
