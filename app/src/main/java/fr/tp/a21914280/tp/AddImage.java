package fr.tp.a21914280.tp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import fr.tp.a21914280.tp.util.ApiUtils;
import fr.tp.a21914280.tp.util.ResponseAPI;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddImage extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE=1;
    private static final int MY_PERISSIONS_REQUEST = 100;
    private int PICK_IMAGE_FROM_GALLERIE = 1;
    private boolean isCamera;
    private Button bnChoose;
    private Button bnUpload;
    private Button send;
    private ImageView image;
    private Bitmap bitmap;
    private String imagePath;
    private String idAnnonce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        bnChoose = (Button) findViewById(R.id.bnChoose);
        bnUpload = (Button) findViewById(R.id.bnUpload);
        send = (Button) findViewById(R.id.send);
        image = (ImageView) findViewById(R.id.imageChoose);

        bnUpload.setOnClickListener(this);
        bnChoose.setOnClickListener(this);


        if(ContextCompat.checkSelfPermission(AddImage.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(AddImage.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERISSIONS_REQUEST);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(AddImage.this, new String[] {Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("  Ajouter une image ");

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), Liste_Annonce.class);
                startActivity(intent);
            }
        });

        //Reception de l'id de l'annonce auquel on ajout l'image.
        Intent mIntent = getIntent();
        if(mIntent.getExtras()!=null) {
            idAnnonce = getIntent().getExtras().getString("AnnonceID");
        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.bnChoose :
                selectIamge();
                isCamera = false;
                break;

            case R.id.bnUpload :
                uploadImage();
                isCamera = true;
                break;
        }

    }

    //Selectionner l'image de la galérie
    public void selectIamge(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "select picture"), PICK_IMAGE_FROM_GALLERIE);
    }

    //prendre une photo
    public void uploadImage(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            //imagePath = getRealPathFromUrl(path);
            if (isCamera) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                image.setImageBitmap(imageBitmap);
            } else{

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                    image.setImageBitmap(bitmap);
                    image.setVisibility(View.VISIBLE);
                    bnChoose.setEnabled(false);
                    bnUpload.setEnabled(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERISSIONS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //
                }
                else{

                }
            } return;
        }
    }

    //Parssing de lurl de l'image
    public String getRealPathFromUrl(Uri uri){
        String[] projection= {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(AddImage.this,uri,projection,null,null,null);
        Cursor cursor = loader.loadInBackground();
        int column_idx = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_idx);
        cursor.close();
        return result;
    }


    public void send(View v){
        toastMessage("Image bien enrégistrée !!");
        Intent intent = new Intent (getApplicationContext(), Liste_Annonce.class);
        //addImage();
        startActivity(intent);
    }

    // appel de l'Api pour enrégistrer l'iamge
    public void addImage() {
        File file = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photo = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        RequestBody requestId =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), idAnnonce);

        retrofit2.Call<ResponseAPI> apiResponseCall = ApiUtils.getAPIService().uploadImage(photo, requestId);


        apiResponseCall.enqueue(new retrofit2.Callback<ResponseAPI>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseAPI> call, retrofit2.Response<ResponseAPI> response) {

                if (response.body().isSucces()) {
                    //Annonce mAnnonce = response.body().getAnnonce();
                    Toast.makeText(AddImage.this, "Image bien enrégistrée !!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(retrofit2.Call<ResponseAPI> call, Throwable t) {
                //toast
            }
        });
    }


    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


}
