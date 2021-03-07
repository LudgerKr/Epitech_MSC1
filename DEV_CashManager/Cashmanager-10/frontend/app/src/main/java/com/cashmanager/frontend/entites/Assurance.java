package com.cashmanager.frontend.entites;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Assurance {
    private String vehiculeID;
    private String 	compagnieAssuranceID;
    private String libelleAssurance;
    private String typeAssurance;
    private String dateSouscription;
    private String dateValidite;
    private String primeAssurance;

    @SerializedName("assurance")
    private String text;

    public String getVehiculeID() {
        return vehiculeID;
    }

    public String getCompagnieAssuranceID() {
        return compagnieAssuranceID;
    }

    public String getLibelleAssurance() {
        return libelleAssurance;
    }

    public String getTypeAssurance() {
        return typeAssurance;
    }

    public String getDateSouscription() {
        return dateSouscription;
    }

    public String getDateValidite() {
        return dateValidite;
    }

    public String getPrimeAssurance() {
        return primeAssurance;
    }

    public String getText() {
        return text;
    }

    public Assurance(String vehiculeID, String compagnieAssuranceID, String libelleAssurance, String typeAssurance, String dateSouscription, String dateValidite, String primeAssurance) {
        this.vehiculeID = vehiculeID;
        this.compagnieAssuranceID = compagnieAssuranceID;
        this.libelleAssurance = libelleAssurance;
        this.typeAssurance = typeAssurance;
        this.dateSouscription = dateSouscription;
        this.dateValidite = dateValidite;
        this.primeAssurance = primeAssurance;
    }
}
