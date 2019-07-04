package com.example.rijskviewer.activities;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rijskviewer.R;

public class ArtWorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork);

        // cacher les elements par défauts
        //  getSupportActionBar().hide();

        // récupération de données
        String author = getIntent().getExtras().getString("artwork_author") ;
        String title  = getIntent().getExtras().getString("artwork_title");
        String desc  = getIntent().getExtras().getString("artwork_desc");
        String imageUrl = getIntent().getExtras().getString("artwork_image") ;

        //ini Views

        CollapsingToolbarLayout collapsingToolbarLayout=findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tvTitle = findViewById(R.id.aa_anime_title);
        TextView tvAuthor = findViewById(R.id.aa_maker);
        TextView tvDesc = findViewById(R.id.aa_description);
        final ImageView image = findViewById(R.id.aa_thumbnail);


        // on met les valeurs dans les textview adéquat

        tvTitle.setText(title);
        tvAuthor.setText(author);
        tvDesc.setText(desc);
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


        // concernant l image il faut Glide

        Glide.with(this).load(imageUrl).apply(requestOptions).into(image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(600, 800);
                image.setLayoutParams(layoutParams);
            }
        });

    }
}