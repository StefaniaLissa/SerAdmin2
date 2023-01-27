package com.example.seradmin.Recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seradmin.R;

import java.util.ArrayList;

public class AdaptadorListado extends RecyclerView.Adapter<AdaptadorListado.ViewHolder> {

    private ArrayList<PerfilesClientes> perfilesList;

    public interface ItemClickListener {
        void onClick(View view, int position, PerfilesClientes perfilesClientes);
    }

    private ItemClickListener clickListener;

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    private RecyclerViewClickListener listener;

    public AdaptadorListado(ArrayList<PerfilesClientes> dataSet, RecyclerViewClickListener listener) {
        perfilesList = dataSet;
        this.listener = listener;
        //perfilesList.addAll(dataSet);;

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView nomClienteCajaPerfiles;
        private final TextView letraNombre;
        private final ImageView imagenCajaPerfiles;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            nomClienteCajaPerfiles = (TextView) v.findViewById(R.id.NomClienteCajaPerfiles);
            letraNombre = (TextView) v.findViewById(R.id.letraNombre);
            imagenCajaPerfiles = (ImageView) v.findViewById(R.id.imagenCajaPerfiles);

        }


        public TextView getNomCliente() {
            return nomClienteCajaPerfiles;
        }

        public TextView getLetraNom() {
            return letraNombre;
        }

        public ImageView getImagenPerfil() {
            return imagenCajaPerfiles;
        }

        public void onClick(View view) {
            // Si tengo un manejador de evento lo propago con el índice
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition(), perfilesList.get(getAdapterPosition()));
        }

    }

    /*public AdaptadorImagen(PerfilesImagen[] dataSet) {
        perfilesList = new ArrayList<>();
        perfilesList.addAll(Arrays.asList (dataSet));;

    }*/


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_perfiles, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getNomCliente().setText(" " + perfilesList.get(position).getNombre() +
                " " + perfilesList.get(position).getApellidos());
        holder.getLetraNom().setText(perfilesList.get(position).getLetra());
        holder.getImagenPerfil().setImageResource(perfilesList.get(position).getImagen());

    }

    @Override
    public int getItemCount() {
        return perfilesList.size();
    }

}