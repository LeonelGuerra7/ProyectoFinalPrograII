package gt.edu.umg.proyectofinalprograii;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import gt.edu.umg.proyectofinalprograii.databinding.ActivityAgregarPlatilloBinding;

public class AgregarPlatilloActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText editTextNombre, editTextDescripcion;
    private ImageView imageViewPlatillo;
    private Bitmap imagenPlatillo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_platillo);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        imageViewPlatillo = findViewById(R.id.imageViewPlatillo);
        Button buttonTomarFoto = findViewById(R.id.buttonTomarFoto);
        Button buttonGuardarPlatillo = findViewById(R.id.buttonGuardarPlatillo);

        // Botón para tomar una foto
        buttonTomarFoto.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        // Botón para guardar el platillo
        buttonGuardarPlatillo.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString();
            String descripcion = editTextDescripcion.getText().toString();
            Platillo nuevoPlatillo = new Platillo(nombre, descripcion, imagenPlatillo);
            Intent resultIntent = new Intent();
            resultIntent.putExtra("newPlatillo", nuevoPlatillo);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imagenPlatillo = (Bitmap) extras.get("data");
            imageViewPlatillo.setImageBitmap(imagenPlatillo);
        }
    }


}