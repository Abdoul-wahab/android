package fr.tp.a21914280.tp.util;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("success")
    @Expose
    private boolean succes;


    @SerializedName("response")
    @Expose
    private String response;

    public Response(Boolean succes,String response) {
        this.succes = succes;
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }
}
