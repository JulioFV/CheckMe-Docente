package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.adapter.AdapterEstudiante;
import com.itsoeh.checkmedocente.modelo.MDocente;
import com.itsoeh.checkmedocente.modelo.MEstudiante;
import com.itsoeh.checkmedocente.modelo.MGrupo;
import com.itsoeh.checkmedocente.modelo.MTutor;
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
 * Use the {@link estudiante_Docente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class estudiante_Docente extends Fragment {
    private Bundle paquete;
    private MGrupo objGpo;
    private RecyclerView rec;
    private ArrayList<MEstudiante> lista;
    private EditText txtFiltro;
    private AdapterEstudiante adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        paquete = getArguments();
        rec=view.findViewById(R.id.Tutor_recycler_view_estud_grupo);
        txtFiltro=view.findViewById(R.id.estudiantes_txtNombre);

        if (paquete != null) {
            objGpo = (MGrupo) paquete.getSerializable("objeto");
            Log.e("DATOS QUE PASAN HACIA LA LISTA DE ESTUD", objGpo.toString());
        }
        lista=llenadoDesdeBD();
        txtFiltro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                buscador(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void buscador(String s) {
        ArrayList<MEstudiante> lista2 = new ArrayList<>();

        if (s != null && !s.isEmpty()) {
            // Convertimos 's' a minúsculas para una búsqueda más flexible
            String searchTerm = s.toLowerCase();

            for (MEstudiante gpo : lista) {
                // Verificamos que los valores no sean nulos antes de hacer contains()
                if ((gpo.getMatricula() != null && gpo.getMatricula().toLowerCase().contains(searchTerm)) ||
                        (gpo.getNombre() != null && gpo.getNombre().toLowerCase().contains(searchTerm)) ||
                        (gpo.getApp() != null && gpo.getApp().toLowerCase().contains(searchTerm)) ||
                        (gpo.getApm() != null && gpo.getApm().toLowerCase().contains(searchTerm))) {

                    lista2.add(gpo);
                }
            }
        }

        // Enviar la lista filtrada al adaptador
        adapter.filtro(lista2);
    }

    private ArrayList<MEstudiante> llenadoDesdeBD() {
        ArrayList<MEstudiante> lista=new ArrayList<MEstudiante>();

        //Crea un AlertDialog
        Alert dialogo = new Alert(getContext());
        dialogo.mostrarDialogoProgress("Por favor espere...","Conectandose con el servidor");

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.LISTARTUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialogo.cerrarDialogo();
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response

                            JSONObject contenido = new JSONObject(response);
                            JSONArray array=contenido.getJSONArray("contenido");
                            MEstudiante obj=new MEstudiante();
                            for (int i = 0; i < array.length(); i++) {
                                obj=new MEstudiante();
                                JSONObject pos=new JSONObject(array.getString(i));

                                obj.setIdInscripcion(pos.getInt("idInscripcion"));
                                obj.setNombre(pos.getString("nombre")+" "+pos.getString("app") + " "+
                                        pos.getString("apm"));
                                obj.setCorreo(pos.getString("correo"));
                                obj.setMatricula(pos.getString("matricula"));
                                obj.setIdEstudiante(pos.getInt("idEstudiante"));
                                obj.setEdo(pos.getString("edo"));
                                obj.setMuni(pos.getString("muni"));
                                obj.setCol(pos.getString("col"));
                                obj.setGen(pos.getString("gen"));
                                obj.setContrasenia(pos.getString("contrasenia"));
                                obj.setIdCarrera(pos.getInt("idCarrera"));

                                lista.add(obj);
                            }

                            rec.setHasFixedSize(true);
                            rec.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter=new AdapterEstudiante(lista,objGpo);
                            rec.setAdapter(adapter);
                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON
                            dialogo.mostrarDialogoBoton("ERROR","La información no se pudo leer");

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogo.cerrarDialogo();
                // DETECTA ERRORES EN LA COMUNICACIÓN
               dialogo.mostrarDialogoBoton("ERROR","No se pudo conectar al servidor");
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                //PASA PARAMETROS A LA SOLICITUD

                param.put("id",objGpo.getIdDocente()+"");
                param.put("clave",objGpo.getClave()+"");


                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);


        return lista;
    }

    // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

    public estudiante_Docente() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment estudiante_Docente.
         */
        // TODO: Rename and change types and number of parameters
        public static estudiante_Docente newInstance (String param1, String param2){
            estudiante_Docente fragment = new estudiante_Docente();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_estudiante__docente, container, false);
        }
    }

