package fr.tp.a21914280.tp;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import fr.tp.a21914280.tp.R;
import fr.tp.a21914280.tp.model.Annonce;
import fr.tp.a21914280.tp.model.AnnonceDBHelper;
import fr.tp.a21914280.tp.util.ItemClickListner;

public class ListAnnonceStock extends AppCompatActivity implements ItemClickListner {

    private static List<Annonce> list;
    private AnnonceDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_annonce_stock);
        dbHelper = new AnnonceDBHelper(this);
        listAnnoncestock();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        myToolbar.setTitle("Annonces Stockés");
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
            case R.id.my_ann:
                Intent intent7 = new Intent (getApplicationContext(), Liste_Annonce.class);
                intent7.putExtra("mesAnnonces", "ok");
                startActivity(intent7);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        Intent intent = new Intent(getApplicationContext(), MyAnnonceStockDetails.class);

        intent.putExtra("DetailsAnnonceStock", list.get(position));

        Pair<View, String> pair = Pair.create(transitionView, "transitionAnnonceImage");
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair);
        startActivity(intent);
    }

    public void listAnnoncestock(){

        list = dbHelper.getAll();
        initRecyclerView(list);
    }
}
