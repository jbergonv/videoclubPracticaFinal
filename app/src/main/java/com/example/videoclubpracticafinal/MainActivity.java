package com.example.videoclubpracticafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    Button boton, botonModificarSerie,botonNuevoCapitulo,editarCapitulo,mostrarCSV,mostrarJSON,mostrarXML;
    //Version final (creo)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = findViewById(R.id.insertarSerie);
        botonModificarSerie = findViewById(R.id.modificarSerie);
        botonNuevoCapitulo = findViewById(R.id.botonInsertarCapitulo);
        editarCapitulo = findViewById(R.id.editarCapitulo);
        mostrarCSV = findViewById(R.id.mostrarCSV);
        mostrarJSON = findViewById(R.id.mostrarJSON);
        mostrarXML = findViewById(R.id.mostrarXML);
        int id=1;
        String nombre="pepito";
        String fecha = "07/12/2020";
        String cadena = "netflix";
        int temporadas = 12;

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,insertarSerie.class);
                startActivity(intent);


            }
        });

        botonModificarSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,editarSerie.class);
                startActivity(intent);

            }
        });

        botonNuevoCapitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,nuevoCapituloActivity.class);
                startActivity(intent);

            }
        });

        editarCapitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,editarCapituloActivity.class);
                startActivity(intent);

            }
        });

        mostrarCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,mostrarCSVActivity.class);
                startActivity(intent);

            }
        });

        mostrarJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent = new Intent(MainActivity.this,mostrarJSONActivity.class);
               startActivity(intent);

            }
        });

        mostrarXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,mostrarXMLActivity.class);
                startActivity(intent);

            }
        });




    }
}