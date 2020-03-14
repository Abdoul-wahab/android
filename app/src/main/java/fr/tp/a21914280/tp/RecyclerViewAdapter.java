package fr.tp.a21914280.tp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;


import de.hdodenhof.circleimageview.CircleImageView;
import fr.tp.a21914280.tp.model.Annonce;
import fr.tp.a21914280.tp.util.ItemClickListner;


class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.AnnonceViewHolder> {

    protected List<Annonce> list;
    protected Context mContext;
    private ItemClickListner onItemCick;

    public RecyclerViewAdapter( Context mContext, List<Annonce> list, ItemClickListner onItemCick) {
        this.list = list;
        this.mContext = mContext;
        this.onItemCick = onItemCick;
    }

    @NonNull
    @Override
    public AnnonceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup,false);
        AnnonceViewHolder holder = new AnnonceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnnonceViewHolder annonceViewHolder, int position) {
        Annonce annonce = list.get(position);
        Log.i("AnnoncesList", annonce.getTitre());

        annonceViewHolder.bind(annonce);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // contenueur de vues de Personnes
    public class AnnonceViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView prix;
        TextView ville;
        TextView itemTitre;
        ConstraintLayout parentLayout;

        public AnnonceViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            itemTitre = itemView.findViewById(R.id.titre);
            prix = itemView.findViewById(R.id.prix);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            ville =itemView.findViewById(R.id.ville);

            if(itemView != null){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemCick.itemClick(v, getAdapterPosition(),image);
                }
            });}

        }

        public void bind(Annonce annonce){
            if(annonce.getImages() !=null) {
                if(annonce.getImages().size() != 0){
                    int i = (new Random()).nextInt(annonce.getImages().size());
                        Glide.with(mContext)
                                .asBitmap()
                                .load(annonce.getImages().get(i))
                                .into(this.image);
                }else{
                    Glide.with(mContext)
                            .asBitmap()
                            .load(R.drawable.ic_menu_camera)
                            .into(image);

                }
            }else{
                Glide.with(mContext)
                        .asBitmap()
                        .load(R.drawable.ic_menu_camera)
                        .into(image);

            }
            Log.i("AnnoncesList", annonce.getTitre());
            this.itemTitre.setText(annonce.getTitre());
            this.prix.setText(String.valueOf("Prix :" + annonce.getPrix())+ " €");
            this.ville.setText("Ville :" + annonce.getVille());

        }
    }

}
