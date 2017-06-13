package com.example.dam.gestordejuegos.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dam.gestordejuegos.pojo.Entrada;
import com.example.dam.gestordejuegos.R;

import java.util.ArrayList;

/**
 * Created by dam on 23/3/17.
 */

public class AdaptadorRvEntrada extends RecyclerView.Adapter<AdaptadorRvEntrada.aeViewHolder> {
    private ArrayList<Entrada> entradas;

    @Override
    public aeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_entrada, parent, false);
        aeViewHolder ae = new aeViewHolder(v);
        return ae;
    }

    @Override
    public void onBindViewHolder(aeViewHolder holder, int pos) {

        holder.tvDescripcion.setText(entradas.get(pos).getDescripcion());
        if (entradas.get(pos).getImagen().compareTo("X") != 0) {
            holder.ivFoto.setVisibility(View.VISIBLE);
            entradas.get(pos).mostrarImagen(holder.ivFoto);
        }else{
            holder.ivFoto.setVisibility(View.GONE);
        }
        holder.tvActualizacion.setText(entradas.get(pos).getFechaModi());
        holder.tvCreacion.setText(entradas.get(pos).getFechaCrea());
    }


    @Override
    public int getItemCount() {
        return entradas.size();
    }

    public class aeViewHolder extends RecyclerView.ViewHolder {


        public ImageView ivFoto;
        public TextView tvDescripcion;
        public TextView tvActualizacion;
        public TextView tvCreacion;

        public aeViewHolder(View v) {
            super(v);


            ivFoto = (ImageView) v.findViewById(R.id.ivFoto);
            tvDescripcion = (TextView) v.findViewById(R.id.tvDescripcion);
            tvActualizacion = (TextView) v.findViewById(R.id.tvActualizacion);
            tvCreacion = (TextView) v.findViewById(R.id.tvCreacion);


        }


    }


}
