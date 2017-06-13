package com.example.dam.gestordejuegos.mvp.vista.modelo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;
import com.example.dam.gestordejuegos.contrato.ContratoMain;
import com.example.dam.gestordejuegos.gestor.GestorEntrada;
import com.example.dam.gestordejuegos.gestor.GestorJuego;
import com.example.dam.gestordejuegos.pojo.Juego;

/**
 * Created by dam on 28/3/17.
 */

public class ModeloGJ implements ContratoMain.InterfaceModelo {

    private ContentResolver resolver;
    private Cursor cursor;
    private Uri uri = ContratoBaseDeDatos.CONTENT_URI_JUEGO;
    private GestorEntrada ge;
    private GestorJuego gj;

    public ModeloGJ(Context vista) {
        ge = new GestorEntrada(vista);
        gj = new GestorJuego(vista);
        this.resolver = vista.getContentResolver();
    }


    @Override
    public void close() {
        cursor.close();

    }

    @Override
    public long deleteJuego(int position) {
        cursor = resolver.query(uri, null, null, null, null, null);
        cursor.moveToPosition(position);
        Juego j = Juego.getJuego(cursor);
        cursor.close();
        return this.deleteJuego(j);
    }

    @Override
    public long deleteJuego(Juego j) {
        String where = ContratoBaseDeDatos.TablaJuego._ID + " = ? ";
        String donde = ContratoBaseDeDatos.TablaEntrada.ID_JUEGO + " = ? ";
        String[] argumentos = {j.getId() + ""};


        return this.resolver.delete(uri, where, argumentos);
    }
    @Override
    public long deleteJuego(long id) {
        String where = ContratoBaseDeDatos.TablaJuego._ID + " = ? ";

        String[] argumentos = {id + ""};


        return this.resolver.delete(uri, where, argumentos);
    }

    @Override
    public Juego getJuego(int position) {
        cursor = resolver.query(uri, null, null, null, null, null);
        cursor.moveToPosition(position);
        //   System.out.println("En el modelo posicion" + position);
        Juego j = Juego.getJuego(cursor);
        String where = ContratoBaseDeDatos.TablaEntrada.ID_JUEGO + " = ? ";
        String[] argumentos = {j.getId() + ""};
        //  ge.delete(where,argumentos);
        // System.out.println("en el modelo , juego :" + j.toString());
        cursor.close();
        return j;
    }


    public Juego getJuego(long id) {
      /*  String where = ContratoBaseDeDatos.TablaJuego._ID + " = ? ";
        String[] args = {id + ""};
        cursor = resolver.query(uri,null,where,args,null);


        Juego j = Juego.getJuego(cursor);

        cursor.close();

        return j;*/
        return gj.get(id);

    }


    public int buscarJuego(Juego j) {
        String where = ContratoBaseDeDatos.TablaJuego._ID + " = ? ";
        String[] argumentos = {j.getTitulo() + ""};
        return this.resolver.delete(uri, where, argumentos);
    }


    @Override
    public void loadData(OnDatosCargadosOyente listener) {
        cursor = resolver.query(uri, null, null, null, null, null);
        listener.setCursor(cursor);
    }

    public Cursor loadData2(OnDatosCargadosOyente listener, String consulta) {
        if (consulta == "" || consulta.isEmpty() || consulta == null) {
            consulta = null;
        }

        cursor = resolver.query(uri, null, null, null, consulta, null);
        listener.setCursor(cursor);
        return cursor;
    }

    public Cursor cursorOrdenado(String consulta) {
        if (consulta == "" || consulta.isEmpty() || consulta == null) {
            consulta = null;
        }

        cursor = resolver.query(uri, null, null, null, consulta, null);

        return cursor;
    }

    public Cursor cursorPrueba(String consulta) {
        Cursor c;
        switch (consulta) {
            case ContratoBaseDeDatos.TablaJuego.FECHA:
                c = gj.getCursorOF();
                break;
            case ContratoBaseDeDatos.TablaJuego.VALORACION:
                c = gj.getCursorOV();
                break;
            case ContratoBaseDeDatos.TablaJuego.TITULO:
                c = gj.getCursorOT();
                break;
            default:
                c = gj.getCursor();
        }
        return c;
    }



}
