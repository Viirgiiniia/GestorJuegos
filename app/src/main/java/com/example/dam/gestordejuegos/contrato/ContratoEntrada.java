package com.example.dam.gestordejuegos.contrato;

import android.database.Cursor;

import com.example.dam.gestordejuegos.pojo.Entrada;

/**
 * Created by dam on 22/3/17.
 */

public interface ContratoEntrada {
    interface  InterfazModelo{
        void close();
        Entrada getEntrada();
        Entrada getEntrada(String s);
        long deleteItem(int pos);

        long deleteItem(Entrada e);
        long saveEntrada(Entrada e);
        void loadData(OnDataLoadListener listener);
        interface OnDataLoadListener {
            public void setCursor(Cursor c);
        }

    }
    interface  InterfazPresentador{
        void onPause();
        void onResume();
        long onSaveEntrada(Entrada e);
        long onDeleteEntrada(Entrada e);
    }
    interface  InterfazVista{
        void mostrarEntrada(Entrada e);
    }

}
