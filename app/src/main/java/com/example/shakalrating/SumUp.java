package com.example.shakalrating;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.os.Environment.DIRECTORY_PICTURES;

public class SumUp extends AppCompatActivity {
    String messageWithRating;

    private RadioGroup plec;
    private RadioGroup wiek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_up);


        plec = (RadioGroup) findViewById(R.id.radioPlec);
        wiek = (RadioGroup) findViewById(R.id.radioWiek);

        Intent intent = getIntent();
        messageWithRating = intent.getStringExtra(Rating.EXTRA_MESSAGE);
        //TextView textView = findViewById(R.id.editText);
        //textView.setText(message);
    }

    public void endRating(View v){
        save();
        setResult(RESULT_OK, null);
        finish();
    }

    public void back(View v){
        finish();
    }

    private void save(){
        createFinalSaveRow();

        String imagePath = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).toString()
                    +"/SHAKALrating/rating";

        File savingFile = new File(imagePath);

        try {
            FileOutputStream outputStream = new FileOutputStream(savingFile, true);
            outputStream.write(messageWithRating.getBytes());
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //TextView textView = findViewById(R.id.editText);
        //textView.setText(messageWithRating);
    }

    private void createFinalSaveRow(){
        messageWithRating = messageWithRating + checkSex() + ";" + checkAge() + ";\n";
    }

    private String checkSex(){
        if (plec.getCheckedRadioButtonId() == findViewById(R.id.kobieta).getId())
            return "k";
        if (plec.getCheckedRadioButtonId() == findViewById(R.id.mezczyzna).getId())
            return "m";
        return "n";
    }

    private String checkAge(){
        if (wiek.getCheckedRadioButtonId() == findViewById(R.id.w14).getId())
            return "<15";
        if (wiek.getCheckedRadioButtonId() == findViewById(R.id.w15).getId())
            return "15-18";
        if (wiek.getCheckedRadioButtonId() == findViewById(R.id.w19).getId())
            return "19-22";
        if (wiek.getCheckedRadioButtonId() == findViewById(R.id.w23).getId())
            return "23-26";
        if (wiek.getCheckedRadioButtonId() == findViewById(R.id.w26).getId())
            return ">26";
        return "n";
    }
}
