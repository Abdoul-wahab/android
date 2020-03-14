package fr.tp.a21914280.tp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.tp.a21914280.tp.model.Annonce;

public class Envoie_mail extends AppCompatActivity {

    private EditText destinataire;
    private EditText cc;
    private EditText objet;
    private EditText message;
    private Button Btnenvoyer;
    private String val_destinataire;
    private String val_cc;
    private String val_objet;
    private String val_message;
    private Annonce mAnnonce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envoie_mail);


        destinataire = (EditText) findViewById(R.id.destinataire);
        cc = (EditText) findViewById (R.id.cc);
        objet = (EditText) findViewById (R.id.objet);
        message = (EditText) findViewById (R.id.objet);
        Btnenvoyer = (Button) findViewById (R.id.envoyer);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(" Envoyer un message ");

        Intent intent = getIntent();
        if(intent.getSerializableExtra("objet_annonce") != null){
            mAnnonce = (Annonce) intent.getSerializableExtra("objet_annonce");}

        if(mAnnonce.getEmailContact() != ""){
            destinataire.setText(mAnnonce.getEmailContact());
        }

        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), AnnoncePage.class);
                intent.putExtra("annonceID", mAnnonce.getId());
                startActivity(intent);
            }
        });
        Btnenvoyer.setOnClickListener(new View.OnClickListener(
) {
            public void onClick(View view) {
                if(!TextUtils.isEmpty(destinataire.getText().toString())){
                     val_destinataire = destinataire.getText().toString();
                }
                if(!TextUtils.isEmpty(cc.getText().toString())){
                     val_cc = cc.getText().toString();
                }
                if(!TextUtils.isEmpty(message.getText().toString())){
                     val_message = message.getText().toString();
                }
                if(!TextUtils.isEmpty(objet.getText().toString())){
                     val_objet = objet.getText().toString();
                }
                Send(val_destinataire, val_message, val_objet, val_cc);
            }
        });
    }

    protected void Send(String val_destinataire, String val_message, String val_objet, String  val_cc) {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, val_destinataire);
        emailIntent.putExtra(Intent.EXTRA_CC, val_cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, val_objet);
        emailIntent.putExtra(Intent.EXTRA_TEXT, val_message);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(Envoie_mail.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}