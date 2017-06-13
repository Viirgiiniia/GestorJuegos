package com.example.dam.gestordejuegos.mvp.vista.modelo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;
import com.example.dam.gestordejuegos.contrato.ContratoJuego;
import com.example.dam.gestordejuegos.gestor.GestorEntrada;
import com.example.dam.gestordejuegos.gestor.GestorJuego;
import com.example.dam.gestordejuegos.gestor.GestorUnion;
import com.example.dam.gestordejuegos.pojo.Entrada;
import com.example.dam.gestordejuegos.pojo.Juego;

import java.util.ArrayList;

/**
 * Created by dam on 23/3/17.
 */

public class ModeloJuego implements ContratoJuego.InterfaceModelo {
    private GestorJuego gj;
    private GestorUnion gu;
    private GestorEntrada ge;
    private ContentResolver resolver;
    private Cursor cursor;
    private Uri uri = ContratoBaseDeDatos.CONTENT_URI_JUEGO;
    private Uri uriE = ContratoBaseDeDatos.CONTENT_URI_ENTRADA;


    public ModeloJuego(Context c) {
        gj = new GestorJuego(c);
        gu = new GestorUnion(c);
        ge=new GestorEntrada(c);
        this.resolver = c.getContentResolver();

    }

    @Override
    public long deleteItem(int pos) {
        return 0;
    }

    @Override
    public long deleteItem(Juego j) {
       return 0;
    }

    @Override
    public long deleteEntrada(int pos) {

        cursor.moveToPosition(pos);
        Entrada en = Entrada.getEntrada(cursor);

        return this.deleteEntrada(en);
    }

    @Override
    public long deleteEntrada(String s)
    {
        return ge.delete(s);
    }

    @Override
    public void close() {
        gj.close();
        gu.close();
        cursor.close();
        //

    }

    @Override
    public Juego getJuego(long id) {
        return gj.get(id);
    }

    @Override
    public long saveJuego(Juego j) {
        long r = 1;
        if (j.getId() == 0) {
            r = this.insertJuego(j);
        } else {
            r = this.updateJuego(j);
        }
        return r;
    }

    private long updateJuego(Juego j) {
        if (j.getTitulo().trim().compareTo("") == 0 && j.getDescripcion().trim().compareTo("") == 0) {
            this.deleteItem(j);
            String where = ContratoBaseDeDatos.TablaJuego._ID + "=?";
            String[] args = {j.getId() + ""};
            resolver.delete(uri, where, args);
            return 0;
        }
        String where = ContratoBaseDeDatos.TablaJuego._ID + "=?";
        String[] args = {j.getId() + ""};
        return resolver.update(uri, j.getContentValues(), where, args);
    }

    private long insertJuego(Juego j) {
        if (j.getTitulo().trim().compareTo("") == 0 && j.getDescripcion().trim().compareTo("") == 0) {
            return 0;
        }
        ContentValues valores = j.getContentValues();
        return Long.parseLong(this.resolver.insert(uri, j.getContentValues()).getLastPathSegment());
    }


    public ArrayList<Entrada> arrayEntradas(long id) {

        Cursor c = gu.getCursorId(id);

        ArrayList<Entrada> arr = new ArrayList<Entrada>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Entrada e = new Entrada(c.getLong(0), c.getLong(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));


            arr.add(e);
        }

        return arr;
    }


    public ArrayList<Entrada> tablaEntradas() {

        Cursor c = gu.getTablaEntrada();

        ArrayList<Entrada> arr = new ArrayList<Entrada>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Entrada e = new Entrada(c.getLong(0), c.getLong(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6));


            arr.add(e);
        }


        return arr;
    }

    public ArrayList<Juego> tablaJuego() {

        Cursor c = gu.getTablaJuego();
        ArrayList<Juego> arr = new ArrayList<Juego>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Juego j = new Juego(c.getLong(0), c.getLong(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6),c.getInt(7));


            arr.add(j);
        }


        return arr;
    }






    public Entrada darEntrada(String s){
        return  getEntrada(s);

    }

    public Entrada darEntrada(long id){
        return  getEntrada(id);

    }




    private long insertEntrada(Entrada l) {
        if (l.getDescripcion().trim().compareTo("") == 0) {
            return 0;
        }
        ContentValues valores = l.getCV();

        return Long.parseLong(this.resolver.insert(uri, l.getCV()).getLastPathSegment());

        //return gn.insert(n);
    }

    public long saveEntrada(Entrada l) {
        long r;
        if (l.getIdEntrada() == 0) {
            r = this.insertEntrada(l);//inserta la nota
        } else {
            r = this.updateEntrada(l);//modifica la nota
        }
        return r;
    }


    public long deleteEntrada(Entrada e) {

        String where = ContratoBaseDeDatos.TablaEntrada._ID + " = ? ";
        String[] argumentos = {e.getIdEntrada() + ""};
        return this.resolver.delete(uri, where, argumentos);
    }


    private long updateEntrada(Entrada e) {
        if (e.getDescripcion().trim().compareTo("") == 0) {
            this.deleteEntrada(e);

            String where = ContratoBaseDeDatos.TablaEntrada._ID + " = ? ";
            String[] arguments = {e.getIdEntrada() + ""};
            resolver.delete(uri, where, arguments);
        //    ge.delete(e);
            return 0;
        }
        String where = ContratoBaseDeDatos.TablaEntrada._ID + " = ? ";
        String[] arguments = {e.getIdEntrada() + ""};
        return resolver.update(uri, e.getCV(), where, arguments);
       // return ge.update(e);
    }


    public Entrada getEntrada(String s) {
        String where = ContratoBaseDeDatos.TablaEntrada.DESCRIPCION + " = ? ";
        String[] argumentos = {s + ""};
        Entrada e = ge.get(s);

        return e;

    }

    public Entrada getEntrada(long id) {

        Entrada e = ge.get(id);

        return e;

    }


}
