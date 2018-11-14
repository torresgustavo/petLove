package com.example.gustavo.petlov.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.example.gustavo.petlov.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Adapter.AnimalsAdapter;
import Entities.Animals;
import fireBaseConfiguration.FireBaseConfig;

public class Activity_ViewPetUser extends AppCompatActivity {

    private ListView listAnimals;
    private ArrayAdapter<Animals> adapterAnimals;
    private ArrayList<Animals> pets;
    private DatabaseReference firebase;
    private FirebaseAuth userFireBase = FireBaseConfig.getFireBaseAutentication();
    private ValueEventListener valueEventListenerAnimals;
    private Button btnVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pet_user);

        pets = new ArrayList<>();
        listAnimals = findViewById(R.id.listViewAnimals);
        adapterAnimals = new AnimalsAdapter(this, pets);

        listAnimals.setAdapter(adapterAnimals);

        firebase = FireBaseConfig.getFireBase().child("usuarios").child(userFireBase.getCurrentUser().getUid()).child("pets");

        valueEventListenerAnimals = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pets.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    Animals animalsUser = dados.getValue(Animals.class);

                    pets.add(animalsUser);
                }

                adapterAnimals.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        btnVoltar = findViewById(R.id.btn_Voltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarMenu();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerAnimals);
    }

    @Override
    protected void onStop() {
        firebase.removeEventListener(valueEventListenerAnimals);
        super.onStop();
    }

    private void voltarMenu(){
        Intent intent = new Intent(Activity_ViewPetUser.this, Activity_Principal.class);
        startActivity(intent);
    }
}
