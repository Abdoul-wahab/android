package fr.tp.a21914280.tp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import fr.tp.a21914280.tp.model.Annonce;
import fr.tp.a21914280.tp.util.ApiUtils;
import fr.tp.a21914280.tp.util.ItemClickListner;
import fr.tp.a21914280.tp.util.ListeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Liste_Annonce extends AppCompatActivity implements ItemClickListner {

    protected static List<Annonce> objectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste__annonce);

        Intent mIntent = getIntent();

        if(mIntent.getExtras()!=null) {
            SharedPreferences p = getSharedPreferences("MyPseudo", Context.MODE_PRIVATE);
            String pseudo = p.getString("pseudo", "");
            //Preference pref = new Preference();
            if(!TextUtils.isEmpty(pseudo)){
                Log.e("ppp", "onCreate: "+pseudo);
                responseList(pseudo);
            }else{
                toastMessage("Vous n'avez pas d'annonces enrégistées !!");
            }
        }else{
                makeApiCall();
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("  Toutes les annonces ");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), Liste_Annonce.class);
                startActivity(intent);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.acceuil:
                Intent intent2 = new Intent (getApplicationContext(), Liste_Annonce.class);
                startActivity(intent2);
                return true;
            case R.id.ann_hasard:
                Intent intent3 = new Intent (getApplicationContext(), AnnoncePage.class);
                startActivity(intent3);
                return true;
            case R.id.create:
                Intent intent4 = new Intent (getApplicationContext(), create.class);
                startActivity(intent4);
                return true;
            case R.id.profil:
                Intent intent5 = new Intent (getApplicationContext(), Preference.class);
                startActivity(intent5);
                return true;
            case R.id.ann_stockes:
                Intent intent6 = new Intent (getApplicationContext(), ListAnnonceStock.class);
                startActivity(intent6);
                return true;
            case R.id.my_ann:
                Intent intent7 = new Intent (getApplicationContext(), Liste_Annonce.class);
                intent7.putExtra("mesAnnonces", "ok");
                startActivity(intent7);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liste_annonce, menu);
        return true;
    }

    public void initRecyclerView(List<Annonce> objectList){

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        // créer l'adapteur de Personnes en lui donnant la liste d'objets Personne
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, objectList, this);
        // dire au recyclerView d'utiliser l'adapter créé
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    public void itemClick(View view, int position, View transitionView) {

        SharedPreferences p = getSharedPreferences("MyPseudo", Context.MODE_PRIVATE);
        String myPseudo = p.getString("pseudo", "");
        Intent intent;
        Log.d("JML", "PSEUD: "+ myPseudo);
        Log.d("JML", "GETPSEUDO: "+ objectList.get(position).getPseudo());

        if ( objectList.get(position).getPseudo().equals(myPseudo) ) {

            Log.d("JML", "itemClick2: "+ myPseudo);
            intent = new Intent(getApplicationContext(), MyAnnonces.class);
            intent.putExtra("annonceID", objectList.get(position).getId());
        }else {
            intent = new Intent(getApplicationContext(), AnnoncePage.class);
            intent.putExtra("annonceID", objectList.get(position).getId());
        }

        Pair<View, String> pair = Pair.create(transitionView, "transitionAnnonceImage");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair);
        startActivity(intent);
    }

    public void makeApiCall() {

        Call<ListeResponse> apiResponseCall = ApiUtils.getAPIService().getListAnnonce();
        apiResponseCall.enqueue(new Callback<ListeResponse>() {
            @Override
            public void onResponse(Call<ListeResponse> call, Response<ListeResponse> response) {
                ListeResponse annonces = response.body();
                if( annonces != null && annonces.isSucces()){

                    objectList = annonces.getList();
                    initRecyclerView(objectList);

                }else{
                    toastMessage("Aucune annonce disponnible");
                }
            }

            @Override
            public void onFailure(Call<ListeResponse> call, Throwable t) {
                toastMessage("Une erreur a survenue !!");
            }
        });

    }


    public void responseList(String pseudo) {

        Call<ListeResponse> apiResponseCall = ApiUtils.getAPIService().myList(pseudo);
        apiResponseCall.enqueue(new Callback<ListeResponse>() {
            @Override
            public void onResponse(Call<ListeResponse> call, Response<ListeResponse> response) {
                ListeResponse annonces = response.body();
                if( annonces.getList().size()>0 && annonces.isSucces()){
                    Log.d("JML", "onResponse: "+annonces.getList().get(0).getTitre());
                    objectList = annonces.getList();

                    initRecyclerView(objectList);

                }else{
                    toastMessage("Vous n'avez pas enrégistré d'annonces");
                }
            }

            @Override
            public void onFailure(Call<ListeResponse> call, Throwable t) {
                toastMessage("Une erreur a survenue !!");
            }
        });

    }
    // Toast
    public void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }


}