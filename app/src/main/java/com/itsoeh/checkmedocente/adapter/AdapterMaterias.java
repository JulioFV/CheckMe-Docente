package com.itsoeh.checkmedocente.adapter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.checkmedocente.R;
import com.itsoeh.checkmedocente.modelo.MMaterias;
import com.itsoeh.checkmedocente.modelo.MTutor;

import java.util.ArrayList;

public class AdapterMaterias extends RecyclerView.Adapter<AdapterMaterias.ViewHolderMaterias>{

    private ArrayList<MMaterias> lista;
    private Bundle paquete;
    private NavController navegador;
    public AdapterMaterias(ArrayList<MMaterias> lista) {
        this.lista = lista;
    }


    @NonNull
    @Override
    public AdapterMaterias.ViewHolderMaterias onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_materia_tut,parent,false);
        return new ViewHolderMaterias(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMaterias.ViewHolderMaterias holder, int position) {
        holder.mat=lista.get(position);


        paquete = new Bundle();//Creacion del Bundle
        MMaterias mat=lista.get(position);

        holder.txtDocente.setText(holder.mat.getNombre());
        holder.txtClave.setText(holder.mat.getClave());
        holder.txtNombreMateria.setText(holder.mat.getNombreAsig());
        holder.btnAsistencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paquete.putSerializable("objeto" , mat);
                Log.e("QUE SE PASA HACIA ASISTENCIAS",paquete.toString());
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_materiasTutorado_to_asistencias_tutorado,paquete);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolderMaterias extends RecyclerView.ViewHolder {

        TextView txtNombreMateria,txtClave,txtDocente;

        MMaterias mat;
        ImageView btnAsistencias;
        public ViewHolderMaterias(@NonNull View itemView) {
            super(itemView);
            txtNombreMateria=itemView.findViewById(R.id.item_mat_nombreasig);
            txtClave=itemView.findViewById(R.id.item_mat_clave);
            txtDocente=itemView.findViewById(R.id.item_mat_docente);
            btnAsistencias=itemView.findViewById(R.id.asistencias_tut);
        }
    }

    public void filtro(ArrayList<MMaterias> filtrados){
        this.lista = filtrados;
        notifyDataSetChanged();
    }
}
