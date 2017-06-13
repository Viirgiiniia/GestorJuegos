package com.example.dam.gestordejuegos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;

/**
 * Created by dam on 22/3/17.
 */

public class Ayudante extends SQLiteOpenHelper{

   public static final int VERSION= 2;


    public Ayudante(Context context) {
        super(context, ContratoBaseDeDatos.BASEDATOS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//crea la base de datos
        String sql;
        sql="create table if not exists " + ContratoBaseDeDatos.TablaJuego.TABLA +
                " (" +
                ContratoBaseDeDatos.TablaJuego._ID + " integer primary key autoincrement , " +
                ContratoBaseDeDatos.TablaJuego.TITULO + " text, " +
                ContratoBaseDeDatos.TablaJuego.DESCRIPCION +" text not null, "+
                ContratoBaseDeDatos.TablaJuego.IMAGEN + " text, " +
                ContratoBaseDeDatos.TablaJuego.FECHA + " text, " +
                ContratoBaseDeDatos.TablaJuego.VALORACION + " integer," +
                ContratoBaseDeDatos.TablaJuego.LANZADOR+" text,"+
                ContratoBaseDeDatos.TablaJuego.PREINSTALADO+" integer "+

                ")";
        Log.v("sql",sql);
        db.execSQL(sql);

        sql = "create table if not exists " + ContratoBaseDeDatos.TablaEntrada.TABLA +
                " (" +
                ContratoBaseDeDatos.TablaEntrada._ID + " integer primary key autoincrement, " +
                ContratoBaseDeDatos.TablaEntrada.ID_JUEGO + " integer, " +
                ContratoBaseDeDatos.TablaEntrada.TIPO+ " text, " +
                ContratoBaseDeDatos.TablaEntrada.DESCRIPCION +  " text, "+
                ContratoBaseDeDatos.TablaEntrada.IMAGEN + " text, " +
                ContratoBaseDeDatos.TablaEntrada.FECHACREA + " text, " +
                ContratoBaseDeDatos.TablaEntrada.FECHAMODI + " text "
                + ")";
        Log.v("sqlLista",sql);
        db.execSQL(sql);




    }

                          private static final String DATABASE_ALTER = "ALTER TABLE "
                        + ContratoBaseDeDatos.TablaJuego.TABLA + " ADD COLUMN " + ContratoBaseDeDatos.TablaJuego.PREINSTALADO + " integer" ;
    /*
                          private static final String DATABASE_ALTER2 = "ALTER TABLE "
                        + ContratoBaseDeDatos.TablaJuego.TABLA + " ADD COLUMN " + ContratoBaseDeDatos.TablaJuego.FECHA + " text;";
                        */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//modifica la base de datos

        if(oldVersion<newVersion){
                 db.execSQL(DATABASE_ALTER);
            //       db.execSQL(DATABASE_ALTER2);
        }
                    /*String sql="drop table if exists " + ContratoBaseDeDatos.TablaJuego.TABLA;
                    db.execSQL(sql);
                    sql="drop table if exists " + ContratoBaseDeDatos.TablaLista.TABLA;
                    db.execSQL(sql);
                    onCreate(db);*/
                    //Log.v("sql",sql);
                    //LO SUYO ES GUARDAR LOS DATOS Y ACTUALIZAR LA TABLA
                    //PASOS:
                    //CREAR TABLA TEMPORAL





                    //PASAR LOS DATOS A LA TABLA TEMPORAL
                    //BORRAR TABLA VIEJA
                    //CREAR TABLA NUEVA
                    //LLEVAR LOS DATOS DE LA TABLA TEMPORAL A LA TABLA NUEVA
                    //BORRAR LA TABLA TEMPORAL
    }
}

