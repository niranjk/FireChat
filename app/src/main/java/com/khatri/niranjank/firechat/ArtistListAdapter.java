package com.khatri.niranjank.firechat;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by niranjank on 18/07/17.
 */

public class ArtistListAdapter extends ArrayAdapter<Artist>{

    private Activity context;
    private List<Artist> artistList;

    public ArtistListAdapter(Activity context, List<Artist> artistsList) {
        super(context, R.layout.listitem, artistsList);
        this.context = context;
        this.artistList = artistsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listitem, null,true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.list_item_name);
        TextView textViewGenre = (TextView) listViewItem.findViewById(R.id.list_item_genre);

        Artist artist = artistList.get(position);

        textViewName.setText(artist.getArtistName());
        textViewGenre.setText(artist.getArtistGenre());

        return listViewItem;
    }


}
