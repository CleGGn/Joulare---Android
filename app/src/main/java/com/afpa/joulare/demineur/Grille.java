package com.afpa.joulare.demineur;

import android.content.Context;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.afpa.joulare.R;

public class Grille extends LinearLayout {

    int count = 0;
    int width = 10;
    int height = 12;
    int mineAmount = 10;
    LinearLayout monLayout = findViewById(R.id.grille);
    public Cellule[][] grille = new Cellule[height][width];

    // Constructeur
    public Grille(Context context) {
        super(context);
       double distribution = mineAmount / (width * height);
        for(int a = 0; a <height; a++) {
            for (int b = 0; b < width; b++) {
                if (Math.random() < distribution && mineAmount > 0) {
                    grille[a][b] = new Cellule(true);
                    mineAmount--;
                } else {
                    grille[a][b] = new Cellule(false);
                }
            }
        }

        for (int i = 0; i < height ; i++){
            ImageView mesRangs = new ImageView(monLayout.getContext());
            for (int j = 0 ; j < width ; j++){
                ImageView mesColonnes = new ImageView(mesRangs.getContext());
                mesColonnes.setImageResource(R.drawable.cell);
                count++;
                mesColonnes.setId(count);
                monLayout.addView(mesColonnes);
            }
        }
    }
}
