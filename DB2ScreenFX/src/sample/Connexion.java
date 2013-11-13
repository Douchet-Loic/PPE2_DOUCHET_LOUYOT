package sample;

import java.sql.*;
import java.util.ArrayList;

public class Connexion {
    //attributs
    private String identifiant;
    private String motdepasse;
    private ArrayList<Base> listeBase;
    private ArrayList<Table> listeTable;


    //accesseurs
    public String getMotdepasse() {
        return motdepasse;
    }
    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }
    public String getIdentifiant() {
        return identifiant;
    }
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }
    public ArrayList<Base> getListeBase() {
        return listeBase;
    }
    public void setListeBase(ArrayList<Base> listeBase) {
        this.listeBase = listeBase;
    }
    //constructeur
    public Connexion (String id, String mdp){
        this.identifiant = id;
        this.motdepasse = mdp;
        this.listeBase = new ArrayList<Base>();
    }
    //méthodes
    public Connection getConnection(){
        System.out.println("-------- MySQL JDBC Connection Testing ------------");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306", this.identifiant, this.motdepasse);   // requette de connection a la base de donnée avec comme variable les login de la BDD donnée par l' utilisateur
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return connection;
    }
    //méthode qui retourne un objet de type Connection pour une base donnée
    public Connection getConnection(Base b){
        System.out.println("-------- MySQL JDBC Connection Testing ------------");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
        }
        System.out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+b.getNomBase(), this.identifiant, this.motdepasse);

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return connection;
    }
    //methode qui instancie toutes les bases du SGBD
    public void chargerBase(){
        Connection cnx = this.getConnection();
        try{
            DatabaseMetaData dmd = cnx.getMetaData();
            //récupération des informations
            String catalogTerm = dmd.getCatalogTerm();
            ResultSet resultat = dmd.getCatalogs();
            //affichage des informations
            System.out.println("Terme du SGBD pour catalogue = "+catalogTerm);
            while(resultat.next()){
                String nomCatalog = resultat.getString("TABLE_CAT");
                int i = resultat.getRow();
                //System.out.println(catalogTerm+" "+resultat.getRow()+ " = "+nomCatalog);
                Base b = new Base(resultat.getRow(), nomCatalog);
                this.listeBase.add(b);
            }//fin while
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //méthode qui ajoute instance toute les tables d'une base
    public void getLesTablesDuneBase(Base b){
        Connection cnx = this.getConnection();
        Table d;
        int j=0;
        System.out.println(b.getNomBase());
        try{
            //on récupère les métadonnées à partir de la connexion
            DatabaseMetaData dmd = cnx.getMetaData();
            //récupération des informations
            ResultSet tables = dmd.getTables(b.getNomBase(),null,"%",null);
            //affichage des informations
            while(tables.next()){
                for(int i=0; i<tables.getMetaData().getColumnCount();i++){
                    String nomColonne = tables.getMetaData().getColumnName(i+1);
                    Object valeurColonne = tables.getObject(i+1);
                }//fin for
                j++;
                String nomTable = tables.getString("TABLE_NAME");
                d = new Table(j, nomTable);
                b.ajouterTable(d);
                //this.listeTable.add(d);
            } //fin while
            //b.setListeTable(this.listeTable);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String[] getNomsColonnes(ResultSet resultat) throws SQLException{
        ResultSetMetaData metadata = resultat.getMetaData();
        String[] noms = new String[metadata.getColumnCount()];
        for(int i = 0; i < noms.length; i++){
            String nomColonne = metadata.getColumnName(i+1);
            noms[i] = nomColonne;
        }
        return noms;
    }
    public void afficheNomColonnes(Table t, Base b) throws SQLException{
        Connection cnx = this.getConnection(b);

        try{
            String sql = "SELECT * FROM "+t.getNom();
            Statement statement = cnx.createStatement();
            ResultSet resultat = statement.executeQuery(sql);
            String[] noms = getNomsColonnes(resultat);
            for(int i = 0; i < noms.length; i++){
                System.out.println(noms[i]);
                Colonne c = new Colonne(i, noms[i]);
                t.ajouterColonne(c);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void getLigneColonnes(Table t, Base b, Colonne c) throws SQLException{
        Connection cnx = this.getConnection(b);
        try{
            String sql = "SELECT "+c.getNomcolonne()+" FROM "+t.getNom();
            Statement statement = cnx.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                String nomL = rs.getString(c.getNomcolonne());
                Ligne l = new Ligne(nomL);
                c.ajouterLigne(l);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
