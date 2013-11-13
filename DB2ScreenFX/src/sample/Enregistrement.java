package sample;

import java.util.ArrayList;

public class Enregistrement {
    //attributs
    private String nomChamp;
    private String typeChamp;
    private String nomColonne;
    private String lesLignes;
    public String getNomChamp() {
        return nomChamp;
    }
    public void setNomChamp(String nomChamp) {
        this.nomChamp = nomChamp;
    }
    public String getTypeChamp() {
        return typeChamp;
    }
    public void setTypeChamp(String typeChamp) {
        this.typeChamp = typeChamp;
    }
    public String getNomColonne() {
        return nomColonne;
    }
    public void setNomColonne(String nomColonne) {
        this.nomColonne = nomColonne;
    }
    public String getLesLignes() {
        return lesLignes;
    }
    public void setLesLignes(String lesLignes) {
        this.lesLignes = lesLignes;
    }
    //Constructeur
    public Enregistrement(String nomChamp, String typeChamp, String nomColonne, String lesLignes) {
        this.nomChamp = nomChamp;
        this.typeChamp = typeChamp;
        this.nomColonne = nomColonne;
        this.lesLignes = lesLignes;
    }
}
