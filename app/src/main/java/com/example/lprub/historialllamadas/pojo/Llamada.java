package com.example.lprub.historialllamadas.pojo;

import android.content.ContentValues;

import com.example.lprub.historialllamadas.basedatos.ContratoCall;

import java.util.Date;

/**
 * Created by lprub on 27/01/2016.
 */
public class Llamada {
    private String numero;
    private Date fecha;

    public Llamada(String numero, Date fecha) {
        this.numero = numero;
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ContentValues getContentValuesEntrante(){
        ContentValues cv = new ContentValues();
        cv.put(ContratoCall.TablaEntrantes.NUMERO, this.numero);
        long tmp=fecha.getTime();
        cv.put(ContratoCall.TablaEntrantes.FECHA,tmp);
        return cv;
    }

    public ContentValues getContentValuesSaliente(){
        ContentValues cv = new ContentValues();
        cv.put(ContratoCall.TablaSalientes.NUMERO,this.numero);
        long tmp=fecha.getTime();
        cv.put(ContratoCall.TablaSalientes.FECHA,tmp);
        return cv;
    }

    public ContentValues getContentValuesPerdida(){
        ContentValues cv = new ContentValues();
        cv.put(ContratoCall.TablaPerdidas.NUMERO,this.numero);
        long tmp=fecha.getTime();
        cv.put(ContratoCall.TablaPerdidas.FECHA,tmp);
        return cv;
    }

}
