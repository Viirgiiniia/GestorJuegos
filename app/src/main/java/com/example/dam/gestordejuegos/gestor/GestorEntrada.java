package com.example.dam.gestordejuegos.gestor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;
import com.example.dam.gestordejuegos.pojo.Entrada;

/**
 * Created by dam on 22/3/17.
 */

public class GestorEntrada extends Gestor<Entrada> {
    public GestorEntrada(Context c) {
        super(c);
    }

    public GestorEntrada(Context c, boolean write) {
        super(c, write);
    }

    @Override
    public long insert(Entrada objeto) {
      return  this.insert(ContratoBaseDeDatos.TablaEntrada.TABLA, objeto.getCV());
    }

    @Override
    public long insert(ContentValues objeto) {
        return this.insert(ContratoBaseDeDatos.TablaEntrada.TABLA, objeto);

    }

    @Override
    public int deleteAll() {
        return this.deleteAll(ContratoBaseDeDatos.TablaEntrada.TABLA);
    }

    @Override
    public int delete(Entrada objeto) {
        String condicion = ContratoBaseDeDatos.TablaEntrada.DESCRIPCION + " = ?";
        String[] argumentos = { objeto.getDescripcion() + "" };
        return this.delete(ContratoBaseDeDatos.TablaEntrada.TABLA, condicion, argumentos);
    }
    //@Override
    public int delete(String objeto) {
        String condicion = ContratoBaseDeDatos.TablaEntrada.DESCRIPCION + " = ?";
        String[] argumentos = { objeto + "" };
        return this.delete(ContratoBaseDeDatos.TablaEntrada.TABLA, condicion, argumentos);
    }

    @Override
    public int update(Entrada objeto) {
        ContentValues valores = objeto.getCV();
        String condicion = ContratoBaseDeDatos.TablaEntrada._ID + " = ?";
        String[] argumentos = { objeto.getIdEntrada() + "" };
        return this.update(ContratoBaseDeDatos.TablaEntrada.TABLA, valores, condicion, argumentos);
    }
    public int delete(String condicion, String[] argumentos){
        return this.delete(ContratoBaseDeDatos.TablaEntrada.TABLA, condicion, argumentos);
    }

    @Override
    public int update(ContentValues valores, String condicion, String[] argumentos) {
        return this.update(ContratoBaseDeDatos.TablaEntrada.TABLA, valores, condicion, argumentos);

    }


    @Override
    public Cursor getCursor() {
        return this.getCursor(ContratoBaseDeDatos.TablaEntrada.TABLA, ContratoBaseDeDatos.TablaEntrada.PROJECTION_ALL, ContratoBaseDeDatos.TablaEntrada.SORT_ORDER_DEFAULT
        );

    }

    @Override
    public Cursor getCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return this.getCursor(ContratoBaseDeDatos.TablaEntrada.TABLA, columns, selection, selectionArgs, groupBy, having, orderBy);

    }
    public Entrada get(long id){
        String where = ContratoBaseDeDatos.TablaEntrada._ID + " = ? ";
        String[] parametros = {id + ""};
        Cursor c = this.getCursor(ContratoBaseDeDatos.TablaEntrada.PROJECTION_ALL, where, parametros, null, null, ContratoBaseDeDatos.TablaEntrada.SORT_ORDER_DEFAULT);
        if(c.getCount() > 0) {
            c.moveToFirst();
            Entrada entrada = Entrada.getEntrada(c);
            return entrada;
        }
        return null;
    }
    public Entrada get(String s){
        String where = ContratoBaseDeDatos.TablaEntrada.DESCRIPCION + " = ? ";
        String[] parametros = {s + ""};
        Cursor c = this.getCursor(ContratoBaseDeDatos.TablaEntrada.PROJECTION_ALL, where, parametros, null, null, ContratoBaseDeDatos.TablaEntrada.SORT_ORDER_DEFAULT);
        if(c.getCount() > 0) {
            c.moveToFirst();
            Entrada entrada = Entrada.getEntrada(c);
            return entrada;
        }
        return null;
    }
}
