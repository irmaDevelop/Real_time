package com.irmadevelop.authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.irmadevelop.authentication.model.Artist;

public class MainActivity extends AppCompatActivity {

    private static final String ARTIST_NODE = "Artists"; //Artistis es el primer nodo del arbol en la bd en firebase

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // la linea anterior habilita la persistencia de firebase cuando este sin internet

        databaseReference = FirebaseDatabase.getInstance().getReference(); //obtiene android-ejemplos-ee333 (data base name)
    }

    public void createArtist(View view){
        //hace como que inserta algo y luego obtiene una key
        Artist artist = new Artist(databaseReference.push().getKey(), "Garbage", "Rock");
        //hacer que el databasereference apunte a Artist
        databaseReference.child(ARTIST_NODE).child(artist.getId()).setValue(artist);
        //Hasta aqui se hara automaticamente la insercion del dato

    }


}
