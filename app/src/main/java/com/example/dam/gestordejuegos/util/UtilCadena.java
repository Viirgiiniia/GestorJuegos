package com.example.dam.gestordejuegos.util;

/**
 * Created by dam on 23/3/17.
 */

public class UtilCadena {

    public static String getCondiciones (String CondicionesIni, String NewCondicion){

        return getCondiciones(CondicionesIni, NewCondicion, "and");
    }

    public static String getCondiciones(String CondicionesIni, String NewCondicion, String Conector){

        if(CondicionesIni==null || CondicionesIni.trim().length() == 0){
            return NewCondicion;
        }

        return CondicionesIni + " " + Conector + " " + NewCondicion;
    }

    public static String[] getNewArray(String[] ArrayInicial, String parametro){
        String[] newArray;

        if(ArrayInicial == null){
            return new String[]{parametro};
        }

        newArray = new String[ArrayInicial.length + 1];

        for (int i = 0; i< ArrayInicial.length; i++){
            newArray[i]=ArrayInicial[i];
        }

        newArray[ArrayInicial.length] = parametro;

        return newArray;
    }

}
