package com.example.gustavo.petlov.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gustavo.petlov.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import Entities.Users;
import Helper.Base64Custom;
import Helper.Preferences;
import fireBaseConfiguration.fireBaseConfig;

public class Register extends AppCompatActivity{


        private Button bt_cadastrar;
        private EditText txb_nome, txb_senha, txb_confsenha, txb_email, txb_endereco, txb_bairro, txb_numero, txb_cep;
        private Spinner stateSpinner;
        private FirebaseAuth autentication;
        private Users users = new Users();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.register);

            txb_nome = (EditText) findViewById(R.id.txb_name);
            txb_email = (EditText) findViewById(R.id.txb_email);
            txb_endereco = (EditText) findViewById(R.id.txb_endereco);
            txb_numero = (EditText) findViewById(R.id.txb_numero);
            txb_cep = (EditText) findViewById(R.id.txb_cep);
            txb_senha = (EditText) findViewById(R.id.txb_senha);
            txb_confsenha = (EditText) findViewById(R.id.txb_confsenha);
            txb_bairro = (EditText) findViewById(R.id.txb_bairro);
            stateSpinner = (Spinner) findViewById(R.id.spinnerState);
            bt_cadastrar = (Button) findViewById(R.id.btn_cadastrar);

            bt_cadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (txb_nome.getText().toString().equals("") || txb_senha.getText().toString().equals("") ||
                            txb_confsenha.getText().toString().equals("") || txb_bairro.getText().toString().equals("") ||
                            txb_numero.getText().toString().equals("") || txb_email.getText().toString().equals("") ||
                            txb_cep.getText().toString().equals("") || txb_endereco.getText().toString().equals("")) {
                        Toast.makeText(Register.this, "Preencha todos os campos", Toast.LENGTH_LONG).show();
                    }
                    else if (!txb_confsenha.getText().toString().equals(txb_senha.getText().toString())){
                        Toast.makeText(Register.this, "As senhas inseridas não coincidem, tente novamente.", Toast.LENGTH_LONG).show();
                        txb_confsenha.setText("");
                    }
                    else{
                        users.setName(txb_nome.getText().toString());
                        users.setEmail(txb_email.getText().toString());
                        users.setPassword(txb_senha.getText().toString());
                        users.setCep(txb_cep.getText().toString());
                        users.setDistrict(txb_bairro.getText().toString());
                        users.setNumber(txb_numero.getText().toString());
                        users.setAddress(txb_endereco.getText().toString());
                        users.setStates(stateSpinner.getSelectedItem().toString());

                        registerUser();
                    }


                }
            });
        }

        public void registerUser(){
                autentication = fireBaseConfig.getFireBaseAutentication();
                autentication.createUserWithEmailAndPassword(users.getEmail(), users.getPassword()
                ).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "Cadastro realizado com sucesso !", Toast.LENGTH_LONG).show();

                            String identifierUser = Base64Custom.codifiedBase64(users.getEmail());
                            FirebaseUser userFireBase = task.getResult().getUser();
                            users.setId(identifierUser);
                            users.save();

                            Preferences preferences = new Preferences(Register.this);
                            preferences.saveUserPreferences(identifierUser,users.getName());

                            openLoginUser();

                        }else {
                            String exception = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                exception = "Senha muito fraca, tente outra mais forte.";
                            } catch (FirebaseAuthEmailException e) {
                                exception = "Email inválido.";
                            } catch (FirebaseAuthUserCollisionException e) {
                                exception = "Este e-mail já existe no sistema, tente com outro.";
                            } catch (Exception e) {
                                exception = "Erro ao efetuar o cadastro.";
                                e.printStackTrace();
                            }
                            Toast.makeText(Register.this, "Erro ao efetuar o cadastro !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }

        public void openLoginUser(){
            Intent intentOpenLogin = new Intent(Register.this, Login.class);
            startActivity(intentOpenLogin);
        }

        public void spinnerState(){

            ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.states));
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stateSpinner.setAdapter(stateAdapter);
        }
}
