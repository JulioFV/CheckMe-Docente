package com.itsoeh.checkmedocente.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.itsoeh.checkmedocente.R;
import com.itsoeh.checkmedocente.modelo.MGrupo;

import java.util.ArrayList;

public class AdapterGrupo extends RecyclerView.Adapter<AdapterGrupo.viewHolderGrupo> {

    private ArrayList<MGrupo> lista;
    private Bundle paquete;

    private TextView txtUsuario;



    public AdapterGrupo(ArrayList<MGrupo> lista){

        this.lista=lista;
    }

    @NonNull
    @Override
    public AdapterGrupo.viewHolderGrupo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupo,null,false);

        return new viewHolderGrupo(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGrupo.viewHolderGrupo holder, int position) {

        MGrupo gpo=lista.get(position);
        holder.txtNombreAsignatura.setText(gpo.getNombreAsig());
        holder.txtClaveGrupo.setText(gpo.getClave());
        holder.txtNombreDocente.setText(gpo.getNombreDoc());
        holder.txtPeriodo.setText(gpo.getNombrePer());
        paquete = new Bundle();





        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paquete.putSerializable("objeto" , gpo);
                clicEditar(v);
            }
        });
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paquete.putSerializable("objeto" , gpo);
                clicEliminar(v);
            }
        });
        //holder.txtOp.setText(gpo.getOp()==1?"Ordinario":gpo.getOp()==2?"Recursamiento":"Especial");

        holder.btnver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paquete.putSerializable("objeto" , gpo);
                clicVer(v);
            }
        });

    }

    private void clicEliminar(View v) {

    }

    private void clicEditar(View v) {

    }

    private void clicVer(View v) {

        NavController nav = Navigation.findNavController(v);
        nav.navigate(R.id.action_grupos_docente_to_estudiante_Docente,paquete);



    }

    @Override
    public int getItemCount() {return lista.size();
    }

    public void filtro(ArrayList<MGrupo> filtrados){
        this.lista=filtrados;
        notifyDataSetChanged();
    }

    public class viewHolderGrupo extends RecyclerView.ViewHolder {

        Button btnver;
        ImageView btnEditar,btnEliminar;
        EditText txtNombreAsignatura, txtNombreDocente,txtClaveGrupo,txtPeriodo,txtOp;

        public viewHolderGrupo(@NonNull View itemView) {
            super(itemView);
            txtNombreAsignatura = itemView.findViewById(R.id.item_gpo_txtNombre);
            txtClaveGrupo = itemView.findViewById(R.id.item_gpo_clave);
            txtNombreDocente = itemView.findViewById(R.id.item_gpo_docente);
            txtPeriodo = itemView.findViewById(R.id.item_gpo_periodo);
            txtOp= itemView.findViewById(R.id.item_gpo_op2);
            btnver = itemView.findViewById(R.id.item_gpo_btnver);
            txtUsuario=itemView.findViewById(R.id.Grupos_txtNombre);
            btnEditar=itemView.findViewById(R.id.it_btn_editar);
            btnEliminar=itemView.findViewById(R.id.it_btn_eliminar);

        }
    }


}

