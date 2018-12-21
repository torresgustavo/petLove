package com.example.gustavo.petlov.Activits;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.gustavo.petlov.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import Entities.Users;
import fireBaseConfiguration.FireBaseConfig;

public class Activity_Login extends AppCompatActivity {

    private Button bt_login;
    private Button bt_register;
    private FirebaseAuth autentication;
    private EditText login, password;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        bt_register = findViewById(R.id.bt_Register);
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });

        bt_login = findViewById(R.id.bt_Login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = findViewById(R.id.txb_user);
                password = findViewById(R.id.txb_password);
                if (!password.getText().toString().equals("") || !login.getText().toString().equals("")){
                    try {
                        validateLogin(login.getText().toString(), password.getText().toString());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(Activity_Login.this, "Preencha todos os campos.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void validateLogin (String email, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        autentication = FireBaseConfig.getFireBaseAutentication();

        autentication.signInWithEmailAndPassword(email, Users.generateHashPassword(password)).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    openMenu();
                    Toast.makeText(Activity_Login.this, "Login efetuado com sucesso !", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Activity_Login.this, "Usuario ou Senha inválidos !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openRegister(){
        Intent intent = new Intent(Activity_Login.this, Activity_Register.class);
        startActivity(intent);
    }
    public void openMenu(){
        Intent intentOpenMenu = new Intent(Activity_Login.this, Activity_MenuUser.class);
        startActivity(intentOpenMenu);
    }
}
