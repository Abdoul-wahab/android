package fr.tp.a21914280.tp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import fr.tp.a21914280.tp.model.Annonce;
import fr.tp.a21914280.tp.model.AnnonceDBHelper;
import fr.tp.a21914280.tp.util.ApiUtils;
import fr.tp.a21914280.tp.util.Response;
import fr.tp.a21914280.tp.util.ResponseAPI;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static fr.tp.a21914280.tp.MyAnnonces.REQUEST_PHONE_CALL;

public class AnnoncePage extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE=1;

    private AnnonceDBHelper dbHelper;
    private static Annonce mAnnonce;
    TextView title;
    TextView description;
    TextView price;
    TextView pseudo;
    TextView emailContact;
    TextView telContact;
    TextView ville;
    //TextView cp;
    TextView date;
    ImageView image;
    private Context context;
    private static int img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce);

        title = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        //cp = (TextView) findViewById(R.id.cp);
        ville = (TextView) findViewById(R.id.ville);
        price = (TextView) findViewById(R.id.price);
        telContact = (TextView) findViewById(R.id.contact);
        emailContact = (TextView) findViewById(R.id.email);
        date = (TextView) findViewById(R.id.date);
        image = (ImageView) findViewById(R.id.image);
        pseudo = (TextView) findViewById(R.id.pseudo);
        dbHelper = new AnnonceDBHelper(AnnoncePage.this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("  Details ");

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), Liste_Annonce.class);
                startActivity(intent);
            }
        });

        if (getIntent().getExtras() != null) {
            //Bundle a = getIntent().getExtras();
            String id = getIntent().getExtras().getString("annonceID");
            Log.d("ttgg", "onCreate: " + id);
        apiCall(id);
    } else {
           apiRandomAnnonce();
        }
    }


    public void viewAnnoneDetails(Annonce annonce) {
        title.setText(annonce.getTitre());
        description.setText(annonce.getDescription());
        //cp.setText(annonce.getCp());
        pseudo.setText(annonce.getPseudo());
        ville.setText(annonce.getVille());
        price.setText(String.valueOf(annonce.getPrix())+"€" );
        telContact.setText(annonce.getTelContact());
        emailContact.setText(annonce.getEmailContact());
        showImage(annonce);
        Locale locale = new Locale("fr", "FR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        date.setText(dateFormat.format(new Date(annonce.getDate())));
    }



    //Affichage de l'image
    public void showImage(Annonce annonce) {
        if (annonce.getImages() != null && annonce.getImages().size() != 0) {
            Glide.with(this)
                    .asBitmap()
                    .load(annonce.getImages().get(img))
                    .into(image);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.ic_launcher_background)
                    .into(image);
        }
    }

    //défiler les images au clique
    public void imageList(View view) {
        ImageView clique = (ImageView) view;
        if(mAnnonce.getImages() != null && mAnnonce.getImages().size() > 0) {
            img = (mAnnonce.getImages().size() - 1 > img) ? img + 1 : 0;
            Glide.with(this)
                    .asBitmap()
                    .load(mAnnonce.getImages().get(img))
                    .into(image);
        }
    }


    //Contacter l'annonceur par téléphone
    public void tContact(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+mAnnonce.getTelContact()));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else
        {
            startActivity(intent);
        }
    }


    //Contacter l'annonceur par mail
    public void mContact(View v){
        Intent intent = new Intent (getApplicationContext(), Envoie_mail.class);
        intent.putExtra("objet_annonce", mAnnonce);
        startActivity(intent);
    }


    //Rédirection vers le formulaire prérempli de Mise à jour d'une annonce
    public void updateForm(View view){
        Intent intent = new Intent (getApplicationContext(), update.class);
        intent.putExtra("annonce", mAnnonce);startActivity(intent);
    }


    // Appel de l'API pour les détails de l'annonce dont l'id est passé en paramètre
    public void apiCall(String id) {

        retrofit2.Call<ResponseAPI> apiResponseCall = ApiUtils.getAPIService().getAnnonce(id);
        apiResponseCall.enqueue(new retrofit2.Callback<ResponseAPI>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseAPI> call, retrofit2.Response<ResponseAPI> response) {
                if (response.body().isSucces()) {
                    mAnnonce = response.body().getAnnonce();
                    viewAnnoneDetails(mAnnonce);
                }
            }
            @Override
            public void onFailure(retrofit2.Call<ResponseAPI> call, Throwable t) {
                //creer un tost !!!
            }
        });

    }

    // SUppression Annonce
    public void delete(View v) {
        retrofit2.Call<Response> apiResponseCall = ApiUtils.getAPIService().deleteAnnonce(mAnnonce.getId());
        apiResponseCall.enqueue(new retrofit2.Callback<Response>() {
            @Override
            public void onResponse(retrofit2.Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body().isSucces()) {
                    Log.d("tgtg", "onResponse: Bien Supprimé !!!");
                    Intent intent = new Intent(getApplicationContext(), Liste_Annonce.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Response> call, Throwable t) {
                //creer un tost !!!
            }
        });
    }


    //Annonce au Hasard
    public void apiRandomAnnonce() {

        retrofit2.Call<ResponseAPI> apiResponseCall = ApiUtils.getAPIService().randomAnnonce();
        apiResponseCall.enqueue(new retrofit2.Callback<ResponseAPI>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseAPI> call, retrofit2.Response<ResponseAPI> response) {
                if (response.body().isSucces()) {
                    if (response.body().isSucces()) {
                        mAnnonce = response.body().getAnnonce();
                        viewAnnoneDetails(mAnnonce);
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseAPI> call, Throwable t) {
                //creer un tost !!!
            }
        });
    }


    //Stockage d'une annonce dans la bd
    public void stockAnnonce(View v){

        AddData(mAnnonce);
    }

    public void AddData(Annonce annonce) {
        boolean insertData = dbHelper.addAnnonce(annonce);

        if (insertData) {
            toastMessage("Bien enrégistré !!");
        } else {
            toastMessage("Something went wrong");
        }
    }



    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}