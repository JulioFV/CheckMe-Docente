package com.itsoeh.checkmedocente.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.R;
import com.itsoeh.checkmedocente.modelo.MEstudiante;
import com.itsoeh.checkmedocente.modelo.MPaseLista;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdapterPaseLista extends RecyclerView.Adapter<AdapterPaseLista.ViewHolderPaseLista> {
    private ArrayList<MPaseLista> lista;
    private Context contexto;
    private int est;




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


        if(pase.getOpcion() == 1){
            holder.Areaeditar.setVisibility(View.GONE);
            holder.btnGuardar.setVisibility(View.GONE);
        } else if (pase.getOpcion() == 2) {
            holder.Areaeditar.setVisibility(View.VISIBLE);
            holder.btnGuardar.setVisibility(View.VISIBLE);
            holder.SPEstado.setEnabled(false);
        }
        holder.SPEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                est=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.habEdicion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlertDialog.Builder msg = new AlertDialog.Builder(contexto);
                msg.setTitle("EDITAR");
                msg.setMessage("Ahora Puedes editar");
                msg.setPositiveButton("Aceptar",null);
                AlertDialog dialog=msg.create();
                msg.show();
                holder.btnGuardar.setClickable(true);
                holder.SPEstado.setEnabled(true);
            }
        });
        holder.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuardarCambios(pase.getIdPase());
            }
        });

        if(pase.getObservaciones()!= null){
            holder.txtObservaciones.setText(pase.getObservaciones()+"");

        }else{
            holder.txtObservaciones.setText("Ninguna Observacion");
        }
        if(pase.getEstado() == 0){
                holder.txtEstado.setText("Asistencia");
            } else if (pase.getEstado() == 1) {
                holder.txtEstado.setText("Falta");
            } else if (pase.getEstado() == 2) {
                holder.txtEstado.setText("Retardo");
            } else if (pase.getEstado() == 3) {
                holder.txtEstado.setText("Justificante");
            }

        holder.txtFecha.setText(pase.getFecha()+"");
        holder.txtIdPase.setText(pase.getIdPase()+"");


    }

    private void GuardarCambios(int idPase) {
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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.ACTUALIZAPASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("ACTUALIZANDO");
                            msg.setMessage("Tarea realizada con exito");
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
                param.put("idPase",idPase+"");
                param.put("estado",est+"");

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
    public void filtro(ArrayList<MPaseLista> filtrados) {
        this.lista = filtrados;
        notifyDataSetChanged();
    }

    public class ViewHolderPaseLista extends RecyclerView.ViewHolder {
        private TextView txtIdPase,txtFecha,txtEstado,txtObservaciones;
        private LinearLayout Areaeditar;
        private ImageView btnGuardar;
        private Switch habEdicion;
        private Spinner SPEstado;
        public ViewHolderPaseLista(@NonNull View itemView) {
            super(itemView);
            txtIdPase=itemView.findViewById(R.id.it_pase_idpase);
            txtFecha=itemView.findViewById(R.id.it_pase_txtfecha);
            txtEstado=itemView.findViewById(R.id.it_pase_estado);
            txtObservaciones=itemView.findViewById(R.id.it_pase_txtobservaciones);
            Areaeditar = itemView.findViewById(R.id.linear_editar_asistencia);
            btnGuardar = itemView.findViewById(R.id.btn_modificar_asistencia);
            habEdicion = itemView.findViewById(R.id.sw_editar_pase);
            SPEstado = itemView.findViewById(R.id.sp_editar_estado);

        }
    }
}
