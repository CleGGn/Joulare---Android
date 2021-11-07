package com.afpa.joulare.minesweeper;

public class Cellule {

    public static final int MINE = -1;
    public static final int VIDE = 0;

    private int value;
    private boolean revealed;
    private boolean flagged;

    public Cellule(int value){
        this.value = value;
        this.revealed = false;
        this.flagged = false;
    }

    public int getValue(){
        return value;
    }

    public boolean getFlag() {
        return flagged;
    }

    public void setFlag(boolean flag) {
         flagged = flag;
    }

    public boolean getReveal() {
        return revealed;
    }

    public void setReveal(boolean flag) {
        revealed = flag;
    }
}
