package com.example.draft_tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class tipoHipoteca extends AppCompatActivity implements View.OnClickListener{

    String url= "https://www.kelisto.es/hipotecas";
    String setBancofijo,setBancofijo2,setBancofijo3;
    String setTINfijo,setTINfijo2,setTINfijo3;
    String setTAEfijo,setTAEfijo2,setTAEfijo3;
    Double tinValuefijo,tinValuefijo2,tinValuefijo3;
    Double taeValuefijo,taeValuefijo2,taeValuefijo3;

    Button hipotecaFija;
    Button hipotecaVariable;

    TextView textTest;

    HipotecaFijaTable listaHipotecaFIja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_hipoteca);


        hipotecaFija = (Button) findViewById(R.id.hipotecaFija);
        hipotecaVariable = (Button) findViewById(R.id.hipotecaVariable);

        hipotecaFija.setOnClickListener(this);

        listaHipotecaFIja = new HipotecaFijaTable(this);

        Content content = new Content();
        content.execute();


    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.hipotecaFija:
                Intent intent2 = new Intent(tipoHipoteca.this, Dashboard.class);
                startActivity(intent2);
                finish();
                break;
        }

    }

    class Content extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... voids){
            try {
                Document text = Jsoup.connect(url).get();
                /// Banco1///
                Element bancoElement = text.selectFirst("#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(1) > span.CardLayout_card-layout__header__p0KJh > div > div > div.CardHeader_card-header__header__data_wrapper__O_v4B > a > span");
                setBancofijo=bancoElement.text();

                Element tinElement = text.selectFirst("#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(1) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-3__7evFm > span");
                setTINfijo=tinElement.text();
                tinValuefijo = Double.parseDouble(setTINfijo.replace(",", ".").replaceAll("[^\\d.]", "")) / 100;

                Element taeElement = text.selectFirst("#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(1) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-5__2WQn2 > span");
                setTAEfijo=taeElement.text();
                taeValuefijo = Double.parseDouble(setTAEfijo.replace(",", ".").replaceAll("[^\\d.]", "")) / 100;

                //// Banco 2////
                Element bancoElement2 = text.selectFirst("#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(2) > span.CardLayout_card-layout__header__p0KJh > div > div > div.CardHeader_card-header__header__data_wrapper__O_v4B > a > span");
                setBancofijo2=bancoElement2.text();

                Element tinElement2 = text.selectFirst("#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(2) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-3__7evFm > span");
                setTINfijo2=tinElement2.text();
                tinValuefijo2 = Double.parseDouble(setTINfijo2.replace(",", ".").replaceAll("[^\\d.]", "")) / 100;

                Element taeElement2 = text.selectFirst("#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(2) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-5__2WQn2 > span");
                setTAEfijo2=taeElement2.text();
                taeValuefijo2 = Double.parseDouble(setTAEfijo2.replace(",", ".").replaceAll("[^\\d.]", "")) / 100;

                ///// Banco 3////
                Element bancoElement3 = text.selectFirst("#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(3) > span.CardLayout_card-layout__header__p0KJh > div > div > div.CardHeader_card-header__header__data_wrapper__O_v4B > a > span");
                setBancofijo3=bancoElement3.text();

                Element tinElement3 = text.selectFirst("#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(3) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-3__7evFm > span");
                setTINfijo3=tinElement3.text();
                tinValuefijo3 = Double.parseDouble(setTINfijo3.replace(",", ".").replaceAll("[^\\d.]", "")) / 100;

                Element taeElement3 = text.selectFirst("#__next > div > section:nth-child(3) > div > div > div:nth-child(2) > div > div.Tabs_tabs__tab-content__c0YTa.Tabs_tabs__tab-content--visible__hyVc5 > div > div:nth-child(2) > div > div:nth-child(3) > span.CardLayout_card-layout__table__Rl_O4 > div > div.MortgagesTable_mortgages-table__cell-5__2WQn2 > span");
                setTAEfijo3=taeElement3.text();
                taeValuefijo3 = Double.parseDouble(setTAEfijo3.replace(",", ".").replaceAll("[^\\d.]", "")) / 100;

            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid){

            //super.onPostExecute(aVoid);
            //textTest.setText(String.valueOf(tinValuefijo2));

            ListaHipotecaFIja F1 = new ListaHipotecaFIja();
            F1.setBanco(setBancofijo);
            F1.setTin(tinValuefijo);
            F1.setTae(taeValuefijo);

            ListaHipotecaFIja F2 = new ListaHipotecaFIja();
            F2.setBanco(setBancofijo2);
            F2.setTin(tinValuefijo2);
            F2.setTae(taeValuefijo2);

            ListaHipotecaFIja F3 = new ListaHipotecaFIja();
            F3.setBanco(setBancofijo3);
            F3.setTin(tinValuefijo3);
            F3.setTae(taeValuefijo3);

            try {
                listaHipotecaFIja.insertHipFijaData(F1);
                listaHipotecaFIja.insertHipFijaData(F2);
                listaHipotecaFIja.insertHipFijaData(F3);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}