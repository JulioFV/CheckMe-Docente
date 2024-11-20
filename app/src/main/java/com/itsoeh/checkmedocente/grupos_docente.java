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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.adapter.AdapterGrupo;
import com.itsoeh.checkmedocente.modelo.MDocente;
import com.itsoeh.checkmedocente.modelo.MGrupo;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link grupos_docente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class grupos_docente extends Fragment {
    private CardView back, btnPerfil, btnPase, btnCrear;
    private NavController navegador;
    private RecyclerView rec;
    private ArrayList<MGrupo> lista;
    private AdapterGrupo adapter;
    MDocente obj = new MDocente();
    private  Bundle paquete;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public grupos_docente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment grupos_docente.
     */
    // TODO: Rename and change types and number of parameters
    public static grupos_docente newInstance(String param1, String param2) {
        grupos_docente fragment = new grupos_docente();
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
        return inflater.inflate(R.layout.fragment_grupos_docente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        back = view.findViewById(R.id.grupos_btn_back);
        btnPerfil = view.findViewById(R.id.MisGrupos_btn_miPerfil);
        btnPase = view.findViewById(R.id.MisGrupos_btn_paseDeLista);
        btnCrear = view.findViewById(R.id.Grupos_btn_crearGrupo);
        rec=view.findViewById(R.id.Misgrupos_RecyclerView);
        navegador = Navigation.findNavController(view);

        paquete=this.getArguments();
        if(paquete!=null){
            obj=(MDocente) paquete.getSerializable("user");
            Log.e("datosMaestro",obj.toString());
        }

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicBack();
            }
        });
        btnCrear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicCrear();
            }
        });
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicPerfil();
            }
        });
        btnPase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicPase();
            }
        });

        paquete=this.getArguments();
        if(paquete!=null){
            obj=(MDocente) paquete.getSerializable("user");
            Log.e("datosMaestro",obj.toString());
        }

        lista=llenadoDesdeBD();
        rec=view.findViewById(R.id.Misgrupos_RecyclerView);
        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        //lista=llenadoManual();
        adapter=new AdapterGrupo(lista);
        rec.setAdapter(adapter);
    }



    private ArrayList<MGrupo> llenadoDesdeBD() {
        ArrayList<MGrupo> lista=new ArrayList<MGrupo>();

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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.LISTARGPO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            JSONObject contenido = new JSONObject(response);
                            Log.e("respuesta 1", String.valueOf(contenido));
                            JSONArray array=contenido.getJSONArray("contenido");
                            MGrupo obj=new MGrupo();
                            for (int i = 0; i < array.length(); i++) {
                                obj=new MGrupo();
                                JSONObject pos=new JSONObject(array.getString(i));
                                obj.setIdGrupo(pos.getInt("idGrupo"));
                                obj.setIdAsignatura(pos.getInt("idAsignatura"));
                                obj.setIdDocente(pos.getInt("idDocente"));
                                obj.setIdPeriodo(pos.getInt("idPeriodo"));
                                obj.setClave(pos.getString("clave"));
                                obj.setNombreAsig(pos.getString("nombreAsig"));
                                obj.setNombreDoc(pos.getString("nombreDoc")+" "+pos.getString("app") + " "+
                                        pos.getString("apm"));
                                obj.setNombrePer(pos.getString("nombrePer"));
                                lista.add(obj);
                            }

                            rec.setHasFixedSize(true);
                            rec.setLayoutManager(new LinearLayoutManager(getContext()));
                            adapter=new AdapterGrupo(lista);
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
            protected Map<String, String> getParams(){
                Map<String, String> param=new HashMap<String,String>();
                //PASA PARAMETROS A LA SOLICITUD
                param.put("id",obj.getIdDocente()+"");
               // param.put("id","1");
                Log.e("objeto", obj.toString());
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);


        return lista;
    }


    private void clicPase() {
        navegador.navigate(R.id.action_grupos_docente_to_pase_de_lista, paquete);
    }

    private void clicPerfil() {
        navegador.navigate(R.id.action_grupos_docente_to_perfil_docente, paquete);
    }

    private void clicBack() {
        navegador.navigate(R.id.action_grupos_docente_to_docente, paquete);
    }
    private void clicCrear() {
        navegador.navigate(R.id.action_grupos_docente_to_CRUD_Grupo, paquete);
    }
}