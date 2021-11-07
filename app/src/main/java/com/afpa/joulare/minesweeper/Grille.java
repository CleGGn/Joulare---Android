package com.afpa.joulare.minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Grille{
    public List<Cellule> cellules;
    public int size;

    public Grille(int size){
    this.size = size;
    cellules = new ArrayList<>();

        for (int i = 0; i< size; i++){
            cellules.add(new Cellule(Cellule.VIDE));
        }
    }

}
