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


        mpInGame = MediaPlayer.create(getBaseContext(),R.raw.molennel);
        mpInGame.setLooping(true);
        mpInGame.seekTo(0);
        mpInGame.start();

        View nom = findViewById(R.id.choiceName);
        String strNom = nom.toString();

        Intent intent = new Intent(NameActivity.this, GameOnActivity.class);
        intent.putExtra("nom", strNom);
        startActivity(intent);
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
