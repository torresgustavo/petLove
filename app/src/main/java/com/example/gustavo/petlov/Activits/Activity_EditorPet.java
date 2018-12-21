package com.example.gustavo.petlov.Activits;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gustavo.petlov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;

import Entities.Animals;
import fireBaseConfiguration.FireBaseConfig;

public class Activity_EditorPet extends AppCompatActivity {

    private FirebaseAuth userFireBase = FireBaseConfig.getFireBaseAutentication();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private ImageView img_pet;
    private Boolean trade = false;
    private Uri selectedImage = null;
    private Bundle data;
    private EditText txt_petName, txt_petWeigth, txt_petAge, txt_petRace;
    private Animals pets = new Animals();

    private static final int RESULT_LOAD_IMAGE = 1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.save_editPet:
                    if (txt_petName.getText().toString().equalsIgnoreCase(pets.getName()) && txt_petAge.getText().toString().equalsIgnoreCase(pets.getAge()) &&
                            txt_petRace.getText().toString().equalsIgnoreCase(pets.getRace()) && txt_petWeigth.getText().toString().equalsIgnoreCase(pets.getWeight()) &&
                            trade == false ){
                        Toast.makeText(Activity_EditorPet.this, "Os dados não tiveram alterações, faça alguma alteração para atualizar.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        editar();
                    }
                    return true;
                case R.id.remove_pet:
                    excluir();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__editor_pet);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        img_pet = findViewById(R.id.img_PerfilPet);
        txt_petName = findViewById(R.id.txb_PetName2);
        txt_petWeigth = findViewById(R.id.txb_PetWeight2);
        txt_petAge = findViewById(R.id.txb_PetAge2);
        txt_petRace = findViewById(R.id.txb_PetRace2);

        img_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        Intent receiveData = getIntent();
        data = receiveData.getExtras();

        pets.setId(data.getString("id"));
        pets.setPhotoPerfil(data.getString("img"));
        pets.setWeight(data.getString("weigth"));
        pets.setName(data.getString("name"));
        pets.setRace(data.getString("race"));
        pets.setAge(data.getString("age"));

        Picasso.get().load(Uri.parse(pets.getPhotoPerfil())).into(img_pet);
        txt_petName.setText(pets.getName());
        txt_petAge.setText(pets.getAge());
        txt_petWeigth.setText(pets.getWeight());
        txt_petRace.setText(pets.getRace());
    }

    public void editar (){
        pets.setAge(txt_petAge.getText().toString());
        pets.setRace(txt_petRace.getText().toString());
        pets.setWeight(txt_petWeigth.getText().toString());
        if (trade == true) {
            uploadImage();
        }

        DatabaseReference firebase = FireBaseConfig.getFireBase().child("usuarios").child(userFireBase.getUid()).child("pets").child(pets.getId());
        firebase.setValue(pets);

        Toast.makeText(Activity_EditorPet.this, "Dados do Pet atualizados com sucesso !", Toast.LENGTH_LONG).show();
    }

    public void excluir (){
        StorageReference storageRef = storage.getReference();
        final StorageReference petsRef = storageRef.child("pets/"+userFireBase.getUid()+"/"+pets.getName());
        petsRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                DatabaseReference firebase = FireBaseConfig.getFireBase().child("usuarios").child(userFireBase.getUid()).child("pets").child(pets.getId());
                firebase.removeValue();

                Toast.makeText(Activity_EditorPet.this, "Pet deletado com sucesso!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Activity_EditorPet.this, Activity_MenuUser.class);
                startActivity(intent);
            }
        });
    }

    public boolean uploadImage () {
            final StorageReference storageRef = storage.getReference();

            final StorageReference petsRef = storageRef.child("pets/"+userFireBase.getUid()+"/"+pets.getName());

            petsRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    pets.setName(txt_petName.getText().toString());
                    final StorageReference petsRef = storageRef.child("pets/"+userFireBase.getUid()+"/"+pets.getName());

                    UploadTask uploadTask = petsRef.putFile(selectedImage);

                    uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            petsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    pets.setPhotoPerfil(uri.toString());
                                }
                            });
                        }
                    });
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Activity_EditorPet.this, "Erro no carregamento da foto.", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            return false;
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            img_pet.setImageURI(selectedImage);
            trade = true;
        }
    }
}
