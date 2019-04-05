package com.example.shakalrating;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startRating (View v){
        Intent intent = new Intent(this, Rating.class);
        startActivity(intent);
    }

    public void closeApp(View v){
        finish();
    }
}
