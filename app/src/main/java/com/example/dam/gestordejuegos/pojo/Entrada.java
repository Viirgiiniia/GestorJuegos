package com.example.dam.gestordejuegos.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.dam.gestordejuegos.contrato.ContratoBaseDeDatos;

/**
 * Created by dam on 22/3/17.
 */

public class Entrada implements Parcelable {
    private long idJuego, idEntrada;
    private String tipo, descripcion, imagen, fechaCrea, fechaModi;

    public Entrada( long idEntrada, long idJuego,String tipo, String descripcion, String imagen, String fechaCrea, String fechaModi) {
        this.idJuego = idJuego;
        this.idEntrada = idEntrada;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.fechaCrea = fechaCrea;
        this.fechaModi = fechaModi;
    }

    public Entrada() {
        this.idJuego = 0;
        this.idEntrada = 0;
        this.tipo = null;
        this.descripcion = null;
        this.imagen = null;
        this.fechaCrea = null;
        this.fechaModi = null;
    }

    protected Entrada(Parcel in) {
        idEntrada = in.readLong();
        idJuego = in.readLong();
        tipo = in.readString();
        descripcion = in.readString();
        imagen = in.readString();
        fechaCrea = in.readString();
        fechaModi = in.readString();
    }

    public static final Creator<Entrada> CREATOR = new Creator<Entrada>() {
        @Override
        public Entrada createFromParcel(Parcel in) {
            return new Entrada(in);
        }

        @Override
        public Entrada[] newArray(int size) {

            return new Entrada[size];
        }
    };

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public static Creator<Entrada> getCREATOR() {
        return CREATOR;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(long idJuego) {
        this.idJuego = idJuego;
    }

    public long getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(long idEntrada) {
        this.idEntrada = idEntrada;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(String fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public String getFechaModi() {
        return fechaModi;
    }

    public void setFechaModi(String fechaModi) {
        this.fechaModi = fechaModi;
    }

    @Override
    public String toString() {
        return "Entrada{" +

                "idEntrada=" + idEntrada +
                ",idJuego=" + idJuego +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen='" + imagen + '\'' +
                ", fechaCrea='" + fechaCrea + '\'' +
                ", fechaModi='" + fechaModi + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(idEntrada);
        dest.writeLong(idJuego);
        dest.writeString(tipo);
        dest.writeString(descripcion);
        dest.writeString(imagen);
        dest.writeString(fechaCrea);
        dest.writeString(fechaModi);
    }

    public ContentValues getCV() {
        return this.getCV(false);
    }

    public ContentValues getCV(boolean withId) {
        ContentValues valores = new ContentValues();

        if (withId) {
            valores.put(ContratoBaseDeDatos.TablaEntrada._ID, this.getIdEntrada());
        }

        valores.put(ContratoBaseDeDatos.TablaEntrada.ID_JUEGO, this.getIdJuego());
        valores.put(ContratoBaseDeDatos.TablaEntrada.TIPO, this.getTipo());
        valores.put(ContratoBaseDeDatos.TablaEntrada.DESCRIPCION, this.getDescripcion());
        valores.put(ContratoBaseDeDatos.TablaEntrada.IMAGEN, this.getImagen());
        valores.put(ContratoBaseDeDatos.TablaEntrada.FECHACREA, this.getFechaCrea());
        valores.put(ContratoBaseDeDatos.TablaEntrada.FECHAMODI, this.getFechaModi());

        return valores;
    }

    public static Entrada getEntrada(Cursor c) {
        Entrada objeto = new Entrada();


        objeto.setIdEntrada(c.getLong(c.getColumnIndex(ContratoBaseDeDatos.TablaEntrada._ID)));
        //System.out.println("id:"+objeto.getIdEntrada());
        objeto.setIdJuego(c.getLong(c.getColumnIndex(ContratoBaseDeDatos.TablaEntrada.ID_JUEGO)));
      //  System.out.println("idjuego: " + objeto.getIdJuego());
        objeto.setTipo(c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaEntrada.TIPO)));
       // System.out.println("tipo" + objeto.getTipo());
        objeto.setDescripcion(c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaEntrada.DESCRIPCION)));
       // System.out.println("descripcion" + objeto.getDescripcion());

        //System.out.println("no existe" + c.getColumnIndex(ContratoBaseDeDatos.TablaEntrada.IMAGEN));

        objeto.setImagen(c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaEntrada.IMAGEN)));

        //System.out.println("FOTO"+ objeto.getImagen());
        objeto.setFechaCrea(c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaEntrada.FECHACREA)));
        //System.out.println("fecha creacion"+objeto.getFechaCrea());
        objeto.setFechaModi(c.getString(c.getColumnIndex(ContratoBaseDeDatos.TablaEntrada.FECHAMODI)));
        //System.out.println("fecha modi"+objeto.getFechaModi());
        return objeto;
    }

    public void mostrarImagen(ImageButton ib){
        ib.setImageBitmap(BitmapFactory.decodeFile(getImagen()));
    }
    public void mostrarImagen(ImageView iv){
        iv.setImageBitmap(BitmapFactory.decodeFile(getImagen()));
    }


}
