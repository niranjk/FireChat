package com.khatri.niranjank.firechat;

import android.app.Activity;
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

public class TrackListAdapter extends ArrayAdapter<Track>{
    private Activity context;
    private List<Track> tracks;

    public TrackListAdapter(Activity context, List<Track> tracks) {
        super(context, R.layout.listitem_tracklist, tracks);
        this.context = context;
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.listitem_tracklist, null,true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.list_item_trackname);
        TextView textViewRating = (TextView) listViewItem.findViewById(R.id.list_item_rating);

        Track track = tracks.get(position);

        textViewName.setText(track.getTrackName());
        textViewRating.setText(String.valueOf(track.getTrackRating()));

        return listViewItem;
    }

}
