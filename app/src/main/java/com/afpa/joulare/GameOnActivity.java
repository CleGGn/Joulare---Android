package com.afpa.joulare;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;


import java.util.Locale;

public class GameOnActivity extends Activity {

    public final static String TAG = "GameOnActivity"; // Le TAG pour les Log

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadLocale();
        setContentView(R.layout.activity_gameon);
        int count = 0;

        LinearLayout monLayout = findViewById(R.id.grille);
        for (int i = 0; i < 10 ; i++){
            LinearLayout mesRangs = new LinearLayout(monLayout.getContext());
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mesRangs.setGravity(Gravity.CENTER);
            monLayout.addView(mesRangs, linearParams);
            for (int j = 0 ; j < 10 ; j++){
                LinearLayout mesColonnes = new LinearLayout(mesRangs.getContext());
                LinearLayout.LayoutParams linearParams2 = new LinearLayout.LayoutParams(100, 100);
                mesColonnes.setGravity(Gravity.CENTER);
                String imgName="@drawable/cell";
                mesColonnes.setBackground(getDrawable(getResources().getIdentifier(imgName, null, getPackageName())));
                mesColonnes.setGravity(Gravity.CENTER);
                count++;
                mesColonnes.setId(count);
                mesRangs.addView(mesColonnes,linearParams2);
                mesColonnes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clicCase(v);
                        String newImg ="@drawable/emptycell";
                        mesColonnes.setBackground(getDrawable(getResources().getIdentifier(newImg, null, getPackageName())));
                    }
                });
            }
        }
        TextView nomJoueur = findViewById(R.id.nomJoueur);
        Intent nom = getIntent();
        String strNom = nom.getExtras().getString("nom");
        nomJoueur.setText(strNom);
    }

    /**
     * Fonction qui affiche quelque chose au moment du clic sur la vue
     * @param v la vue
     */
    private void clicCase(View v) {
        int idVue = v.getId();
        Toast.makeText(this,"CASE:" + idVue,Toast.LENGTH_SHORT).show();
        Log.i(TAG, "CASE : " + idVue);

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
     * Fonction qui affiche une bulle de dialogue et met un terme ou non à l'instance de la partie
     * @param view la vue
     */
    public void clicForfeit(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(GameOnActivity.this);
        builder.setTitle("Attention !");
        builder.setMessage("Voulez-vous vraiment quitter ?");

        builder.setCancelable(true); // Si l'utilisateur clique à coté de la boite, ça annule tout

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                NameActivity.mpInGame.stop();
                Intent intent = new Intent(GameOnActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
