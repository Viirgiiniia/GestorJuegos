package com.example.dam.gestordejuegos.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dam.gestordejuegos.R;
import com.example.dam.gestordejuegos.pojo.Entrada;

import java.util.ArrayList;

/**
 * Created by dam on 18/5/17.
 */

public class AdaptadorEdicionDeEntradas extends RecyclerView.Adapter<AdaptadorEdicionDeEntradas.ViewHolder> {
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemLongClickListener onItemLongClickListener;
    private ArrayList<Entrada> array;


    public AdaptadorEdicionDeEntradas(ArrayList<Entrada> entradas) {

        this.array = entradas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView contenido;
        public ImageView imagen;
        public TextView modificacion;
        public TextView creacion;

        public ViewHolder(View v) {
            super(v);
            contenido = (TextView) v.findViewById(R.id.tvDescripcion);
            imagen = (ImageView) v.findViewById(R.id.ivFoto);
            modificacion = (TextView) v.findViewById(R.id.tvActualizacion);
            creacion = (TextView) v.findViewById(R.id.tvCreacion);

            v.setOnClickListener(this);
            v.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemHolderClick(this);
        }

        @Override
        public boolean onLongClick(View v) {
            onItemHolderLongClick(this);
            return false;
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }


    private void onItemHolderClick(AdaptadorEdicionDeEntradas.ViewHolder vh) {

        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, vh.itemView, vh.getAdapterPosition(), vh.getItemId());
        }
        // System.out.println("ItemHolder Adaptador");
    }

    private void onItemHolderLongClick(AdaptadorEdicionDeEntradas.ViewHolder vh) {
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(null, vh.itemView, vh.getAdapterPosition(), vh.getItemId());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_entrada, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, int position) {
        vh.creacion.setText(array.get(position).getFechaCrea());
        vh.modificacion.setText(array.get(position).getFechaModi());
        if(array.get(position).getImagen()==null){
            vh.imagen.setVisibility(View.GONE);
        }else{
        if (array.get(position).getImagen().compareTo("X")!=0) {

            vh.imagen.setVisibility(View.VISIBLE);
           array.get(position).mostrarImagen(vh.imagen);
        }else {
           vh.imagen.setVisibility(View.GONE);
        }
        vh.contenido.setText(array.get(position).getDescripcion());

    }}

    public String getDescripcion(int pos) {
        String s = array.get(pos).getDescripcion();
        return s;

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

}
