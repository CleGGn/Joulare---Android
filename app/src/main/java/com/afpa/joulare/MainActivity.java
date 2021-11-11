package com.afpa.joulare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.afpa.joulare.audio.AudioHandler;

import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    public final static String Mes_Prefs = "Mes_Prefs"; // Ref fichier préférences
    public final static String TAG = "MainActivity"; // Le TAG pour les Log


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadLocale();
        setContentView(R.layout.activity_main);

        Intent audio = new Intent(this, AudioHandler.class);
        startService(audio);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Fonction qui renvoie à la vue Un joueur
     * @param view la vue
     */
    public void clicSinglePlayer(View view){
        Log.i(TAG, "clicPlay");
        Intent intent = new Intent(MainActivity.this, NameActivity.class);
        startActivity(intent);
    }

    /**
     * Fonction qui renvoie à la vue Classement
     * @param view la vue
     */
    public void clicRank(View view){
        Log.i(TAG, "clicRank");
        Intent intent = new Intent(MainActivity.this, RankActivity.class);
        startActivity(intent);
    }

    /**
     * Fonction qui renvoie à la vue Options
     * @param view la vue
     */
    public void clicOptions(View view){
        Log.i(TAG, "clicOptions");
        Intent intent = new Intent(MainActivity.this, ParamActivity.class);
        startActivity(intent);
    }

    /**
     * Fonction qui quitte l'application
     * @param view la vue
     */
    public void clicExit(View view) {
        Log.i(TAG, "clicExit");
        finish();
        System.exit(0);
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

}