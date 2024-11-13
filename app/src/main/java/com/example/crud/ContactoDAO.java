package com.example.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ContactoDAO {
    SQLiteDatabase db;
    ArrayList<Contacto> contactoLista=new ArrayList<Contacto>();
    Contacto contacto;
    Context context;
    String nombreBD="contactosBD";
    String tabla = "CREATE TABLE contactos(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " nombre TEXT, " +
            "edad INTEGER, " +
            "telefono TEXT, " +
            "email TEXT)";

    public ContactoDAO(Context context) {
        this.context = context;
        db=context.openOrCreateDatabase(nombreBD,Context.MODE_PRIVATE,null);
        if (!tableExists(db, "contactos")) {
            db.execSQL(tabla); // Crear la tabla solo si no existe
        }
    }
    private boolean tableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
    public boolean inserter(Contacto contacto){
        ContentValues values = new ContentValues();
        values.put("nombre", contacto.getNombre());
        values.put("edad", contacto.getEdad());
        values.put("telefono", contacto.getTelefono());
        values.put("email", contacto.getEmail());
        long result = db.insert("contactos", null, values);
        return result != -1;
    }
    public boolean eliminar(int id) {
        return (db.delete("contactos", "id = ?", new String[]{String.valueOf(id)}) > 0);
    }

    public boolean editar(Contacto contacto) {
        ContentValues values = new ContentValues();
        values.put("nombre", contacto.getNombre());
        values.put("edad", contacto.getEdad());
        values.put("telefono", contacto.getTelefono());
        values.put("email", contacto.getEmail());
        int result = db.update("contactos", values, "id = ?", new String[]{String.valueOf(contacto.getId())});
        return result > 0;
    }
    public ArrayList<Contacto> listar() {
        ArrayList<Contacto> contactoLista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM contactos", null);
        if (cursor.moveToFirst()) {
            do {
                Contacto contacto = new Contacto();
                contacto.setId(cursor.getInt(0));
                contacto.setNombre(cursor.getString(1));
                contacto.setEdad(cursor.getInt(2));
                contacto.setTelefono(cursor.getString(3));
                contacto.setEmail(cursor.getString(4));
                contactoLista.add(contacto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactoLista;
    }

    public Contacto ver(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM contactos WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            contacto = new Contacto();
            contacto.setId(cursor.getInt(0));
            contacto.setNombre(cursor.getString(1));
            contacto.setEdad(cursor.getInt(2));
            contacto.setTelefono(cursor.getString(3));
            contacto.setEmail(cursor.getString(4));
        }
        cursor.close();
        return contacto;
    }


}
