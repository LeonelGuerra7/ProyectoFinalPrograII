package gt.edu.umg.proyectofinalprograii;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.Manifest;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import gt.edu.umg.proyectofinalprograii.databinding.ActivityAgregarPlatilloBinding;

public class AgregarPlatilloActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
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
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                solicitarPermisoCamara();
            } else {
                abrirCamara();
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

    private void solicitarPermisoCamara() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            // Mostrar un diálogo explicativo antes de pedir el permiso
            new AlertDialog.Builder(this)
                    .setTitle("Permiso de cámara requerido")
                    .setMessage("Para tomar una foto del platillo, necesitas permitir el uso de la cámara. ¿Quieres permitirlo?")
                    .setPositiveButton("Permitir", (dialog, which) -> ActivityCompat.requestPermissions(AgregarPlatilloActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION))
                    .setNegativeButton("No permitir", (dialog, which) -> dialog.dismiss())
                    .create()
                    .show();
        } else {
            // Solicitar permiso directamente
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
    }

    private void abrirCamara() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCamara();
            } else {
                new AlertDialog.Builder(this)
                        .setMessage("Permiso de cámara denegado. No podrás tomar una foto del platillo.")
                        .setPositiveButton("Ok", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            }
        }
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