package com.example.lprub.historialllamadas.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.example.lprub.historialllamadas.basedatos.ContratoCall;
import com.example.lprub.historialllamadas.pojo.Llamada;

import java.util.Date;

/**
 * Created by 2dam on 13/01/2016.
 */
public class ReceptorLlamada extends BroadcastReceiver {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date fecha;
    private static boolean isIncoming;
    private static String numero;
    private static String numeroSaliente;

    private boolean regLlamadaEntrante(Context context, String numero, Date fecha){
        Llamada call=new Llamada(numero,fecha);
        context.getContentResolver().insert(ContratoCall.TablaEntrantes.CONTENT_URI,call.getContentValuesEntrante());
        return true;
    }
    private boolean regLlamadaSaliente(Context context, String numero, Date fecha){
        Llamada call=new Llamada(numero,fecha);
        context.getContentResolver().insert(ContratoCall.TablaSalientes.CONTENT_URI,call.getContentValuesSaliente());
        return true;
    }
    private boolean regLlamadaPerdida(Context context, String numero, Date fecha){
        Llamada call=new Llamada(numero,fecha);
        context.getContentResolver().insert(ContratoCall.TablaPerdidas.CONTENT_URI, call.getContentValuesPerdida());
        return true;
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        final Context c = context;
        TelephonyManager tm = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            numeroSaliente = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");
        }
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        tm.listen(new PhoneStateListener() {
            public void onCallStateChanged(int state, String incomingNumber) {
                if (lastState == state) {
                    //No change, debounce extras
                    return;
                }
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        isIncoming = true;
                        fecha = new Date();
                        numero = incomingNumber;
                        System.out.println("Numero:" + incomingNumber);
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        //Transition of ringing->offhook are pickups of incoming calls.  Nothing done on them
                        if (lastState != TelephonyManager.CALL_STATE_RINGING) {
                            isIncoming = false;
                            fecha = new Date();
                            //Llamada Saliente
                            regLlamadaSaliente(context, numeroSaliente, fecha);
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                        if (lastState == TelephonyManager.CALL_STATE_RINGING) {
                            //Llamada perdida
                            regLlamadaPerdida(context, numero, fecha);
                        } else if (isIncoming) {
//                           //Llamada recibida
                            regLlamadaEntrante(context, numero, fecha);
                        }
                        break;
                }
                lastState = state;
            }

        }, PhoneStateListener.LISTEN_CALL_STATE);
    }
}