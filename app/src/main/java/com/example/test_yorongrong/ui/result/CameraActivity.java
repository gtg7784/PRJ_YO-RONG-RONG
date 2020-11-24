package com.example.test_yorongrong.ui.result;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.test_yorongrong.R;

public class CameraActivity extends AppCompatActivity {
    Bitmap img = null;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent intent = getIntent();
        byte[] data = getIntent().getByteArrayExtra("cameraImg");
        img = BitmapFactory.decodeByteArray(data, 0, data.length);
        img = rotateBmp(img);

        imageView = (ImageView) findViewById(R.id.cameraImg);
        imageView.setImageBitmap(img);

        Toast.makeText(getApplicationContext(), "Create Image", (int) 3).show();
    }

    public Bitmap rotateBmp(Bitmap bmp){
        Matrix matrix = new Matrix();
        //set image rotation value to 90 degrees in matrix.
        matrix.postRotate(90);
        //supply the original width and height, if you don't want to change the height and width of bitmap.
        Bitmap result = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),bmp.getHeight(), matrix, true);

        return result;
    }
}