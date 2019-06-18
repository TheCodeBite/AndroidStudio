package com.example.eltaxista;

public class DB_Products {
    public static final String TABLA_NAME = "products";
    public static final String TABLA_ID = "id";
    public static final String CAMPO_NAME = "NAME";
    public static final String CAMPO_PRICE = "PRICE";

    public static final String CREAR_TABLA = "CREATE TABLE " + TABLA_NAME + " (" + TABLA_ID +
            " INTEGER, "
            + CAMPO_NAME + " TEXT, "
            + CAMPO_PRICE + "TEXT" + ")";



}
