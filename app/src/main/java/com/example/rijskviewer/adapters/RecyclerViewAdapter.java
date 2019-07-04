package com.example.rijskviewer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rijskviewer.activities.ArtWorkActivity;
import com.example.rijskviewer.activities.ArtworkListActivity;
import com.example.rijskviewer.beans.ArtWork;
import com.example.rijskviewer.R;
import com.example.rijskviewer.beans.Artist;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Artist> mData;
    private RequestOptions option;


    public RecyclerViewAdapter(Context mContext, List<Artist> mData) {
        this.mContext = mContext;
        // elle concerne la liste affiché dans Recycler View
        this.mData = mData;
        // request option pour Glide
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        System.out.println("RecyclerViewAdapter");
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        // pour lire le xml où il y a la liste
        view=layoutInflater.inflate(R.layout.artist_row_item, viewGroup,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        // ici, quand on appuie sur un element de la liste, il déclenche l'action vers le details de l element
        viewHolder.viewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ArtworkListActivity.class);
                i.putExtra("current_author",mData.get(viewHolder.getAdapterPosition()).getAuthor());

                mContext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int i) {
        holder.artistName.setText(mData.get(i).getAuthor());
        holder.artworkNumber.setText(mData.get(i).getArtWorkNumberString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView artistName;
        TextView artworkNumber;
        LinearLayout viewContainer;


        public MyViewHolder(View itemView){
            super(itemView);
            // on instancie les objets en récupérant les id du fichier xml anim_row_item
            viewContainer = itemView.findViewById(R.id.container);
            artistName = itemView.findViewById(R.id.artistName);
            artworkNumber = itemView.findViewById(R.id.artworkNumber);

        }
    }
}