package com.itsoeh.checkmedocente.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.itsoeh.checkmedocente.modelo.MEstudiante;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterEstudiante extends RecyclerView.Adapter<AdapterEstudiante.ViewHolderEstudiante> {
    private ArrayList<MEstudiante> lista;
    private int genero;
    private Context contexto;
    private int est;


    public AdapterEstudiante(ArrayList<MEstudiante> lista) {
        this.lista = lista;
    }


    @NonNull
    @Override
    public AdapterEstudiante.ViewHolderEstudiante onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estudiante_doc, null, false);
            contexto = parent.getContext();
        return new AdapterEstudiante.ViewHolderEstudiante(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEstudiante.ViewHolderEstudiante holder, int position) {
        MEstudiante estu = lista.get(position);
        holder.txtNombre.setText(estu.getNombre());
        holder.txtmat.setText(estu.getMatricula() + "");
        holder.txtcorreo.setText(estu.getCorreo());
        holder.idGrupo.setText(estu.getIdGrupo() + "");
        holder.idEstudiante.setText(estu.getIdEstudiante() + "");
        holder.idInscripcion.setText(estu.getIdInscripcion() + "");
        genero = Integer.parseInt(estu.getGen());
        holder.spinEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                est = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (genero ==  0) {
            holder.imgFoto.setImageResource(R.drawable.perfil_alumna);
        } else if (genero == 1) {
            holder.imgFoto.setImageResource(R.drawable.perfil_alumn);

        }
        holder.btnPaseLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicPasarLista(estu.getIdInscripcion(),est, String.valueOf(holder.txtObservaciones.getText()));
            }
        });

    }

    private void clicPasarLista(int idInscripcion,int estado,String observaciones) {

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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.PASARLISTA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("Guardado");
                            msg.setMessage("La información se guardó correctamente");
                            msg.setPositiveButton("Aceptar",null);
                            AlertDialog dialog=msg.create();
                            msg.show();



                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON

                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo leer");
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
                param.put("idInscripcion",idInscripcion+"");
                param.put("estado",estado+"");
                param.put("observaciones",observaciones);


                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);

    }

    @Override
    public int getItemCount() {
        return this.lista.size();
    }

    public void filtro(ArrayList<MEstudiante> filtrados) {
        this.lista = filtrados;
        notifyDataSetChanged();
    }

    public class ViewHolderEstudiante extends RecyclerView.ViewHolder {

        TextView txtNombre, txtmat, txtcorreo, idGrupo, idEstudiante, idInscripcion;
        EditText txtObservaciones;
        Spinner spinEstado;
        ImageView imgFoto,btnPaseLista;

        public ViewHolderEstudiante(@NonNull View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.item_est_doc_nombre);
            txtmat = itemView.findViewById(R.id.item_est_doc_matricula);
            txtcorreo = itemView.findViewById(R.id.item_est_doc_correo);
            idGrupo = itemView.findViewById(R.id.item_gpo_doc_grupo);
            idEstudiante = itemView.findViewById(R.id.item_est_doc_idestu);
            idInscripcion = itemView.findViewById(R.id.item_est_doc_id_ins);
            imgFoto = itemView.findViewById(R.id.item_estu_img_gen);
            txtObservaciones = itemView.findViewById(R.id.paselista_txtobservaciones);
            spinEstado = itemView.findViewById(R.id.spin_pase_lista);
            btnPaseLista = itemView.findViewById(R.id.btn_pase_lista_alumn);

        }
    }
}

