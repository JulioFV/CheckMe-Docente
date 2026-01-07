package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.adapter.AdapterTutor;
import com.itsoeh.checkmedocente.modelo.MDocente;
import com.itsoeh.checkmedocente.modelo.MGrupo;
import com.itsoeh.checkmedocente.utils.SessionManager;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.modelo.MTutor;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frg_Tutorados#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_Tutorados extends Fragment {
        private SessionManager sessionManager;
        private EditText txtFiltro;
        private AdapterTutor adapter;
        private ArrayList<MTutor> lista;
        private Bundle paquete;
        private RecyclerView rec;
        private NavController navegador;
        private MGrupo grupo;
        private ImageView btnAgrgarTutorado,btnGrupos,btnPerfil,btnMenu;
        private MDocente objDoc;
        private Spinner spinGpo;
        private ArrayList<MGrupo> listaGpo;
        private TextView txtGrupo;
        private MGrupo gpoSelect;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        paquete=getArguments();
        sessionManager = new SessionManager(this.getActivity().getApplicationContext());

            objDoc = sessionManager.getDoc();
            Log.e("OBJETO A TUTORADOS",objDoc.toString());


        txtFiltro = view.findViewById(R.id.tut_txt_filtro);
        btnAgrgarTutorado=view.findViewById(R.id.tut_btn_addtut);
        btnPerfil=view.findViewById(R.id.tut_btn_perfil);
        btnGrupos=view.findViewById(R.id.tut_btn_grupos);
        btnMenu=view.findViewById(R.id.tut_btn_menu);
        rec=view.findViewById(R.id.recycler_view_tutorados);
        spinGpo=view.findViewById(R.id.frgtut_spin_gpo);
        txtGrupo=view.findViewById(R.id.tut_txtgpo);

        navegador = Navigation.findNavController(view);


        listaGpo= new ArrayList<MGrupo>();

        this.cargarGrupos(view);

        lista=llenadoDesdeBD();


        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicPerfil();
            }
        });
        btnGrupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicGrupos();
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicMenu();
            }
        });
        spinGpo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gpoSelect = (MGrupo) parent.getItemAtPosition(position);
                txtGrupo.setText(""+gpoSelect.getClave());
                lista=llenadoDesdeBD();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAgrgarTutorado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicAddTut();
            }
        });

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

    private void clicMenu() {
        if(paquete!=null){
            paquete.putSerializable("user",objDoc);
        }
        navegador.navigate(R.id.action_frg_Tutorados_to_docente,paquete);
    }

    private void clicGrupos() {
        if(paquete!=null){
            paquete.putSerializable("user",objDoc);
        }
        navegador.navigate(R.id.action_frg_Tutorados_to_grupos_docente,paquete);
    }

    private void clicPerfil() {
        if(paquete!=null){
            paquete.putSerializable("user",objDoc);
        }
        navegador.navigate(R.id.action_frg_Tutorados_to_perfil_docente,paquete);
    }

    private void clicAddTut() {
        if(paquete!=null){
            paquete.putSerializable("user",objDoc);
        }
        navegador.navigate(R.id.action_frg_Tutorados_to_frg_AgregarTutorado,paquete);
    }

    private void buscador(String s) {
        ArrayList<MTutor> lista2 = new ArrayList<>();

        if (s != null && !s.isEmpty()) {
            // Convertimos 's' a minúsculas para una búsqueda más flexible
            String searchTerm = s.toLowerCase();

            for (MTutor gpo : lista) {
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

    private void cargarGrupos(View v) {
        // Crear un AlertDialog para indicar que se está conectando al servidor
        AlertDialog.Builder msg = new AlertDialog.Builder(this.getContext());

        // Crear un ProgressBar con estilo de carga indeterminada
        ProgressBar progressBar = new ProgressBar(this.getContext());
        progressBar.setIndeterminate(true);

        // Configurar el AlertDialog con un título, mensaje y ProgressBar
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Por favor, espera");
        builder.setMessage("Conectandose con el servidor...");
        builder.setView(progressBar);
        builder.setCancelable(false); // Evitar que el usuario pueda cerrar el dialogo manualmente
        AlertDialog dialog = builder.create();
        dialog.show();

        // Crear la cola de solicitudes usando VolleySingleton
        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(this.getContext()).getRequestQueue();

        // Crear una solicitud POST para obtener los datos de los grupos
        StringRequest solicitud = new StringRequest(Request.Method.POST, API.LISTARGPOTUTORADO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss(); // Cerrar el cuadro de diálogo al recibir respuesta

                        int posi = 0; // Variable para guardar la posición del grupo seleccionado
                        try {
                            // Convertir la respuesta en un objeto JSON
                            JSONObject contenido = new JSONObject(response);
                            JSONArray array = contenido.getJSONArray("contenido");

                            MGrupo obj;

                            // Iterar sobre el array JSON para procesar los datos
                            for (int i = 0; i < array.length(); i++) {
                                obj = new MGrupo();
                                JSONObject pos = new JSONObject(array.getString(i));
                                // Asignar valores al objeto MGrupo
                                obj.setIdGrupo(pos.getInt("idGrupo"));
                                obj.setIdAsignatura(pos.getInt("idAsignatura"));
                                obj.setIdDocente(pos.getInt("idDocente"));
                                obj.setIdPeriodo(pos.getInt("idPeriodo"));
                                obj.setClave(pos.getString("clave"));
                                obj.setNombreAsig(pos.getString("nombreAsig"));
                                obj.setNombreDoc(pos.getString("nombreDoc") + " " +
                                        pos.getString("app") + " " + pos.getString("apm"));
                                obj.setNombrePer(pos.getString("nombrePer"));
                                listaGpo.add(obj); // Agregar el objeto a la lista de grupos
                                // Verificar si es el grupo seleccionado
                                if (grupo != null && obj.getIdGrupo() == grupo.getIdGrupo()) {
                                    posi = i;
                                }
                            }

                            // Crear un ArrayAdapter para poblar un Spinner con los grupos
                            ArrayAdapter<MGrupo> adapter2 = new ArrayAdapter<>(v.getContext(),
                                    android.R.layout.simple_spinner_item, listaGpo);
                            // Configurar el estilo del dropdown del Spinner
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            // Asignar el adapter al Spinner
                            spinGpo.setAdapter(adapter2);
                            spinGpo.setSelection(posi); // Seleccionar el grupo correspondiente

                        } catch (Exception ex) {
                            // Manejar errores en la lectura del JSON
                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo leer");
                            msg.setPositiveButton("Aceptar", null);
                            AlertDialog dialog = msg.create();
                            dialog.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss(); // Cerrar el cuadro de diálogo en caso de error

                        // Manejar errores de comunicación con el servidor
                        msg.setTitle("Error");
                        msg.setMessage("No se puede conectar al servidor");
                        msg.setPositiveButton("Aceptar", null);
                        AlertDialog dialog = msg.create();
                        dialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Pasar parámetros a la solicitud
                Map<String, String> param = new HashMap<>();
                param.put("id", objDoc.getIdDocente() + "");
                return param;
            }
        };

        // Enviar la solicitud a la cola de solicitudes
        colaDeSolicitudes.add(solicitud);
    }
    private ArrayList<MTutor> llenadoDesdeBD() {

        ArrayList<MTutor> lista=new ArrayList<MTutor>();
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
        StringRequest solicitud= new StringRequest(Request.Method.POST,API.LISTARTUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response

                            JSONObject contenido = new JSONObject(response);
                            JSONArray array=contenido.getJSONArray("contenido");
                            MTutor obj=new MTutor();
                            for (int i = 0; i < array.length(); i++) {
                                obj=new MTutor();
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
                            adapter=new AdapterTutor(lista);
                            rec.setAdapter(adapter);
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
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                //PASA PARAMETROS A LA SOLICITUD

               param.put("id",objDoc.getIdDocente()+"");

                param.put("clave",txtGrupo.getText().toString());


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

    public frg_Tutorados() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_Tutorados.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_Tutorados newInstance(String param1, String param2) {
        frg_Tutorados fragment = new frg_Tutorados();
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
        return inflater.inflate(R.layout.fragment_frg__tutorados, container, false);
    }
}