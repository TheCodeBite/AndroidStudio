package com.example.examen;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class controllerLista extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context contexto;
    ArrayList<Producto> lista;
    int index;

    public controllerLista(Context contexto, ArrayList<Producto> lista) {
        this.contexto = contexto;
        this.lista = lista;
        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final View vista_external = inflater.inflate(R.layout.activity_main,null);
        final View vista = inflater.inflate(R.layout.lista, null);

        final TextView todos_productos = vista_external.findViewById(R.id.total_productos);
        final TextView costo_total = vista_external.findViewById(R.id.total_costo);



        final TextView texto = vista.findViewById(R.id.datos);
        final Button eliminar = vista.findViewById(R.id.eliminar);
        final Button editar = vista.findViewById(R.id.editar);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(contexto);
                View formulario = inflater.inflate(R.layout.form, null);

                //Button guardar = formulario.findViewById(R.id.guardar);
                final TextView nombre = formulario.findViewById(R.id.nombre);
                final TextView fecha = formulario.findViewById(R.id.fecha);
                final TextView precio = formulario.findViewById(R.id.precio);
                final CheckBox status = formulario.findViewById(R.id.status);

                nombre.setText(lista.get(position).getNombre());
                fecha.setText(lista.get(position).getFecha());
                precio.setText(lista.get(position).precio + "");
                status.setChecked(lista.get(position).isStatus());
                mBuilder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!nombre.getText().toString().isEmpty() && !fecha.getText().toString().isEmpty() && !precio.getText().toString().isEmpty()) {
                            lista.get(position).setNombre(nombre.getText().toString());
                            lista.get(position).setFecha(fecha.getText().toString());
                            lista.get(position).setPrecio(Float.parseFloat(precio.getText().toString()));
                            lista.get(position).setStatus(status.isChecked());

                            costo_total.setText("Productos: " + Todos_los_precios());
                            System.out.println("EL PRODUCTO " + Todos_los_precios());

                            Toast mensaje =  Toast.makeText(contexto.getApplicationContext(),"DATOS ACTUALIZADOS",Toast.LENGTH_SHORT);
                            mensaje.show();

                        }else{
                            Toast mensaje =  Toast.makeText(contexto.getApplicationContext(),"NECESITA RELLENAR TODOS LOS CAMPOS",Toast.LENGTH_SHORT);
                            mensaje.show();
                        }
                    }
                });
                mBuilder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                mBuilder.setView(formulario);
                AlertDialog dialogo = mBuilder.create();
                dialogo.show();

            }
        });


        if(lista.get(position).isStatus()){
            System.out.println("ENTRO AQUI WE ");
            editar.setEnabled(false);
        }else{
            System.out.println("NO WE NO ENTRO");
        }



        texto.setText("Producto: " + lista.get(position).getNombre() +
                "\nPrecio: $" + lista.get(position).getPrecio() +
                "\nFecha: " + lista.get(position).getFecha() +
                "\nEstatus: " + lista.get(position).isStatus());

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { eliminar(position); }
        });

        return vista;
    }

    public void eliminar(int posicion){ lista.remove(posicion); index = posicion;}

    public ArrayList<Producto> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Producto> lista) { this.lista = lista; }

    public float Todos_los_precios(){
        float precios = 0 ;
        for (int i = 0 ; i < lista.size() ; i++){
            precios += lista.get(i).precio;
        }

        return precios;
    }

    public int getIndex() {
        return index;
    }
}
