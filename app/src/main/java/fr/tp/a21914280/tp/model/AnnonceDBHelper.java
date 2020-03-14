package fr.tp.a21914280.tp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class AnnonceDBHelper extends SQLiteOpenHelper {

    private static final  String DATABASE_NAME = "db_annonces";
    private static final  String TABLE_NAME = "annonces";
    private static final  String ID = "id";
    private static final  String AN_ID = "annonce_id";
    private static final  String TITRE = "titre";
    private static final  String DESCRIPTION = "description";
    private static final  String PSEUDO = "pseudo";
    private static final  String VILLE = "ville";
    private static final  String CP = "cp";
    private static final  String MAIL = "telContact";
    private static final  String TEL = "emailContact";
    private static final  String PRIX = "prix";
    private static final  String DATE = "date";

    public AnnonceDBHelper(Context context){
        super(context,DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creation de la table annonces
        String createTable = "create table "+ TABLE_NAME +
                "(id INTEGER PRIMARY KEY, "+AN_ID+" TEXT, " +TITRE+" TEXT, "+DESCRIPTION+" TEXT, "+PRIX+" TEXT, "+PSEUDO+" TEXT, "+VILLE+" TEXT, "+CP+" TEXT, "+MAIL+" TEXT, "+TEL+" TEXT, "+DATE+" TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }
    //
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addAnnonce(Annonce annonce){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(AN_ID, annonce.getId());
        //contentValues.put(AN_ID, annonce.getId());

        contentValues.put(AN_ID, annonce.getId());
        contentValues.put(TITRE, annonce.getTitre());
        contentValues.put(DESCRIPTION, annonce.getDescription());
        contentValues.put(PRIX, String.valueOf(annonce.getPrix()));
        contentValues.put(PSEUDO, annonce.getPseudo());
       //
        contentValues.put(VILLE, annonce.getVille());
        contentValues.put(CP, annonce.getCp());
        contentValues.put(MAIL, annonce.getEmailContact());
        contentValues.put(TEL, annonce.getTelContact());
        contentValues.put(DATE, String.valueOf(annonce.getDate()));
        //
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return true;
    }



    public Annonce readAnnonce(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + AN_ID + " = '" + id + "'";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        Log.d("verif", "getItemAnnonce: "+data.getString(data.getColumnIndex(DESCRIPTION)));
        String an_id = data.getString(data.getColumnIndex(AN_ID));
        String titre = data.getString(data.getColumnIndex(TITRE));
        String desc = data.getString(data.getColumnIndex(DESCRIPTION));
        String ps = data.getString(data.getColumnIndex(PSEUDO));
        String mail = data.getString(data.getColumnIndex(MAIL));
        String tel = data.getString(data.getColumnIndex(TEL));
        String ville = data.getString(data.getColumnIndex(VILLE));
        String cp = data.getString(data.getColumnIndex(CP));
        String prix = data.getString(data.getColumnIndex(PRIX));
        String date = data.getString(data.getColumnIndex(DATE));
        Annonce annonce = new Annonce(titre, desc, ps, Integer.parseInt(prix),mail,tel,ville,cp);
        annonce.setDate(Integer.parseInt(date));
        annonce.setId(an_id);
        return annonce;
    }

    //suppression de la bd
    public void delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + AN_ID + " = '" + id + "'";
        db.execSQL(query);
    }

    public List<Annonce> getAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        List<Annonce> list = new ArrayList<>();
        while(!data.isAfterLast()){
            String id = data.getString(data.getColumnIndex(AN_ID));
            String titre = data.getString(data.getColumnIndex(TITRE));
            String desc = data.getString(data.getColumnIndex(DESCRIPTION));
            String ps = data.getString(data.getColumnIndex(PSEUDO));
            String mail = data.getString(data.getColumnIndex(MAIL));
            String tel = data.getString(data.getColumnIndex(TEL));
            String ville = data.getString(data.getColumnIndex(VILLE));
            String cp = data.getString(data.getColumnIndex(CP));
            String prix = data.getString(data.getColumnIndex(PRIX));
            String date = data.getString(data.getColumnIndex(DATE));
            Annonce annonce = new Annonce(titre, desc, ps, Integer.parseInt(prix),mail,tel,ville,cp);
            annonce.setDate(Integer.parseInt(date));
            annonce.setId(id);
            list.add(annonce);
            data.moveToNext();
        }
        return list;
    }


}
