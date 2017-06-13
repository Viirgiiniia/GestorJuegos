package com.example.dam.gestordejuegos.dialogo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.dam.gestordejuegos.R;
import com.example.dam.gestordejuegos.pojo.Entrada;


/**
 * Created by dam on 5/12/16.
 */

public class DialogoBorrarEntrada extends DialogFragment {
    private Entrada e;
    // Interfaz de comunicación
    OnBorrarItemDialogListener listener;

    public DialogoBorrarEntrada() {
    }

    public static DialogoBorrarEntrada newInstance(Entrada e) {
        DialogoBorrarEntrada fragment = new DialogoBorrarEntrada();
        Bundle args = new Bundle();
        args.putParcelable("Entrada",e);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            e=getArguments().getParcelable("Entrada");
        }
    }
    //MUESTRA UN MENSAJE DE CONFIRMACION DE BORRAR
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogBorrar();
    }
    public AlertDialog createDialogBorrar() {
        String titulo_dialogo= String.format("%s %s", getString(R.string.etiqueta_dialogo_borrar),e.getTipo());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo_dialogo);
        builder.setMessage(R.string.mensaje_confirm_borrar);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onBorrarPossitiveButtonClick(e);//Borra
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onBorrarNegativeButtonClick();//No borra
            }
        });
        AlertDialog alertBorrar = builder.create();
        return alertBorrar;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnBorrarItemDialogListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnBorrarDialogListener");

        }
    }

}

