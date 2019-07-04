package com.example.rijskviewer.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rijskviewer.R;
import com.example.rijskviewer.adapters.ArtworkListAdapter;
import com.example.rijskviewer.beans.ArtWork;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArtworkListActivity extends AppCompatActivity {
    private String currentAuthor;
    private String URL="https://www.rijksmuseum.nl/api/en/collection?key=QKkH603N&format=JSON&principalMaker=Marius Bauer";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<ArtWork> artWorkList;
    private JSONArray artworkList;
    private JsonObjectRequest jsonObjectRequest;
    private android.support.v7.widget.RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artwork_list);

        currentAuthor = getIntent().getExtras().getString("current_author") ;
        artWorkList= new ArrayList<>();
        jsonRequest("https://www.rijksmuseum.nl/api/en/collection?key=QKkH603N&format=JSON&ps=100&principalMaker="+currentAuthor);

    }

    public void jsonRequest(String url){
        // request=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("url est "+jsonObjectRequest);
                        for (int j = 0; j < response.length(); j++) {

                            try {
                                System.out.println(response.getJSONArray("artObjects"));
                                artworkList = response.getJSONArray("artObjects");
                                //JSONObject jsonObject=null;
                                for (int i = 0; i < artworkList.length(); i++) {
                                    JSONObject artwork = artworkList.getJSONObject(i);
                                    if (!artwork.get("webImage").equals(null) && !artwork.getJSONObject("webImage").getString("url").equals(null))
                                        System.out.println(i + " : " + artwork.getJSONObject("webImage").getString("url"));
                                    else {
                                        System.out.println("webImage or URL is null deleting from array");
                                        artworkList.remove(i);
                                    }
                                    ArtWork newArtwork = new ArtWork();
                                    newArtwork.setTitle(artwork.getString("title"));
                                    newArtwork.setLongTitle(artwork.getString("longTitle"));
                                    newArtwork.setPrincipalMaker(artwork.getString("principalOrFirstMaker"));
                                    newArtwork.setImage(artwork.getJSONObject("webImage").getString("url"));
                                    artWorkList.add(newArtwork);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        setuprecyclerview(artWorkList);
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });

        requestQueue= Volley.newRequestQueue(ArtworkListActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void setuprecyclerview(List<ArtWork> listeAnime) {

        android.support.v7.widget.RecyclerView recyclerView = findViewById(R.id.artworkListRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        ArtworkListAdapter artworkListAdapter = new ArtworkListAdapter(this,listeAnime);
        recyclerView.setAdapter(artworkListAdapter);
    }
}
