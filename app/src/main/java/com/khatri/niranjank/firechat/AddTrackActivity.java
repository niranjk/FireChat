package com.khatri.niranjank.firechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTrackActivity extends AppCompatActivity {

    TextView textViewArtistName;
    EditText editTextTrackName;
    SeekBar seekBarRating;
    ListView listViewTracks;
    Button buttonAddTrack;


    // first create the database reference object
    DatabaseReference databaseTracks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        textViewArtistName = (TextView) findViewById(R.id.addtrack_artistname);
        editTextTrackName = (EditText) findViewById(R.id.addtrack_trackname);
        seekBarRating = (SeekBar) findViewById(R.id.addtrack_seekBarRating);
        listViewTracks = (ListView) findViewById(R.id.addtrack_listview);
        buttonAddTrack = (Button) findViewById(R.id.addtrack_addTrack);

        Intent intent = getIntent();

        String id = intent.getStringExtra(MainActivity.ARTIST_ID);
        String name = intent.getStringExtra(MainActivity.ARTIST_NAME);

        textViewArtistName.setText(name);

        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);


        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrack();
            }
        });
    }

    private void saveTrack() {
        String trackName = editTextTrackName.getText().toString().trim();
        int rating = seekBarRating.getProgress();

        if(!TextUtils.isEmpty(trackName)){
            String id = databaseTracks.push().getKey();
            Track track = new Track(id, trackName, rating);

            databaseTracks.child(id).setValue(track);
            Toast.makeText(this,"tracks saved sucessfully",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"track name should not be empty", Toast.LENGTH_LONG).show();
        }

    }
}