package sample;


import java.util.ArrayList;

public class Table {
    private int id;
    private String nom;
    private ArrayList<Colonne> listeColonne;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public ArrayList<Colonne> getListeColonne() {
        return listeColonne;
    }
    public void setListeColonne(ArrayList<Colonne> listeColonne) {
        this.listeColonne = listeColonne;
    }
    public Table(int unid, String unnom){
        this.id = unid;
        this.nom = unnom;
        this.listeColonne = new ArrayList<Colonne>();
    }
    public String toString() {
        return ("Le numero de la table: "+this.id+" , le nomde la table: "+this.nom);    //To change body of overridden methods use File | Settings | File Templates.
    }
    public void ajouterColonne(Colonne c){
        this.listeColonne.add(c);
    }
}
