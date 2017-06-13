package com.example.dam.gestordejuegos.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;

import java.util.ArrayList;

/**
 * Created by dam on 22/3/17.
 */

public class Juego implements Parcelable {

    private long id, valoracion;
    private String titulo, imagen, fecha, lanzador, descripcion;
    private ArrayList<Entrada> entradas;
    private int preinstalado;


    public Juego(long id, long valoracion, String titulo, String imagen, String fecha, String lanzador, String descripcion, int preinstaldo) {
        this.id = id;
        this.valoracion = valoracion;
        this.titulo = titulo;
        this.imagen = imagen;
        this.fecha = fecha;
        this.lanzador = lanzador;
        this.descripcion = descripcion;
        entradas = new ArrayList<>();
        this.preinstalado = preinstaldo;

    }

    public Juego() {
        this.id = 0;
        this.valoracion = 0;
        this.titulo = null;
        this.imagen = null;
        this.fecha = null;
        this.lanzador = lanzador;
        this.descripcion = null;
        entradas = new ArrayList<>();
        this.preinstalado = 0;
    }


    public Juego(String titulo, String paquete, String icono) {
        this.id = 0;
        this.valoracion = 0;
        this.titulo = titulo;

        this.imagen = icono;
        this.fecha = null;
        this.lanzador = paquete;
        this.descripcion = null;
        entradas = new ArrayList<>();
        preinstalado = 1;
    }


    protected Juego(Parcel in) {
        id = in.readLong();
        valoracion = in.readLong();
        titulo = in.readString();
        imagen = in.readString();
        fecha = in.readString();
        lanzador = in.readString();
        descripcion = in.readString();
//        entradas = in.readArrayList(null);
        preinstalado = in.readInt();
    }

    public static final Creator<Juego> CREATOR = new Creator<Juego>() {
        @Override
        public Juego createFromParcel(Parcel in) {
            return new Juego(in);
        }

        @Override
        public Juego[] newArray(int size) {

            return new Juego[size];
        }
    };


    public int getPreinstalado() {
        return preinstalado;
    }

    public void setPreinstalado(int preinstalado) {
        this.preinstalado = preinstalado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        try {
            this.id = id;
        } catch (NumberFormatException e) {
            this.id = 0;
        }
    }

    public long getValoracion() {
        return valoracion;
    }

    public void setValoracion(long valoracion) {
        this.valoracion = valoracion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLanzador() {
        return lanzador;
    }

    public void setLanzador(String lanzador) {
        this.lanzador = lanzador;
    }

    public ArrayList<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayList<Entrada> entradas) {
        this.entradas = entradas;
    }

    @Override
    public String toString() {
        return "Juego{" +
                "id=" + id +
                ", valoracion=" + valoracion +
                ", titulo='" + titulo + '\'' +
                ", imagen='" + imagen + '\'' +
                ", fecha='" + fecha + '\'' +
                ", lanzador='" + lanzador + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", entradas=" + entradas +
                ", preinstalado=" + preinstalado +
                '}';
    }

    public ContentValues getContentValues() {
        return this.getContentValues(false);
    }

    public ContentValues getContentValues(boolean withId) {
        ContentValues valores = new ContentValues();
        if (withId) {
            valores.put(ContratoBaseDeDatos.TablaJuego._ID, this.getId());
        }
        valores.put(ContratoBaseDeDatos.TablaJuego.VALORACION, this.getValoracion());
        valores.put(ContratoBaseDeDatos.TablaJuego.TITULO, this.getTitulo());
        valores.put(ContratoBaseDeDatos.TablaJuego.IMAGEN, this.getImagen());
        valores.put(ContratoBaseDeDatos.TablaJuego.FECHA, this.getFecha());
        valores.put(ContratoBaseDeDatos.TablaJuego.LANZADOR, this.getLanzador());
        valores.put(ContratoBaseDeDatos.TablaJuego.DESCRIPCION, this.getDescripcion());
        valores.put(ContratoBaseDeDatos.TablaJuego.PREINSTALADO, this.getPreinstalado());

        return valores;
    }

    public static Juego getJuego(Cursor c) {
        Juego objeto = new Juego();

        objeto.setId(c.getLong(c.getColumnIndex(ContratoBaseDeDatos.TablaJuego._ID)));

        objeto.setValoracion(c.getLong(c.getColumnIndex(ContratoBaseDeDatos.TablaJuego.VALORACION)));

        objeto.setTitulo(c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaJuego.TITULO)));
        //System.out.println( " TITULO= [" +objeto.getTitulo());
        objeto.setImagen(c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaJuego.IMAGEN)));
        //System.out.println(" IMAGEN= [" +objeto.getImagen());
        objeto.setFecha(c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaJuego.FECHA)));
        //System.out.println(" FECHAn= [" +objeto.getFecha());

        //System.out.println(" valoracion= [" + objeto.valoracion+ "]");
//        System.out.println(c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaJuego.LANZADOR.toString())));
      try {
            objeto.setLanzador(c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaJuego.LANZADOR)));
    } catch (Exception e) {
            objeto.setLanzador("/");
    }
        //System.out.println(" LANZADOR= [" +objeto.lanzador);
        objeto.setDescripcion((c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaJuego.DESCRIPCION))));
        //System.out.println("DESCRIPCION= [" +objeto.descripcion);
        objeto.setPreinstalado((c.getInt(c.getColumnIndex(ContratoBaseDeDatos.TablaJuego.PREINSTALADO))));

        return objeto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(valoracion);
        dest.writeString(titulo);
        dest.writeString(imagen);
        dest.writeString(fecha);
        dest.writeString(lanzador);
        dest.writeString(descripcion);
        dest.writeList(entradas);
        dest.writeInt(preinstalado);
    }

    public void mostrarImagen(ImageButton ib) {
        ib.setImageBitmap(BitmapFactory.decodeFile(getImagen()));
    }

    public void mostrarImagen(ImageView iv) {
        iv.setImageBitmap(BitmapFactory.decodeFile(getImagen()));
    }

}
