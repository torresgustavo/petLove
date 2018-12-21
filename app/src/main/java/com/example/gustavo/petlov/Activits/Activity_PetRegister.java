package com.example.gustavo.petlov.Activits;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import fireBaseConfiguration.FireBaseConfig;
import Entities.Animals;

public class Activity_PetRegister extends AppCompatActivity {

    private Button btn_savePet;
    private Animals pets = new Animals();
    private FirebaseAuth userFireBase = FireBaseConfig.getFireBaseAutentication();
    private EditText edt_Name, edt_Race, edt_Age, edt_Weight;
    private ImageView petImage;
    private static Uri selectedImage;
    private FirebaseStorage storage = FirebaseStorage.getInstance();


    private static final int RESULT_LOAD_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petregister);

        edt_Name = (EditText) findViewById(R.id.txb_PetName);
        edt_Age = (EditText) findViewById(R.id.txb_PetAge);
        edt_Race = (EditText) findViewById(R.id.txb_PetRace);
        edt_Weight = (EditText) findViewById(R.id.txb_PetWeight);
        btn_savePet = (Button) findViewById(R.id.btn_savePet);
        petImage = findViewById(R.id.img_petImg);

        petImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        btn_savePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pets.setName(edt_Name.getText().toString());
                pets.setAge(edt_Age.getText().toString());
                pets.setRace(edt_Race.getText().toString());
                pets.setWeight(edt_Weight.getText().toString());

                uploadImage(selectedImage);
            }
        });
    }

    private void registerPet (Animals pets){
        DatabaseReference firebase = FireBaseConfig.getFireBase().child("usuarios").child(userFireBase.getUid()).child("pets").push();
        pets.setId(firebase.getKey());
        firebase.setValue(pets);


        Toast.makeText(Activity_PetRegister.this, "Pet cadastrado com sucesso !", Toast.LENGTH_LONG).show();
    }

    private void limparCampos(){
        selectedImage = null;
        edt_Weight.setText("");
        edt_Race.setText("");
        edt_Age.setText("");
        edt_Name.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Activity_PetRegister.this, Activity_MenuUser.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            petImage.setImageURI(selectedImage);
        }
    }

    public boolean uploadImage (Uri selectedImage){
        StorageReference storageRef = storage.getReference();

        final StorageReference petsRef = storageRef.child("pets/"+userFireBase.getUid()+"/"+pets.getName()+"/");
        UploadTask uploadTask = petsRef.putFile(selectedImage);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Activity_PetRegister.this, "Erro no carregamento da foto.", Toast.LENGTH_LONG).show();
            }
        });

        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                petsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                    }
                });
            }
        });

        if (uploadTask.isComplete()){
            return true;
        }

        else {
            return false;
        }
    }


}
