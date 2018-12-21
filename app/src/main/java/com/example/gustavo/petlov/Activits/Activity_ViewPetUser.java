package com.example.gustavo.petlov.Activits;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


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

    private Animals petsSel;


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

        listAnimals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                petsSel = adapterAnimals.getItem(i);
                Bundle data = new Bundle();
                data.putString("name", petsSel.getName());
                data.putString("age", petsSel.getAge());
                data.putString("weigth", petsSel.getWeight());
                data.putString("race", petsSel.getRace());
                data.putString("img", petsSel.getPhotoPerfil());
                data.putString("id", petsSel.getId());


                Intent editar = new Intent(Activity_ViewPetUser.this, Activity_EditorPet.class);
                editar.putExtras(data);
                startActivity(editar);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Activity_ViewPetUser.this, Activity_MenuUser.class);
        startActivity(intent);
    }
}
