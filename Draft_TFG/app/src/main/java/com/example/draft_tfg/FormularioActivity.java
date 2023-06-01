package com.example.draft_tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FormularioActivity extends AppCompatActivity implements View.OnClickListener{

    EditText precioViviendaEditText,dineroNecesitaEditText,añosHipotecaEditText,provinciaEditText,edadEditText;

    RadioGroup tipoViviendaRadioGroup ;
    RadioButton nuevaRadioButton ;
    RadioButton segundaManoRadioButton ;

    RadioGroup usoViviendaRadioGroup  ;
    RadioButton habitualRadioButton ;
    RadioButton segundaViviendaRadioButton ;

    Spinner tipoBancoSpinner ;


    Button enviarButton ;

    FormularioTable tablaformulario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        // Vinculamos las variables con los elementos del layout
        precioViviendaEditText = findViewById(R.id.preciovivienda);
        dineroNecesitaEditText = findViewById(R.id.dineroneed);
        añosHipotecaEditText = findViewById(R.id.añoshipoteca);
        provinciaEditText = findViewById(R.id.provincia);
        edadEditText = findViewById(R.id.edad);
        tipoViviendaRadioGroup = findViewById(R.id.tipoViviendaRadioGroup);
        nuevaRadioButton = findViewById(R.id.nuevaRadioButton);
        segundaManoRadioButton = findViewById(R.id.segundaManoRadioButton);
        usoViviendaRadioGroup = findViewById(R.id.usoViviendaRadioGroup);
        habitualRadioButton = findViewById(R.id.habitualRadioButton);
        segundaViviendaRadioButton = findViewById(R.id.segundaViviendaRadioButton);
        tipoBancoSpinner = findViewById(R.id.tipoBancoSpinner);
        enviarButton = findViewById(R.id.enviarButton);

        enviarButton.setOnClickListener(this);
        tablaformulario = new FormularioTable(this);

    }


    public void onClick(View v){

        switch (v.getId()){
            case R.id.enviarButton:
                ListaFormulario f = new ListaFormulario();
                //f.setPrecioVivienda(Double.parseDouble(precioViviendaEditText.getText().toString()));
                String precioText = precioViviendaEditText.getText().toString();
                int precio = Integer.parseInt(precioText);
                f.setPrecioVivienda(precio);

                //f.setDineroNecesita(Double.parseDouble(dineroNecesitaEditText.getText().toString()));
                String dineronecesitaText = dineroNecesitaEditText.getText().toString();
                int dineroN = Integer.parseInt(dineronecesitaText);
                f.setDineroNecesita(dineroN);

                //f.setAñosPago(Integer.parseInt(añosHipotecaEditText.getText().toString()));
                String añosText = añosHipotecaEditText.getText().toString();
                int años = Integer.parseInt(añosText);
                f.setAñosPago(años);

                int tipoViviendaSeleccionadaId = tipoViviendaRadioGroup.getCheckedRadioButtonId();

                // Verificar qué opción fue seleccionada
                if (tipoViviendaSeleccionadaId == R.id.nuevaRadioButton) {
                    f.setTipoVivienda("N"); // Asignar "N" para vivienda nueva
                } else if (tipoViviendaSeleccionadaId == R.id.segundaManoRadioButton) {
                    f.setTipoVivienda("S"); // Asignar "S" para vivienda de segunda mano
                }

                int tipoUsoViviendaSeleccionadaId = usoViviendaRadioGroup.getCheckedRadioButtonId();

                // Verificar qué opción fue seleccionada
                if (tipoUsoViviendaSeleccionadaId == R.id.habitualRadioButton) {
                    f.setUsoVivienda("H"); // Asignar "N" para vivienda Habitual
                } else if (tipoUsoViviendaSeleccionadaId == R.id.segundaViviendaRadioButton) {
                    f.setUsoVivienda("S"); // Asignar "S" para segunda vivienda
                }

                f.setProvincia(provinciaEditText.getText().toString());

                //f.setEdad(Integer.parseInt(edadEditText.getText().toString()));
                String edadText = edadEditText.getText().toString();
                int edad = Integer.parseInt(edadText);
                f.setEdad(edad);

                // Obtener el índice seleccionado del Spinner tipoBancoSpinner
                int tipoBancoSeleccionadoIndex = tipoBancoSpinner.getSelectedItemPosition();

                // Obtener el valor seleccionado del Spinner
                String tipoBancoSeleccionado = tipoBancoSpinner.getItemAtPosition(tipoBancoSeleccionadoIndex).toString();

                // Asignar el valor del Spinner al campo tipp_banco
                f.setTipoBanco(tipoBancoSeleccionado);

               try {
                    if (tablaformulario.insertFormulario(f)) {
                        Toast.makeText(FormularioActivity.this, "Gracias por la información!", Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(FormularioActivity.this, tipoHipoteca.class);
                        startActivity(intent2);
                        finish();
                    }
                } catch (Exception e) {
                    Toast.makeText(FormularioActivity.this, "Error al insertar en la base de datos: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
        }

    }





}