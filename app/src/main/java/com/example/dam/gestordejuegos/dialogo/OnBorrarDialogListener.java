package com.example.dam.gestordejuegos.dialogo;


import com.example.dam.gestordejuegos.pojo.Juego;

/**
 * Created by Dam on 27/03/2017.
 */

public interface OnBorrarDialogListener {
    void onBorrarPossitiveButtonClick(Juego j);
    void onBorrarNegativeButtonClick();
}

