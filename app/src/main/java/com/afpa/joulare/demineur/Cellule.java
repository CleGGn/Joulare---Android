package com.afpa.joulare.demineur;

public class Cellule {

    private boolean mine;
    private boolean revealed;
    private boolean flagged;

    public Cellule(Boolean mine){
        this.mine = mine;
        this.revealed = false;
        this.flagged = false;
    }

    public boolean getMine(){
        return mine;
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
