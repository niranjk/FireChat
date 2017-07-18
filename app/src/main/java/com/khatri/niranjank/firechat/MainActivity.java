package com.khatri.niranjank.firechat;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME = "artistname";
    public static final String ARTIST_ID = "artistid";


    EditText editText;
    Button button;
    Spinner spinner;

    DatabaseReference databaseArtist;

    ListView listViewArtist;

    List<Artist> artistList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * if you dont pass the parameter you will get the root node of the json tree
        * */
        databaseArtist = FirebaseDatabase.getInstance().getReference("artists");


        editText = (EditText) findViewById(R.id.main_edit);
        button = (Button) findViewById(R.id.main_button1);
        spinner = (Spinner) findViewById(R.id.main_spinnerGenre);


        listViewArtist = (ListView) findViewById(R.id.listview);
        artistList = new ArrayList<>();


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addArtist();
            }
        });

        listViewArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Artist artist = artistList.get(position);
                Intent intent = new Intent(getApplicationContext(), AddTrackActivity.class );
                intent.putExtra(ARTIST_ID, artist.getArtistId());
                intent.putExtra(ARTIST_NAME, artist.getArtistName());

                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseArtist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artistList.clear();

                for(DataSnapshot artistSnapshot: dataSnapshot.getChildren()){
                    Artist artist = artistSnapshot.getValue(Artist.class);

                    artistList.add(artist);
                }

                ArtistListAdapter adapter = new ArtistListAdapter(MainActivity.this,artistList);
                listViewArtist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addArtist() {
        String name = editText.getText().toString().trim();
        String genre = spinner.getSelectedItem().toString();

        if(!TextUtils.isEmpty(name)){

            /*
            * create unique id for the artist
            * use the push method of firebase database
            * */
            String id = databaseArtist.push().getKey();
            Artist artist = new Artist(id,name, genre);

            databaseArtist.child(id).setValue(artist);

            Toast.makeText(this,"Artist Added.",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this,"You should enter a name.", Toast.LENGTH_LONG).show();
        }
    }



}
