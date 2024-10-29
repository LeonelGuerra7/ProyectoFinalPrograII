package gt.edu.umg.proyectofinalprograii.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import gt.edu.umg.proyectofinalprograii.Platillo;
import gt.edu.umg.proyectofinalprograii.R;
public class PlatilloAdapter extends RecyclerView.Adapter<PlatilloAdapter.PlatilloViewHolder> {
    private Context context;
    private List<Platillo> platillos;

    public PlatilloAdapter(Context context, List<Platillo> platillos) {
        this.context = context;
        this.platillos = platillos;
    }

    @NonNull
    @Override
    public PlatilloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_platillo, parent, false);
        return new PlatilloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlatilloViewHolder holder, int position) {
        Platillo platillo = platillos.get(position);

        // Cargar la imagen desde la URI
        if (platillo.getImagenUri() != null) {
            holder.imagenPlatillo.setImageURI(Uri.parse(platillo.getImagenUri()));
        } else {
            // Si no hay imagen, mostrar una imagen por defecto
            holder.imagenPlatillo.setImageResource(R.drawable.pruebaa); // Cambia a pruebaa2 si lo deseas
        }

        holder.nombrePlatillo.setText(platillo.getNombre());
        holder.descripcionPlatillo.setText(platillo.getDescripcion());
    }

    @Override
    public int getItemCount() {
        return platillos.size();
    }

    public static class PlatilloViewHolder extends RecyclerView.ViewHolder {
        ImageView imagenPlatillo;
        TextView nombrePlatillo;
        TextView descripcionPlatillo;

        public PlatilloViewHolder(@NonNull View itemView) {
            super(itemView);
            imagenPlatillo = itemView.findViewById(R.id.imagenPlatillo);
            nombrePlatillo = itemView.findViewById(R.id.nombrePlatillo);
            descripcionPlatillo = itemView.findViewById(R.id.descripcionPlatillo);
        }
    }
}
