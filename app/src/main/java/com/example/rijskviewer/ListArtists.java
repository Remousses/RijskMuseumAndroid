package com.example.rijskviewer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListArtists extends Fragment {
    private TextView textView;

    public ListArtists() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_list_artists, container, false);
        textView = view.findViewById(R.id.txtDisplay);
        String message = getArguments().getString("message");
        textView.setText(message);
        return view;
    }

}
