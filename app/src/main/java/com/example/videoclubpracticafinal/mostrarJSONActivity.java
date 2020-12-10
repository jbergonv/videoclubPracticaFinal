package com.example.videoclubpracticafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

public class mostrarJSONActivity extends AppCompatActivity {

    Button serie,capitulo;
    ListView lista;
    static String SERVIDOR = "http://169.254.111.247";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_j_s_o_n);

        serie = findViewById(R.id.mostrarSerieJSON);
        capitulo = findViewById(R.id.mostrarCapituloJSON);
        lista = findViewById(R.id.lista);

        serie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DescargarJSON descargarJSON = new DescargarJSON();
                descargarJSON.execute("/videoclub/listadoJSONSeries.php");

            }
        });

        capitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DescargarJSON2 descargarJSON2 = new DescargarJSON2();
                descargarJSON2.execute("/videoclub/listadoJSONCapitulos.php");

            }
        });




    }

    private class DescargarJSON extends AsyncTask<String, Void, Void> {
        String consulta = "";
        ArrayAdapter<String> adapter;
        List<String> list = new ArrayList<String>();

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            JsonParser parser = new JsonParser();

            int tamano;
            JsonArray jsonarray = parser.parse(consulta).getAsJsonArray();
            JsonElement elemento1 = jsonarray.get(0);
            JsonObject objeto1 = elemento1.getAsJsonObject();
            String[] nombredecampos = new String[5];
            Set<String> campos = objeto1.keySet();
            int i = 0;
            int contador = 0;

            for (String campo : campos) {
                if (i % 2 != 0) {
                    nombredecampos[contador] = campo;
                    contador++;
                }
                i++;

            }
            String[] fila = new String[5];


            for (JsonElement elemento : jsonarray) {

                JsonObject objeto = elemento.getAsJsonObject();

                fila[0] = objeto.get(nombredecampos[0]).getAsString();
                fila[1] = objeto.get(nombredecampos[1]).getAsString();

                fila[2] = objeto.get(nombredecampos[2]).getAsString();

                fila[3] = objeto.get(nombredecampos[3]).getAsString();
                fila[4] = objeto.get(nombredecampos[4]).getAsString();
                list.add( fila[0] + "  "  + fila[1] + "  "  + fila[2] + "  "  + fila[3] + "  "+fila[4]);


            }


            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
            lista.setAdapter(adapter);


        }

        @Override
        protected Void doInBackground(String... strings) {
            String script = strings[0];
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(SERVIDOR + script);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                    String linea = "";
                    while ((linea = br.readLine()) != null) {
                        consulta += linea + "\n";
                    }
                    br.close();
                    inputStream.close();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


    private class DescargarJSON2 extends AsyncTask<String, Void, Void> {
        String consulta = "";
        ArrayAdapter<String> adapter;
        List<String> list = new ArrayList<String>();

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            JsonParser parser = new JsonParser();

            int tamano;
            JsonArray jsonarray = parser.parse(consulta).getAsJsonArray();
            JsonElement elemento1 = jsonarray.get(0);
            JsonObject objeto1 = elemento1.getAsJsonObject();
            String[] nombredecampos = new String[6];
            Set<String> campos = objeto1.keySet();
            int i = 0;
            int contador = 0;

            for (String campo : campos) {
                if (i % 2 != 0) {
                    nombredecampos[contador] = campo;
                    contador++;
                }
                i++;

            }
            String[] fila = new String[6];


            for (JsonElement elemento : jsonarray) {

                JsonObject objeto = elemento.getAsJsonObject();

                fila[0] = objeto.get(nombredecampos[0]).getAsString();
                fila[1] = objeto.get(nombredecampos[1]).getAsString();

                fila[2] = objeto.get(nombredecampos[2]).getAsString();

                fila[3] = objeto.get(nombredecampos[3]).getAsString();
                fila[4] = objeto.get(nombredecampos[4]).getAsString();
                fila[5] = objeto.get(nombredecampos[5]).getAsString();
                list.add(fila[0] + "  " + fila[1] + "  "  + fila[2] + "  "  + fila[3] + "  "+fila[4]+" "+fila[5]);


            }


            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
            lista.setAdapter(adapter);


        }

        @Override
        protected Void doInBackground(String... strings) {
            String script = strings[0];
            URL url;
            HttpURLConnection httpURLConnection;

            try {
                url = new URL(SERVIDOR + script);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

                    String linea = "";
                    while ((linea = br.readLine()) != null) {
                        consulta += linea + "\n";
                    }
                    br.close();
                    inputStream.close();
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}