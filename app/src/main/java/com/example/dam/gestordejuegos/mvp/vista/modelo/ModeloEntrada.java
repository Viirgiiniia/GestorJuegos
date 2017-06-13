package com.example.dam.gestordejuegos.mvp.vista.modelo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;
import com.example.dam.gestordejuegos.contrato.ContratoEntrada;
import com.example.dam.gestordejuegos.gestor.GestorEntrada;
import com.example.dam.gestordejuegos.pojo.Entrada;

/**
 * Created by dam on 23/3/17.
 */

public class ModeloEntrada implements ContratoEntrada.InterfazModelo {
    private GestorEntrada ge;
    Cursor cursor;
    private ContentResolver resolver;
    private Uri uri = ContratoBaseDeDatos.CONTENT_URI_ENTRADA;

    public ModeloEntrada(Context c) {
        ge = new GestorEntrada(c);
        this.resolver = c.getContentResolver();
    }

    @Override
    public void close() {

        ge.close();
        cursor.close();
    }

    @Override
    public Entrada getEntrada() {
        return null;
    }


    public Entrada getEntrada(String s) {
        String where = ContratoBaseDeDatos.TablaEntrada.DESCRIPCION + " = ? ";
        String[] argumentos = {s + ""};
        Cursor c = this.resolver.query(uri, null, where, argumentos, null);
        Entrada e = Entrada.getEntrada(c);
        return e;

    }


    @Override
    public long deleteItem(int pos) {
        cursor.moveToPosition(pos);
        Entrada e = Entrada.getEntrada(cursor);

        return this.deleteItem(e);

    }


    @Override
    public long deleteItem(Entrada e) {
        String where = ContratoBaseDeDatos.TablaEntrada._ID + " = ? ";
        String[] argumentos = {e.getIdEntrada() + ""};

        return this.resolver.delete(uri, where, argumentos);
    }

    @Override
    public long saveEntrada(Entrada e) {
        long r;
        if (e.getIdEntrada() == 0) {
            r = this.insertEntrada(e);
        } else {
            r = this.updateEntrada(e);
        }
        return r;
    }

    @Override
    public void loadData(OnDataLoadListener listener) {
        cursor = ge.getCursor();
        listener.setCursor(cursor);
    }


    private long updateEntrada(Entrada e) {
        if (e.getDescripcion().trim().compareTo("") == 0) {
            this.deleteItem(e);
            String where = ContratoBaseDeDatos.TablaEntrada._ID + "=?";
            String[] args = {e.getIdEntrada() + ""};
            resolver.delete(uri, where, args);
            return 0;
        }
        String where = ContratoBaseDeDatos.TablaEntrada._ID + "=?";
        String[] args = {e.getIdEntrada() + ""};
        return resolver.update(uri, e.getCV(), where, args);
    }

    private long insertEntrada(Entrada j) {
        if (j.getDescripcion().trim().compareTo("") == 0) {
            return 0;
        }
        ContentValues valores = j.getCV();

        return Long.parseLong(this.resolver.insert(uri, j.getCV()).getLastPathSegment());

    }

}
