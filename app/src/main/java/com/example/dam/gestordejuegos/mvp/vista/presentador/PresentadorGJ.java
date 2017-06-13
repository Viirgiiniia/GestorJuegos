package com.example.dam.gestordejuegos.mvp.vista.presentador;

import android.content.Context;
import android.database.Cursor;

import com.example.dam.gestordejuegos.contrato.ContratoJuego;
import com.example.dam.gestordejuegos.contrato.ContratoMain;
import com.example.dam.gestordejuegos.mvp.vista.modelo.ModeloGJ;
import com.example.dam.gestordejuegos.mvp.vista.modelo.ModeloJuego;
import com.example.dam.gestordejuegos.pojo.Icono;
import com.example.dam.gestordejuegos.pojo.Juego;

import java.util.ArrayList;

/**
 * Created by dam on 28/3/17.
 */

public class PresentadorGJ implements ContratoMain.InterfazPresentador {

    private ContratoMain.InterfaceModelo modelo;
    private ContratoMain.InterfazVista vista;
    private ContratoMain.InterfaceModelo.OnDatosCargadosOyente oyente;
    private ContratoJuego.InterfaceModelo modeloJuego;

    public PresentadorGJ(ContratoMain.InterfazVista vista) {

        this.vista = vista;
        this.modeloJuego = new ModeloJuego((Context) vista);
        this.modelo = new ModeloGJ((Context) vista);

        oyente = new ContratoMain.InterfaceModelo.OnDatosCargadosOyente() {
            @Override
            public void setCursor(Cursor c) {
                PresentadorGJ.this.vista.mostrarJuegos(c);
            }
        };
    }


    @Override
    public void onAddJuego() {

    }

    @Override
    public void onAgregarJuego() {
        this.vista.mostrarAgregarJuego();

    }


    @Override
    public void onEditarJuego(int position) {
        Juego j = this.modelo.getJuego(position);
        this.vista.mostrarEditarJuego(j);
    }

    @Override
    public void onEditarJuego(Juego j) {
        this.vista.mostrarEditarJuego(j);
    }

    @Override
    public void onDeleteJuego(int position) {
        this.modelo.deleteJuego(position);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onDeleteJuego(Juego j) {
        this.modelo.deleteJuego(j);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onDeleteJuego(long id ) {
        this.modelo.deleteJuego(id);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onShowBorrarJuego(int position) {
        Juego n = this.modelo.getJuego(position);
        this.vista.mostrarBorrarJuego(n);
    }


    public int onBuscar(Juego j) {

        return this.modelo.buscarJuego(j);

    }


    public int totalJuegos() {
        return modeloJuego.tablaJuego().size();
    }

    private boolean repetido(Juego j) {

        Boolean repe = true;
        int cont = 0;
        if (j.getLanzador() == null || j.getPreinstalado() == 0 && j.getId() == 0) {

            repe = false;
        } else {

            ArrayList<Juego> todos = modeloJuego.tablaJuego();
            for (int i = 0; i < todos.size(); i++) {

                if (todos.get(i).getPreinstalado() == 0) {
                    if (j.getLanzador().compareTo(todos.get(i).getLanzador()) == 0) {
                        j.setPreinstalado(1);
                    }
                } else {

                    if (j.getPreinstalado() == 1) {

                        cont++;


                    } else {

                    }
                }
            }
            if (cont != 0) {
                System.out.println("repe");
                repe = true;
            } else {
                repe = false;
            }
        }

        System.out.println(cont);
        return repe;
    }


    public Juego darJuego(int position) {

        Juego j = this.modelo.getJuego(position);
        // System.out.println("dar juego , juego :" + n.toString());
        return j;
    }

    @Override
    public Juego darJuego(long id) {

        Juego j = this.modelo.getJuego(id);
        // System.out.println("dar juego , juego :" + n.toString());
        return j;
    }

    public Cursor recargar(String consulta) {
        return this.modelo.loadData2(oyente, consulta);
    }

    public Cursor getCursorOrdenado(String consulta) {
        return modelo.cursorPrueba(consulta);
    }

    public void aniadirApps(ArrayList<Juego> arr) {
        ArrayList<Icono> iconos = vista.getIconos();
        System.out.println("ICONOS-------" + iconos.toString());
        System.out.println("APPSPS..>" + arr.toString());
        for (int i = 0; i < arr.size(); i++) {
            if (repetido(arr.get(i))) {
                //PASA TOTALMENTE DEL JUEGO
            } else {
                arr.get(i).setPreinstalado(1);
                modeloJuego.saveJuego(arr.get(i));
            }//Guarda

        }
    }

}
