package com.example.clicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button myButton;
    TextView myText;
    int clicks = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myButton = (Button)findViewById(R.id.purplebutton);
        myText = (TextView)findViewById(R.id.textView);
    }

    public void onClick(View v){
        clicks++;
        myText.setText(clicks + " cookie" + (clicks == 1 ? "" : "s"));
    }
}