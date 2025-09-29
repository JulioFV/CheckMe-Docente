package com.itsoeh.checkmedocente.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.R;
import com.itsoeh.checkmedocente.modelo.MGrupo;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterGrupo extends RecyclerView.Adapter<AdapterGrupo.viewHolderGrupo> {

    private ArrayList<MGrupo> lista;
    private Bundle paquete;
    private Context contexto;
    private TextView txtUsuario;



    public AdapterGrupo(ArrayList<MGrupo> lista){

        this.lista=lista;
          
    }

    @NonNull
    @Override
    public AdapterGrupo.viewHolderGrupo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupo,null,false);
        contexto = parent.getContext();
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

        holder.btnGrafica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paquete.putInt("idGrupo",gpo.getIdGrupo());
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_grupos_docente_to_grafica_Grupo,paquete);
            }
        });





        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController nav = Navigation.findNavController(v);
                paquete.putInt("op",2);
                paquete.putSerializable("objeto" ,gpo);
                nav.navigate(R.id.action_grupos_docente_to_CRUD_Grupo,paquete);

            }
        });
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEliminar(gpo.getIdGrupo());
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

    private void clicEliminar(int idGrupo) {
        AlertDialog.Builder msg = new AlertDialog.Builder(contexto);

        // Crear un ProgressBar
        ProgressBar progressBar = new ProgressBar(contexto);
        progressBar.setIndeterminate(true); // Estilo de carga indeterminada

        // Crear el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setTitle("Por favor, espera");
        builder.setMessage("Conectandose con el servidor...");
        builder.setView(progressBar);
        builder.setCancelable(false); // Evitar que se pueda cancelar
        AlertDialog dialog = builder.create();
        dialog.show();

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(contexto).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.ELIMINARGPO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("Eliminando");
                            msg.setMessage("La información se elimino correctamente");
                            msg.setPositiveButton("Aceptar",null);
                            AlertDialog dialog=msg.create();
                            msg.show();
                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON

                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo eliminar");
                            msg.setPositiveButton("Aceptar",null);
                            AlertDialog dialog=msg.create();

                            msg.show();


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                // DETECTA ERRORES EN LA COMUNICACIÓN
                msg.setTitle("Error");
                msg.setMessage("No se pudo conectar con el servidor");
                msg.setPositiveButton("Aceptar",null);
                AlertDialog dialog=msg.create();
                msg.show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> param=new HashMap<String,String>();
                //PASA PARAMETROS A LA SOLICITUD
                param.put("idGrupo",idGrupo + "");


                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);
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
        ImageView btnEditar,btnEliminar,btnGrafica;
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
            btnGrafica=itemView.findViewById(R.id.it_btn_grafica);

        }
    }


}

