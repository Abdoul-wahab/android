package fr.tp.a21914280.tp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import fr.tp.a21914280.tp.model.Annonce;
import fr.tp.a21914280.tp.model.AnnonceDBHelper;

import static fr.tp.a21914280.tp.MyAnnonces.REQUEST_PHONE_CALL;

public class MyAnnonceStockDetails extends AppCompatActivity {

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
        setContentView(R.layout.activity_my_annonce_strock_details);

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
        dbHelper = new AnnonceDBHelper(MyAnnonceStockDetails.this);

        if (getIntent().getExtras() != null) {
            //Bundle a = getIntent().getExtras();
            mAnnonce = (Annonce) getIntent().getSerializableExtra("DetailsAnnonceStock");
            viewAnnoneDetails(mAnnonce);
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
        //showImage(annonce);

        Locale locale = new Locale("fr", "FR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        date.setText(dateFormat.format(new Date(annonce.getDate())));
    }

    public void mContact(View v){
        Intent intent = new Intent (getApplicationContext(), Envoie_mail.class);
        intent.putExtra("objet_annonce", mAnnonce);
        startActivity(intent);
    }

    public void tContact(View v){
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

    public void supprimer(View v){
        dbHelper.delete(mAnnonce.getId());
        toastMessage("Bien supprimé !!");
        Intent intent = new Intent(getApplicationContext(), ListAnnonceStock.class);
        startActivity(intent);
    }

    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
