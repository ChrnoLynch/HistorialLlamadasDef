package com.example.lprub.historialllamadas.basedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lprub on 12/01/2016.
 */
public class Ayudante extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="call.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        String sql="drop table if exists "
                + ContratoCall.TablaEntrantes.TABLAENTRANTES;
        db.execSQL(sql);
        String sq2="drop table if exists "
                + ContratoCall.TablaSalientes.TABLASALIENTES;
        db.execSQL(sq2);
        String sq3="drop table if exists "
                + ContratoCall.TablaPerdidas.TABLAPERDIDAS;
        db.execSQL(sq3);
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//Cuando se baja la aplicación y se crea por primera vez(no hay versión previa de la aplicación)-
        String sql;
        sql="create table "+ ContratoCall.TablaEntrantes.TABLAENTRANTES+ " ("+
                ContratoCall.TablaEntrantes._ID+ " integer primary key autoincrement, "+
                ContratoCall.TablaEntrantes.NUMERO+" text, "+
                ContratoCall.TablaEntrantes.FECHA+" int)";
                db.execSQL(sql);
        String sq2;
        sq2="create table "+ ContratoCall.TablaSalientes.TABLASALIENTES+ " ("+
                ContratoCall.TablaSalientes._ID+ " integer primary key autoincrement, "+
                ContratoCall.TablaSalientes.NUMERO+" text, "+
                ContratoCall.TablaSalientes.FECHA+" int)";
        db.execSQL(sq2);
        String sq3;
        sq3="create table "+ ContratoCall.TablaPerdidas.TABLAPERDIDAS+ " ("+
                ContratoCall.TablaPerdidas._ID+ " integer primary key autoincrement, "+
                ContratoCall.TablaPerdidas.NUMERO+" text, "+
                ContratoCall.TablaPerdidas.FECHA +" int)";
        db.execSQL(sq3);
    }
}
