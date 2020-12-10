package com.example.videoclubpracticafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class mostrarXMLActivity extends AppCompatActivity {

    Button serie,capitulo;
    ListView lista;
    static String SERVIDOR = "http://169.254.111.247";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_x_m_l);

        serie = findViewById(R.id.mostrarSerieXML);
        capitulo = findViewById(R.id.mostrarCapituloXML);
        lista = findViewById(R.id.lista);

        serie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DescargarXML descargarXML = new DescargarXML();
                descargarXML.execute("/videoclub/listadoXMLSerie.php");

            }
        });

        capitulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DescargarXML descargarXML = new DescargarXML();
                descargarXML.execute("/videoclub/listadoXMLCapitulo.php");

            }
        });

    }

    private class DescargarXML extends AsyncTask<String, Void, Void> {
        String consultaxml = "";
        ArrayAdapter<String> adapter;
        List<String> list = new ArrayList<String>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... strings) {
            String script = strings[0];
            URL url;
            HttpURLConnection httpURLConnection;
            boolean rellenas = false;
            String [] campos=null;
            try {
                url = new URL(SERVIDOR + script);
                httpURLConnection = (HttpURLConnection) url.openConnection();

                if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    try {
                        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                        DocumentBuilder db = dbf.newDocumentBuilder();
                        Document doc = db.parse(url.openStream());
                        Element raiz = doc.getDocumentElement();

                        //System.out.println("raiz" + raiz.getNodeName());

                        NodeList hijos = raiz.getChildNodes();
                        int contador=0;
                        for (int i = 0; i < hijos.getLength(); i++) {
                            String datos="";
                            Node nodo = hijos.item(i);
                            if(nodo instanceof Element){
                                NodeList nietos=nodo.getChildNodes();
                                String [] fila=new String[nietos.getLength()];
                                for (int j = 0; j < nietos.getLength(); j++) {

                                    datos+=nietos.item(j).getNodeName();

                                    if(nietos.item(j) instanceof Element){
                                        fila[j]=nietos.item(j).getTextContent();
                                        datos+=fila[j]+" ";
                                    }
                                }
                                list.add(datos);

                                rellenas=true;

                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, list);
            lista.setAdapter(adapter);
        }
    }

}