package fr.tp.a21914280.tp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import fr.tp.a21914280.tp.model.AnnonceDBHelper;

public class test_save_image extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_envoie_image);

        /*File storageDir = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/app");
        storageDir.mkdirs();*/
        Log.d("Files", "TOUT debut: ");

        download_mage();
        Log.d("Files", " Apres appel download");
/*
        String path = Environment.getExternalStorageDirectory().toString()+"/Pictures  ";
        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.d("Files", "Size: "+ files.length);
        for (int i = 0; i < files.length; i++)
        {
            Log.d("Files", "FileName:" + files[i].getName());
        }*/
    }

  public void download_mage(){

      Glide.with(this)
              .asBitmap()
              .load("\"https:\\/\\/farm5.staticflickr.com\\/4609\\/38984233005_99ebb2a81a_q.jpg")
              .into(new SimpleTarget<Bitmap>(100,100) {

                  @Override
                  public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                      Log.d("Files", "Fin glide" );

                      saveImage(resource);
                  }

              });
  }

    private String saveImage(Bitmap image) {
        String savedImagePath = null;
        Log.d("Files", "Debut saveimage" );

        String imageFileName = "JPEG_" + "FILE_NAME" + ".jpg";
        File storageDir = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/app");
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, imageFileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath);
            Toast.makeText(getApplicationContext(), "IMAGE SAVED", Toast.LENGTH_LONG).show();
        }
        return savedImagePath;
    }

    private void galleryAddPic(String imagePath) {
        Log.d("Files", "debut galerry " );

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imagePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

}
