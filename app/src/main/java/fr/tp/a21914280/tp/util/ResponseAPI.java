package fr.tp.a21914280.tp.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import fr.tp.a21914280.tp.model.Annonce;

public class ResponseAPI {

    @SerializedName("success")
    @Expose
    private boolean succes;


    @SerializedName("response")
    @Expose
    private Annonce annonce;

    public ResponseAPI(Boolean succes,Annonce result) {
        this.succes = succes;
        this.annonce = result;
    }

    public Annonce getAnnonce() {
        return annonce;
    }


    public void setAnnonce(Annonce annonce) {
        this.annonce = annonce;
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }
}
