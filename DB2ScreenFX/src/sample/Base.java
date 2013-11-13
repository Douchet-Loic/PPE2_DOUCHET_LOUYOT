package sample;

import java.util.ArrayList;

public class Base {
    private int numBase;
    private String nomBase;

    private ArrayList<Table> listeTable;
    public int getNumBase() {
        return numBase;
    }
    public void setNumBase(int numBase) {
        this.numBase = numBase;
    }
    public String getNomBase() {
        return nomBase;
    }
    public void setNomBase(String nomBase) {
        this.nomBase = nomBase;
    }
    public ArrayList<Table> getListeTable() {
        return listeTable;
    }
    public void setListeTable(ArrayList<Table> listeTable) {
        this.listeTable = listeTable;
    }
    public Base(int numbase, String nombase){
        this.numBase = numbase;
        this.nomBase = nombase;
        this.listeTable = new ArrayList<Table>();
    }
    public void afficherTable(){
        for (int i = 0; i < this.listeTable.size(); i++){
            Table table = this.listeTable.get(i);
            System.out.println(table.toString());
        }
    }
    public void ajouterTable(Table t){
        this.listeTable.add(t);
    }
    public String toString() {
        return ("Numero base: "+this.numBase+" , nom base: "+this.nomBase);
    }
}
