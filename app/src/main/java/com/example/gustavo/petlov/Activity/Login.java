package com.example.gustavo.petlov.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gustavo.petlov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import Entities.Users;
import fireBaseConfiguration.fireBaseConfig;

public class Login extends AppCompatActivity {

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
                if (!login.getText().toString().equals("") || !password.getText().toString().equals("")){
                    users = new Users();
                    users.setEmail(login.getText().toString());
                    users.setPassword(password.getText().toString());

                    validateLogin();
                }
                else {
                    Toast.makeText(Login.this, "Preencha todos os campos.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void validateLogin (){
        autentication = fireBaseConfig.getFireBaseAutentication();
        autentication.signInWithEmailAndPassword(users.getEmail(),users.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    openMenu();
                    Toast.makeText(Login.this, "Login efetuado !", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(Login.this, "Usuario ou Senha inválidos !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void openRegister(){
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }
    public void openMenu(){
        Intent intentOpenMenu = new Intent(Login.this, Menu.class);
        startActivity(intentOpenMenu);
    }
}
