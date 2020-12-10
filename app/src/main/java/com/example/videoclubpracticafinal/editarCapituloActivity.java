package com.example.videoclubpracticafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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

import cz.msebera.android.httpclient.Header;

public class editarCapituloActivity extends AppCompatActivity {

    ListView lista;
    static String SERVIDOR = "http://169.254.111.247";
    static final int MAXIMO = 100;
    int[] arrayIdCapitulo = new int[MAXIMO];
    String[] arrayTitulo = new String[MAXIMO];
    int[] arrayTemporadas = new int[MAXIMO];
    int[] arrayCapitulo = new int[MAXIMO];
    String[] arrayResumen = new String[MAXIMO];
    int cont=0;
    int idAModificar;
    Button modificar,eliminar;
    EditText titulo,temporada,numero,resumen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_capitulo);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        lista = findViewById(R.id.lista);
        modificar = findViewById(R.id.botonModificar);
        eliminar = findViewById(R.id.botonEliminar);
        titulo = findViewById(R.id.modificarTitulo);
        temporada = findViewById(R.id.modificarTemporada);
        numero = findViewById(R.id.modificarNumero);
        resumen = findViewById(R.id.modificarResumen);

        modificar.setEnabled(false);
        eliminar.setEnabled(false);

        refrescarLista();


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                modificar.setEnabled(true);
                eliminar.setEnabled(true);

                titulo.setText(arrayTitulo[position]);
                temporada.setText(""+arrayTemporadas[position]);
                numero.setText(""+arrayCapitulo[position]);
                resumen.setText(arrayResumen[position]);
                idAModificar=arrayIdCapitulo[position];



            }
        });



        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AsyncHttpClient cliente2 = new AsyncHttpClient();
                RequestParams parametros = new RequestParams();
                parametros.put("id",idAModificar);
                parametros.put("titulo",titulo.getText().toString());
                parametros.put("temporada",Integer.parseInt(temporada.getText().toString()));
                parametros.put("numeroCapitulo",Integer.parseInt(numero.getText().toString()));
                parametros.put("resumen",resumen.getText().toString());

                String SCRIPT2 = "http://169.254.111.247/videoclub/actualizarCapitulo.php";

                cliente2.post(SCRIPT2, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        Toast.makeText(editarCapituloActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();
                        refrescarLista();

                    }

                    @Override
                    public void onProgress(long bytesWritten, long totalSize) {
                        super.onProgress(bytesWritten, totalSize);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });

            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncHttpClient cliente = new AsyncHttpClient();
                RequestParams parametros = new RequestParams();
                parametros.put("id",idAModificar);

                String SCRIPT = "http://169.254.111.247/videoclub/borrarCapitulo.php";

                cliente.post(SCRIPT, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        Toast.makeText(editarCapituloActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();
                        refrescarLista();

                    }

                    @Override
                    public void onProgress(long bytesWritten, long totalSize) {
                        super.onProgress(bytesWritten, totalSize);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });

            }
        });


    }

    public void refrescarLista(){

        DescargarCSV descargarCSV = new DescargarCSV();
        descargarCSV.execute("/videoclub/listadoCSVCapitulo.php");
        cont=0;

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
                String dato = " "+campos[0]; //id_serie

                dato+=" "+campos[1]; //id_capitulo
                arrayIdCapitulo[cont] = Integer.parseInt(campos[1]);

                dato+=" "+campos[2]; //titulo

                arrayTitulo[cont] = campos[2];

                dato+=" "+campos[3]; //temporada

                arrayTemporadas[cont] = Integer.parseInt(campos[3]);

                dato+=" "+campos[4]; //numeroCapitulo

                arrayCapitulo[cont] = Integer.parseInt(campos[4]);

                dato+=" "+campos[5]; //resumen

                arrayResumen[cont] = campos[5];

                list.add(dato);

                cont++;

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