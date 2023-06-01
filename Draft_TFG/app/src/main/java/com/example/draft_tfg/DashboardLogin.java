package com.example.draft_tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class DashboardLogin extends AppCompatActivity implements View.OnClickListener{

    TextView textoprueba;
    int datoTest;
    int userId;

    //Variables para calcular KPI

    float tin, tin2, tin3;
    int plazoEnAnios;
    int plazoEnMeses;
    int amPrestamo;
    float cuotaMensual, cuotaMensual2, cuotaMensual3;

    //Variables para KPI
    TextView titulokpi, mejorCuotaMensual, banco1,banco2,banco3,tinT1,tinT2,tinT3,tae1,tae2,tae3;

    Button modificarConsulta, login,updateDash ;
    // Varibales para barchart
    BarChart bChart;
    PieChart pChart;

    LinearLayout popupLayout;
    EditText editTextAmount, editTextYears;

    FormularioTable f ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_login);

        modificarConsulta = (Button) findViewById(R.id.modificarC);
        modificarConsulta.setOnClickListener(this);

        login = (Button) findViewById(R.id.irInicio);
        login.setOnClickListener(this);

        editTextAmount = findViewById(R.id.editTextAmount);
        editTextYears = findViewById(R.id.editTextYears);

        updateDash= (Button) findViewById(R.id.updateDash);
        updateDash.setOnClickListener(this);

        popupLayout = (LinearLayout) findViewById(R.id.popupLayout);


        banco1 = (TextView) findViewById(R.id.banco1);
        banco2 = (TextView) findViewById(R.id.banco2);
        banco3 = (TextView) findViewById(R.id.banco3);
        tinT1 = (TextView) findViewById(R.id.tin1);
        tinT2 = (TextView) findViewById(R.id.tin2);
        tinT3 = (TextView) findViewById(R.id.tin3);
        tae1 = (TextView) findViewById(R.id.tae1);
        tae2 = (TextView) findViewById(R.id.tae2);
        tae3 = (TextView) findViewById(R.id.tae3);

        titulokpi = (TextView) findViewById(R.id.tituloOpcionHipoteca);
        mejorCuotaMensual = (TextView) findViewById(R.id.mejorPrecioHipoteca);
        bChart = findViewById(R.id.bar_chart);
        pChart = findViewById(R.id.pie_chart);

        // Obtener el ID del usuario desde el Intent
        userId = getIntent().getIntExtra("userId", -1); // Configura el valor de userId
        f = new FormularioTable(this);
        f.setUserId(userId); // Configura el valor de userId en la instancia de FormularioTable

    //KPI object
        //Titulo de la mejor oferta de TIN
        HipotecaFijaTable h = new HipotecaFijaTable(this);
        String tituloMejorOferta = h.obtenerBancoMejorTin();
        titulokpi.setText(tituloMejorOferta);

        //Captamos en la variable el am de prestamo

        amPrestamo = f.obtenerDineroNecesita();

        //Captamos en la varibale el TIN 1 2 3
        tin = (float) h.obtenerMejorTin();
        tin2 = (float) h.obtenerSegundoMinTin();
        tin3 = (float) h.obtenerTercerMinTin();

        //Captamos en la variable el añoMes prestamo
        plazoEnAnios = f.obtenerTiempoPrestamo();

        plazoEnMeses = plazoEnAnios * 12;

        //Calculamos la mejor cuota mensual segun sitemas frances cuotaMensual = (prestamo * (TIN / 12)) / (1 - Math.pow(1 + (TIN / 12), -plazoEnMeses));

        cuotaMensual = (float) ((amPrestamo * (tin / 12)) / (1 - Math.pow(1 + (tin / 12), -plazoEnMeses)));
        cuotaMensual2 =(float) ((amPrestamo * (tin2 / 12)) / (1 - Math.pow(1 + (tin2 / 12), -plazoEnMeses)));
        cuotaMensual3 = (float) ((amPrestamo * (tin3 / 12)) / (1 - Math.pow(1 + (tin3 / 12), -plazoEnMeses)));

        mejorCuotaMensual.setText(String.format("%.2f €", cuotaMensual));


    //BarChart
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, cuotaMensual));
        entries.add(new BarEntry(2, cuotaMensual2));
        entries.add(new BarEntry(3, cuotaMensual3));

        BarDataSet dataSet = new BarDataSet(entries, "Label");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);

        BarData barData = new BarData(dataSet);
        bChart.setData(barData);
        bChart.getDescription().setEnabled(false);
        bChart.setFitBars(true);
        bChart.invalidate();

//PieChart
        //Calculamos lo siguiente :
        float totalInterest = (cuotaMensual * plazoEnMeses) - amPrestamo;
        float capitalPaid = amPrestamo - totalInterest;
        // Calcular el porcentaje del pago que se destina a pagar el capital
        float capitalPercentage1 = (capitalPaid / cuotaMensual) * 100;

        float totalInterest2 = (cuotaMensual2 * plazoEnMeses) - amPrestamo;
        float capitalPaid2 = amPrestamo - totalInterest2;
        // Calcular el porcentaje del pago que se destina a pagar el capital
        float capitalPercentage2 = (capitalPaid2 / cuotaMensual2) * 100;

        float totalInterest3 = (cuotaMensual3 * plazoEnMeses) - amPrestamo;
        float capitalPaid3 = amPrestamo - totalInterest3;
        // Calcular el porcentaje del pago que se destina a pagar el capital
        float capitalPercentage3 = (capitalPaid3 / cuotaMensual3) * 100;


        List<PieEntry> entriesP = new ArrayList<>();
        entriesP.add(new PieEntry(capitalPercentage1, "Banco 1"));
        entriesP.add(new PieEntry(capitalPercentage2, "Banco 2"));
        entriesP.add(new PieEntry(capitalPercentage3, "Banco 3"));

        PieDataSet dataSetP = new PieDataSet(entriesP, "PieChart");

        dataSetP.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSetP.setSliceSpace(2f);
        dataSetP.setValueTextColor(Color.WHITE);
        dataSetP.setValueTextSize(12f);

        PieData data = new PieData(dataSetP);
        data.setValueFormatter(new PercentFormatter());
        pChart.setData(data);
        pChart.invalidate();
        pChart.setUsePercentValues(true);
        pChart.getDescription().setEnabled(false);
        pChart.setDrawHoleEnabled(false);
        pChart.setTransparentCircleRadius(0f);
        pChart.setRotationEnabled(false);

//Table
        //Titulo de bancos
        String titulobanco1 = h.obtenerBancoMejorTin();
        banco1.setText(titulobanco1);
        banco1.setEllipsize(TextUtils.TruncateAt.END);

        String titulobanco2 = h.obtenerBancoSmejorTin();
        banco2.setText(titulobanco2);
        banco2.setEllipsize(TextUtils.TruncateAt.END);

        String titulobanco3 = h.obtenerBancoTmejorTin();
        banco3.setText(titulobanco3);
        banco3.setEllipsize(TextUtils.TruncateAt.END);

        //Titulo de TIN
        tinT1.setText(String.format("%.2f%%", tin * 100));
        tinT2.setText(String.format("%.2f%%", tin2 * 100));
        tinT3.setText(String.format("%.2f%%", tin3 * 100));

        //Titulo de TAE
        Double taetable = h.obtenerTAE();
        tae1.setText(String.format("%.2f%%", taetable * 100));

        Double taetable2 = h.obtenerSegundoTAE();
        tae2.setText(String.format("%.2f%%", taetable2 * 100));

        Double taetable3 = h.obtenerTercerTAE();
        tae3.setText(String.format("%.2f%%", taetable3 * 100));


    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.modificarC:
                popupLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.irInicio:
                Intent intent3 = new Intent(DashboardLogin.this, LoginActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.updateDash:

                String precioText = editTextAmount.getText().toString();
                int dineroNecesita = Integer.parseInt(precioText);

                String añoText = editTextYears.getText().toString();
                int añoPago = Integer.parseInt(añoText);

                f.actualizarDatos(dineroNecesita, añoPago);
                Intent intent4 = new Intent(DashboardLogin.this, LoginActivity.class);
                startActivity(intent4);
                finish();

                break;
        }
    }


}