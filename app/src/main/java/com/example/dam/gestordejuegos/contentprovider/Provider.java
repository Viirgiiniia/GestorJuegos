package com.example.dam.gestordejuegos.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;
import com.example.dam.gestordejuegos.gestor.GestorEntrada;
import com.example.dam.gestordejuegos.gestor.GestorJuego;
import com.example.dam.gestordejuegos.gestor.GestorUnion;
import com.example.dam.gestordejuegos.util.UtilCadena;

/**
 * Created by dam on 23/3/17.
 */


public class Provider extends ContentProvider {

    //URI_MATCHER
    private static final UriMatcher URI_MATCHER;

    //CURSORES
    private static final int TODO_JUEGO = 0;
    private static final int CONCRETO_JUEGO = 1;
    private static final int TODO_ENTRADA = 2;
    private static final int CONCRETO_ENTRADA = 3;


    //GESTORES
    private static GestorJuego gj;
    private static GestorEntrada ge;
    private static GestorUnion gu;


    //CONSTRUCTOR ESTATICO
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        //CURSOR 0 TABLA NOTAS
        URI_MATCHER.addURI(ContratoBaseDeDatos.AUTORIDAD, ContratoBaseDeDatos.TablaJuego.TABLA, TODO_JUEGO);
        //CURSOR 1
        URI_MATCHER.addURI(ContratoBaseDeDatos.AUTORIDAD, ContratoBaseDeDatos.TablaJuego.TABLA + "/#", CONCRETO_JUEGO);
        //CURSOR 2 TABLA LISTAS
        URI_MATCHER.addURI(ContratoBaseDeDatos.AUTORIDAD, ContratoBaseDeDatos.TablaEntrada.TABLA, TODO_ENTRADA);
        //CURSOR 3
        URI_MATCHER.addURI(ContratoBaseDeDatos.AUTORIDAD, ContratoBaseDeDatos.TablaEntrada.TABLA + "/#", CONCRETO_ENTRADA);
    }

    public Provider() {
    }

    @Override
    public boolean onCreate() {
        gj = new GestorJuego(getContext());
        gj.getCursor();
        ge = new GestorEntrada(getContext());
        gu = new GestorUnion(getContext());


        return true;


    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c;

        int tipo = URI_MATCHER.match(uri);
        String id;
        String descripcion;
        String tabla;

        if (tipo < 0) {
            throw new IllegalArgumentException("Error no devuelve QUERY");
        }
        if (tipo == CONCRETO_JUEGO) {
            id = uri.getLastPathSegment();
            selection = UtilCadena.getCondiciones(selection, ContratoBaseDeDatos.TablaJuego._ID + " = ?");
            selectionArgs = UtilCadena.getNewArray(selectionArgs, id);
            tabla = ContratoBaseDeDatos.TablaJuego.TABLA;
        } else if (tipo == CONCRETO_ENTRADA) {
            id = uri.getLastPathSegment();
            selection = UtilCadena.getCondiciones(selection, ContratoBaseDeDatos.TablaEntrada._ID + " = ?");
            selectionArgs = UtilCadena.getNewArray(selectionArgs, id);
            tabla = ContratoBaseDeDatos.TablaEntrada.TABLA;
        } else if (tipo == CONCRETO_ENTRADA) {
            descripcion = uri.getLastPathSegment();
            selection = UtilCadena.getCondiciones(selection, ContratoBaseDeDatos.TablaEntrada.DESCRIPCION + " = ?");
            selectionArgs = UtilCadena.getNewArray(selectionArgs, descripcion);
            tabla = ContratoBaseDeDatos.TablaEntrada.TABLA;
        } else if (tipo == TODO_JUEGO) {
            selection = null;
            selectionArgs = null;
            tabla = ContratoBaseDeDatos.TablaJuego.TABLA;

        } else {
            throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = ContratoBaseDeDatos.TablaEntrada._ID + " DESC";
        }
        System.out.println("aqui --->"+sortOrder);
        c = gj.getCursor(tabla, projection, selection, selectionArgs, null, null, sortOrder);

        //c = gj.getCursorOT();
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        switch (URI_MATCHER.match(uri)) {
            case TODO_JUEGO:
                return ContratoBaseDeDatos.TablaJuego.CONTENT_TYPE;
            case TODO_ENTRADA:
                return ContratoBaseDeDatos.TablaEntrada.CONTENT_TYPE;
            case CONCRETO_JUEGO:
                return ContratoBaseDeDatos.TablaJuego.CONTENT_ITEM_TYPE;
            case CONCRETO_ENTRADA:
                return ContratoBaseDeDatos.TablaEntrada.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int tipo = URI_MATCHER.match(uri);
        long id = 0;

        switch (tipo) {
            case TODO_JUEGO:
                id = gj.insert(values);
                break;
            case TODO_ENTRADA:
                id = ge.insert(values);
                break;
            //default: throw  new IllegalArgumentException("Error de inserción Uri desconocida: " + uri);
        }

        if (id > 0) {
            Uri uriGelle = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(uriGelle, null);
            return uriGelle;
        }
        throw new IllegalArgumentException("Error de insercion de fila en: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int tipo = URI_MATCHER.match(uri);
        int borrados = 0;
        String id;
        String newSelection;

        //gestor = new GestionNota(getContext());

        switch (tipo) {

            case CONCRETO_JUEGO:

                id = uri.getLastPathSegment();
                borrados = gj.delete(ContratoBaseDeDatos.TablaJuego._ID + " = ?", new String[]{id});
                //newSelection = UtilCadena.getCondiciones(selection, ContratoBaseDeDatos.TablaJuego._ID + " = ?");
                break;

            case CONCRETO_ENTRADA:

                id = uri.getLastPathSegment();
                borrados = gj.delete(ContratoBaseDeDatos.TablaEntrada._ID + " = ?", new String[]{id});
                //newSelection = UtilCadena.getCondiciones(selection,ContratoBaseDeDatos.TablaEntrada._ID + " = ?");
                break;

            case TODO_JUEGO:

                borrados = gj.delete(selection, selectionArgs);
                break;

            case TODO_ENTRADA:

                borrados = ge.delete(selection, selectionArgs);

                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " + uri);
        }

        if (borrados > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return borrados;
    }


    public Cursor getEntradas(long idJuego) {
        return gu.getCursorId(idJuego);

    }


    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        int tipo = URI_MATCHER.match(uri);
        int valor;

        String id = uri.getLastPathSegment();

        if (tipo < 0) {
            throw new IllegalArgumentException("Error de UPDATE: " + uri);
        } else if (tipo == CONCRETO_JUEGO) {
            selection = UtilCadena.getCondiciones(selection, ContratoBaseDeDatos.TablaJuego._ID + " = ?");
            selectionArgs = UtilCadena.getNewArray(selectionArgs, id);
        } else if (tipo == CONCRETO_ENTRADA) {
            selection = UtilCadena.getCondiciones(selection, ContratoBaseDeDatos.TablaEntrada._ID + " = ?");
            selectionArgs = UtilCadena.getNewArray(selectionArgs, id);
        }
        try {
             valor = gj.update(values, selection, selectionArgs);
            if (valor > 0) {
                getContext().getContentResolver().notifyChange(uri, null);

            }
        } catch (Exception e) {
             valor = ge.update(values, selection, selectionArgs);
            if (valor > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }

        }
        return valor;
    }
    }
