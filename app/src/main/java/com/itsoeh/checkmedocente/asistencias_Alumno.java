package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.adapter.AdapterPaseLista;
import com.itsoeh.checkmedocente.modelo.MGrupo;
import com.itsoeh.checkmedocente.modelo.MMaterias;
import com.itsoeh.checkmedocente.modelo.MPaseLista;
import com.itsoeh.checkmedocente.utils.Alert;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link asistencias_Alumno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class asistencias_Alumno extends Fragment {

    private RecyclerView rec;
    private AdapterPaseLista adapter;
    private ArrayList<MPaseLista> lista;
    private Bundle paquete;
    private NavController navegador;
    private MMaterias obj;
    private MGrupo objGpo;
    private int idInscripcion;
    private TextView lblNombre;
    private CardView btnRegresar;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rec=view.findViewById(R.id.recycler_view_asistencias_alumno);
        lblNombre=view.findViewById(R.id.asistencias_lblnombre);
        navegador = Navigation.findNavController(view);

        paquete= this.getArguments();
        idInscripcion = paquete.getInt("idInscripcion");
        lblNombre.setText(paquete.getString("nombre"));
        btnRegresar=view.findViewById(R.id.asistencias_btn_back);
        lista=llenadoDesdeBD();
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private ArrayList<MPaseLista> llenadoDesdeBD() {
        ArrayList<MPaseLista> lista=new ArrayList<MPaseLista>();

        //Crea un AlertDialog
        Alert dialogo = new Alert(getContext());
        dialogo.mostrarDialogoProgress("Por favor espere...","Conectandose con el servidor");

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.LISTARASISTENCIAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialogo.cerrarDialogo();
                        try {
                            JSONObject contenido = new JSONObject(response);
                            JSONArray array=contenido.getJSONArray("contenido");
                            MPaseLista obj=new MPaseLista();

                                for (int i = 0; i < array.length(); i++) {
                                    obj=new MPaseLista();
                                    JSONObject pos=new JSONObject(array.getString(i));

                                    obj.setIdPase(pos.getInt("idPase"));
                                    obj.setFecha(pos.getString("fecha"));
                                    obj.setIdInscripcion(pos.getInt("idInscripcion"));
                                    obj.setEstado(pos.getInt("estado"));
                                    obj.setObservaciones(pos.getString("observaciones"));
                                    obj.setOpcion(2);
                                    Log.e("COMO SE LLENA EL OBJETO",obj.toString());
                                    lista.add(obj);
                                }


                            rec.setHasFixedSize(true);
                            rec.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter=new AdapterPaseLista(lista);
                            rec.setAdapter(adapter);
                        }catch (Exception ex){
                            if(ex.toString().contains("No value for")){
                                dialogo.mostrarDialogoBoton("AVISO","Aun no hay asistencias");
                            }else{
                                Log.e("Error",ex.toString());
                                dialogo.mostrarDialogoBoton(
                                        "ERROR","Ocurrio un error inesperado \nIntente de nuevo más tarde");
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogo.mostrarDialogoBoton(
                        "ERROR","No se pudo conectar con el servidor");
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                param.put("idInscripcion",idInscripcion+"");
                return param;
            }
        };
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

    public asistencias_Alumno() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment asistencias_Alumno.
     */
    // TODO: Rename and change types and number of parameters
    public static asistencias_Alumno newInstance(String param1, String param2) {
        asistencias_Alumno fragment = new asistencias_Alumno();
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
        return inflater.inflate(R.layout.fragment_asistencias__alumno, container, false);
    }
}