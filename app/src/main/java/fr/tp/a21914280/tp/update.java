package fr.tp.a21914280.tp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import fr.tp.a21914280.tp.model.Annonce;
import fr.tp.a21914280.tp.util.ApiUtils;
import fr.tp.a21914280.tp.util.ResponseAPI;
import retrofit2.Call;
import retrofit2.Callback;

public class update extends AppCompatActivity {

    private EditText titre;
    private EditText description;
    private EditText prix;
    private EditText ville;
    private EditText cp;
    private TextView error;
    private Annonce mAnnonce = new Annonce("","","",0,"","","","");
    //pour vérifier c'est pour une modification ou un enregistrement
    private String annonceId;




    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        error = (TextView) findViewById(R.id.s_error);
        titre = (EditText) findViewById (R.id.titre);
        description = (EditText) findViewById (R.id.description);
        prix = (EditText) findViewById (R.id.prix);
        ville = (EditText) findViewById (R.id.pseudo);
        cp = (EditText) findViewById (R.id.cp);


        Intent intent = getIntent();
        if(intent.getSerializableExtra("annonce") != null){
            mAnnonce = (Annonce) intent.getSerializableExtra("annonce");

            //Remplissage du formulaire par les informations de l'annonce à modifier
            if(mAnnonce.getTitre() != ""){titre.setText(mAnnonce.getTitre());}
            if(mAnnonce.getDescription() != ""){description.setText(mAnnonce.getDescription());}
            if(Integer.toString(mAnnonce.getPrix()) != ""){prix.setText(Integer.toString(mAnnonce.getPrix()));}
            if(mAnnonce.getVille() != ""){ville.setText(mAnnonce.getVille());}
            if(mAnnonce.getCp() != ""){cp.setText(mAnnonce.getCp());}

            Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
            myToolbar.setTitle("");
            setSupportActionBar(myToolbar);
            myToolbar.setTitle("  Modifiez votre annonce ");
            myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent (getApplicationContext(), AnnoncePage.class);
                    intent.putExtra("annonceID", mAnnonce.getId());
                    startActivity(intent);
                }
            });
        }

    }
    /*public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                acueil();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
    public void acueil(){
        Intent intent = new Intent (getApplicationContext(), Liste_Annonce.class);
        startActivity(intent);
    }

    public void update(View view){
        Annonce annonce = mAnnonce;
        //Vérification des infos du formulaire
        if(!TextUtils.isEmpty(titre.getText().toString().trim())){
            annonce.setTitre(titre.getText().toString());
            if(!TextUtils.isEmpty(description.getText().toString())){
                annonce.setDescription(description.getText().toString());
            }
            if(!TextUtils.isEmpty(prix.getText().toString())){
                annonce.setPrix(Integer.parseInt(prix.getText().toString()));
            }
            if(!TextUtils.isEmpty(ville.getText().toString())){
                annonce.setVille(ville.getText().toString());
            }
            if(!TextUtils.isEmpty(cp.getText().toString())){
                annonce.setCp(cp.getText().toString());
            }
            //Recupération des info des préférences
            SharedPreferences p = getSharedPreferences("MyPseudo", Context.MODE_PRIVATE);
            String pseudo = p.getString("pseudo", "");

            SharedPreferences m = getSharedPreferences("MyMail", Context.MODE_PRIVATE);
            String mail = m.getString("mail", "");

            SharedPreferences t = getSharedPreferences("MyMail", Context.MODE_PRIVATE);
            String tel = t.getString("phone", "");

            if(!TextUtils.isEmpty(pseudo)){
                annonce.setPseudo(pseudo);
            }
            if(!TextUtils.isEmpty(mail)){
                annonce.setEmailContact(mail);
            }
            if(!TextUtils.isEmpty(tel)){
                annonce.setTelContact(tel);
            }
            updateSave(annonce);

        }else{
            error.setText("Remplir tout les champs !!!");
        }
    }

    public void updateSave(Annonce a){
        Call<ResponseAPI> apiResponseCall = ApiUtils.getAPIService().updateAnnonce(a.getId(),a.getTitre(),a.getDescription(),a.getPseudo(),a.getPrix(),a.getEmailContact(),a.getTelContact(),a.getVille(),a.getCp());
        apiResponseCall.enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, retrofit2.Response<ResponseAPI> response) {
                if(response.body().isSucces()){
                    ResponseAPI rep = response.body();
                    Annonce an = rep.getAnnonce();
                    Log.d("tagg", "onResponse: Update"+an.getTitre());

                    //Redirection vers la page de l'annonce
                    Intent intent = new Intent (getApplicationContext(), AnnoncePage.class);
                    intent.putExtra("annonceID", mAnnonce.getId());
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
