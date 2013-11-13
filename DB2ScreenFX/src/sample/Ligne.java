package sample;

public class Ligne {
    //attributs
    private String nom;
    //accesseurs
    public String GetNom(){
        return this.nom;
    }
    public void SetNom(String nom){
        this.nom = nom;
    }
    //constructeur
    public Ligne(String unnom){
        this.nom = unnom;
    }
    //méthode
    public String toString(){
        return this.nom;
    }
}
