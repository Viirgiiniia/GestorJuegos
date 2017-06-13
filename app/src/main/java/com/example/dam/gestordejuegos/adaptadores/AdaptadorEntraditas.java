package com.example.dam.gestordejuegos.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dam.gestordejuegos.R;
import com.example.dam.gestordejuegos.pojo.Entrada;

import java.util.ArrayList;

/**
 * Created by dam on 4/4/17.
 */

public class AdaptadorEntraditas extends BaseAdapter {
    private Context context;
    private ArrayList<Entrada> items;

    public AdaptadorEntraditas(Context context, ArrayList<Entrada> items) {
        this.context = context;
        this.items = items;
    }


    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Entrada getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (convertView == null) {
            // Create a new view into the list.
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.card_entrada, parent, false);
        }


        ImageView imagen = (ImageView) rowView.findViewById(R.id.ivFoto);
        TextView contenido = (TextView) rowView.findViewById(R.id.tvDescripcion);
        TextView fCreacion = (TextView) rowView.findViewById(R.id.tvCreacion);
        TextView fModificacion = (TextView) rowView.findViewById(R.id.tvActualizacion);

        Entrada item = this.items.get(position);

        if (item.getImagen().compareTo("X")!=0) {
            imagen.setVisibility(View.VISIBLE);
            item.mostrarImagen(imagen);
        }else{
            imagen.setVisibility(View.GONE);
        }
        
        contenido.setText(item.getDescripcion());
        fCreacion.setText(item.getFechaCrea());
        fModificacion.setText(item.getFechaModi());


        return rowView;


    }
}
