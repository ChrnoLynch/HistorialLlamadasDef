package com.example.lprub.historialllamadas.funciones;

import android.database.Cursor;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by lprub on 02/02/2016.
 */
public class Funciones {

    public static int[] contadorLlamadas(Cursor c) {
        int contadorD = 0;
        int contadorL = 0;
        int contadorM = 0;
        int contadorMi = 0;
        int contadorJ = 0;
        int contadorV = 0;
        int contadorS = 0;


        while (c.moveToNext()) {
            long fecha = c.getLong(c.getColumnIndex("fecha")); //Estamos en la columnna fecha
            int caso;
            Date cap=new Date(fecha);
            caso=cap.getDay();

            switch (caso) {
                case 0://Domingo
                    contadorD++;
                    break;

                case 1: //Lunes
                    contadorL++;
                    break;

                case 2: //Martes
                    contadorM++;
                    break;

                case 3: //Miercoles
                    contadorMi++;
                    break;

                case 4: //Jueves
                    contadorJ++;
                    break;

                case 5: //Viernes
                    contadorV++;
                    break;

                case 6: //Sabado
                    contadorS++;
                    break;

                default:
                    break;
            }
        }
        int[] result = {contadorD, contadorL, contadorM, contadorMi, contadorJ, contadorV, contadorS};
        return result;
    }
}
