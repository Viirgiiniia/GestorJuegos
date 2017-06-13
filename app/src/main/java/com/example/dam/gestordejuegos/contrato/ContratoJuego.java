package com.example.dam.gestordejuegos.contrato;

import com.example.dam.gestordejuegos.pojo.Entrada;
import com.example.dam.gestordejuegos.pojo.Juego;

import java.util.ArrayList;

/**
 * Created by dam on 22/3/17.
 */

public interface ContratoJuego {
    interface InterfaceModelo {

        Entrada getEntrada(long id);

        long deleteItem(int pos);

        long deleteItem(Juego j);

        long deleteEntrada(int pos);

        long deleteEntrada(Entrada e);

        void close();
        ArrayList<Juego> tablaJuego();
        Juego getJuego(long id);

        long saveJuego(Juego j);

        ArrayList arrayEntradas(long id);

        long deleteEntrada(String s);

        Entrada darEntrada(String s);

        Entrada darEntrada(long id);

        long saveEntrada(Entrada e);
        ArrayList<Entrada> tablaEntradas();
    }

    interface InterfazPresentador {
        ArrayList<Entrada> tablaEntradas();
        ArrayList<Juego> tablaJuego();
        void onPause();

        void onResume();

        void onShowBorrarEntrada(int position);

        void onShowBorrarEntrada(String s);

        void onDeleteEntrada(int position);

        void onDeleteEntrada(Entrada e);

        long onSaveJuego(Juego j);

        long onSaveEntrada(Entrada e);

        void onAddImagen();

        Entrada pasaEntrada(long id);

        Entrada pasaEntrada(String s);

        ArrayList darArray(long id);
    }

    interface InterfazVista {
        void mostrarJuego(Juego j);

        void mostrarImagen();
        ArrayList<Entrada> tablaEntradas();

        void mostrarConfirmarBorrarItem(Entrada e);

        void dividirArrayEntradas(ArrayList<Entrada> e);

        void darBrilloEstrellas();


    }
}
