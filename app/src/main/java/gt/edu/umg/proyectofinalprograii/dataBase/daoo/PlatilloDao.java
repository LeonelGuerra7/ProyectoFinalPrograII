package gt.edu.umg.proyectofinalprograii.dataBase.daoo;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import gt.edu.umg.proyectofinalprograii.Platillo;
@Dao
public interface PlatilloDao {

    @Insert
    default void insertar(Platillo platillo) {
        Log.d("PlatilloDao", "Insertando platillo: " + platillo.getNombre());
        // Inserta el platillo
    }

    @Update
    void actualizar(Platillo platillo);

    @Delete
    void eliminar(Platillo platillo);

    @Query("SELECT * FROM platillos")
    List<Platillo> listarTodos();
}
