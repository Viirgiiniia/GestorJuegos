package com.example.dam.gestordejuegos.pojo;

import android.graphics.drawable.Drawable;
import android.os.Parcel;

/**
 * Created by rodri on 27/05/2017.
 */

public class Icono {

    Drawable img;
    String app;

    public Icono(Drawable img, String app) {
        this.img = img;
        this.app = app;
    }

    public Drawable getimg() {
        return img;
    }

    public void setimg(Drawable img) {
        this.img = img;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    protected Icono(Parcel in) {
      //  img = in.read


    }

    @Override
    public String toString() {
        return "Icono{" +
                "img=" + img +
                ", app='" + app + '\'' +
                '}';
    }


}
