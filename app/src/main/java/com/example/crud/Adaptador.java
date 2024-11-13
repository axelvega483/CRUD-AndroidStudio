package com.example.crud;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {
    ArrayList<Contacto> listaContacto = new ArrayList<>();
    ContactoDAO dao;
    Contacto contacto;
    Activity activity;
    int id;

    Button guardar ,cancelar;

    public Adaptador(){
        listaContacto = new ArrayList<>();
    }
    public Adaptador(ArrayList listaContacto, ContactoDAO dao,Activity activity){
        this.listaContacto=listaContacto;
        this.dao=dao;
        this.activity=activity;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getCount() {
        return listaContacto != null ? listaContacto.size() : 0;
    }

    @Override
    public Contacto getItem(int i) {
        contacto=listaContacto.get(i);
        return contacto;

    }

    @Override
    public long getItemId(int i) {
        contacto=listaContacto.get(i);
        return contacto.getId();
    }

    @Override
    public View getView(int posicion, View view, ViewGroup viewGroup) {
       View v=view;
       if(v==null){
           LayoutInflater inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.item,null);
       }
       contacto=getItem(posicion);
       TextView tvNombre=v.findViewById(R.id.tvNombre);
       TextView tvEdad=v.findViewById(R.id.tvEdad);
       TextView tvTelefono=v.findViewById(R.id.tvTelefono);
       TextView tvEmail=v.findViewById(R.id.tvEmail);
       ImageButton editar=v.findViewById(R.id.imageButtonEditar);
       ImageButton eliminar=v.findViewById(R.id.imageButtonEliminar);

       tvNombre.setText(contacto.getNombre());
       tvEdad.setText(String.valueOf(contacto.getEdad()));
       tvTelefono.setText(contacto.getTelefono());
       tvEmail.setText(contacto.getEmail());

       editar.setTag(posicion);
       eliminar.setTag(posicion);

        editar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int posicion=Integer.parseInt(view.getTag().toString());
                Dialog dialogo = new Dialog(activity);
                dialogo.setTitle("Editar Registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();
                EditText nombre = dialogo.findViewById(R.id.nombre);
                EditText email = dialogo.findViewById(R.id.email);
                EditText telefono = dialogo.findViewById(R.id.telefono);
                EditText edad = dialogo.findViewById(R.id.edad);
                guardar = dialogo.findViewById(R.id.guardar);
                cancelar = dialogo.findViewById(R.id.cancelar);
                guardar.setText("Editar");
                contacto=listaContacto.get(posicion);
                setId(contacto.getId());
                nombre.setText(contacto.getNombre());
                email.setText(contacto.getEmail());
                telefono.setText(contacto.getTelefono());
                edad.setText(String.valueOf(contacto.getEdad()));

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            contacto = new Contacto(getId(),
                                    nombre.getText().toString(),
                                    Integer.parseInt(edad.getText().toString()),
                                    telefono.getText().toString(),
                                    email.getText().toString());
                            dao.editar(contacto);
                            listaContacto.set(posicion,contacto);
                            notifyDataSetChanged();
                            dialogo.dismiss();

                        } catch (Exception e) {
                            Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogo.dismiss();
                    }
                });
            }

        });
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("¿Desea eliminar el registro?");
                builder.setCancelable(false);

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int posicion = Integer.parseInt(view.getTag().toString());
                        contacto = listaContacto.get(posicion);
                        dao.eliminar(contacto.getId());
                        listaContacto.remove(posicion);
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });


        return v;
    }
}
