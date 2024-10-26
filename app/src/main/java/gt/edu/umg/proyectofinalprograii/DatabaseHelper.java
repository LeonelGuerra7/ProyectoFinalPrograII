package gt.edu.umg.proyectofinalprograii;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "platillos.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PLATILLOS = "platillos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_IMAGEN_URI = "imagen_uri";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PLATILLOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOMBRE + " TEXT NOT NULL, " +
                    COLUMN_DESCRIPCION + " TEXT NOT NULL, " +
                    COLUMN_IMAGEN_URI + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLATILLOS);
        onCreate(db);
    }

    // Método para agregar un platillo
    public void agregarPlatillo(String nombre, String descripcion, String imagenUri) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_DESCRIPCION, descripcion);
        values.put(COLUMN_IMAGEN_URI, imagenUri);
        db.insert(TABLE_PLATILLOS, null, values);
        db.close();
    }


    // Método para eliminar un platillo
    public void eliminarPlatillo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLATILLOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}