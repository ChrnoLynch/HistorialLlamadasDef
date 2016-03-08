package com.example.lprub.historialllamadas.basedatos;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by lprub on 13/01/2016.
 */
public class ContratoCall {

    private ContratoCall() {
    }

    public static abstract class TablaPerdidas implements BaseColumns {

        public static final String TABLAPERDIDAS = "Perdidas";
        public static final String NUMERO = "numero";
        public static final String FECHA = "fecha";

        //La autoridad es la cadena q identifica a qué contentprovider se llama
        public final static String AUTHORITY = "com.example.lprub.historialllamadas.basedatos.ProveedorCall";
        //Definir como llego a la tabla cliente (a q tabla estoy llegando)
        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLAPERDIDAS);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLAPERDIDAS;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLAPERDIDAS;
    }

    public static abstract class TablaEntrantes implements BaseColumns {
        public static final String  TABLAENTRANTES = "Entrantes";

        public static final String  NUMERO = "numero";
        public static final String  FECHA = "fecha";

        public final static String AUTHORITY = "com.example.lprub.historialllamadas.basedatos.ProveedorCall";

        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLAENTRANTES);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLAENTRANTES;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLAENTRANTES;
    }

    public static abstract class TablaSalientes implements BaseColumns {
        public static final String  TABLASALIENTES= "Salientes";
        public static final String  NUMERO = "numero";
        public static final String  FECHA = "fecha";


        public final static String AUTHORITY = "com.example.lprub.historialllamadas.basedatos.ProveedorCall";

        public final static Uri CONTENT_URI =
                Uri.parse("content://" + AUTHORITY + "/" + TABLASALIENTES);
        public final static String SINGLE_MIME =
                "vnd.android.cursor.item/vnd." + AUTHORITY + TABLASALIENTES;
        public final static String MJLTIPLE_MIME =
                "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLASALIENTES;

    }

}
