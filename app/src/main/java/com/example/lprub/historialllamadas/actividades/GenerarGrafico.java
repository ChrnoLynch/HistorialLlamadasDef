package com.example.lprub.historialllamadas.actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lprub.historialllamadas.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class GenerarGrafico extends AppCompatActivity {
    private WebView grafico;
    private int[] contadorSemana;
    private ArrayList numerosPopulares;
    private ArrayList repeticionesPopulares;
    private String tipo="column";
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_grafico);
        this.grafico= (WebView) findViewById(R.id.webView);
        WebSettings webSettings = grafico.getSettings();
        webSettings.setJavaScriptEnabled(true);
        switch (getIntent().getStringExtra("tipo")){
            case "semana":
                contadorSemana=getIntent().getIntArrayExtra("contador");
                grafico.loadUrl("file:///android_asset/canvas/graficoSemana.html");
                grafico.addJavascriptInterface(this, "InterfazAndroid");
                break;
            case "popular":
                numerosPopulares=getIntent().getStringArrayListExtra("numeros");
                repeticionesPopulares=getIntent().getIntegerArrayListExtra("repeticiones");
                    grafico.loadUrl("file:///android_asset/canvas/graficoLlamadas.html");
                    grafico.addJavascriptInterface(this, "InterfazAndroid");
                break;
            default:
                break;
        }
        spin();
    }

    public void spin(){
        spinner = (Spinner) findViewById(R.id.spGrafico);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.graficos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (spinner.getSelectedItemPosition()) {
                    case 0:
                        tipo="column";
                        break;
                    case 1:
                        tipo="area";
                        break;
                    case 2:
                        tipo="spline";
                        break;
                    case 3:
                        tipo="pie";
                        break;
                    case 4:
                        tipo="doughnut";
                        break;
                    case 5:
                        tipo="bar";
                        break;
                }
                grafico.reload();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    @JavascriptInterface
    public int enviarDia(int x){
        return contadorSemana[x];
    }
    @JavascriptInterface
    public String enviarTituloPopular(int x){
        return (String) numerosPopulares.get(x);
    }
    @JavascriptInterface
    public int enviarRepetiocionesPopular(int x){
        return (int) repeticionesPopulares.get(x);
    }
    @JavascriptInterface
    public String tipo(){
        return tipo;
    }

}
