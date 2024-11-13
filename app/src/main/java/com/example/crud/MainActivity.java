package com.example.crud;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ContactoDAO dao;
    Adaptador adapter;
    ArrayList<Contacto> lista;
    ImageButton agregar;
    Button guardar, cancelar;
    Contacto contacto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new ContactoDAO(this);
        lista = dao.listar();
        adapter = new Adaptador(lista, dao, this);
        ListView listView = findViewById(R.id.lista);
        agregar = findViewById(R.id.agregar);
        listView.setAdapter(adapter);

        // Manejo de clic en los elementos de la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contacto contactoSeleccionado = lista.get(i);
                // Aquí puedes agregar un dialogo o una actividad para mostrar los detalles
                Toast.makeText(MainActivity.this, "Contacto: " + contactoSeleccionado.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogo = new Dialog(MainActivity.this);
                dialogo.setTitle("Nuevo Registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();

                EditText nombre = dialogo.findViewById(R.id.nombre);
                EditText email = dialogo.findViewById(R.id.email);
                EditText telefono = dialogo.findViewById(R.id.telefono);
                EditText edad = dialogo.findViewById(R.id.edad);
                guardar = dialogo.findViewById(R.id.guardar);
                cancelar = dialogo.findViewById(R.id.cancelar);
                guardar.setText("Agregar");

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Validación básica de campos vacíos
                        if (nombre.getText().toString().isEmpty() || email.getText().toString().isEmpty() ||
                                telefono.getText().toString().isEmpty() || edad.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, "Por favor completa todos los campos.", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                // Crear el nuevo contacto
                                contacto = new Contacto(nombre.getText().toString(),
                                        Integer.parseInt(edad.getText().toString()),
                                        telefono.getText().toString(),
                                        email.getText().toString());
                                dao.inserter(contacto); // Insertar en base de datos
                                lista.add(contacto); // Agregar a la lista
                                adapter.notifyDataSetChanged(); // Notificar al adaptador
                                dialogo.dismiss(); // Cerrar el diálogo
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogo.dismiss(); // Cerrar el diálogo sin hacer nada
                    }
                });
            }
        });
    }
}
