package com.example.dam.gestordejuegos.gestor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;
import com.example.dam.gestordejuegos.pojo.Entrada;

/**
 * Created by dam on 22/3/17.
 */

public class GestorUnion  extends Gestor {

    public GestorUnion(Context c) {
        super(c);
    }

    public GestorUnion(Context c, boolean write) {
        super(c, write);
    }

    @Override
    public long insert(Object objeto) {
        return 0;
    }

    @Override
    public long insert(ContentValues objeto) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    @Override
    public int delete(Object objeto) {
        return 0;
    }


    public int deleteItem(Entrada objeto) {
        String condicion = ContratoBaseDeDatos.TablaJuego._ID + "= ? ";
        String[] argumentos = { objeto.getIdEntrada() + "" };
        return this.delete(ContratoBaseDeDatos.TablaJuego.TABLA, condicion, argumentos);
    }



    @Override
    public int update(Object objeto) {
        return 0;
    }

    @Override
    public int update(ContentValues valores, String condicion, String[] argumentos) {
        return 0;
    }

    @Override
    public Cursor getCursor() {
        SQLiteDatabase bd= this.getBasedatos();
        String sql = "SELECT * FROM JUEGO UNION ALL SELECT * FROM ENTRADA";
        return bd.rawQuery(sql,null);
    }
    public Cursor getCursorId(long idJuego) {
        SQLiteDatabase bd= this.getBasedatos();
        String sql ="SELECT Entrada._ID ,Entrada.ID_JUEGO ,Entrada.TIPO,Entrada.DESCRIPCION,Entrada.IMAGEN ,Entrada.FECHACREA,Entrada.FECHAMODI" +
                " from Entrada  inner join juego on Entrada.id_juego = juego._id where Juego._id="+idJuego;
        System.out.println("BUSCO LA ID "+ idJuego);
        return bd.rawQuery(sql,null);
    }

    public Cursor getTablaEntrada() {
        SQLiteDatabase bd= this.getBasedatos();
        String sql ="SELECT * from Entrada ";
        return bd.rawQuery(sql,null);
    }
    public Cursor getTablaJuego() {
        SQLiteDatabase bd= this.getBasedatos();
        String sql ="SELECT * from Juego ";
        return bd.rawQuery(sql,null);
    }


    @Override
    public Cursor getCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return null;
    }
}
