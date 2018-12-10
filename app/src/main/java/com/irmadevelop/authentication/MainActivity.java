package com.irmadevelop.authentication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.irmadevelop.authentication.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ARTIST_NODE = "Artists"; //Artistis es el primer nodo del arbol en la bd en firebase
    private static final String TAG = "MainActivity";
    private DatabaseReference databaseReference;

    private ListView lstArtist;
    private ArrayAdapter arrayAdapter;
    private List<String> artistNames;
    private List<Artist> artists; //Array, lista de artistas. Se llena tal cual se llena el de strings


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstArtist = (ListView) findViewById(R.id.lstArtist);
        artistNames = new ArrayList<>();
        artists = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, artistNames); //en lugar de android.R.layout.simple_list_item_1 de ir un textview, pero se obta por el recurso de google.
        lstArtist.setAdapter(arrayAdapter);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true); //habilita la persistencia de firebase cuando este sin internet
        databaseReference = FirebaseDatabase.getInstance().getReference(); //obtiene android-ejemplos-ee333 (data base name)

        databaseReference.child(ARTIST_NODE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                artistNames.clear();
                artists.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Artist artist = snapshot.getValue(Artist.class);
                        Log.w(TAG, "Artist Name: " + artist.getName());
                        artistNames.add(artist.getName());
                            artists.add(artist);

                    }
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //implementar el evento en el que se mantienne presionado largo rato la opcion
        lstArtist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                //era i, lo cambio por position ya que contien la posicin del elemento de la lista.
                String idArtist = artists.get(position).getId();
                artists.remove(position);
                artistNames.remove(position);
                //Se manejan dos listas para no tener que manejar un custom adapter o adaptador personalizado
                //Hasta ahora solo hemos reseteado las listas
                //toda decirle a firebase que anule ese objeto en particular referenciando a la base de datos.
                databaseReference.child(ARTIST_NODE).child(idArtist).removeValue();

                return true;
            }
        });
    }


    public void createArtist(View view){
        //hace como que inserta algo y luego obtiene una key
        Artist artist = new Artist(databaseReference.push().getKey(), "Garbage", "Rock");
        //hacer que el databasereference apunte a Artist
        databaseReference.child(ARTIST_NODE).child(artist.getId()).setValue(artist);
        //Hasta aqui se hara automaticamente la insercion del dato

    }


}
