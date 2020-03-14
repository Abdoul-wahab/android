package fr.tp.a21914280.tp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Preference extends AppCompatActivity {

    private EditText pseudo;
    private EditText mail;
    private EditText phone;
    private TextView affichage;

    protected static String myPseudo;
    protected static String myPhone;
    protected static String myMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        pseudo = (EditText) findViewById (R.id.vid2);
        mail = (EditText) findViewById (R.id.mail);
        phone = (EditText) findViewById (R.id.phone);

        myToolbar.setTitle("");
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(" Mon profil ");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getApplicationContext(), Liste_Annonce.class);
                startActivity(intent);
            }
        });

        SharedPreferences p = getSharedPreferences("MyPseudo", Context.MODE_PRIVATE);
        myPseudo = p.getString("pseudo", "");



        SharedPreferences t = getSharedPreferences("MyPhone", Context.MODE_PRIVATE);
        myPhone = t.getString("phone", "");


        SharedPreferences m = getSharedPreferences("MyMail", Context.MODE_PRIVATE);
        myMail = m.getString("mail", "");

        pseudo.setText(myPseudo);
        mail.setText(myMail);
        phone.setText(myPhone);



    }

    public void load(View v){
        pseudo.getText().toString();
        mail.getText().toString();
        phone.getText().toString();

        SharedPreferences mPseudo = getSharedPreferences("MyPseudo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPseudo.edit();
        editor.putString("pseudo",pseudo.getText().toString());
        editor.commit();

        SharedPreferences mPhone = getSharedPreferences("MyPhone", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = mPhone.edit();
        editor2.putString("phone",phone.getText().toString());
        editor2.commit();


        SharedPreferences mMail = getSharedPreferences("MyMail", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor3 = mMail.edit();
        editor3.putString("mail",mail.getText().toString());
        editor3.commit();


    }
}
