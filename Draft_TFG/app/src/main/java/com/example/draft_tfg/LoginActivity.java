package com.example.draft_tfg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    // Declaramos las variables
    EditText user, password;
    Button inicioSesion;
    TextView registro;

    LoginTable LoginTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Vinculamos las variables con los elementos del layout
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        inicioSesion = (Button) findViewById(R.id.inicioSesion);
        registro = (TextView) findViewById(R.id.registro);

        inicioSesion.setOnClickListener(this);
        registro.setOnClickListener(this);
        LoginTable=new LoginTable(this);

    }
    public void onClick(View v){

        switch (v.getId()){
            case R.id.inicioSesion:
                String u = user.getText().toString();
                String c = password.getText().toString();
                if (u.equals("")&&c.equals("")){
                    Toast.makeText(LoginActivity.this, "ERROR: Campos vacíos", Toast.LENGTH_LONG).show();
                }else if (LoginTable.login(u,c)==1){
                    int userId = LoginTable.getIdByUsuario(u); // Obtener el ID del usuario
                    FormularioTable formularioTable = new FormularioTable(this); // Crear una instancia de FormularioTable
                    formularioTable.setUserId(userId);
                    Toast.makeText(LoginActivity.this, "Datos Correctos", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(LoginActivity.this, DashboardLogin.class);
                    intent2.putExtra("userId", userId); // Pasar el ID del usuario al intent

                    startActivity(intent2);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Usuario o Contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.registro:
                transitionBack2();
                break;
        }

    }

    @Override
    public  void onBackPressed() {
        transitionBack2();
    }

    public void transitionBack2() {

        Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(intent);
        finish();
    }

}
