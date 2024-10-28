package gt.edu.umg.proyectofinalprograii;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView locationTv;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private Button btnAgregarPlatillo;  // Declaración del botón

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        locationTv = findViewById(R.id.locationTv);
        btnAgregarPlatillo = findViewById(R.id.btnAgregarPlatillo);  // Inicialización del botón
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getCurretLocation();

        // Configuración del OnClickListener para el botón
        btnAgregarPlatillo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AgregarPlatilloActivity.class);
            startActivity(intent);
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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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

