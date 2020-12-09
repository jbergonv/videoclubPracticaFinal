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

public class insertarSerie extends AppCompatActivity {


    EditText id,nombre,fecha,cadena,temporadas;
    Button botonInsertar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_serie);

        id = findViewById(R.id.editTextID);
        nombre = findViewById(R.id.editTextNombreSerie);
        fecha = findViewById(R.id.editTextFecha);
        cadena = findViewById(R.id.editTextCadena);
        temporadas = findViewById(R.id.editTextTemporadas);
        botonInsertar = findViewById(R.id.insertar_serie);


        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncHttpClient cliente = new AsyncHttpClient();
                RequestParams parametros = new RequestParams();
                parametros.put("id",Integer.parseInt(id.getText().toString()));
                parametros.put("nombre",nombre.getText().toString());
                parametros.put("fecha_estreno",fecha.getText().toString());
                parametros.put("cadena",cadena.getText().toString());
                parametros.put("temporadas",Integer.parseInt(temporadas.getText().toString()));

                String SCRIPT = "http://169.254.111.247/videoclub/insertarSerie.php";

              cliente.post(SCRIPT, parametros, new AsyncHttpResponseHandler() {
                  @Override
                  public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                      Toast.makeText(insertarSerie.this, new String(responseBody), Toast.LENGTH_SHORT).show();

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