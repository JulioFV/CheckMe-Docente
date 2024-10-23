package com.itsoeh.checkmedocente.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.checkmedocente.R;
import com.itsoeh.checkmedocente.modelo.MTutor;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterTutor extends RecyclerView.Adapter<AdapterTutor.ViewHolder>{
<<<<<<< HEAD
=======


>>>>>>> 075e6c7 (Proyecto Funcionando al 100 (SOLO API DE TUTOR))
        private ArrayList<MTutor> lista;
        private Bundle paquete;


        public AdapterTutor(ArrayList<MTutor> lista) {
            this.lista = lista;

        }


    @NonNull
    @Override
    public AdapterTutor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estudiante,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTutor.ViewHolder holder, int position) {
            MTutor mTutor = lista.get(position);
            // AQUI VAN LOS EVENTOS DE SET TEXT Y ONCLICK LISTENER
        MTutor tut=lista.get(position);
        holder.lblCorreo.setText(mTutor.getCorreo()+"");
        holder.lblIdEstu.setText(mTutor.getIdEstudiante()+"");
        holder.lblMatricula.setText(mTutor.getMatricula()+"");
        holder.lblNombreEstu.setText(mTutor.getNombre()+"");
        holder.lblIdGrupo.setText(mTutor.getIdGrupo()+"");
        holder.lblIDasig.setText(mTutor.getIdInscripcion()+"");
            paquete = new Bundle();//Creacion del Bundle

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paquete.putSerializable("objeto" , tut);
                clicEliminar(v);
            }
        });
    }

    private void clicEliminar(View v) {
        NavController nav = Navigation.findNavController(v);
        paquete.putInt("op",1);
        nav.navigate(R.id.action_frg_Tutorados_to_del_Tutorado,paquete);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    public void filtro(ArrayList<MTutor> filtrados){
            this.lista = filtrados;
            notifyDataSetChanged();
    }
<<<<<<< HEAD
=======

>>>>>>> 075e6c7 (Proyecto Funcionando al 100 (SOLO API DE TUTOR))
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView lblMatricula,lblNombreEstu,lblIdEstu,lblCorreo,lblIdGrupo,lblIDasig;
        private ImageView btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
<<<<<<< HEAD
=======

>>>>>>> 075e6c7 (Proyecto Funcionando al 100 (SOLO API DE TUTOR))
            btnEliminar=itemView.findViewById(R.id.it_est_btn_eliminar);
            lblMatricula = itemView.findViewById(R.id.item_est_lblmatricula);
            lblNombreEstu = itemView.findViewById(R.id.item_est_lblnombre);
            lblIdEstu = itemView.findViewById(R.id.item_est_lblidestu);
            lblCorreo = itemView.findViewById(R.id.item_est_lblcorreo);
            lblIdGrupo = itemView.findViewById(R.id.item_gpo_lblgrupo);
            lblIDasig = itemView.findViewById(R.id.item_gpo_lbl_id_ins);
<<<<<<< HEAD
=======

>>>>>>> 075e6c7 (Proyecto Funcionando al 100 (SOLO API DE TUTOR))
        }
    }
}
