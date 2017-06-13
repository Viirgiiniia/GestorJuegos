package com.example.dam.gestordejuegos.mvp.vista.presentador;

import android.content.Context;

import com.example.dam.gestordejuegos.contrato.ContratoJuego;
import com.example.dam.gestordejuegos.mvp.vista.modelo.ModeloJuego;
import com.example.dam.gestordejuegos.pojo.Entrada;
import com.example.dam.gestordejuegos.pojo.Juego;

import java.util.ArrayList;

/**
 * Created by dam on 23/3/17.
 */

public class PresentadorJuego implements ContratoJuego.InterfazPresentador {
    private ContratoJuego.InterfaceModelo modelo;
    private ContratoJuego.InterfazVista vista;

    public PresentadorJuego(ContratoJuego.InterfazVista vista) {
        this.vista = vista;
        this.modelo = new ModeloJuego((Context) vista);
    }

    public ArrayList<Entrada> darArray(long id) {
        return modelo.arrayEntradas(id);
    }

    @Override
    public ArrayList<Entrada> tablaEntradas() {
       return modelo.tablaEntradas();
    }

    @Override
    public ArrayList<Juego> tablaJuego() {
        return modelo.tablaJuego();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onShowBorrarEntrada(int position) {

    }

    @Override
    public void onShowBorrarEntrada(String s) {
        modelo.deleteEntrada(s);
    }

    @Override
    public void onDeleteEntrada(int position) {
        modelo.deleteEntrada(position);

    }
public Entrada pasaEntrada(String s){
    return modelo.darEntrada(s);
}

    public Entrada pasaEntrada(long id){
        return modelo.darEntrada(id);
    }
    @Override
    public void onDeleteEntrada(Entrada e) {
        modelo.deleteEntrada(e);
    }

    @Override
    public long onSaveJuego(Juego j) {

        return this.modelo.saveJuego(j);
    }
    @Override
    public long onSaveEntrada(Entrada e) {

        return this.modelo.saveEntrada(e);
    }

    @Override
    public void onAddImagen() {
        this.vista.mostrarImagen();
    }
}
