package com.example.dam.gestordejuegos.mvp.vista.presentador;

import android.content.Context;

import com.example.dam.gestordejuegos.contrato.ContratoEntrada;
import com.example.dam.gestordejuegos.mvp.vista.modelo.ModeloEntrada;
import com.example.dam.gestordejuegos.pojo.Entrada;

/**
 * Created by dam on 23/3/17.
 */

public class PresentadorEntrada implements ContratoEntrada.InterfazPresentador {
    private ContratoEntrada.InterfazModelo modelo;
    private ContratoEntrada.InterfazVista vista;
    public PresentadorEntrada(ContratoEntrada.InterfazVista vista){
        this.vista=vista;
        this.modelo=new ModeloEntrada((Context)vista);
    }
    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public long onSaveEntrada(Entrada e) {
        return modelo.saveEntrada(e);
    }

    @Override
    public long onDeleteEntrada(Entrada e) {
        return modelo.deleteItem(e);
    }
}
