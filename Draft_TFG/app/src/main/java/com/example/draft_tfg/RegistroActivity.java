package com.example.draft_tfg;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{

    // Declaramos las variables
    EditText nombre, apellido, usuarioRegistro, contrasenaRegistro;
    Button buttonRegistro;
    TextView buttonInicioSesion;

    LoginTable tableRegistro;


    FormularioTable formularioTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Vinculamos las variables con los elementos del layout
        nombre = (EditText) findViewById(R.id.nombre);
        apellido = (EditText) findViewById(R.id.apellido);
        usuarioRegistro = (EditText) findViewById(R.id.usuarioRegistro);
        contrasenaRegistro = (EditText) findViewById(R.id.contrasenaRegistro);
        buttonRegistro = (Button) findViewById(R.id.buttonRegistro);
        buttonInicioSesion = (TextView) findViewById(R.id.buttonInicioSesion);

        buttonRegistro.setOnClickListener(this);
        buttonInicioSesion.setOnClickListener(this);
        tableRegistro=new LoginTable(this);
        formularioTable=new FormularioTable(this);
    }


    public void onClick(View v){

        switch (v.getId()){
            case R.id.buttonRegistro:
                ListaLogin u = new ListaLogin();
                u.setNombre(nombre.getText().toString());
                u.setApellido(apellido.getText().toString());
                u.setUsuario(usuarioRegistro.getText().toString());
                u.setContrasena(contrasenaRegistro.getText().toString());
                if (!u.isNull()){
                    Toast.makeText(RegistroActivity.this, "ERROR: Campos nulos", Toast.LENGTH_LONG).show();
                }else if (tableRegistro.insertUsuario(u)){
                    Toast.makeText(RegistroActivity.this, "Registro exitoso!", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(RegistroActivity.this, FormularioActivity.class);
                    startActivity(intent2);
                    finish();
                }else {
                    Toast.makeText(RegistroActivity.this, "Usuario ya registrado", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.buttonInicioSesion:
                transitionBack();
                break;
        }

    }

    @Override
    public  void onBackPressed() {
        transitionBack();
    }

    public void transitionBack(){

        Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }
}