package com.irmadevelop.authentication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        // la linea anterior habilita la persistencia de firebase cuando este sin internet

        databaseReference = FirebaseDatabase.getInstance().getReference(); //obtiene android-ejemplos-ee333 (data base name)


    }
}
