package sample;

import java.util.ArrayList;


public class Colonne {
    private int idcolonne;
    private String nomcolonne;
    private ArrayList<Ligne> lesLignes;

    //acccesseurs
    public int getIdcolonne() {
        return idcolonne;
    }
    public void setIdcolonne(int idcolonne) {
        this.idcolonne = idcolonne;
    }
    public String getNomcolonne() {
        return nomcolonne;
    }
    public void setNomcolonne(String nomcolonne) {
        this.nomcolonne = nomcolonne;
    }
    public ArrayList<Ligne> getLesLignes() {
        return lesLignes;
    }
    public void setLesLignes(ArrayList<Ligne> lesLignes) {
        this.lesLignes = lesLignes;
    }
    //constructeur
    public Colonne(int unid, String unnom){
        this.idcolonne = unid;
        this.nomcolonne = unnom;
        this.lesLignes = new ArrayList<Ligne>();
    }
    public String toString() {
        return ("Id colonne :"+this.idcolonne+"Nom colonne : "+this.nomcolonne);
    }
    public void ajouterLigne(Ligne l){
        this.lesLignes.add(l);
    }
}

