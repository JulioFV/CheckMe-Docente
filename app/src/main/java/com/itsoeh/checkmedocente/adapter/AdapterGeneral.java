package com.itsoeh.checkmedocente.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.checkmedocente.R;
import com.itsoeh.checkmedocente.modelo.MGeneral;
import com.itsoeh.checkmedocente.modelo.MHistorial;

import java.util.ArrayList;

public class AdapterGeneral extends RecyclerView.Adapter<AdapterGeneral.viewHolderGeneral> {
    private ArrayList<MGeneral> lista;
    private Bundle paquete;
    private MGeneral obj;

    public AdapterGeneral(ArrayList<MGeneral> lista){
        this.lista=lista;
    }

    @NonNull
    @Override
    public AdapterGeneral.viewHolderGeneral onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial,null,false);
        return new viewHolderGeneral(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGeneral.viewHolderGeneral holder, int position) {
        holder.obj=lista.get(position);
        holder.txtPeriodo.setText(holder.obj.getPeriodo()+"");
        holder.txtPromedio.setText(holder.obj.getProm()+"");
        holder.txtCreditos.setText(holder.obj.getCreditos()+"");
        paquete = new Bundle();

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void filtro(ArrayList<MGeneral> filtrados){
        this.lista=filtrados;
        notifyDataSetChanged();
    }

    public class viewHolderGeneral extends RecyclerView.ViewHolder {
        public MGeneral obj;
        public TextView txtPeriodo;
        public TextView txtPromedio;
        public TextView txtCreditos;

        public viewHolderGeneral(@NonNull View itemView) {
            super(itemView);
            txtPeriodo=itemView.findViewById(R.id.general_periodo);
            txtPromedio=itemView.findViewById(R.id.general_promedio);
            txtCreditos=itemView.findViewById(R.id.general_creditos);
        }
    }
}