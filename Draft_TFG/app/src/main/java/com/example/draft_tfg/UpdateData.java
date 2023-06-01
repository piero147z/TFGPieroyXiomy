package com.example.draft_tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateData extends AppCompatActivity implements View.OnClickListener {

    EditText editTextAmount, editTextYears;
    Button updateDash;
    FormularioTable formularioUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        editTextAmount = findViewById(R.id.editTextAmount);
        editTextYears = findViewById(R.id.editTextYears);
        updateDash = findViewById(R.id.updateDash);

        updateDash.setOnClickListener(this);

        formularioUpdate = new FormularioTable(this);

    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.updateDash:

                String precioText = editTextAmount.getText().toString();
                int dineroNecesita = Integer.parseInt(precioText);

                String añoText = editTextYears.getText().toString();
                int añoPago = Integer.parseInt(añoText);

                formularioUpdate.updateUltimoRegistro(dineroNecesita, añoPago);

                Intent intent2 = new Intent(UpdateData.this, Dashboard.class);
                startActivity(intent2);
                finish();
                break;
        }

    }
}