package gt.edu.umg.proyectofinalprograii.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import gt.edu.umg.proyectofinalprograii.R;
public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView locationTv;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private Button btnAgregarPlatillo;
    private Button btnPlatillosGuardados;
    private Button btnIntegrantes; // Nuevo botón para mostrar integrantes
    private LinearLayout linearLayoutIntegrantes; // LinearLayout para los integrantes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        locationTv = findViewById(R.id.locationTv);
        btnAgregarPlatillo = findViewById(R.id.btnAgregarPlatillo);
        btnPlatillosGuardados = findViewById(R.id.btnPlatillosGuardados);
        btnIntegrantes = findViewById(R.id.buttonIntegrantes); // Inicializa el botón de integrantes
        linearLayoutIntegrantes = findViewById(R.id.linearLayoutIntegrantes); // Inicializa el LinearLayout de integrantes

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getCurretLocation();

        // Navegar a AgregarPlatilloActivity
        btnAgregarPlatillo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AgregarPlatilloActivity.class);
            startActivity(intent);
        });

        // Navegar a PlatillosGuardadosActivity
        btnPlatillosGuardados.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PlatillosGuardadosActivity.class);
            startActivity(intent);
        });

        // Mostrar u ocultar la información de integrantes al hacer clic en el botón
        btnIntegrantes.setOnClickListener(v -> {
            if (linearLayoutIntegrantes.getVisibility() == View.GONE) {
                linearLayoutIntegrantes.setVisibility(View.VISIBLE);
            } else {
                linearLayoutIntegrantes.setVisibility(View.GONE);
            }
        });
    }

    private void getCurretLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION
            );
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                locationTv.setText(
                        "Latitud: " + location.getLatitude() + "\n" +
                                "Longitud: " + location.getLongitude()
                );
            } else {
                locationTv.setText("No se pudo obtener la ubicación");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurretLocation();
            } else {
                locationTv.setText("Permiso de ubicación denegado");
            }
        }
    }
}
