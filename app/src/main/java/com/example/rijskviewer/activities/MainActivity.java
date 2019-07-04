package com.example.rijskviewer.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.rijskviewer.Interfaces.VolleyCallback;
import com.example.rijskviewer.ListFragmentCollections;
import com.example.rijskviewer.R;
import com.example.rijskviewer.adapters.RecyclerViewAdapter;
import com.example.rijskviewer.api.MuseumApi;
import com.example.rijskviewer.beans.ArtWork;
import com.example.rijskviewer.beans.Artist;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private MuseumApi museumApi;
    private List<Artist> artWorkList = new ArrayList<>();


    private String artistName = null;
    private Boolean net = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        museumApi = new MuseumApi(MainActivity.this);

        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);*/
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();

        if (!net) {
            museumApi.readFromJson(callVolleyCallback(artistName), artistName);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Callback pour récupérer la liste des oeuvres d'un artiste
     * @return le callback
     */
    private VolleyCallback callVolleyCallback(String artistName){
        if(artistName != null){
            System.out.println("Récupération de la liste des oeuvres pour l'artiste " + artistName);
            return new VolleyCallback() {
                @Override
                public void onSuccess(final JSONObject response) {
                    museumApi.getArtWorkByArtist(artWorkList, response);

                    net = true;
                    toggleLoader();
                }
            };
        }

        System.out.println("Récupération de la liste des artistes");
        return new VolleyCallback() {
            @Override
            public void onSuccess(final JSONObject response) {
                museumApi.getArtists(artWorkList, response);
//                museumApi.getArtWorkByArtist(artWorkList, response);

                net = true;
                toggleLoader();
            }
        };
    }

    public void toggleLoader(){
        ProgressBar loader = (ProgressBar) findViewById(R.id.loader);
        System.out.println("loader.getVisibility() : " + loader.getVisibility());

        if(loader.getVisibility() == View.GONE){
            loader.setVisibility(View.VISIBLE);
        } else {
            loader.setVisibility(View.GONE);
        }
    }

    public void setUpdateRecyclerView(List<Artist> artWorkList) {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewid);
        System.out.println("test");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(this, artWorkList);
        recyclerView.setAdapter(myAdapter);
    }
}
