package gt.edu.umg.proyectofinalprograii.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.Manifest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import gt.edu.umg.proyectofinalprograii.Platillo;
import gt.edu.umg.proyectofinalprograii.R;
import gt.edu.umg.proyectofinalprograii.dataBase.entity.sqlDatabase;

public class AgregarPlatilloActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private EditText editTextNombre, editTextDescripcion;
    private ImageView imageViewPlatillo;
    private Bitmap imagenPlatillo; // Mantener el Bitmap solo para mostrar la imagen
    private String imagenUri; // URI de la imagen
    private sqlDatabase db; // Instancia de la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_platillo);

        // Inicializar vistas
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        imageViewPlatillo = findViewById(R.id.imageViewPlatillo);
        Button buttonTomarFoto = findViewById(R.id.buttonTomarFoto);
        Button buttonGuardarPlatillo = findViewById(R.id.buttonGuardarPlatillo);
        Button buttonSalirPrincipal = findViewById(R.id.btnSalirPrincipal);

        // Inicializar la base de datos
        db = sqlDatabase.getDatabase(this);

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

            // Verifica que los campos no estén vacíos
            if (nombre.isEmpty() || descripcion.isEmpty() || imagenUri == null) {
                Toast.makeText(this, "Por favor, completa todos los campos y toma una foto.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear un nuevo platillo usando la URI de la imagen
            Platillo nuevoPlatillo = new Platillo(nombre, descripcion, imagenUri);

            // Insertar el platillo en la base de datos en un hilo separado
            new Thread(() -> {
                db.platilloDao().insertar(nuevoPlatillo);
                runOnUiThread(() -> {
                    Toast.makeText(AgregarPlatilloActivity.this, "Platillo guardado correctamente.", Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("newPlatillo", nuevoPlatillo);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                });
            }).start();
        });

        // Botón para regresar a MainActivity
        buttonSalirPrincipal.setOnClickListener(v -> {
            Intent intent = new Intent(AgregarPlatilloActivity.this, MainActivity.class);
            startActivity(intent);
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

            // Guardar la imagen y obtener su URI
            imagenUri = guardarImagen(imagenPlatillo);
        }
    }

    private String guardarImagen(Bitmap bitmap) {
        try {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(storageDir, "platillo_" + System.currentTimeMillis() + ".jpg");

            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            return Uri.fromFile(imageFile).toString(); // Retorna la URI como String
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
