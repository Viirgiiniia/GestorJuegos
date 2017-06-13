package com.example.dam.gestordejuegos.mvp.vista.vista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dam.gestordejuegos.R;
import com.example.dam.gestordejuegos.contrato.ContratoEntrada;
import com.example.dam.gestordejuegos.pojo.Entrada;

/**
 * Created by dam on 23/3/17.
 */

public class VistaEntrada extends AppCompatActivity implements ContratoEntrada.InterfazVista {
TextView tvTitulo, tvCreacion,tvModificacion,tvDescripcion;
    ImageView ivFoto;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_entrada);
        tvCreacion.findViewById(R.id.tvCreacion);
        tvDescripcion.findViewById(R.id.tvDescripcion);

        tvModificacion.findViewById(R.id.tvActualizacion);
        ivFoto.findViewById(R.id.ivFoto);
   /*    ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abre la galeria y elige otra y tal
            }
        });*/
    }

    @Override
    public void mostrarEntrada(Entrada e) {

    }

}
