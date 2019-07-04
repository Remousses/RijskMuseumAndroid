package com.example.rijskviewer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rijskviewer.activities.ArtWorkActivity;
import com.example.rijskviewer.beans.ArtWork;
import com.example.rijskviewer.R;

import org.w3c.dom.Text;

import java.util.List;

public class ArtworkListAdapter extends RecyclerView.Adapter<ArtworkListAdapter.MyViewHolder> {

    private Context mContext;
    private List<ArtWork> mData;
    private RequestOptions option;

    public ArtworkListAdapter(Context mContext, List<ArtWork> mData) {
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
        view=layoutInflater.inflate(R.layout.artwork_row_item, viewGroup,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        // ici, quand on appuie sur un element de la liste, il déclenche l'action vers le details de l element
        viewHolder.viewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, ArtWorkActivity.class);
                i.putExtra("artwork_author",mData.get(viewHolder.getAdapterPosition()).getAuthor());
                i.putExtra("artwork_title",mData.get(viewHolder.getAdapterPosition()).getTitle());
                i.putExtra("artwork_image",mData.get(viewHolder.getAdapterPosition()).getImage());
                i.putExtra("artwork_desc",mData.get(viewHolder.getAdapterPosition()).getLongTitle());

                mContext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int i) {

        holder.tvTitle.setText(mData.get(i).getTitle());
        holder.tvAuthor.setText((mData.get(i).getAuthor()));
        //charger l'image de l'url et la stocker dans l'imageview grâce à Glide

        Glide.with(mContext).load(mData.get(i).getImage()).apply(option).into(holder.imgThumbnail);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvAuthor;
        ImageView imgThumbnail;
        LinearLayout viewContainer;


        public MyViewHolder(View itemView){
            super(itemView);
            // on instancie les objets en récupérant les id du fichier xml anim_row_item
            viewContainer = itemView.findViewById(R.id.artwork_container);
            tvTitle = itemView.findViewById(R.id.artworkTitle);
            tvAuthor = itemView.findViewById(R.id.artworkAuthor);
            imgThumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
}