package com.example.dam.gestordejuegos.contrato;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dam on 22/3/17.
 */

public class ContratoBaseDeDatos {
    public final static String AUTORIDAD="com.example.dam.gestordejuegos.contentprovider";
    public final static Uri CONTENT_URI= Uri.parse("content://"+AUTORIDAD);
    public final static Uri CONTENT_URI_JUEGO = Uri.withAppendedPath(CONTENT_URI, TablaJuego.TABLA);
    public final static Uri CONTENT_URI_ENTRADA = Uri.withAppendedPath(CONTENT_URI, TablaEntrada.TABLA);
    public final static String BASEDATOS="gestorjuegos.sqlite";

    private ContratoBaseDeDatos(){}

    public static abstract class  TablaJuego implements BaseColumns{
        public static final String TABLA= "juego";
        public static final String TITULO= "titulo";
        public static final String IMAGEN= "imagen";
        public static final String FECHA= "fecha";
        public static final String VALORACION= "valoracion";
        public static final String DESCRIPCION = "descripcion";
        public static final String LANZADOR = "lanzador" ;
        public static final String PREINSTALADO = "preinstalado" ;
        public static final String[] PROJECTION_ALL = {_ID, TITULO,IMAGEN,FECHA,VALORACION, DESCRIPCION,LANZADOR,PREINSTALADO};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";
        public static final String SORT_ORDER_TITULO = TITULO + " desc";
        public static final String SORT_ORDER_FECHA =FECHA + " desc";
        public static final String SORT_ORDER_VALORACION = VALORACION + " desc";

        //TIPOS MIME
        //CONCRETO
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
        // TODO
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;



    }


    public static abstract class TablaEntrada implements BaseColumns{
        public static final String TABLA= "entrada";
        public static final String ID_JUEGO= "id_juego";
        public static final String TIPO= "tipo";
        public static final String DESCRIPCION= "descripcion";
        public static final String IMAGEN = "imagen";
        public static final String FECHACREA= "fechacrea";
        public static final String FECHAMODI= "fechamodi";

        public static final String[] PROJECTION_ALL = {_ID, ID_JUEGO,TIPO,IMAGEN, DESCRIPCION, FECHACREA, FECHAMODI};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";

        //TIPOS MIME
        //CONCRETO
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
        //TODO
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
    }








}
