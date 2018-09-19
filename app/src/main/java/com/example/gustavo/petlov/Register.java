package com.example.gustavo.petlov;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Register extends Activity{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.register);

            Spinner stateSpinner = (Spinner) findViewById(R.id.spinnerState);

            ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_expandable_list_item_1, getResources().getStringArray(R.array.states));
            stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stateSpinner.setAdapter(stateAdapter);
        }
}
