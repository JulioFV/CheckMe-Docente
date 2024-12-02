package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.modelo.MDocente;
import com.itsoeh.checkmedocente.modelo.MGrupo;
import com.itsoeh.checkmedocente.volley.VolleySingleton;
import com.itsoeh.checkmedocente.volley.API;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frg_AgregarTutorado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_AgregarTutorado extends Fragment {

    private ImageView btnTutorados,btnAgregar,btnGrupos,btnPerfil,btnMenu;
    private NavController navegador;
    private EditText txtIdGrupo,txtIdEstudiante,txtOp;
    private Bundle paquete;
    private MDocente objDoc;
    private MGrupo grupo;
    private Spinner spinGpo;
    private ArrayList<MGrupo> listaGpo;
    private MGrupo gpoSelect;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTutorados = view.findViewById(R.id.addtut_btn_tutorados);
        btnAgregar = view.findViewById(R.id.add_tut_btn_add);
        txtIdGrupo = view.findViewById(R.id.add_tut_txtid_gpo);
        txtIdEstudiante = view.findViewById(R.id.add_tut_txtid_est);
        txtOp=view.findViewById(R.id.add_tut_txt_op);
        btnGrupos=view.findViewById(R.id.addtut_btn_grupos);
        btnPerfil=view.findViewById(R.id.addtut_btn_perfil);
        btnMenu= view.findViewById(R.id.addtut_btn_menu);
        spinGpo=view.findViewById(R.id.spin_add_tut);
        listaGpo= new ArrayList<MGrupo>();
        navegador = Navigation.findNavController(view);//ESTO ES PARA QUER FUNCIONE EL NAVEGADOR
        paquete=getArguments();
        if(paquete != null){
            objDoc = (MDocente) paquete.getSerializable("user");

        }
        this.cargarGrupos(view);
        spinGpo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gpoSelect = (MGrupo) parent.getItemAtPosition(position);
                txtIdGrupo.setText(gpoSelect.getIdGrupo()+"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicMenu();
            }
        });
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
        btnTutorados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicTutorados();
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicAgregar();
            }
        });

    }

    private void cargarGrupos(View v) {
        // Crear el AlertDialog
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

        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud = new StringRequest(Request.Method.POST, API.LISTARGPO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        int posi = 0;
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            JSONObject contenido = new JSONObject(response);//convierte la respuesta en un objeto JSON
                            JSONArray array = contenido.getJSONArray("contenido");//

                            MGrupo obj = new MGrupo();

                            for (int i = 0; i < array.length(); i++) {//recorre el arreglo
                                obj = new MGrupo();
                                JSONObject pos = new JSONObject(array.getString(i));//convierte la posicion en un objeto JSON
                                obj.setIdGrupo(pos.getInt("idGrupo"));
                                obj.setIdAsignatura(pos.getInt("idAsignatura"));
                                obj.setIdDocente(pos.getInt("idDocente"));
                                obj.setIdPeriodo(pos.getInt("idPeriodo"));
                                obj.setClave(pos.getString("clave"));
                                obj.setNombreAsig(pos.getString("nombreAsig"));
                                obj.setNombreDoc(pos.getString("nombreDoc") + " " + pos.getString("app") + " " +
                                        pos.getString("apm"));
                                obj.setNombrePer(pos.getString("nombrePer"));


                                listaGpo.add(obj);
                                if (grupo != null)
                                    if (obj.getIdGrupo() == grupo.getIdGrupo()) {
                                        posi = i;

                                    }
                            }

                            // Crear un ArrayAdapter utilizando el array de objetos

                            ArrayAdapter<MGrupo> adapter2 = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, listaGpo);


                            // Especificar el layout a usar
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                            // Asignar el adapter al Spinner
                            spinGpo.setAdapter(adapter2);
                            spinGpo.setSelection(posi);


                        } catch (Exception ex) {
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON
                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo leer");
                            msg.setPositiveButton("Aceptar", null);
                            AlertDialog dialog = msg.create();
                            dialog.show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                // DETECTA ERRORES EN LA COMUNICACIÓN
                msg.setTitle("Error");
                msg.setMessage("No se puede conectar al servidor");
                msg.setPositiveButton("Aceptar", null);
                AlertDialog dialog = msg.create();
                dialog.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<String, String>();
                //PASA PARAMETROS A LA SOLICITUD
                param.put("id", objDoc.getIdDocente() + "");
                // param.put("id","1");

                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);
    }

    private void clicMenu() {

        if(paquete!=null){
            paquete.putSerializable("user",objDoc);
        }
        navegador.navigate(R.id.action_frg_AgregarTutorado_to_docente,paquete);


    }

    private void clicPerfil() {
        if(paquete!=null){
            paquete.putSerializable("user",objDoc);
        }
        navegador.navigate(R.id.action_frg_AgregarTutorado_to_perfil_docente,paquete);
    }

    private void clicGrupos() {
        if(paquete!=null){
            paquete.putSerializable("user",objDoc);
        }
        navegador.navigate(R.id.action_frg_AgregarTutorado_to_grupos_docente,paquete);
    }

    private void clicAgregar() {
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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.AGREGARTUT,
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
                param.put("idGrupo",txtIdGrupo.getText()+"");
                param.put("idEstudiante",txtIdEstudiante.getText()+"");
                param.put("op",txtOp.getText().toString());
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);

    }

    private void clicTutorados() {
        if(paquete!=null){
            paquete.putSerializable("user",objDoc);
        }
        navegador.navigate(R.id.action_frg_AgregarTutorado_to_frg_Tutorados,paquete);
    }


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frg_AgregarTutorado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_AgregarTutorado.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_AgregarTutorado newInstance(String param1, String param2) {
        frg_AgregarTutorado fragment = new frg_AgregarTutorado();
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
        return inflater.inflate(R.layout.fragment_frg__agregar_tutorado, container, false);
    }
}