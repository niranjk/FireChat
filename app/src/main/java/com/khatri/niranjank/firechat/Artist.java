package com.khatri.niranjank.firechat;

/**
 * Created by niranjank on 18/07/17.
 */

public class Artist {
    String artistId;
    String artistName;
    String artistGenre;

    /*
    * this is a blank constructor is used while reading a value*/
    public Artist(){

    }

    public Artist(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    public String getArtistGenre() {
        return artistGenre;
    }

    public String getArtistId() {
        return artistId;
    }


    public String getArtistName() {
        return artistName;
    }

}
