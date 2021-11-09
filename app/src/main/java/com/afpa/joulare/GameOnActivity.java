package com.afpa.joulare;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GameOnActivity extends Activity {

    public final static String TAG = "GameOnActivity"; // Le TAG pour les Log
    public boolean [][] checkMine = new boolean[12][10];
    private boolean mine = true;
    float width = 10;
    float height = 12;
    float mineAmount = 12;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadLocale();
        setContentView(R.layout.activity_gameon);
        // Initialisation du TIMER
        TextView timer = (TextView) findViewById( R.id.timer );
        new CountDownTimer(5*60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(new SimpleDateFormat("mm:ss:SS").format(new Date( millisUntilFinished)));
            }
            public void onFinish() {
                timer.setText("X");
            }
        }.start();

        //Affichage du nom du joueur
        TextView nomJoueur = findViewById(R.id.nomJoueur);
        Intent nom = getIntent();
        String strNom = nom.getExtras().getString("nom");
        nomJoueur.setText(strNom);


        //Affichage de la grille
        LinearLayout monLayout = findViewById(R.id.grille);
        int count = -1;
        for (int i = 0; i < 12 ; i++){
            float distribution = mineAmount / (width * height);
            float mult100 = Math.round(distribution * 100);
            LinearLayout mesRangs = new LinearLayout(monLayout.getContext());
            LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            mesRangs.setGravity(Gravity.CENTER);
            monLayout.addView(mesRangs, linearParams);
            for (int j = 0 ; j < 10 ; j++){
                if (Math.round(Math.random() * mult100) == 4 && mineAmount > 0) {
                    checkMine[i][j] = mine;
                    mineAmount--;
                } else {
                    checkMine[i][j] = !mine;
                } Log.i(TAG,"Mine :" + checkMine[i][j]);
                LinearLayout mesColonnes = new LinearLayout(mesRangs.getContext());
                LinearLayout.LayoutParams linearParams2 = new LinearLayout.LayoutParams(105, 105);
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
                        verifyBoard(mesColonnes.getId());
                        String emptyCell ="@drawable/emptycell";
                        String mineCell = "@drawable/mine";

                        if(verifyBoard(mesColonnes.getId())) {
                            mesColonnes.setBackground(getDrawable(getResources().getIdentifier(mineCell, null, getPackageName())));
                        } else {
                            mesColonnes.setBackground(getDrawable(getResources().getIdentifier(emptyCell, null, getPackageName())));
                        }

                    });
                }

            }
        }
    }

    /**
     * Fonction qui affiche quelque chose au moment du clic sur la vue
     * @param v la vue
     */
    private void clicCase(View v) {
        int idVue = v.getId();
        Log.i(TAG, "CASE : " + idVue);
    }

    private boolean verifyBoard(int idVue){
        int i;
        int j;
    if(idVue > 10){
        i = idVue / 10;
        j = idVue % 10;
        return checkMine[i][j];
    } else {
        i = 0;
        j = idVue + 1;
        return checkMine[i][j];
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
