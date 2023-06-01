package com.example.draft_tfg;

import static android.content.Intent.getIntent;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mysql.cj.log.Log;

public class FormularioTable {

    Context c;
    ListaFormulario f;
    SQLiteDatabase sql;
    String bd = "BDTFG";
    String tabla = "create table if not exists tablaFormulario (id INTEGER PRIMARY KEY AUTOINCREMENT, precio_vivienda INTEGER, dinero_necesita INTEGER, años_pago INTEGER, Tipo_vivienda TEXT, Uso_vivienda TEXT, provincia TEXT, edad INTEGER, Tipo_banco TEXT )";
    int userId;
    public FormularioTable(Context c){
        this.c = c;
        this.sql = c.openOrCreateDatabase(bd, c.MODE_PRIVATE, null);
        this.sql.execSQL(tabla);
        this.f = new ListaFormulario();
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean insertFormulario(ListaFormulario f) throws Exception {
        ContentValues values = new ContentValues();
        values.put("precio_vivienda", f.getPrecioVivienda());
        values.put("dinero_necesita", f.getDineroNecesita());
        values.put("años_pago", f.getAñosPago());
        values.put("Tipo_vivienda", f.getTipoVivienda());
        values.put("Uso_vivienda", f.getUsoVivienda());
        values.put("provincia", f.getProvincia());
        values.put("edad", f.getEdad());
        values.put("Tipo_banco", f.getTipoBanco());
        return (sql.insert("tablaFormulario",null,values)>0);

    }

    public boolean updateUltimoRegistro(int dineroNecesita, int añoPago) {
        ContentValues values = new ContentValues();
        values.put("dinero_necesita", dineroNecesita);
        values.put("años_pago", añoPago);

        String whereClause = "id = (SELECT MAX(id) FROM tablaFormulario)";

        return (sql.update("tablaFormulario", values, whereClause, null) > 0);
    }

    public boolean actualizarDatos(int dineroNecesita, int añoPago) {
        ContentValues values = new ContentValues();
        values.put("dinero_necesita", dineroNecesita);
        values.put("años_pago", añoPago);

        String whereClause = "id = (SELECT MAX(id) FROM tablaFormulario where id = "+ userId +" )";

        return (sql.update("tablaFormulario", values, whereClause, null) > 0);
    }


    public String obtenerUltimoTipoVivienda() {
        String tipoVivienda = "";
        Cursor cursor = sql.rawQuery("SELECT Tipo_vivienda FROM tablaFormulario ORDER BY id DESC LIMIT 1", null);
        if(cursor.moveToFirst()){
            tipoVivienda = cursor.getString(0);
        }
        cursor.close();
        return tipoVivienda;
    }


    //Prestamo a solicitar

    public int obtenerDineroNecesita1() {
        int dinero_necesita = 0;
        Cursor cursor = sql.rawQuery("SELECT dinero_necesita FROM tablaFormulario  ORDER BY id DESC LIMIT 1", null);
        if(cursor.moveToFirst()){
            dinero_necesita = cursor.getInt(0);
        }
        cursor.close();
        return dinero_necesita;
    }

    public int obtenerDineroNecesita() {
        int dinero_necesita = 0;
        Cursor cursor = sql.rawQuery("SELECT dinero_necesita FROM tablaFormulario WHERE id = " + userId + " ORDER BY id DESC LIMIT 1", null);
        if(cursor.moveToFirst()){
            dinero_necesita = cursor.getInt(0);
        }
        cursor.close();
        return dinero_necesita;
    }



    //Meses del Prestamo

    public int obtenerTiempoPrestamo1() {
        int años_pago = 0;
        Cursor cursor = sql.rawQuery("SELECT años_pago FROM tablaFormulario  ORDER BY id DESC LIMIT 1", null);
        if(cursor.moveToFirst()){
            años_pago = cursor.getInt(0);
        }
        cursor.close();
        return años_pago;
    }

    public int obtenerTiempoPrestamo() {
        int años_pago = 0;
        Cursor cursor = sql.rawQuery("SELECT años_pago FROM tablaFormulario  WHERE id = " + userId + " ORDER BY id DESC LIMIT 1", null);
        if(cursor.moveToFirst()){
            años_pago = cursor.getInt(0);
        }
        cursor.close();
        return años_pago;
    }



}

