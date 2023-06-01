package com.example.draft_tfg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class LoginTable {


    Context c;
    ListaLogin u;
    ArrayList<ListaLogin> lista;
    SQLiteDatabase sql;
    String bd = "BDTFG";
    String tabla = "create table if not exists tablaUsuario(id integer primary key autoincrement, nombre text, apellido text, usuario TEXT UNIQUE, contrasena text)";

    public LoginTable(Context c){
        this.c =c;
        sql = c.openOrCreateDatabase(bd, c.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        u= new ListaLogin();
    }

  public boolean insertUsuario(ListaLogin u){
        if (buscar(u.getUsuario())==0){
            ContentValues values = new ContentValues();
            values.put("nombre",u.getNombre());
            values.put("apellido",u.getApellido());
            values.put("usuario",u.getUsuario());
            values.put("contrasena",u.getContrasena());
            return (sql.insert("tablaUsuario",null,values)>0);
        }else {
            return false;
        }
    }

    public  int buscar(String u){
        int x=0;
        lista=selectUsuario();
        for (ListaLogin us:lista){
            if (us.getUsuario().equals(u)){
                x++;
            }
        }
        return x;
    }

    public  ArrayList<ListaLogin> selectUsuario(){
        ArrayList<ListaLogin> lista = new ArrayList<ListaLogin>();
        lista.clear();
        Cursor cr=sql.rawQuery("select * from tablaUsuario", null);
        if (cr!=null&&cr.moveToFirst()){
            do {
                ListaLogin u = new ListaLogin();
                u.setId(cr.getInt(0));
                u.setNombre(cr.getString(1));
                u.setApellido(cr.getString(2));
                u.setUsuario(cr.getString(3));
                u.setContrasena(cr.getString(4));
                lista.add(u);
            }while (cr.moveToNext());
        }
        return lista;
    }

    public int login(String u, String p){
        int a=0;
        Cursor cr=sql.rawQuery("select * from tablaUsuario", null);
        if (cr!=null&&cr.moveToFirst()){
            do {
                if (cr.getString(3).equals(u)&&cr.getString(4).equals(p)){
                    a++;

                }
            }while (cr.moveToNext());
        }
        return a;
    }

    public int getIdByUsuario(String u) {
        int id = -1; // Valor predeterminado si no se encuentra el usuario
        Cursor cr = sql.rawQuery("SELECT id FROM tablaUsuario WHERE usuario = ?", new String[]{u});
        if (cr != null && cr.moveToFirst()) {
            id = cr.getInt(0);
        }
        cr.close();
        return id;
    }



    public ListaLogin getUsuario(String u, String p){
        lista=selectUsuario();
        for (ListaLogin us:lista){
            if (us.getUsuario().equals(u)&&us.getContrasena().equals(p)){
                return us;
            }
        }
        return null;
    }

    public ListaLogin getUsuarioById(int id){
        lista=selectUsuario();
        for (ListaLogin us:lista){
            if (us.getId()==id){
                return us;
            }
        }
        return null;
    }


}

