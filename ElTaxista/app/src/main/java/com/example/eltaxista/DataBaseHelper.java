package com.example.eltaxista;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.provider.ContactsContract;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    //CREAREMOS LA CONEXION A LA BASE DE DATOS
    //BASE DE DATOS LOCAL
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "productsdb";
    private static final String TABLE = "products";

    //DECLARAMOS LA COLUMNAS DE LA TABLA products
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "nombre";
    private static final String KEY_PRICE = "precio";

    public DataBaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("SE ENTRO ACA ALGUNA VEZ?");
        String CREATE_TABLE ="CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_PRICE + " TEXT)";
        db.execSQL(CREATE_TABLE);
        //db.close();
    }

    public void insertProduct(String nombre, String precio){
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("INSERT INTO " + TABLE + " (" + KEY_NAME+", " + KEY_PRICE+ ") VALUES ( '"+ nombre + "' , '" + precio + "' );");
        db.execSQL("INSERT INTO " + TABLE + " (" + KEY_NAME+", " + KEY_PRICE+ ") VALUES ( '"+ nombre + "' , '" + precio + "' );");
        db.close();
    }

    public ArrayList<String> getProducts(){
        ArrayList<String> productos = new ArrayList<>();
        String QUERY = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY, null);

        if(cursor.moveToFirst()){
            System.out.println("EXISTEN PRODUCTOS PAPU");
            do{
                productos.add(cursor.getString(0) + "\n" + cursor.getString(1) + "\n" + cursor.getString(2));
            }while(cursor.moveToNext());
        }

        db.close();
        return productos;
    }

    public ArrayList<String> delete(int id){
        ArrayList<String> productos = new ArrayList<>();
        String QUERY = "DELETE FROM " + TABLE + " WHERE id = " + id;

        System.out.println(QUERY);

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(QUERY);

        productos = getProducts();

        db.close();
        return productos;
    }

    public ArrayList<String> update(int id, String name, String precio){

        ArrayList<String> productos = new ArrayList<>();

        String QUERY = "UPDATE " + TABLE + " SET "
                + KEY_NAME + " = '" + name +"', "
                + KEY_PRICE + " = '" + precio
                + "' WHERE id = " + id;
        System.out.println(QUERY);

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(QUERY);

        productos = getProducts();

        db.close();
        return productos;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}
