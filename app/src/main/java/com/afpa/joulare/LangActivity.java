package com.afpa.joulare;


import static com.afpa.joulare.MainActivity.TAG;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afpa.joulare.audio.AudioHandler;

import java.util.Locale;

public class LangActivity extends AppCompatActivity {

    public final static String TAG = "LangActivity"; // Le TAG pour les Log


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadLocale();
        setContentView(R.layout.activity_lang);

        Intent svc=new Intent(this, AudioHandler.class);
        startService(svc);

        Button fr = findViewById(R.id.fr);
        Button eng = findViewById(R.id.en);
        Button es = findViewById(R.id.es);

        /**
         * Fonction qui change la langue et la configuration de l'application en français si l'événement est déclenché
         */
        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "clicFR");
                Locale locale = new Locale("fr");
                String strLocale = locale.toString();
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;

                SharedPreferences mesPrefs = getSharedPreferences(MainActivity.Mes_Prefs, MODE_PRIVATE);
                String language = mesPrefs.getString("Language", "");

                if (!language.equals(strLocale)) {
                SharedPreferences.Editor edMesPrefs = mesPrefs.edit();
                edMesPrefs.putString("Language", strLocale).apply();

                Intent intent = new Intent(LangActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                } else {
                    Toast.makeText(LangActivity.this,"Vous êtes déja en français", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Fonction qui change la langue et la configuration de l'application en anglais si l'événement est déclenché
         */
        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "clicENG");
                Locale locale = new Locale("eng");
                String strLocale = locale.toString();
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;

                SharedPreferences mesPrefs = getSharedPreferences(MainActivity.Mes_Prefs, MODE_PRIVATE);
                String language = mesPrefs.getString("Language", "");

                if (!language.equals(strLocale)) {
                SharedPreferences.Editor edMesPrefs = mesPrefs.edit();
                edMesPrefs.putString("Language", strLocale).apply();

                Intent intent = new Intent(LangActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                } else {
                    Toast.makeText(LangActivity.this,"You are already in english", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Fonction qui change la langue et la configuration de l'application en espagnol si l'événement est déclenché
         */
        es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "clicES");
                Locale locale = new Locale("es");
                String strLocale = locale.toString();
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;

                SharedPreferences mesPrefs = getSharedPreferences(MainActivity.Mes_Prefs, MODE_PRIVATE);
                String language = mesPrefs.getString("Language", "");

                if (!language.equals(strLocale)) {
                SharedPreferences.Editor edMesPrefs = mesPrefs.edit();
                edMesPrefs.putString("Language", strLocale).apply();

                Intent intent = new Intent(LangActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                } else {
                    Toast.makeText(LangActivity.this,"Ya estas en español", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
     * fonction qui termine l'intent et revient à l'écran précédent
     * @param v la vue
     */
    public void retourClic(View v) {
        Log.i(TAG, "retourClic");
        finish();
    }
}
