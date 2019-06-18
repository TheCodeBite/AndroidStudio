package com.example.eltaxista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView name = findViewById(R.id.name),
                price = findViewById(R.id.price);

        Button register = findViewById(R.id.register),
                ver_lista = findViewById(R.id.view_lista);
        final DataBaseHelper dbHadler = new DataBaseHelper(MainActivity.this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHadler.insertProduct(name.getText().toString(), price.getText().toString());
                Toast.makeText(getApplicationContext(), "Producto Insertado", Toast.LENGTH_SHORT).show();
                name.setText("");
                price.setText("");


            }
        });

        ver_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DetailsProductsActivity.class);
                startActivity(intent);
            }
        });


    }
}