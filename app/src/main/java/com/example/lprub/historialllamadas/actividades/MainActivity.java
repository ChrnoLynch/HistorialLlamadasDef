package com.example.lprub.historialllamadas.actividades;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lprub.historialllamadas.funciones.Funciones;
import com.example.lprub.historialllamadas.R;
import com.example.lprub.historialllamadas.basedatos.Ayudante;
import com.example.lprub.historialllamadas.basedatos.ContratoCall;
import com.example.lprub.historialllamadas.basedatos.ProveedorCall;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
        private ProveedorCall pc;
        private Spinner spinner;
        private TextView fechaFinal;
        private DatePicker dtFechaInicial;
        private DatePicker dtFechaFinal;
        private RadioGroup rgb;
        private RadioButton rbTodos;
        private Cursor cursor1;
        private long inicial;
        private long fin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Ayudante ayudante=new Ayudante(this);
        ayudante.getReadableDatabase();

        fechaFinal= (TextView) findViewById(R.id.tvFechaFinal);
        dtFechaFinal= (DatePicker) findViewById(R.id.fechaFinal);
        dtFechaInicial= (DatePicker) findViewById(R.id.fechaInicial);
        rgb= (RadioGroup) findViewById(R.id.rgTipo);
        rbTodos= (RadioButton) findViewById(R.id.rbTotal);
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.llamadas, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItemPosition()==0){
                    fechaFinal.setVisibility(View.GONE);
                    dtFechaFinal.setVisibility(View.GONE);
                    rbTodos.setVisibility(View.VISIBLE);
                }else{
                    fechaFinal.setVisibility(View.VISIBLE);
                    dtFechaFinal.setVisibility(View.VISIBLE);
                    rbTodos.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void generarGrafico(View v){

        int []cont={};
        switch (spinner.getSelectedItemPosition()){
            case 0:
                //Fecha inicial
                GregorianCalendar fecha=new GregorianCalendar();
                fecha.set(dtFechaInicial.getYear(),dtFechaInicial.getMonth(),dtFechaFinal.getDayOfMonth(),0,0,0);
                int aux = fecha.DAY_OF_WEEK-1;
                fecha.set(dtFechaInicial.getYear(),dtFechaInicial.getMonth(),dtFechaInicial.getDayOfMonth()-aux,0,0,0);
                inicial=fecha.getTimeInMillis();

                //Fecha final
                fecha.set(dtFechaInicial.getYear(),dtFechaInicial.getMonth(),dtFechaInicial.getDayOfMonth()+7,0,0,0);
                fin=fecha.getTimeInMillis();

                switch(rgb.getCheckedRadioButtonId()){
                    case R.id.rbRecibida:
                        cursor1 =getContentResolver().query(ContratoCall.TablaEntrantes.CONTENT_URI, null, ContratoCall.TablaEntrantes.FECHA + ">=" + inicial + " AND " + ContratoCall.TablaEntrantes.FECHA + "<=" + fin, null, null);
                        cont=Funciones.contadorLlamadas(cursor1);
                        break;
                    case R.id.rbPerdidas:
                        cursor1 =getContentResolver().query(ContratoCall.TablaPerdidas.CONTENT_URI, null, ContratoCall.TablaPerdidas.FECHA+ ">=" + inicial + " AND " + ContratoCall.TablaEntrantes.FECHA + "<=" + fin, null, null);
                        cont=Funciones.contadorLlamadas(cursor1);
                        break;
                    case R.id.rbEnviados:
                        cursor1 = getContentResolver().query(ContratoCall.TablaSalientes.CONTENT_URI, null, ContratoCall.TablaSalientes.FECHA + ">=" + inicial + " AND " + ContratoCall.TablaEntrantes.FECHA + "<=" + fin, null, null);
                        cont=Funciones.contadorLlamadas(cursor1);
                        break;
                    case R.id.rbTotal:
                        cont=totalSemana(inicial,fin);
                        break;
                }
                if(cont[0]!=0||cont[1]!=0||cont[2]!=0||cont[3]!=0||cont[4]!=0||cont[5]!=0||cont[6]!=0){
                Intent i=new Intent(this,GenerarGrafico.class);
                i.putExtra("tipo", "semana");
                i.putExtra("contador",cont);
                startActivity(i);}
                else{
                    Toast.makeText(this,"No hay llamadas en la semana selecionada",Toast.LENGTH_LONG).show();
                }
                break;
            case 1:
                //Fecha inicial
                fecha=new GregorianCalendar();
                fecha.set(dtFechaInicial.getYear(),dtFechaInicial.getMonth(),dtFechaInicial.getDayOfMonth(),0,0,0);
                inicial=fecha.getTimeInMillis();
                //Fecha Final
                fecha=new GregorianCalendar();
                fecha.set(dtFechaFinal.getYear(),dtFechaFinal.getMonth(),dtFechaFinal.getDayOfMonth(),0,0,0);
                fin=fecha.getTimeInMillis();
                ArrayList<String> numeros=new ArrayList<>();
                ArrayList<Integer> repeticiones=new ArrayList<>();
                switch(rgb.getCheckedRadioButtonId()){
                    case R.id.rbRecibida:
                            cursor1 = getContentResolver().query(ContratoCall.TablaEntrantes.CONTENT_URI, null, null, new String[]{String.valueOf(inicial), String.valueOf(fin)}, "populares");
                        if(cursor1.getCount()==5) {
                            cursor1.moveToFirst();
                            numeros.add(cursor1.getString(0));
                            repeticiones.add(cursor1.getInt(2));
                            while (cursor1.moveToNext()) {
                                numeros.add(cursor1.getString(0));
                                repeticiones.add(cursor1.getInt(2));
                            }
                        }
                        break;
                    case R.id.rbPerdidas:
                        cursor1=getContentResolver().query(ContratoCall.TablaPerdidas.CONTENT_URI,null,null,new String[]{String.valueOf(inicial), String.valueOf(fin)},"populares");
                        if(cursor1.getCount()==5) {
                            cursor1.moveToFirst();
                            numeros.add(cursor1.getString(0));
                            repeticiones.add(cursor1.getInt(2));
                            while (cursor1.moveToNext()) {
                                numeros.add(cursor1.getString(0));
                                repeticiones.add(cursor1.getInt(2));
                            }
                        }
                        break;
                    case R.id.rbEnviados:
//                        cursor1 = getContentResolver().query(ContratoCall.TablaSalientes.CONTENT_URI, new String[]{ContratoCall.TablaSalientes.NUMERO + " ," + (ContratoCall.TablaSalientes.FECHA+" ,count("+ContratoCall.TablaSalientes.NUMERO+") as repeticiones")}, ContratoCall.TablaSalientes.FECHA + ">=" + inicial + " AND " + ContratoCall.TablaSalientes.FECHA + "<=" + fin, null, "repeticiones GROUP BY numero DESC limit 5" );
                        cursor1=getContentResolver().query(ContratoCall.TablaSalientes.CONTENT_URI,null,null,new String[]{String.valueOf(inicial), String.valueOf(fin)},"populares");
                        if(cursor1.getCount()==5) {
                            cursor1.moveToFirst();
                            numeros.add(cursor1.getString(0));
                            repeticiones.add(cursor1.getInt(2));
                            while (cursor1.moveToNext()) {
                                numeros.add(cursor1.getString(0));
                                repeticiones.add(cursor1.getInt(2));
                            }
                        }
                        break;
                }
                if(cursor1.getCount()==5) {
                Intent a=new Intent(this,GenerarGrafico.class);
                a.putExtra("tipo","popular");
                a.putExtra("numeros",numeros);
                a.putExtra("repeticiones",repeticiones);
                startActivity(a);}
                else{
                    Toast.makeText(this,"No hay suficientes datos, debes tener al menos 5 registros",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    public int[] totalSemana(long inicial, long fin) {
        Cursor c1 = getContentResolver().query(ContratoCall.TablaEntrantes.CONTENT_URI, null, ContratoCall.TablaEntrantes.FECHA+ ">=" + inicial + " AND " + ContratoCall.TablaEntrantes.FECHA + "<=" + fin, null, null);
        Cursor c2 = getContentResolver().query(ContratoCall.TablaPerdidas.CONTENT_URI, null, ContratoCall.TablaPerdidas.FECHA+ ">=" + inicial + " AND " + ContratoCall.TablaEntrantes.FECHA + "<=" + fin, null, null);
        Cursor c3 = getContentResolver().query(ContratoCall.TablaSalientes.CONTENT_URI, null, ContratoCall.TablaSalientes.FECHA+ ">=" + inicial + " AND " + ContratoCall.TablaEntrantes.FECHA + "<=" + fin, null, null);
        int ic1[]=Funciones.contadorLlamadas(c1);
        int ic2[]=Funciones.contadorLlamadas(c2);
        int ic3[]=Funciones.contadorLlamadas(c3);
        return totalPorDia(ic1,ic2,ic3);
    }

        public int[] totalPorDia(int[] entrantes, int[] salientes, int[] perdidas) {
        int totalDomingo = entrantes[0] + salientes[0] + perdidas[0];
        int totalLunes = entrantes[1] + salientes[1] + perdidas[1];
        int totalMartes = entrantes[2] + salientes[2] + perdidas[2];
        int totalMiercoles = entrantes[3] + salientes[3] + perdidas[3];
        int totalJueves = entrantes[4] + salientes[4] + perdidas[4];
        int totalViernes = entrantes[5] + salientes[5] + perdidas[5];
        int totalSabado = entrantes[6] + salientes[6] + perdidas[6];
        int[] resultadoTotal = {totalDomingo, totalLunes, totalMartes, totalMiercoles, totalJueves, totalViernes, totalSabado};
        return resultadoTotal;
    }


}
