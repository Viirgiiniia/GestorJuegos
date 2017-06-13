package com.example.dam.gestordejuegos.gestor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;
import com.example.dam.gestordejuegos.pojo.Juego;

/**
 * Created by dam on 22/3/17.
 */

public class GestorJuego extends Gestor<Juego> {

    public GestorJuego(Context c) {
        super(c);
    }

    public GestorJuego(Context c, boolean write) {
        super(c, write);
    }

    public Juego get(long id){
        String where =ContratoBaseDeDatos.TablaJuego._ID+ " = ? ";
        String[] parametros = {id + ""};
        Cursor c = this.getCursor(ContratoBaseDeDatos.TablaJuego.PROJECTION_ALL, where, parametros, null, null, ContratoBaseDeDatos.TablaJuego.SORT_ORDER_DEFAULT);
        if(c.getCount() > 0) {
            c.moveToFirst();
            Juego juego = Juego.getJuego(c);
            return juego;
        }
        return null;
    }



    @Override
    public long insert(Juego objeto) {
        return this.insert(ContratoBaseDeDatos.TablaJuego.TABLA, objeto.getContentValues());
    }

    @Override
    public long insert(ContentValues objeto) {
        return this.insert(ContratoBaseDeDatos.TablaJuego.TABLA, objeto);
    }

    @Override
    public int deleteAll() {
        return this.deleteAll(ContratoBaseDeDatos.TablaJuego.TABLA);
    }

    @Override
    public int delete(Juego objeto) {
        String condicion = ContratoBaseDeDatos.TablaJuego._ID + "= ? ";
        String[] argumentos = { objeto.getId() + "" };
        return this.delete(ContratoBaseDeDatos.TablaJuego.TABLA, condicion, argumentos);
    }


    public int delete(String condicion, String[] argumentos) {
        return this.delete(ContratoBaseDeDatos.TablaJuego.TABLA, condicion, argumentos);
    }

    @Override
    public int update(Juego objeto) {
        ContentValues valores = objeto.getContentValues();
        String condicion = ContratoBaseDeDatos.TablaJuego._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        return this.update(ContratoBaseDeDatos.TablaJuego.TABLA, valores, condicion, argumentos);
    }

    @Override
    public int update(ContentValues valores, String condicion, String[] argumentos) {
        return this.update(ContratoBaseDeDatos.TablaJuego.TABLA, valores, condicion, argumentos);
    }

    @Override
    public Cursor getCursor() {
        return this.getCursor(ContratoBaseDeDatos.TablaJuego.TABLA, ContratoBaseDeDatos.TablaJuego.PROJECTION_ALL, ContratoBaseDeDatos.TablaJuego.SORT_ORDER_DEFAULT);

    }

    public Cursor getCursorOF() {
        return this.getCursor(ContratoBaseDeDatos.TablaJuego.TABLA, ContratoBaseDeDatos.TablaJuego.PROJECTION_ALL, ContratoBaseDeDatos.TablaJuego.SORT_ORDER_FECHA);

    }

    public Cursor getCursorOT() {
        return this.getCursor(ContratoBaseDeDatos.TablaJuego.TABLA, ContratoBaseDeDatos.TablaJuego.PROJECTION_ALL, ContratoBaseDeDatos.TablaJuego.SORT_ORDER_TITULO);

    }

    public Cursor getCursorOV() {
        return this.getCursor(ContratoBaseDeDatos.TablaJuego.TABLA, ContratoBaseDeDatos.TablaJuego.PROJECTION_ALL, ContratoBaseDeDatos.TablaJuego.SORT_ORDER_VALORACION);

    }

    @Override
    public Cursor getCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {


        return this.getCursor(ContratoBaseDeDatos.TablaJuego.TABLA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }


}
