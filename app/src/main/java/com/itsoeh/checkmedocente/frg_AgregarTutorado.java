package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.volley.VolleySingleton;
import com.itsoeh.checkmedocente.volley.API;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frg_AgregarTutorado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_AgregarTutorado extends Fragment {

    private ImageView btnTutorados,btnAgregar;
    private NavController navegador;
    private EditText txtIdGrupo,txtIdEstudiante,txtOp;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnTutorados = view.findViewById(R.id.addtut_btn_tutorados);
        btnAgregar = view.findViewById(R.id.add_tut_btn_add);
        txtIdGrupo = view.findViewById(R.id.add_tut_txtid_gpo);
        txtIdEstudiante = view.findViewById(R.id.add_tut_txtid_est);
        txtOp=view.findViewById(R.id.add_tut_txt_op);
        navegador = Navigation.findNavController(view);//ESTO ES PARA QUER FUNCIONE EL NAVEGADOR
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
                param.put("idGrupo",txtIdGrupo.getText().toString());
                param.put("idEstudiante",txtIdEstudiante.getText().toString());
                param.put("op",txtOp.getText().toString());
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);

    }

    private void clicTutorados() {
        navegador.navigate(R.id.action_frg_AgregarTutorado_to_frg_Tutorados);
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