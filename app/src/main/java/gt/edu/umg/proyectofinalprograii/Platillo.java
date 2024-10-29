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
    private String imagenUri; // URI de la imagen del platillo

    // Constructor vacío
    public Platillo() {}

    // Constructor que inicializa todos los atributos
    public Platillo(int id, String nombre, String descripcion, String imagenUri) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenUri = imagenUri; // Inicializamos la URI
    }

    // Constructor para inicializar solo nombre, descripción e imagen
    public Platillo(String nombre, String descripcion, String imagenUri) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenUri = imagenUri; // Aquí se guarda la ruta
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

    public String getImagenUri() {
        return imagenUri;
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

    public void setImagenUri(String imagenUri) {
        this.imagenUri = imagenUri; // Establecemos la URI de la imagen
    }
}
