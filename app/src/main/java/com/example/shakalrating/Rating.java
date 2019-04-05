package com.example.shakalrating;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.File;

import static android.os.Environment.DIRECTORY_PICTURES;
import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Rating extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "MESSAGE";
    private final int NUMBER_OF_GRAPHICS = 5;
    private float ratingMarks[];
    private int currentPosition = 0;
    private RatingBar bar;
    private ImageView image;
    private Bitmap imageFromStorage;
    private File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        ratingMarks = new float[NUMBER_OF_GRAPHICS];
        for (int i=0; i<5; i++)
            ratingMarks[i]= (float) 2.5;

        bar = findViewById(R.id.ratingBar);
        image = findViewById(R.id.imageFromStorage);
        getImageFromStorage(String.valueOf(currentPosition));
        goToFullSize();
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (requestCode == 0)
            if (resultCode == RESULT_OK)
                this.finish();
    }

    public void moveLeft(View v){
        if (currentPosition == 0){
            Toast toast = Toast.makeText(this, "TO PIERWSZY PROJEKT\nAby wrócić do menu głównego wciśnij przycisk powrotu.", Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            ratingMarks[currentPosition] = bar.getRating();
            currentPosition--;
            bar.setRating(ratingMarks[currentPosition]);
            getImageFromStorage(String.valueOf(currentPosition));
        }
    }

    public void moveRight(View v){
        if (currentPosition == NUMBER_OF_GRAPHICS-1) {
            ratingMarks[currentPosition] = bar.getRating();
            startFinalStep();
        }
        else{
            ratingMarks[currentPosition] = bar.getRating();
            currentPosition++;
            bar.setRating(ratingMarks[currentPosition]);
            getImageFromStorage(String.valueOf(currentPosition));
        }

    }

    private void startFinalStep(){
        Intent intent = new Intent(this, SumUp.class);
        String message = ratingToString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, 0);
        //finishFromChild(this);
    }

    private String ratingToString(){
        String rate = "";
        for (int i=0; i<NUMBER_OF_GRAPHICS; i++){
            rate = rate + ratingMarks[i] + ";";
        }

        return rate;
    }

    public void goToFullSize(){
        image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showFullSize();
                //image.setImageResource(R.drawable.arrow_left);
            }

        });
    }

    private void showFullSize() {
        Intent intent = new Intent(this, FullSizeGraphic.class);
        String message = String.valueOf(currentPosition);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    private void getImageFromStorage(String pictureNumber){
        String imagePath = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).toString()
                +"/SHAKALrating/low/" + pictureNumber + ".jpg";
        //String imagePath = "/storage/self/primary/Pictures/SHAKALrating/low/"+pictureNumber;
        imageFile = new File (imagePath);
        if (imageFile.exists()){
            imageFromStorage = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            image.setImageBitmap(imageFromStorage);
        }
        else{
            Toast toast = Toast.makeText(this, "Nie udalo sie wczytac pliku: "+imagePath, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
