package com.itsoeh.checkmedocente.adapter;

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
import com.itsoeh.checkmedocente.modelo.MEstudiante;

import java.util.ArrayList;

public class AdapterEstudiante extends RecyclerView.Adapter<AdapterEstudiante.ViewHolderEstudiante> {
    private ArrayList<MEstudiante> lista;


    public AdapterEstudiante(ArrayList<MEstudiante> lista) {
        this.lista = lista;
    }


    @NonNull
    @Override
    public AdapterEstudiante.ViewHolderEstudiante onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estudiante,null,false);

        return new AdapterEstudiante.ViewHolderEstudiante(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEstudiante.ViewHolderEstudiante holder, int position) {
        MEstudiante estu=lista.get(position);
        holder.txtNombre.setText(estu.getNombre());
        holder.txtmat.setText(estu.getMatricula()+"");
        holder.txtcorreo.setText(estu.getCorreo());
        holder.idGrupo.setText(estu.getIdGrupo()+"");
        holder.idEstudiante.setText(estu.getIdEstudiante()+"");
        holder.idInscripcion.setText(estu.getIdInscripcion()+"");
    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public void filtro(ArrayList<MEstudiante> filtrados){
        this.lista=filtrados;
        notifyDataSetChanged();
    }

    public class ViewHolderEstudiante extends RecyclerView.ViewHolder {

        TextView txtNombre,txtmat, txtcorreo, idGrupo, idEstudiante, idInscripcion;

        public ViewHolderEstudiante(@NonNull View itemView) {
            super(itemView);
            txtNombre=itemView.findViewById(R.id.item_est_doc_nombre);
            txtmat=itemView.findViewById(R.id.item_est_doc_matricula);
            txtcorreo=itemView.findViewById(R.id.item_est_doc_correo);
            idGrupo=itemView.findViewById(R.id.item_gpo_doc_grupo);
            idEstudiante=itemView.findViewById(R.id.item_est_doc_idestu);
            idInscripcion=itemView.findViewById(R.id.item_est_doc_id_ins);
        }
    }
}

