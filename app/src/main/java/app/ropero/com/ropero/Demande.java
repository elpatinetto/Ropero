package app.ropero.com.ropero;

import java.util.ArrayList;

/**
 * Created by noellodou on 03/08/2017.
 */

public class Demande {
    private String title;
    private ArrayList<Courses> listCourses = new ArrayList<Courses>();
    private String[] listUsers;
    private int statut = 0;
    private double valeurDemande;


    public Demande(){

    }

    public Demande(String titre){
        this.title = titre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Courses> getListCourses() {
        return listCourses;
    }

    public void setListCourses(ArrayList<Courses> listCourses) {
        this.listCourses = listCourses;
    }

    public String[] getListUsers() {
        return listUsers;
    }

    public void setListUsers(String[] listUsers) {
        this.listUsers = listUsers;
    }

    public int getStatut() {
        return statut;
    }

    public void setStatut(int statut) {
        this.statut = statut;
    }

    public double getValeurDemande() {
        return valeurDemande;
    }

    public void setValeurDemande(double valeurDemande) {
        this.valeurDemande = valeurDemande;
    }
}
