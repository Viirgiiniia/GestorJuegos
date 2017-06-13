package com.example.dam.gestordejuegos.contrato;

import android.database.Cursor;

import com.example.dam.gestordejuegos.pojo.Icono;
import com.example.dam.gestordejuegos.pojo.Juego;

import java.util.ArrayList;

/**
 * Created by dam on 22/3/17.
 */

public interface ContratoMain {

    interface InterfaceModelo {
        Cursor loadData2(OnDatosCargadosOyente listener, String consulta);

        void close();

        long deleteJuego(int position);

        long deleteJuego(Juego j);
        long deleteJuego(long id);

        Juego getJuego(int position);
        Juego getJuego(long id);

        void loadData(OnDatosCargadosOyente listener);

        interface OnDatosCargadosOyente {
            public void setCursor(Cursor c);
        }

        int buscarJuego(Juego j);

        Cursor cursorOrdenado(String consulta);
        Cursor cursorPrueba(String consulta);

    }


    interface InterfazPresentador {
        Cursor recargar(String consulta);


        void onAddJuego();

        void onAgregarJuego();

        int onBuscar(Juego j);

        void onEditarJuego(int position);

        void onEditarJuego(Juego j);

        void onDeleteJuego(int position);

        void onDeleteJuego(Juego n);
        void onDeleteJuego(long id);

        void onShowBorrarJuego(int position);

        int totalJuegos();
        Juego darJuego(long id);
        Juego darJuego(int pos);

        Cursor getCursorOrdenado(String cnslta);

    }

    interface InterfazVista {

        ArrayList<Icono> getIconos();

        void mostrarAgregarJuego();

        void mostrarJuegos(Cursor c);

        void mostrarEditarJuego(Juego j);

        void mostrarBorrarJuego(Juego j);
    }
}
