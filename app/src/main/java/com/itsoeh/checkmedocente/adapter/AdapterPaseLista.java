package com.itsoeh.checkmedocente.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.checkmedocente.R;
import com.itsoeh.checkmedocente.modelo.MEstudiante;
import com.itsoeh.checkmedocente.modelo.MPaseLista;

import java.util.ArrayList;

public class AdapterPaseLista extends RecyclerView.Adapter<AdapterPaseLista.ViewHolderPaseLista> {
    private ArrayList<MPaseLista> lista;
    private Context contexto;



    public AdapterPaseLista(ArrayList<MPaseLista> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public AdapterPaseLista.ViewHolderPaseLista onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pase_lista, null, false);
        contexto = parent.getContext();
        return new ViewHolderPaseLista(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPaseLista.ViewHolderPaseLista holder, int position) {
        MPaseLista pase = lista.get(position);

        if(pase.getObservaciones()!= null){
            holder.txtObservaciones.setText(pase.getObservaciones()+"");

        }else{
            holder.txtObservaciones.setText("Ninguna Observacion");

        }
        holder.txtEstado.setText(pase.getEstado()+"");
        holder.txtFecha.setText(pase.getFecha()+"");
        holder.txtIdPase.setText(pase.getIdPase()+"");


    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }
    public void filtro(ArrayList<MPaseLista> filtrados) {
        this.lista = filtrados;
        notifyDataSetChanged();
    }

    public class ViewHolderPaseLista extends RecyclerView.ViewHolder {
        private TextView txtIdPase,txtFecha,txtEstado,txtObservaciones;
        public ViewHolderPaseLista(@NonNull View itemView) {
            super(itemView);
            txtIdPase=itemView.findViewById(R.id.it_pase_idpase);
            txtFecha=itemView.findViewById(R.id.it_pase_txtfecha);
            txtEstado=itemView.findViewById(R.id.it_pase_estado);
            txtObservaciones=itemView.findViewById(R.id.it_pase_txtobservaciones);
        }
    }
}
