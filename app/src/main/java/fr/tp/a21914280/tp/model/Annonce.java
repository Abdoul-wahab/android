package fr.tp.a21914280.tp.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Annonce implements Serializable {
    private String id;
    private String titre;
    private String description;
    private String pseudo;
    private int prix;
    private String emailContact;
    private String telContact;
    private String ville;
    private String cp; //Code postal de la ville
    private List<String> images;
    private int date;


    public Annonce(String title, String description, String pseudo, int price, String emailContact, String telContact, String ville, String cp) {
        this.titre = title;
        this.description = description;
        this.pseudo = pseudo;
        this.prix = price;
        this.emailContact = emailContact;
        this.telContact = telContact;
        this.ville = ville;
        this.cp = cp;
    }


    public String getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getPrix() {
        return prix;
    }

    public String getEmailContact() {
        return emailContact;
    }

    public String getTelContact() {
        return telContact;
    }

    public String getVille() {
        return ville;
    }

    public String getCp() {
        return cp;
    }

    public List<String> getImages() {
        return images;
    }

    public int getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitre(String title) {
        this.titre = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact;
    }

    public void setTelContact(String telContact) {
        this.telContact = telContact;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void setDate(int date) {
        this.date = date;
    }


}




























