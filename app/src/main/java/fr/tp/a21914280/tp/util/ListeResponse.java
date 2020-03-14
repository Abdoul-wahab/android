package fr.tp.a21914280.tp.util;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import fr.tp.a21914280.tp.R;
import fr.tp.a21914280.tp.model.Annonce;

public class ListeResponse {

    @SerializedName("success")
    @Expose
    private boolean succes;

    @SerializedName("response")
    @Expose
    private ArrayList<Annonce> list;

    public ListeResponse(boolean succes, ArrayList<Annonce> list) {
        this.succes = succes;
        this.list = list;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    public void setList(ArrayList<Annonce> list) {
        this.list = list;
    }

    public boolean isSucces() {
        return succes;
    }

    public ArrayList<Annonce> getList() {
        return list;
    }
}


