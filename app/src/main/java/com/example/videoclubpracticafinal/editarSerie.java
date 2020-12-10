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

public class editarSerie extends AppCompatActivity {

    private static final int MAXIMO = 100 ;
    ListView lista;
    static String SERVIDOR = "http://169.254.111.247";
    int[] idV = new int[MAXIMO];
    String[] nombre = new String[MAXIMO];
    String[] fecha  = new String[MAXIMO];
    String[] cadena  = new String[MAXIMO];
    int[] temporadas = new int[MAXIMO];
    Button borrar,actualizar;
    EditText idSerie,nombreSerie,fechaSerie,cadenaSerie,temporadasSerie;


    int cont;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_serie);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        lista = findViewById(R.id.lista);
        borrar = findViewById(R.id.borrarSerie);
        actualizar = findViewById(R.id.actualizarSerie);
        idSerie = findViewById(R.id.idActualizado);
        nombreSerie = findViewById(R.id.editTextTextNombre);
        fechaSerie = findViewById(R.id.editTextDate);
        cadenaSerie = findViewById(R.id.editTextTextCadena);
        temporadasSerie = findViewById(R.id.editTextTemp);

        borrar.setEnabled(false);
        actualizar.setEnabled(false);

        cont = 0;

        refrescarLista();



        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                borrar.setEnabled(true);
                actualizar.setEnabled(true);

                idSerie.setText(""+idV[position]);
                nombreSerie.setText(nombre[position]);
                fechaSerie.setText(fecha[position]);
                cadenaSerie.setText(cadena[position]);
                temporadasSerie.setText(""+temporadas[position]);

            }
        });

        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncHttpClient cliente = new AsyncHttpClient();
                RequestParams parametros = new RequestParams();
                parametros.put("id",Integer.parseInt(idSerie.getText().toString()));

                String SCRIPT = "http://169.254.111.247/videoclub/borrarSerie.php";

                cliente.post(SCRIPT, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        Toast.makeText(editarSerie.this, new String(responseBody), Toast.LENGTH_SHORT).show();
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

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncHttpClient cliente2 = new AsyncHttpClient();
                RequestParams parametros = new RequestParams();
                parametros.put("id",Integer.parseInt(idSerie.getText().toString()));
                parametros.put("nombre",nombreSerie.getText().toString());
                parametros.put("fecha_estreno",fechaSerie.getText().toString());
                parametros.put("cadena",cadenaSerie.getText().toString());
                parametros.put("temporadas",Integer.parseInt(temporadasSerie.getText().toString()));

                String SCRIPT2 = "http://169.254.111.247/videoclub/actualizarSerie.php";

                cliente2.post(SCRIPT2, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        Toast.makeText(editarSerie.this, new String(responseBody), Toast.LENGTH_SHORT).show();
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
        descargarCSV.execute("/videoclub/listadoCSV.php");

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
                idV[cont]=Integer.parseInt(campos[0]);
                dato+=" "+campos[1];
                nombre[cont]=campos[1];
                dato+=" "+campos[2];
                fecha[cont]=campos[2];
                dato+=" "+campos[3];
                cadena[cont]=campos[3];
                dato+=" "+campos[4];
                temporadas[cont]=Integer.parseInt(campos[4]);
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