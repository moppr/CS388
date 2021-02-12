package com.example.clicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button myButton;
    TextView myText;
    TextView myTimerText;
    TextView myScoreText;
    int clicks = 0;
    int best = 0;

    ScheduledExecutorService service;
    ScheduledFuture timer = null;
    int time = 10;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = (Button)findViewById(R.id.purplebutton);
        myText = (TextView)findViewById(R.id.textView);
        myTimerText = (TextView)findViewById(R.id.textView2);
        myScoreText = (TextView)findViewById(R.id.textView3);

        service = Executors.newScheduledThreadPool(1);

        sharedPreferences = getPreferences(MODE_PRIVATE);
        best = sharedPreferences.getInt("best", 0);
        myScoreText.setText("hiscore: " + best);
    }

    public void onClick(View v){
        if (timer == null){
            clicks = 0;
            time = 10;
            startTimer();
        }

        clicks++;
        myText.setText(clicks + " cookie" + (clicks == 1 ? "" : "s"));
    }

    public void startTimer(){
        timer = service.scheduleAtFixedRate(() -> {
            try{
                runOnUiThread(() -> {
                    myTimerText.setText(time + " seconds remaining");
                    time--; //Note: "potentially desyncs our app logic"
                });

                if (time <= 0){
                    //Turn off timer, add cooldown, update hiscore when it reaches 0
                    if (timer != null) {
                        timer.cancel(true);
                        timer = null;
                    }
                    runOnUiThread(() -> {
                        myButton.setEnabled(false);
                    });
                    service.schedule(() -> runOnUiThread(() -> {
                        myButton.setEnabled(true);
                    }), 3, TimeUnit.SECONDS);
                    if (clicks > best){
                        best = clicks;
                        runOnUiThread(() -> myScoreText.setText("hiscore: " + best));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("best", best);
                        editor.apply();
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}