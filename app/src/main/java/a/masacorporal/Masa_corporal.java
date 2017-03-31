package a.masacorporal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Masa_corporal extends AppCompatActivity {


    public EditText peso;
    public EditText altura;
    public TextView tv_resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masa_corporal);


        peso = (EditText) findViewById(R.id.et_peso);
        altura = (EditText) findViewById(R.id.et_altura);
        tv_resultado = (TextView) findViewById(R.id.tv_resultado);

        SharedPreferences prefe = getSharedPreferences("datos", Context.MODE_PRIVATE);
        peso.setText(prefe.getString("peso", ""));
        altura.setText(prefe.getString("altura", ""));

        String[] archivos = fileList();

        if (existe(archivos, "notas.txt"))
            try {
                InputStreamReader archivo = new InputStreamReader(
                        openFileInput("notas.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String todo = "";
                while (linea != null) {
                    todo = todo + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();

                           } catch (IOException e) {
            }



    }

    private boolean existe(String[] archivos, String archbusca) {
        for (int f = 0; f < archivos.length; f++)
            if (archbusca.equals(archivos[f]))
                return true;
        return false;
    }

    public void masa(View view) {

        String valor1 = this.peso.getText().toString();
        String valor2 = this.altura.getText().toString();

        if (valor1.equals("") || valor2.equals("")) {
            tv_resultado.setText(R.string.INGRESE);
            tv_resultado.setTextColor(Color.BLUE);

        } else {


            SharedPreferences preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferencias.edit();
            editor.putString("peso", peso.getText().toString());
            editor.putString("altura", altura.getText().toString());
            editor.commit();


            double v1 = Double.parseDouble(valor1);
            double v2 = Double.parseDouble(valor2);

            double masa = (v2 * v2);
            double total = (v1 / masa);
            double total1 = total * 10000;


            if (total1 <= 16) {
                tv_resultado.setText(R.string.DelgadezSevera);
                tv_resultado.setTextColor(Color.CYAN);
            } else {
                if (total1 > 16 && total1 < 16.99) {
                    tv_resultado.setText(R.string.DelgadezModerada);
                    tv_resultado.setTextColor(Color.CYAN);
                } else {
                    if (total1 >= 17 && total1 < 18.49) {
                        tv_resultado.setText(R.string.DelgadezAceptable);
                        tv_resultado.setTextColor(Color.CYAN);
                    } else {
                        if (total1 >= 18.50 && total1 < 24.99) {
                            tv_resultado.setText(R.string.PesoNormal);
                            tv_resultado.setTextColor(Color.rgb(255, 0, 255));
                        } else {
                            if (total1 >= 25 && total1 < 29.99) {
                                tv_resultado.setText(R.string.Sobrepeso);
                                tv_resultado.setTextColor(Color.rgb(255, 154, 0));
                            } else {
                                if (total1 >= 30 && total1 < 34.99) {
                                    tv_resultado.setText(R.string.ObesoTipoI);
                                    tv_resultado.setTextColor(Color.rgb(255, 154, 0));
                                } else {
                                    if (total1 >= 35 && total1 < 40) {
                                        tv_resultado.setText(R.string.ObesoTipoII);
                                        tv_resultado.setTextColor(Color.rgb(255, 154, 0));
                                    } else {
                                        if (total1 > 40) {
                                            tv_resultado.setText(R.string.ObesoTipoIII);
                                            tv_resultado.setTextColor(Color.rgb(255, 154, 0));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput(
                    "notas.txt", Activity.MODE_PRIVATE));

            long hoy = System.currentTimeMillis();
            Date fecha = new Date(hoy);

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat df1 = new SimpleDateFormat("hh:mm:ss");
            String f = df.format(fecha);
            String f1 = df1.format(fecha);

            archivo.write("Fecha: " + f.toString() + " "+ " Hora: "+f1.toString() + " ");

            archivo.write(peso.getText().toString() + "Kg" +" ");
            archivo.write(altura.getText().toString() + "Cm" +" ");
            archivo.write(tv_resultado.getText().toString());
            archivo.flush();
            archivo.close();
        } catch (IOException e) {
        }
        Toast t = Toast.makeText(this, "Los datos fueron grabados",
                Toast.LENGTH_SHORT);
        t.show();
       }


    public void acercade(View view) {
        Intent i = new Intent(this, AcercaDe.class);
        startActivity(i);


    }

    public void historial(View view) {
        Intent i = new Intent(this, Historial.class);
        startActivity(i);
    }
}

