package com.example.dam.gestordejuegos.util;

import com.example.dam.gestordejuegos.pojo.Juego;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dam on 7/6/17.
 */

public class Json {

    public String jsonAcadena(JSONObject json){

        return "";
    }

    public JSONObject cadenaAjson(String cadena){
        return null;
    }

    public JSONObject juegoAjson(Juego j){
        JSONObject jObj = null;
        try{
            jObj = new JSONObject();
            jObj.put("id",j.getId());
            jObj.put("titulo",j.getTitulo());
            jObj.put("descripcion",j.getDescripcion());
            jObj.put("imagen",j.getImagen());
            jObj.put("fecha",j.getFecha());
            jObj.put("valoracion",j.getValoracion());
            jObj.put("lanzador",j.getLanzador());
            jObj.put("preinstalado",j.getPreinstalado());
            return jObj;

        }catch (JSONException ex){}
        return null;
    }

    public String juego_json(Juego j){
        JSONObject jObj = null;
        try{
            jObj = new JSONObject();
            jObj.put("id",j.getId());
            jObj.put("titulo",j.getTitulo());
            jObj.put("descripcion",j.getDescripcion());
            jObj.put("imagen",j.getImagen());
            jObj.put("fecha",j.getFecha());
            jObj.put("valoracion",j.getValoracion());
            jObj.put("lanzador",j.getLanzador());
            jObj.put("preinstalado",j.getPreinstalado());
            return jObj.toString();

        }catch (JSONException ex){}
        return null;
    }

}
