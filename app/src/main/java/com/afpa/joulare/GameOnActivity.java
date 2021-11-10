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
    public int WIDTH = 4;
    public int HEIGHT = 4;
    public int mineAmount = 1;
    public boolean [][] checkMine = new boolean[HEIGHT][WIDTH];
    private boolean mine = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loadLocale();
        setContentView(R.layout.activity_gameon);
        // Initialisation du TIMER
        TextView timer = (TextView) findViewById(R.id.timer);
        new CountDownTimer(5 * 60000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished)));
            }
            public void onFinish() {
                timer.setText("X");
                //defeat();
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
            for (int i = 0; i < HEIGHT; i++) {
                LinearLayout mesRangs = new LinearLayout(monLayout.getContext());
                LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                mesRangs.setGravity(Gravity.CENTER);
                monLayout.addView(mesRangs, linearParams);
                for (int j = 0; j < WIDTH; j++) {
                    float distribution = mineAmount / (WIDTH * HEIGHT);
                    int mult100 = Math.round(distribution * 100);
                    long random = Math.round(Math.random() * 75);
                    while(mineAmount != 0) {
                        if (random < mult100) {
                            if (checkMine[i][j] != mine) {
                                checkMine[i][j] = mine;
                                mineAmount--;
                            }
                        } else {
                            checkMine[i][j] = !mine;
                        }
                    }
                    Log.i(TAG, "Mine amount :" + mineAmount);
                    LinearLayout mesColonnes = new LinearLayout(mesRangs.getContext());
                    LinearLayout.LayoutParams linearParams2 = new LinearLayout.LayoutParams(100, 100);
                    mesColonnes.setGravity(Gravity.CENTER);
                    String imgName = "@drawable/cell";
                    mesColonnes.setBackground(getDrawable(getResources().getIdentifier(imgName, null, getPackageName())));
                    mesColonnes.setGravity(Gravity.CENTER);
                    count++;
                    mesColonnes.setId(count);
                    mesRangs.addView(mesColonnes, linearParams2);
                    mesColonnes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clicCase(v);
                            //Log.i(TAG, "ID :" + mesColonnes.getId());
                            //Log.i(TAG, "Mine ? " + verifyBoard(mesColonnes.getId()));
                            //Log.i(TAG,  "Mines autour :" + distribImg(mesColonnes.getId()));
                            String emptyCell = "@drawable/emptycell";
                            String mineCell = "@drawable/trex";
                            String cell1 = "@drawable/n1";
                            String cell2 = "@drawable/n2";
                            String cell3 = "@drawable/n3";
                            String cell4 = "@drawable/n4";
                            String cell5 = "@drawable/n5";
                            String cell6 = "@drawable/n6";
                            String cell7 = "@drawable/n7";
                            String cell8 = "@drawable/n8";

                            if (verifyBoard(mesColonnes.getId())) {
                                mesColonnes.setBackground(getDrawable(getResources().getIdentifier(mineCell, null, getPackageName())));
                                //defeat();
                            } else
                                mesColonnes.setBackground(getDrawable(getResources().getIdentifier(emptyCell, null, getPackageName())));

                            if (!verifyBoard(mesColonnes.getId())) {
                                mesColonnes.setBackground(getDrawable(getResources().getIdentifier(emptyCell, null, getPackageName())));
                                if (distribImg(mesColonnes.getId()) == 1) {
                                    mesColonnes.setBackground(getDrawable(getResources().getIdentifier(cell1, null, getPackageName())));
                                } else if (distribImg(mesColonnes.getId()) == 2) {
                                    mesColonnes.setBackground(getDrawable(getResources().getIdentifier(cell2, null, getPackageName())));
                                } else if (distribImg(mesColonnes.getId()) == 3) {
                                    mesColonnes.setBackground(getDrawable(getResources().getIdentifier(cell3, null, getPackageName())));
                                } else if (distribImg(mesColonnes.getId()) == 4) {
                                    mesColonnes.setBackground(getDrawable(getResources().getIdentifier(cell4, null, getPackageName())));
                                } else if (distribImg(mesColonnes.getId()) == 5) {
                                    mesColonnes.setBackground(getDrawable(getResources().getIdentifier(cell5, null, getPackageName())));
                                } else if (distribImg(mesColonnes.getId()) == 6) {
                                    mesColonnes.setBackground(getDrawable(getResources().getIdentifier(cell6, null, getPackageName())));
                                } else if (distribImg(mesColonnes.getId()) == 7) {
                                    mesColonnes.setBackground(getDrawable(getResources().getIdentifier(cell7, null, getPackageName())));
                                } else if (distribImg(mesColonnes.getId()) == 8) {
                                    mesColonnes.setBackground(getDrawable(getResources().getIdentifier(cell8, null, getPackageName())));
                                }
                            }

                        }
                    });
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

    /**
     * Fonction qui parcours le tableau et retourne true pour les cases minés
     * @param idVue l'ID de la case
     */
    private boolean verifyBoard(int idVue) {
        int i;
        int j;
        if (idVue > 10) {
            i = idVue / 10;
            j = idVue % 10;
            return checkMine[i][j];
        } else if(idVue == 10){
            i = 1;
            j = 0;
            return checkMine[i][j];
        } else{
            i = 0;
            j = idVue;
            return checkMine[i][j];
        }
    }

    /**
     * Fonction qui vérifie si la case possède des mines autour et renvoie un compteur qui indiquera le nombre de mines aux alentours
     * @param idVue l'id de la case
     * @return le compteur de mine autour
     */
    public int distribImg(int idVue) {
        int count = 0;
        int i;
        int j;
        if (idVue > WIDTH) {
            i = idVue / WIDTH;
            j = idVue % WIDTH;
        } else if(idVue == WIDTH){
            i = 1;
            j = 0;

        } else{
            i = 0;
            j = idVue;
        }

        if (!verifyBoard(idVue)) {
            if (i==0 && j==0){
                if(verifyBoard(idVue + 1)){
                    count++;
                }
                if(verifyBoard(idVue + WIDTH)){
                    count++;
                }
                if(verifyBoard(idVue + (WIDTH + 1))){
                    count++;
                }
            } else if(i==0 && j==WIDTH-1){
                if(verifyBoard(idVue - 1)){
                    count++;
                }
                if(verifyBoard(idVue + WIDTH)){
                    count++;
                }
                if(verifyBoard(idVue + (WIDTH - 1))){
                    count++;
                }
            } else if (i==0){
                if(verifyBoard(idVue + 1)){
                    count++;
                }
                if(verifyBoard(idVue - 1)){
                    count++;
                }
                if(verifyBoard(idVue + (WIDTH + 1))){
                    count++;
                }
                if(verifyBoard(idVue + WIDTH)){
                    count++;
                }
                if(verifyBoard(idVue + (WIDTH - 1))){
                    count++;
                }
            } else if (i == HEIGHT-1 && j == 0){
                if(verifyBoard(idVue + 1)){
                    count++;
                }
                if(verifyBoard(idVue + (WIDTH - 1))){
                    count++;
                }
                if(verifyBoard(idVue + WIDTH)){
                    count++;
                }
            } else if (i == HEIGHT - 1 && j == WIDTH - 1 ) {
                if(verifyBoard(idVue - 1)){
                    count++;
                }
                if(verifyBoard(idVue - WIDTH)){
                    count++;
                }
                if(verifyBoard(idVue - (WIDTH - 1))){
                    count++;
                }
            } else if (i == HEIGHT - 1){
                if(verifyBoard(idVue + 1)){
                    count++;
                }
                if(verifyBoard(idVue - 1)){
                    count++;
                }
                if(verifyBoard(idVue - (WIDTH + 1))){
                    count++;
                }
                if(verifyBoard(idVue - WIDTH)){
                    count++;
                }
                if(verifyBoard(idVue - (WIDTH - 1))){
                    count++;
                }
            } else if (j == 0){
                if(verifyBoard(idVue + 1)){
                    count++;
                }
                if(verifyBoard(idVue + (WIDTH + 1))){
                    count++;
                }
                if(verifyBoard(idVue + WIDTH)){
                    count++;
                }
                if(verifyBoard(idVue - (WIDTH - 1))){
                    count++;
                }
                if(verifyBoard(idVue - WIDTH)){
                    count++;
                }
            } else if ( j == WIDTH - 1){
                if(verifyBoard(idVue - 1)){
                    count++;
                }
                if(verifyBoard(idVue - (WIDTH - 1))){
                    count++;
                }
                if(verifyBoard(idVue - WIDTH)){
                    count++;
                }
                if(verifyBoard(idVue + WIDTH)){
                    count++;
                }
                if(verifyBoard(idVue + (WIDTH - 1))){
                    count++;
                }
            } else {
                if (verifyBoard(idVue + 1)) {
                    count++;
                }
                if (verifyBoard(idVue - 1)) {
                    count++;
                }
                if (verifyBoard(idVue - (WIDTH + 1))) {
                    count++;
                }
                if (verifyBoard(idVue - WIDTH)) {
                    count++;
                }
                if (verifyBoard(idVue - (WIDTH - 1))) {
                    count++;
                }
                if (verifyBoard(idVue + (WIDTH + 1))) {
                    count++;
                }
                if (verifyBoard(idVue + WIDTH)) {
                    count++;
                }
                if (verifyBoard(idVue + (WIDTH - 1))) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Fonction qui provoque un immense sentiment d'amertume et de colère, et affiche une boite d'alerte permettant de retourner au menu principal
     */
    public void defeat(){
        AlertDialog.Builder builder = new AlertDialog.Builder(GameOnActivity.this);
        builder.setTitle("DEFAITE !");
        builder.setMessage("Vous avez lamentablement échoué...");
        builder.setCancelable(false); //
        builder.setPositiveButton("Menu principal", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                NameActivity.mpInGame.stop();
                Intent intent = new Intent(GameOnActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
