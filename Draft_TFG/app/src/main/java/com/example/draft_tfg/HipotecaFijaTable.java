package com.example.draft_tfg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class HipotecaFijaTable {
    Context c;
    ListaHipotecaFIja F;
    SQLiteDatabase sql;
    String bd = "BDTFG";
    String tabla = "create table if not exists hipoteca_fijaP (id INTEGER PRIMARY KEY AUTOINCREMENT, BANCO TEXT, TIN DECIMAL(5,2), TAE DECIMAL(5,2) )";

    public HipotecaFijaTable(Context c){
        this.c = c;
        this.sql = c.openOrCreateDatabase(bd, c.MODE_PRIVATE, null);
        this.sql.execSQL(tabla);
        this.F = new ListaHipotecaFIja();
    }

    public boolean insertHipFijaData(ListaHipotecaFIja H) throws Exception {
        String banco = H.getBanco();
        double tin = H.getTin();
        double tae = H.getTae();

        // Verificar si los datos ya existen en la tabla hipoteca_fijaP
        Cursor cursor = sql.rawQuery("SELECT * FROM hipoteca_fijaP WHERE BANCO=? AND TIN=? AND TAE=?", new String[] {banco, String.valueOf(tin), String.valueOf(tae)});
        if (cursor.getCount() > 0) {
            // Los datos ya existen, no se inserta nada
            cursor.close();
            return false;
        }
        cursor.close();

        // Insertar los nuevos datos en la tabla hipoteca_fijaP
        ContentValues values = new ContentValues();
        values.put("BANCO", banco);
        values.put("TIN", tin);
        values.put("TAE", tae);
        return (sql.insert("hipoteca_fijaP", null, values) > 0);
    }

    // Obtener la mejor ofertaTIn
    public double obtenerMejorTin() {
        double TIN = 0;
        Cursor cursor = sql.rawQuery("SELECT min(TIN) as TIN FROM hipoteca_fijaP", null);
        if(cursor.moveToFirst()){
            TIN = cursor.getDouble(0);
        }
        cursor.close();
        return TIN;
    }

    public double obtenerTAE() {
        double TIN = 0;
        Cursor cursor = sql.rawQuery("SELECT TAE FROM hipoteca_fijaP where TIN = (select min(TIN) from hipoteca_fijaP) order by id DESC LIMIT 1", null);
        if(cursor.moveToFirst()){
            TIN = cursor.getDouble(0);
        }
        cursor.close();
        return TIN;
    }

    // Obtener el segundo mejor ofertaTIn
    public double obtenerSegundoMinTin() {
        double TIN = 0;
        Cursor cursor = sql.rawQuery("SELECT MIN(TIN) AS TIN FROM hipoteca_fijaP WHERE TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP)", null);
        if(cursor.moveToFirst()){
            TIN = cursor.getDouble(0);
        }
        cursor.close();
        return TIN;
    }

    public double obtenerSegundoTAE() {
        double TIN = 0;
        Cursor cursor = sql.rawQuery("SELECT TAE FROM hipoteca_fijaP WHERE TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP)", null);
        if(cursor.moveToFirst()){
            TIN = cursor.getDouble(0);
        }
        cursor.close();
        return TIN;
    }


    // Obtener el tercer mejor ofertaTIn
    public double obtenerTercerMinTin() {
        double TIN = 0;
        Cursor cursor = sql.rawQuery("SELECT MIN(TIN) AS TIN FROM hipoteca_fijaP WHERE TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP) AND TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP WHERE TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP))", null);
        if(cursor.moveToFirst()){
            TIN = cursor.getDouble(0);
        }
        cursor.close();
        return TIN;
    }

    public double obtenerTercerTAE() {
        double TIN = 0;
        Cursor cursor = sql.rawQuery("SELECT TAE FROM hipoteca_fijaP WHERE TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP) AND TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP WHERE TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP))", null);
        if(cursor.moveToFirst()){
            TIN = cursor.getDouble(0);
        }
        cursor.close();
        return TIN;
    }

    // Obtener el banco de la mejor ofertaTIn
    public String obtenerBancoMejorTin() {
        String BANCO = "";
        Cursor cursor = sql.rawQuery("SELECT BANCO  FROM hipoteca_fijaP where TIN = (select min(TIN) from hipoteca_fijaP) order by id DESC LIMIT 1", null);
        if(cursor.moveToFirst()){
            BANCO = cursor.getString(0);
        }
        cursor.close();
        return BANCO;
    }

    public String obtenerBancoSmejorTin() {
        String BANCO = "";
        Cursor cursor = sql.rawQuery("SELECT BANCO  FROM hipoteca_fijaP where TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP) order by id DESC LIMIT 1", null);
        if(cursor.moveToFirst()){
            BANCO = cursor.getString(0);
        }
        cursor.close();
        return BANCO;
    }

    public String obtenerBancoTmejorTin() {
        String BANCO = "";
        Cursor cursor = sql.rawQuery("SELECT BANCO  FROM hipoteca_fijaP where TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP) AND TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP WHERE TIN NOT IN (SELECT MIN(TIN) FROM hipoteca_fijaP)) order by id DESC LIMIT 1", null);
        if(cursor.moveToFirst()){
            BANCO = cursor.getString(0);
        }
        cursor.close();
        return BANCO;
    }

}
