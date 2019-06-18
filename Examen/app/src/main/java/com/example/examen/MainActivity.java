package com.example.examen;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "form";
    ArrayList<Producto> productos = new ArrayList<>();
    ListView lista;
    ArrayList<Producto> temporal;

    private SwipeRefreshLayout swipeRefreshLayout;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView total_productos = findViewById(R.id.total_productos);
        final TextView costos_total = findViewById(R.id.total_costo);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

        lista = findViewById(R.id.lista);
        final controllerLista listacontroller = new controllerLista(MainActivity.this, productos);
        lista.setAdapter(listacontroller);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View formulario  = getLayoutInflater().inflate(R.layout.form, null);

                final TextView nombre = formulario.findViewById(R.id.nombre);
                final TextView fecha = formulario.findViewById(R.id.fecha);
                final TextView precio = formulario.findViewById(R.id.precio);
                final CheckBox status = formulario.findViewById(R.id.status);

                //refresh(1000, listacontroller, costos_total, total_productos);
                mBuilder.setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!nombre.getText().toString().isEmpty() && !fecha.getText().toString().isEmpty() && !precio.getText().toString().isEmpty()){

                            temporal = listacontroller.getLista();
                            temporal.add(new Producto(nombre.getText().toString(), Float.parseFloat(precio.getText().toString()), fecha.getText().toString(), status.isChecked()));
                            listacontroller.setLista(temporal);
                            lista.setAdapter(listacontroller);

                            costos_total.setText("Costo: $" + listacontroller.Todos_los_precios());
                            total_productos.setText("Productos: " + temporal.size() );

                            Toast mensaje =  Toast.makeText(getApplicationContext(),"Dato Insertado",Toast.LENGTH_SHORT);
                            mensaje.show();

                        }else{
                            Toast mensaje =  Toast.makeText(getApplicationContext(),"NECESITA RELLENAR TODOS LOS CAMPOS",Toast.LENGTH_SHORT);
                            mensaje.show();
                        }
                    }
                });

                mBuilder.setNegativeButton("ELIMINAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                mBuilder.setView(formulario);
                AlertDialog dialogo = mBuilder.create();
                dialogo.show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Esto se ejecuta cada vez que se realiza el gesto
                temporal = listacontroller.getLista();
                costos_total.setText("Costo: $" + listacontroller.Todos_los_precios());
                total_productos.setText("Productos: " + temporal.size() );
                listacontroller.setLista(temporal);
                lista.setAdapter(listacontroller);

                System.out.println("RECARGANDO LOS DATOS");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void refresh(final int milisegundos, final controllerLista listacontroller, final TextView costos_total, final TextView total_productos){
        final Handler handler = new Handler();

        final Runnable runable = new Runnable() {
            @Override
            public void run() {
                System.out.println("refrescando");
                refresh(milisegundos,listacontroller, costos_total, total_productos);
            }
        };
    }
}
