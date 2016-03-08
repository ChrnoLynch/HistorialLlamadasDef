package com.example.lprub.historialllamadas.basedatos;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by lprub on 13/01/2016.
 */
public class ProveedorCall extends ContentProvider {

    //La Uri es cm una URL. LLegamos a un mismo sitio. UriMatcher establece si es un 1 haz una cosa, si es 2 haz otra cosa.
    public static final UriMatcher uriSwitch;
    public static final int ENTRANTES = 1;
    public static final int IDENTRANTES = 2;

    public static final int SALIENTES = 3;
    public static final int IDSALIENTES = 4;


    public static final int PERDIDAS = 5;
    public static final int IDPERDIDAS = 6;





    static{
        uriSwitch = new UriMatcher(UriMatcher.NO_MATCH);
        uriSwitch.addURI(ContratoCall.TablaEntrantes.AUTHORITY, ContratoCall.TablaEntrantes.TABLAENTRANTES, ENTRANTES);//Le damos la instrucción de qué hacer a la URI
        uriSwitch.addURI(ContratoCall.TablaSalientes.AUTHORITY, ContratoCall.TablaSalientes.TABLASALIENTES, SALIENTES);
        uriSwitch.addURI(ContratoCall.TablaPerdidas.AUTHORITY, ContratoCall.TablaPerdidas.TABLAPERDIDAS, PERDIDAS);
        uriSwitch.addURI(ContratoCall.TablaEntrantes.AUTHORITY, ContratoCall.TablaEntrantes.TABLAENTRANTES + "/#", IDENTRANTES);
        uriSwitch.addURI(ContratoCall.TablaSalientes.AUTHORITY, ContratoCall.TablaSalientes.TABLASALIENTES + "/#", IDSALIENTES);
        uriSwitch.addURI(ContratoCall.TablaPerdidas.AUTHORITY, ContratoCall.TablaPerdidas.TABLAPERDIDAS + "/#", IDPERDIDAS);

    }

    private Ayudante abd;

    @Override
    public boolean onCreate() {
        abd = new Ayudante(getContext());
        abd.getReadableDatabase();
        return true;
    }


    @Override
    public String getType(Uri uri) {//Devuelve el tipo mime que corresponde a la uri con que se ha llamado
        switch (uriSwitch.match(uri)){
            case ENTRANTES:
                return ContratoCall.TablaEntrantes.MJLTIPLE_MIME;
            case IDENTRANTES:
                return ContratoCall.TablaEntrantes.SINGLE_MIME;
            case SALIENTES:
                return ContratoCall.TablaSalientes.MJLTIPLE_MIME;
            case IDSALIENTES:
                return ContratoCall.TablaSalientes.SINGLE_MIME;
            case PERDIDAS:
                return ContratoCall.TablaPerdidas.MJLTIPLE_MIME;
            case IDPERDIDAS:
                return ContratoCall.TablaPerdidas.SINGLE_MIME;
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida " + uri);
        }
    }

    //METODO INSERT
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Comprobar que la uri utilizada para hacer la insercion es correcta
        if (uriSwitch.match(uri) != ENTRANTES && uriSwitch.match(uri) != SALIENTES && uriSwitch.match(uri) != PERDIDAS
                && uriSwitch.match(uri) != IDENTRANTES && uriSwitch.match(uri) != IDSALIENTES && uriSwitch.match(uri) != IDPERDIDAS) {
            throw new IllegalArgumentException("URI desconocida : " + uri + uriSwitch.match(uri) );//SI no es correcta la Uri
        }

        //Si el ContentValues es nulo, crea un ContentValues
        ContentValues contentValues;
        if (values == null) {
            throw new IllegalArgumentException("Resultados null ");
        }
        //Validar
        SQLiteDatabase db = abd.getWritableDatabase();//Conectamos a la base de datos en modo escritura
        // Inserción de nueva fila
        switch (uriSwitch.match(uri)) {
            case ENTRANTES:
            long rowId = db.insert(ContratoCall.TablaEntrantes.TABLAENTRANTES, null, values);
            if (rowId > 0) {
                //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                Uri uri_actividad = ContentUris.withAppendedId(ContratoCall.TablaEntrantes.CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(uri_actividad, null);
                return uri_actividad;
            }
            throw new SQLException("Error al insertar fila en : " + uri);

            case SALIENTES:

                long rowId2 = db.insert(ContratoCall.TablaSalientes.TABLASALIENTES, null, values);
                if (rowId2 > 0) {
                    //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                    Uri uri_actividad = ContentUris.withAppendedId(ContratoCall.TablaSalientes.CONTENT_URI, rowId2);
                    getContext().getContentResolver().notifyChange(uri_actividad, null);
                    return uri_actividad;
                }
                throw new SQLException("Error al insertar fila en : " + uri);

            case PERDIDAS:
                long rowId3 = db.insert(ContratoCall.TablaPerdidas.TABLAPERDIDAS, null, values);
                if (rowId3 > 0) {
                    //Si se ha insertado el elemento correctamente, entonces devolvemos la uri del elemento que se acaba de insertar
                    Uri uri_actividad = ContentUris.withAppendedId(ContratoCall.TablaPerdidas.CONTENT_URI, rowId3);
                    getContext().getContentResolver().notifyChange(uri_actividad, null);
                    return uri_actividad;
                }
                throw new SQLException("Error al insertar fila en : " + uri);
                
                default: return null;
        }
    }

    //METODO BORRAR
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = abd.getWritableDatabase();// Vuelve a abrir la base de datos para conectar con ella en modo escritura
        int match = uriSwitch.match(uri);//Obtengo la uri
        int affected;
        switch (match) {
            case ENTRANTES: //Muchas canciones
                affected = db.delete(ContratoCall.TablaEntrantes.TABLAENTRANTES, selection, selectionArgs);
                break;
            case SALIENTES:
                affected = db.delete(ContratoCall.TablaSalientes.TABLASALIENTES, selection, selectionArgs);
                break;
            case PERDIDAS:
                affected = db.delete(ContratoCall.TablaPerdidas.TABLAPERDIDAS, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        // Notificar cambio asociado a la urigetContext().getContentResolver().notifyChange(uri, null);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;//Devuelve el numero de filas borradas
         }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        // Obtener base de datos
        SQLiteDatabase db = abd.getReadableDatabase();
        // Comparar Uri
        int match = uriSwitch.match(uri);

        Cursor c;

        switch (match) {
            case ENTRANTES:
                // Consultando todos los registros
                if(sortOrder=="populares"){
                    //Query personalizada para sacar numeros populares
                    String inicial=selectionArgs[0];
                    String fin=selectionArgs[1];
                    String query="Select " + ContratoCall.TablaEntrantes.NUMERO + ", " +
                            ContratoCall.TablaEntrantes.FECHA + ", " + "count(numero)as repeticiones FROM " +
                            ContratoCall.TablaEntrantes.TABLAENTRANTES + " WHERE "+
                            ContratoCall.TablaEntrantes.FECHA + " >= " + inicial + " AND " +
                            ContratoCall.TablaEntrantes.FECHA + "<=" + fin + " GROUP BY numero ORDER BY repeticiones DESC limit 5";
                    c=db.rawQuery(query,null);}
                else {
                    c = db.query(ContratoCall.TablaEntrantes.TABLAENTRANTES, projection, selection, selectionArgs, null, null, sortOrder);
                }
                Log.v("Camino", "nos hemos metido por el camino del case CLIENTE_ID");
                break;
            case SALIENTES:
                // Consultando todos los registros
                if(sortOrder=="populares"){
                    //Query personalizada para sacar numeros populares
                    String inicial=selectionArgs[0];
                    String fin=selectionArgs[1];
                    System.out.println("FECHA MAL" + inicial);
                    System.out.println("FECHA FINAL MAL"+fin );
                    String query="Select " + ContratoCall.TablaSalientes.NUMERO + ", " +
                            ContratoCall.TablaSalientes.FECHA + ", " + "count(numero)as repeticiones FROM " +
                            ContratoCall.TablaSalientes.TABLASALIENTES + " WHERE "+
                            ContratoCall.TablaSalientes.FECHA + " >= " + inicial + " AND " +
                            ContratoCall.TablaSalientes.FECHA + "<=" + fin + " GROUP BY numero ORDER BY repeticiones DESC limit 5";
                    c=db.rawQuery(query,null);
                }else {
                    c = db.query(ContratoCall.TablaSalientes.TABLASALIENTES, projection, selection, selectionArgs, null, null, sortOrder);
                }
                break;
            case PERDIDAS:
                // Consultando todos los registros
                if(sortOrder=="populares"){
                    //Query personalizada para sacar numeros populares
                    String inicial=selectionArgs[0];
                    String fin=selectionArgs[1];
                    String query="Select " + ContratoCall.TablaPerdidas.NUMERO + ", " +
                            ContratoCall.TablaPerdidas.FECHA + ", " + "count(numero)as repeticiones FROM " +
                            ContratoCall.TablaPerdidas.TABLAPERDIDAS + " WHERE "+
                            ContratoCall.TablaPerdidas.FECHA + " >= " + inicial + " AND " +
                            ContratoCall.TablaPerdidas.FECHA + "<=" + fin + " GROUP BY numero ORDER BY repeticiones DESC limit 5";
                    c=db.rawQuery(query,null);}
                else {
                    c = db.query(ContratoCall.TablaPerdidas.TABLAPERDIDAS, projection, selection, selectionArgs, null, null, sortOrder);
                }
                Log.v("Camino", "nos hemos metido por el camino del case CLIENTE_ID");
                break;
            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        switch (match) {
            case ENTRANTES:
                c.setNotificationUri(getContext().getContentResolver(), ContratoCall.TablaEntrantes.CONTENT_URI);
                    break;
                case SALIENTES:
                    c.setNotificationUri(getContext().getContentResolver(), ContratoCall.TablaSalientes.CONTENT_URI);
                    break;
                case PERDIDAS:
                    c.setNotificationUri(getContext().getContentResolver(), ContratoCall.TablaPerdidas.CONTENT_URI);
                    break;
            }
        return c;
    }
}
