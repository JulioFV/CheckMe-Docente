package com.itsoeh.checkmedocente.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.R;
import com.itsoeh.checkmedocente.modelo.MEstudiante;
import com.itsoeh.checkmedocente.modelo.MPaseLista;
import com.itsoeh.checkmedocente.utils.Alert;
import com.itsoeh.checkmedocente.utils.Dia_Fecha;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pase_lista, parent, false);
        contexto = parent.getContext();
        return new ViewHolderPaseLista(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPaseLista.ViewHolderPaseLista holder, int position) {
        MPaseLista pase = lista.get(position);

        holder.SPEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                est=position;
                switch (position){
                    case 0:
                        holder.SPEstado.setBackground(contexto.getResources().getDrawable(R.drawable.bg_asistencia));
                        break;
                    case 1:
                        holder.SPEstado.setBackground(contexto.getResources().getDrawable(R.drawable.bg_falta));
                        break;
                    case 2:
                        holder.SPEstado.setBackground(contexto.getResources().getDrawable(R.drawable.bg_retardo));
                        break;
                    case 3:
                        holder.SPEstado.setBackground(contexto.getResources().getDrawable(R.drawable.bg_justificante));
                        break;
                    default:
                        holder.SPEstado.setBackground(contexto.getResources().getDrawable(R.drawable.bg_asistencia));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        holder.SPEstado.setSelection(pase.getEstado());
        holder.habEdicion.setChecked(false);
        holder.SPEstado.setEnabled(false);
        holder.habEdicion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                new Alert(contexto).mostrarDialogoBoton("EDICIÓN","Ahora puedes editar este campo");
                holder.SPEstado.setEnabled(true);
                holder.btnGuardar.setVisibility(View.VISIBLE);
                holder.txtObservaciones.setEnabled(true);
                //fondoBtn.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.bg_offline));

            } else {
                new Alert(contexto).mostrarDialogoBoton("EDICIÓN","Se deshabilito la edición");
                holder.SPEstado.setEnabled(false);
                holder.btnGuardar.setVisibility(View.GONE);
                holder.txtObservaciones.setEnabled(false);
               // fondoBtn.setBackground(ContextCompat.getDrawable(requireContext(), R.drawable.degradado3));
            }
        });
        switch (pase.getEstado()){
            case 0:
                holder.SPEstado.setSelection(0);
                holder.SPEstado.setBackground(contexto.getResources().getDrawable(R.drawable.bg_asistencia));
                break;
            case 1:
                holder.SPEstado.setSelection(1);
                holder.SPEstado.setBackground(contexto.getResources().getDrawable(R.drawable.bg_falta));
                break;
            case 2:
                holder.SPEstado.setSelection(2);
                holder.SPEstado.setBackground(contexto.getResources().getDrawable(R.drawable.bg_retardo));
                break;
            case 3:
                holder.SPEstado.setSelection(3);
                holder.SPEstado.setBackground(contexto.getResources().getDrawable(R.drawable.bg_justificante));
                break;
            default:

        }
        String fecha = Dia_Fecha.aFechaLarga(pase.getFecha());
        holder.txtFecha.setText(fecha);
        holder.txtIdPase.setText("Pase de lista");
        /**
         *
         * FALTA AGREGAR LA MATERIA DE DONDE VIENE LA ASISTENCIA
         * 
         * */
    }

    private void GuardarCambios(int idPase) {
        new Alert(contexto).mostrarDialogoProgress("Por favor espere..." , "Conectandose con el servidor");

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(contexto).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.ACTUALIZAPASE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        new Alert(contexto).cerrarDialogo();
                        try {
                            new Alert(contexto).mostrarDialogoBoton(
                                    "Actualización","Tarea realizada con exito");
                        }catch (Exception ex){
                            new Alert(contexto).mostrarDialogoBoton(
                                    "ERROR","Ocurrio un error inesperado \n Intente de nuevo más tarde");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new Alert(contexto).cerrarDialogo();
                new Alert(contexto).mostrarDialogoBoton(
                        "ERROR","Ocurrio un error inesperado");
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
        private TextView txtIdPase,txtFecha,txtMateria;
        private ImageView btnGuardar;
        private Switch habEdicion;
        private Spinner SPEstado;
        private EditText txtObservaciones;
        public ViewHolderPaseLista(@NonNull View itemView) {
            super(itemView);
            txtIdPase=itemView.findViewById(R.id.it_pase_idpase);
            txtFecha=itemView.findViewById(R.id.it_pase_txtfecha);
            txtObservaciones=itemView.findViewById(R.id.it_pase_txtobservaciones);
            btnGuardar = itemView.findViewById(R.id.btn_modificar_asistencia);
            habEdicion = itemView.findViewById(R.id.sw_editar_pase);
            SPEstado = itemView.findViewById(R.id.sp_editar_estado);
            txtMateria = itemView.findViewById(R.id.it_pase_txtmateria);

        }
    }
}
