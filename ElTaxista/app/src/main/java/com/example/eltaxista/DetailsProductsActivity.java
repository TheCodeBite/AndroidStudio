package com.example.eltaxista;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_list_item_2;

public class DetailsProductsActivity extends AppCompatActivity {
    ArrayList<String> productos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_products);
        final DataBaseHelper db = new DataBaseHelper(this);

        final ListView lista = findViewById(R.id.lista);

        productos = db.getProducts();

        final ArrayAdapter<String> items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, productos);
        lista.setAdapter(items);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DetailsProductsActivity.this);
                View form = getLayoutInflater().inflate(R.layout.formulario, null);

                final TextView nombre = form.findViewById(R.id.name),
                        price = form.findViewById(R.id.price);

                String[] split = productos.get(position).split("\n");
                final int tempPosicion = Integer.parseInt(split[0]);
                nombre.setText(split[1]);
                price.setText(split[2]);

                mBuilder.setPositiveButton("GUARDAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productos = db.update(tempPosicion, nombre.getText().toString(), price.getText().toString());
                        ArrayAdapter<String> temp= new ArrayAdapter<String>(DetailsProductsActivity.this, simple_list_item_1, productos );
                        lista.setAdapter(temp);
                    }
                });

                mBuilder.setNegativeButton("ELIMINAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productos = db.delete(tempPosicion);
                        ArrayAdapter<String> temp= new ArrayAdapter<String>(DetailsProductsActivity.this, simple_list_item_1, productos );
                        lista.setAdapter(temp);
                    }
                }).setView(form);
                AlertDialog dialogo = mBuilder.create();
                dialogo.show();

            }
        });


    }

}
