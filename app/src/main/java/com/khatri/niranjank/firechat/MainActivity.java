package com.khatri.niranjank.firechat;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {


    EditText editText;
    Button button;
    Spinner spinner;

    DatabaseReference databaseArtist;



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


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addArtist();
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
