package com.example.basededatos;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class DataBase extends SQLiteOpenHelper {
    private ArrayList<Comentarios> Coment_list = new ArrayList<Comentarios>();

    public DataBase(Context context) {
        super(context, "db_comentarios", null, 33);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE if not exists comentarios(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "nombre TEXT,"
                + "texto TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS comentarios");
        onCreate(db);
    }

    public void addComentario(Comentarios comentario) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", comentario.getNombre());
        contentValues.put("texto", comentario.getTexto());
        db.insert("comentarios", null, contentValues);
        db.close();
    }

    public ArrayList<Comentarios> getComentarios() {
        Coment_list.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from comentarios", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Comentarios item = new Comentarios();
                    @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));
                    item.setNombre(nombre);
                    @SuppressLint("Range") String texto = cursor.getString(cursor.getColumnIndex("texto"));
                    item.setTexto(texto);

                    Coment_list.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return Coment_list;
    }

    public ArrayList<Comentarios> getTextoComentario(String nombre) {
        Coment_list.clear();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select texto from comentarios where nombre='"+ nombre+"'", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    Comentarios item = new Comentarios();
                    @SuppressLint("Range") String texto = cursor.getString(cursor.getColumnIndex("texto"));
                    item.setTexto(texto);

                    Coment_list.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return Coment_list;
    }

    public void removeComentario(String nombre) {
        try {
            String[] args = {nombre};
            getWritableDatabase().delete("comentarios", "nombre=?", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
