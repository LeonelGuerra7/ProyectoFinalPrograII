package gt.edu.umg.proyectofinalprograii.activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gt.edu.umg.proyectofinalprograii.Platillo;
import gt.edu.umg.proyectofinalprograii.adapter.PlatilloAdapter;
import gt.edu.umg.proyectofinalprograii.dataBase.entity.sqlDatabase;
import gt.edu.umg.proyectofinalprograii.databinding.ActivityPlatillosGuardadosBinding;
public class PlatillosGuardadosActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PlatilloAdapter adapter;
    private sqlDatabase db;
    private ActivityPlatillosGuardadosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlatillosGuardadosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configuración del RecyclerView
        recyclerView = binding.recyclerViewPlatillos;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inicializar la base de datos
        db = sqlDatabase.getDatabase(this);

        // Crear una lista de platillos manualmente
        List<Platillo> platillos = new ArrayList<>();
        // Agregar platillos de prueba manualmente
        platillos.add(new Platillo("Prueba", "carros", "android.resource://" + getPackageName() + "/drawable/pruebaa"));
        platillos.add(new Platillo("Prueba 2", "computadora", "android.resource://" + getPackageName() + "/drawable/pruebaa2"));

        // Manejo de errores al listar platillos
        try {
            List<Platillo> platillosDb = db.platilloDao().listarTodos();
            if (platillosDb != null && !platillosDb.isEmpty()) {
                platillos.addAll(platillosDb); // Agregar los platillos de la base de datos
            }
        } catch (Exception e) {
            Log.e("PlatillosGuardados", "Error al acceder a la base de datos", e);
            Toast.makeText(this, "Error al cargar platillos", Toast.LENGTH_SHORT).show();
        }

        // Comprobar si se encontraron platillos
        if (platillos.isEmpty()) {
            Log.d("PlatillosGuardados", "No hay platillos guardados en la base de datos.");
            Toast.makeText(this, "No hay platillos guardados.", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("PlatillosGuardados", "Se encontraron " + platillos.size() + " platillos en la base de datos.");
        }

        // Configurar el adaptador con la lista de platillos
        adapter = new PlatilloAdapter(this, platillos);
        recyclerView.setAdapter(adapter);

        // Configuración del botón de salida
        binding.buttonSalir.setOnClickListener(v -> finish());
    }
}
