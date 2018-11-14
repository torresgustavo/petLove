package com.example.gustavo.petlov.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import com.example.gustavo.petlov.R;

import fireBaseConfiguration.FireBaseConfig;

public class Activity_Principal extends AppCompatActivity {

    FirebaseAuth userFireBase;
    Button btn_RegisterPet, btn_ViewPet;
    private FirebaseAuth autentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        userFireBase = FireBaseConfig.getFireBaseAutentication();
        Toast.makeText(Activity_Principal.this, userFireBase.getCurrentUser().getEmail(), Toast.LENGTH_LONG).show();
        btn_RegisterPet = (Button) findViewById(R.id.btn_RegisterPet);


        btn_RegisterPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterPet();
            }
        });

        btn_ViewPet = findViewById(R.id.btn_ViewMyPets);

        btn_ViewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewMyPets();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sair){
            signOutUser();
        }

        return super.onOptionsItemSelected(item);
    }

    private void signOutUser(){
        userFireBase.signOut();
        Intent intent = new Intent(Activity_Principal.this, Activity_Login.class);
        startActivity(intent);
        finish();
    }

    private void RegisterPet(){
        Intent intent = new Intent(Activity_Principal.this, Activity_PetRegister.class);
        startActivity(intent);
        finish();
    }

    private void ViewMyPets(){
        Intent intent = new Intent(Activity_Principal.this, Activity_ViewPetUser.class);
        startActivity(intent);
        finish();
    }

}
