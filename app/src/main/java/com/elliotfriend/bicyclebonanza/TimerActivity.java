package com.elliotfriend.bicyclebonanza;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elliotfriend.bicyclebonanza.R;

import java.io.StringBufferInputStream;

public class TimerActivity extends Activity {

    // Get our CLASS_NAME for debug output
    private static String CLASS_NAME;

    // Private variable declarations
    protected TextView counter;
    protected Button start;
    protected Button stop;
    protected boolean timerRunning;
    protected long startedAt;
    protected long lastStopped;


    public TimerActivity() {
        CLASS_NAME = getClass().getName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(CLASS_NAME, "onCreate");

        // Enable strict mode, 'cause I'm new here
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll().penaltyLog().build());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        counter = (TextView) findViewById(R.id.timer);
        start = (Button) findViewById(R.id.start_button);
        stop = (Button) findViewById(R.id.stop_button);

        enableButtons();

    }

    public void clickedStart(View view) {
        Log.d(CLASS_NAME, "Clicked start button.");

        startedAt = System.currentTimeMillis();
        timerRunning = true;
        enableButtons();
        setTimeDisplay();
    }

    public void clickedStop(View view) {
        Log.d(CLASS_NAME, "Clicked stop button.");

        lastStopped = System.currentTimeMillis();
        timerRunning = false;
        enableButtons();
        setTimeDisplay();
    }

    protected void enableButtons() {
        Log.d(CLASS_NAME, "Set buttons enabled/disabled");

        start.setEnabled(!timerRunning);
        stop.setEnabled(timerRunning);
    }

    protected void setTimeDisplay() {
        String display;
        long timeNow;
        long diff;
        long seconds;
        long minutes;
        long hours;

        Log.d(CLASS_NAME, "Setting time display");

        if (timerRunning) {
            timeNow = System.currentTimeMillis();
        } else {
            timeNow = lastStopped;
        }

        diff = timeNow - startedAt;

        // no negative time
        if (diff < 0) {
            diff = 0;
        }

        seconds = diff / 1000;
        minutes = seconds / 60;
        hours = minutes / 60;
        seconds = seconds % 60;
        minutes = minutes % 60;

        display = String.format("%d", hours) + ":"
                + String.format("%02d", minutes) + ":"
                + String.format("%02d", seconds);

        counter.setText(display);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
