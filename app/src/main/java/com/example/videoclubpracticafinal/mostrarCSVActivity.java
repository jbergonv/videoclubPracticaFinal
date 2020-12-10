package com.example.videoclubpracticafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class mostrarCSVActivity extends AppCompatActivity {

    Button serie,capitulo;
    ListView lista;
    static String SERVIDOR = "http://169.254.111.247";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_c_s_v);


        serie = findViewById(R.id.mostrarSeriesCSV);
        capitulo = findViewById(R.id.mostrarCapituloCSV);
        lista = findViewById(R.id.lista);


        serie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarSerie();


            }
        });

        capitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mostrarCapitulo();

            }
        });


    }

    void mostrarSerie(){

        DescargarCSV descargarCSV = new DescargarCSV();
        descargarCSV.execute("/videoclub/listadoCSV.php");

    }

    void mostrarCapitulo(){

        DescargarCSV2 descargarCSV = new DescargarCSV2();
        descargarCSV.execute("/videoclub/listadoCSVCapitulo.php");

    }

    private class DescargarCSV extends AsyncTask<String, Void, Void> {
        String total ="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ArrayAdapter<String> adapter;
            List<String> list  = new ArrayList<String>();

            String[] lineas= total.split("\n");

            for(String linea: lineas){
                String[] campos = linea.split(";");

                String dato = " "+campos[0];

                dato+=" "+campos[1];

                dato+=" "+campos[2];

                dato+=" "+campos[3];

                dato+=" "+campos[4];

                list.add(dato);


            }
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,list);
            lista.setAdapter(adapter);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(String... strings) {

            String script = strings[0];
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(SERVIDOR+script);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                    Log.i("TEST","he entrado");

                    String linea ="";
                    while((linea = br.readLine())!=null){
                        total+=linea+"\n";
                    }
                    br.close();
                    inputStream.close();

                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

    private class DescargarCSV2 extends AsyncTask<String, Void, Void> {
        String total ="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ArrayAdapter<String> adapter;
            List<String> list  = new ArrayList<String>();

            String[] lineas= total.split("\n");

            for(String linea: lineas){
                String[] campos = linea.split(";");
                String dato = " "+campos[0]; //id_serie

                dato+=" "+campos[1]; //id_capitulo


                dato+=" "+campos[2]; //titulo



                dato+=" "+campos[3]; //temporada


                dato+=" "+campos[4]; //numeroCapitulo



                dato+=" "+campos[5]; //resumen



                list.add(dato);


            }
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item,list);
            lista.setAdapter(adapter);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(String... strings) {

            String script = strings[0];
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(SERVIDOR+script);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK){
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

                    Log.i("TEST","he entrado");

                    String linea ="";
                    while((linea = br.readLine())!=null){
                        total+=linea+"\n";
                    }
                    br.close();
                    inputStream.close();

                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }


}