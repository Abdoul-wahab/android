package fr.tp.a21914280.tp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import fr.tp.a21914280.tp.model.Annonce;
import fr.tp.a21914280.tp.util.ApiUtils;
import fr.tp.a21914280.tp.util.ResponseAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class create extends AppCompatActivity {

    private EditText titre;
    private EditText description;
    private EditText prix;
    private EditText ville;
    private EditText cp;
    private TextView error;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        error = (TextView) findViewById(R.id.s_error);
        titre = (EditText) findViewById (R.id.titre);
        description = (EditText) findViewById (R.id.description);
        prix = (EditText) findViewById (R.id.prix);
        ville = (EditText) findViewById (R.id.pseudo);
        cp = (EditText) findViewById (R.id.cp);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("  Créer une annonce ");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), Liste_Annonce.class);
                startActivity(intent);
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /*public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                Intent intent = new Intent (getApplicationContext(), Liste_Annonce.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/


    public void send(View V){
        if(!TextUtils.isEmpty(titre.getText().toString().trim())){
            Annonce myAnnonce = new Annonce("","","",0,"","","","");
            myAnnonce.setTitre(titre.getText().toString());

            if(!TextUtils.isEmpty(description.getText().toString())){
                myAnnonce.setDescription(description.getText().toString());
            }
            if(!TextUtils.isEmpty(prix.getText().toString())){
                myAnnonce.setPrix(Integer.parseInt(prix.getText().toString()));
            }
            if(!TextUtils.isEmpty(ville.getText().toString())){
                myAnnonce.setVille(ville.getText().toString());
            }
            if(!TextUtils.isEmpty(cp.getText().toString())){
                myAnnonce.setCp(cp.getText().toString());
            }
            //Recupération des info des préférences
            SharedPreferences p = getSharedPreferences("MyPseudo", Context.MODE_PRIVATE);
            String pseudo = p.getString("pseudo", "");

            SharedPreferences m = getSharedPreferences("MyMail", Context.MODE_PRIVATE);
            String mail = m.getString("mail", "");

            SharedPreferences t = getSharedPreferences("MyMail", Context.MODE_PRIVATE);
            String tel = t.getString("phone", "");

            if(!TextUtils.isEmpty(pseudo)){
                myAnnonce.setPseudo(pseudo);
            }
            if(!TextUtils.isEmpty(mail)){
                myAnnonce.setEmailContact(mail);
            }
            if(!TextUtils.isEmpty(tel)){
                myAnnonce.setTelContact(tel);
            }

            sendAnnonce(myAnnonce);

        }else{
            error.setText("Remplir tout les champs !!!");
        }


    }



    public void sendAnnonce(Annonce a){

        Call<ResponseAPI> apiResponseCall = ApiUtils.getAPIService().saveAnnonce(a.getTitre(),a.getDescription(),a.getPseudo(),a.getPrix(),a.getEmailContact(),a.getTelContact(),a.getVille(),a.getCp());
        apiResponseCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                if(response.body().isSucces()){
                    ResponseAPI rep = response.body();
                    Annonce an = rep.getAnnonce();
                    error.setText("titre : "+ an.getTitre()+"\nid : "+an.getId());
                    Intent intent = new Intent (getApplicationContext(), AnnoncePage.class);
                    intent.putExtra("annonceID", an.getId());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                //creer un tost !!!

            }
        });

    }
}

