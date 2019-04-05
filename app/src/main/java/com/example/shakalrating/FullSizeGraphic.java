package com.example.shakalrating;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;

import static android.os.Environment.DIRECTORY_PICTURES;

public class FullSizeGraphic extends AppCompatActivity {
    private int maxX = 1080;
    private int maxY = 1500;

    private LinearLayout container;
    private int currentTapX;
    private int currentTapY;
    private int currentPositionX;
    private int currentPositionY;
    private int lastTapX;
    private int lastTapY;


    private File imageFile;
    private Bitmap imageFromStorage;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_size_graphic);

        container = (LinearLayout) findViewById(R.id.Container);

        currentPositionX = 540;
        currentPositionY = 750;

        container.scrollTo(currentPositionX, currentPositionY);

        image = (ImageView) findViewById(R.id.imageFullScreen);
        Intent intent = getIntent();
        String message = intent.getStringExtra(Rating.EXTRA_MESSAGE);
        getImageFromStorage(message);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                currentTapX = lastTapX = (int) event.getRawX();
                currentTapY = lastTapY = (int) event.getRawY();
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                int x2 = (int) event.getRawX();
                int y2 = (int) event.getRawY();
                //container.scrollBy(currentX - x2 , currentY - y2);

                int placeToScrollX = currentPositionX + currentTapX - x2;
                int placeToScrollY = currentPositionY + currentTapY - y2;

                if (placeToScrollX>=0 && placeToScrollX<=maxX) {
                    container.scrollBy(currentTapX - x2, 0);
                    currentPositionX = placeToScrollX;
                }

                if (placeToScrollY>=0 && placeToScrollY<=maxY) {
                    container.scrollBy(0, currentTapY - y2);
                    currentPositionY = placeToScrollY;
                }

                currentTapX = x2;
                currentTapY = y2;
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (lastTapX == currentTapX && lastTapY==currentTapY)
                    finish();
                break;
            }
        }
        return true;
    }

    private void getImageFromStorage(String pictureNumber){
        String imagePath = Environment.getExternalStoragePublicDirectory(DIRECTORY_PICTURES).toString()
                +"/SHAKALrating/high/" + pictureNumber + ".jpg";
        //String imagePath = "/storage/self/primary/Pictures/SHAKALrating/low/"+pictureNumber;
        imageFile = new File(imagePath);
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
