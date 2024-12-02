package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.adapter.AdapterGeneral;
import com.itsoeh.checkmedocente.modelo.MDocente;
import com.itsoeh.checkmedocente.modelo.MGeneral;
import com.itsoeh.checkmedocente.modelo.MHistorial;
import com.itsoeh.checkmedocente.modelo.MMaterias;
import com.itsoeh.checkmedocente.modelo.MTutor;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link historialAlumn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class historialAlumn extends Fragment {

    private TextView lblPromedio,lblCreditos,lblPeriodo,lblNombre;
    private Bundle paquete;
    private MTutor tutor;
    private RecyclerView rec;
    private AdapterGeneral adapter;
    private ArrayList<MGeneral> lista;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rec=view.findViewById(R.id.recycler_view_historial);
        lblNombre=view.findViewById(R.id.hist_lblnombre);
        paquete=getArguments();

        if (paquete!=null){
            tutor= (MTutor) paquete.getSerializable("objeto");
            //op=paquete.getInt("op");
            lblNombre.setText(tutor.getNombre() +"");
        }

        lista=llenadoDesdeBD();

    }

    private void listarHistorial() {

        MHistorial obj = new MHistorial();

        //Crea un AlertDialog
        AlertDialog.Builder msg = new AlertDialog.Builder(this.getContext());

        // Crear un ProgressBar
        ProgressBar progressBar = new ProgressBar(this.getContext());
        progressBar.setIndeterminate(true); // Estilo de carga indeterminada

        // Crear el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Por favor, espera");
        builder.setMessage("Conectandose con el servidor...");
        builder.setView(progressBar);
        builder.setCancelable(false); // Evitar que se pueda cancelar
        AlertDialog dialog = builder.create();
        dialog.show();

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.LISTARHISTORIAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            Log.e("PASO 1", response);
                            JSONObject contenido = new JSONObject(response);

                            JSONArray array=contenido.getJSONArray("contenido");
                            MHistorial obj = new MHistorial();
                            for (int i = 0; i < array.length(); i++) {
                                obj = new MHistorial();

                                JSONObject pos = new JSONObject(array.getString(i));
                                obj.setIdHistorial(pos.getInt("idHistorial"));
                                obj.setCreditos(pos.getInt("creditos"));
                                obj.setPromedio(pos.getDouble("prom"));
                                obj.setIdEstudiante(pos.getInt("idEstudiante"));
                                obj.setIdPeriodo(pos.getInt("idPeriodo"));

                            }

                            //AQUI LLENAMOS LAS ETIQUETAS
                            lblCreditos.setText(obj.getCreditos()+"");
                            lblPeriodo.setText(obj.getIdPeriodo()+"");
                            lblPromedio.setText(obj.getPromedio()+"");

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
                param.put("idEstudiante",tutor.getIdEstudiante()+"");
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);
    }

    private ArrayList<MGeneral> llenadoDesdeBD() {
        ArrayList<MGeneral> lista=new ArrayList<MGeneral>();
        AlertDialog.Builder msg = new AlertDialog.Builder(this.getContext());

        // Crear un ProgressBar
        ProgressBar progressBar = new ProgressBar(this.getContext());
        progressBar.setIndeterminate(true); // Estilo de carga indeterminada

        // Crear el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Por favor, espera");
        builder.setMessage("Conectandose con el servidor...");
        builder.setView(progressBar);
        builder.setCancelable(false); // Evitar que se pueda cancelar
        AlertDialog dialog = builder.create();
        dialog.show();

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.LISTARH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            JSONObject contenido = new JSONObject(response);
                            JSONArray array=contenido.getJSONArray("contenido");
                            MGeneral obj=new MGeneral();
                            for (int i = 0; i < array.length(); i++) {
                                obj=new MGeneral();
                                JSONObject pos=new JSONObject(array.getString(i));
                                obj.setIdHistorial(pos.getInt("idHistorial"));
                                obj.setCreditos(pos.getInt("creditos"));
                                obj.setProm(pos.getInt("prom"));
                                obj.setIdEstudiante(pos.getInt("idEstudiante"));
                                obj.setIdPeriodo(pos.getInt("idPeriodo"));
                                obj.setPeriodo(pos.getString("nombre"));
                                lista.add(obj);
                            }
                            Log.e("LISTA",obj.toString());
                            rec.setHasFixedSize(true);
                            rec.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter=new AdapterGeneral(lista);
                            rec.setAdapter(adapter);
                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON
                            msg.setTitle("Error");
                            msg.setMessage("No se pudo leer el archivo JSON....");
                            msg.setPositiveButton("Aceptar",null);
                            AlertDialog dialog = msg.create();
                            msg.show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                // DETECTA ERRORES EN LA COMUNICACIÓN
                msg.setTitle("Error");
                msg.setMessage("No se pudo leer el archivo JSON*-*");
                msg.setPositiveButton("Aceptar",null);
                AlertDialog dialog = msg.create();
                msg.show();
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> param=new HashMap<String,String>();
                param.put("id",tutor.getIdEstudiante()+"");
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);

        return lista;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public historialAlumn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment historialAlumn.
     */
    // TODO: Rename and change types and number of parameters
    public static historialAlumn newInstance(String param1, String param2) {
        historialAlumn fragment = new historialAlumn();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_historial_alumn, container, false);
    }
}