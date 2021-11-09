package com.afpa.joulare;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afpa.joulare.audio.AudioHandler;

import java.util.Locale;

public class NameActivity  extends AppCompatActivity {

    public final static String TAG = "NameActivity"; // Le TAG pour les Log
    public static MediaPlayer mpInGame = new MediaPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadLocale();
        setContentView(R.layout.activity_name);

        Intent svc=new Intent(this, AudioHandler.class);
        startService(svc);
    }

    /**
     * Fonction executée au lancement, elle va récupérer la dernière langue choisie dans le fichier préférences
     */
    public void loadLocale() {
        Log.i(TAG, "loadLocale");
        SharedPreferences prefs = getSharedPreferences("Mes_Prefs", Activity.MODE_PRIVATE);
        String language = prefs.getString("Language", "");
        changeLang(language);
    }

    /**
     * Fonction qui adapte la langue en fonction de celle choisie dans les préférences
     * @param lang la langue présente dans les préférences
     */
    public void changeLang(String lang) {
        Log.i(TAG, "changeLang");
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
    }

    /**
     * Fonction qui lance la partie
     * @param view la vue
     */
    public void clicPlay(View view){
        Log.i(TAG, "clicPlay");

        EditText nom = (EditText) findViewById(R.id.choiceName);
        String strNom = nom.getText().toString();

        if(strNom.isEmpty()){
            Toast.makeText(this, R.string.emptyName, Toast.LENGTH_SHORT).show();
        } else if(strNom.length() > 13){
            Toast.makeText(this, R.string.lengthName, Toast.LENGTH_SHORT).show();
        } else if (strNom.equals("ElPoivrot")){
            MediaPlayer mpLee = MediaPlayer.create(getBaseContext(),R.raw.lee);
            mpLee.start();
            mpInGame = MediaPlayer.create(getBaseContext(),R.raw.molennel);
            mpInGame.start();
            MediaPlayer mpHaha = MediaPlayer.create(getBaseContext(),R.raw.haha);
            Intent intent = new Intent(NameActivity.this, GameOnActivity.class);
            intent.putExtra("nom", strNom);
            startActivity(intent);
        } else {
            mpInGame = MediaPlayer.create(getBaseContext(),R.raw.molennel);
            MediaPlayer mpHaha = MediaPlayer.create(getBaseContext(),R.raw.haha);
            mpInGame.setLooping(true);
            mpInGame.seekTo(0);
            mpInGame.start();
            mpHaha.start();
            Intent intent = new Intent(NameActivity.this, GameOnActivity.class);
            intent.putExtra("nom", strNom);
            startActivity(intent);
        }
    }

    /**
     * fonction qui termine l'intent et revient à l'écran précédent
     * @param v la vue
     */
    public void retourClic(View v) {
        Log.i(TAG, "retourClic");
        finish();
    }


}
