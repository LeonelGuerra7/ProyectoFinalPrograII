package gt.edu.umg.proyectofinalprograii;


import android.graphics.Bitmap;

import java.io.Serializable;

import android.graphics.Bitmap;

import androidx.room.Entity;

import java.io.Serializable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "platillos")
public class Platillo implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id; // ID del platillo
    private String nombre; // Nombre del platillo
    private String descripcion; // Descripción del platillo

    // Constructor vacío
    public Platillo() {}

    // Constructor que inicializa todos los atributos
    public Platillo(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Constructor para inicializar solo nombre y descripción
    public Platillo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Platillo(String nombre, String descripcion, String imagenUri) {
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
