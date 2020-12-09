package com.example.videoclubpracticafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class nuevoCapituloActivity extends AppCompatActivity {

    Button añadir;
    EditText idSerie,idCapitulo,titulo,temporada,numero,resumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_capitulo);

        añadir = findViewById(R.id.nuevoCapitulo);
        idSerie = findViewById(R.id.idSerieNuevo);
        idCapitulo = findViewById(R.id.idCapituloNuevo);
        titulo = findViewById(R.id.tituloCapituloNuevo);
        temporada = findViewById(R.id.temporadaCapituloNuevo);
        numero = findViewById(R.id.numeroCapituloNuevo);
        resumen = findViewById(R.id.resumenCapituloNuevo);

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncHttpClient cliente = new AsyncHttpClient();
                RequestParams parametros = new RequestParams();
                parametros.put("id_serie",Integer.parseInt(idSerie.getText().toString()));
                parametros.put("id_capitulo",Integer.parseInt(idCapitulo.getText().toString()));
                parametros.put("titulo",titulo.getText().toString());
                parametros.put("temporada",Integer.parseInt(temporada.getText().toString()));
                parametros.put("numero_capitulo",Integer.parseInt(numero.getText().toString()));
                parametros.put("resumen",resumen.getText().toString());

                String SCRIPT = "http://169.254.111.247/videoclub/insertarCapitulo.php";

                cliente.post(SCRIPT, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        Toast.makeText(nuevoCapituloActivity.this, new String(responseBody), Toast.LENGTH_SHORT).show();

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
}