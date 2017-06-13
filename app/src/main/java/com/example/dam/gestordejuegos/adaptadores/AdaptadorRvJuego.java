package com.example.dam.gestordejuegos.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dam.gestordejuegos.R;
import com.example.dam.gestordejuegos.pojo.Icono;
import com.example.dam.gestordejuegos.pojo.Juego;

import java.util.ArrayList;

/**
 * Created by dam on 23/3/17.
 */

public class AdaptadorRvJuego extends CursorRecyclerViewAdapter<AdaptadorRvJuego.ajViewHolder> {


    private AdapterView.OnItemClickListener onItemClickListener;

    private AdapterView.OnItemLongClickListener onItemLongClickListener;
    private Context con;
    private ArrayList<Icono> arr;
    private Cursor cursor;

    public AdaptadorRvJuego(Context context, Cursor c, ArrayList<Icono> arr) {
        super(context, c);
        this.con = context;
        this.arr = arr;

    }

    public class ajViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

        public TextView tvTitulo;
        public ImageView ivImagen;
        public TextView tvFecha;
        public Juego juego;


        public ajViewHolder(View view) {
            super(view);
            tvTitulo = (TextView) view.findViewById(R.id.miTitulo);
            ivImagen = (ImageView) view.findViewById(R.id.miImagen);
            tvFecha = (TextView) view.findViewById(R.id.miFecha);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View v) {
            System.out.println("OnItemHolderClick");
            System.out.println(juego.getTitulo() + idJuego());

            onItemHolderClick(this);
        }

        public long idJuego() {
            return juego.getId();
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


    private void onItemHolderClick(ajViewHolder vh) {

        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(null, vh.itemView, vh.getAdapterPosition(), vh.idJuego());
            //  System.out.println("---"+vh.getAdapterPosition()+"...."+vh.idJuego());
        }
        // System.out.println("ItemHolder Adaptador");
    }

    private void onItemHolderLongClick(ajViewHolder vh) {
        if (onItemLongClickListener != null) {
            onItemLongClickListener.onItemLongClick(null, vh.itemView, vh.getAdapterPosition(), vh.idJuego());
        }
    }

    @Override
    public ajViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.miniatura, parent, false);

        ajViewHolder vh = new ajViewHolder(itemView);

        return vh;
    }


    @Override
    public void onBindViewHolder(ajViewHolder holder, Cursor cursor) {

        Juego j = Juego.getJuego(cursor);
        holder.juego = j;
        // System.out.println("-.-.-.-.-.-.--.-.-.-.--." +j.toString());
        holder.tvTitulo.setText(j.getTitulo());
        imagenDefecto(j.getImagen(), holder, j);
        holder.tvFecha.setText(j.getFecha().toString());

    }


    private void imagenDefecto(String s, ajViewHolder holder, Juego j) {

        if (j.getPreinstalado() == 1) {
            holder.ivImagen.setVisibility(View.VISIBLE);
            for (int i = 0; i < arr.size(); i++) {
                String titulo = arr.get(i).getApp();
                if (j.getTitulo().compareTo(titulo) == 0) {

                    holder.ivImagen.setImageDrawable(arr.get(i).getimg());
                }
            }


            //  holder.ivImagen.setImageDrawable(ContextCompat.getDrawable(con, android.R.drawable.ic_menu_agenda));
        } else {
            if (s.compareTo("X")==0 ) {
                holder.ivImagen.setImageDrawable(ContextCompat.getDrawable(con, android.R.drawable.ic_menu_camera));
            }
            if (s == null) {
                holder.ivImagen.setImageDrawable(ContextCompat.getDrawable(con, android.R.drawable.ic_menu_agenda));
            } else {
                if (s.compareTo("X") != 0) {
                    holder.ivImagen.setVisibility(View.VISIBLE);
                    Bitmap b = BitmapFactory.decodeFile(s);
                    holder.ivImagen.setImageBitmap(b);
                }

            }
        }

    }
    //public Cursor swapCursor(Cursor c){
    // return    super.swapCursor(c);
    //}

}



