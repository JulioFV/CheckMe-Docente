package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frg_Registro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frg_Registro extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText txtNombre,txtApp,txtApm,txtCorreo,txtNumTrabajador,txtContrasenia,txtGrado,txtTitulo;
    private CardView btnRegistrar;
    private TextView btnLogin,lblVer;
    private Spinner spGenero;
    private NavController navegador;
    private int Gen;
    private boolean Campos = true;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtNombre=view.findViewById(R.id.reg_txtnombre);
        txtApp=view.findViewById(R.id.reg_txtapp);
        txtApm=view.findViewById(R.id.reg_txtapm);
        txtCorreo=view.findViewById(R.id.reg_txtcorreo);
        txtNumTrabajador=view.findViewById(R.id.reg_txtnumtrabajador);
        txtContrasenia=view.findViewById(R.id.reg_txtcontrasenia);
        txtGrado=view.findViewById(R.id.reg_txtgrado);
        txtTitulo=view.findViewById(R.id.reg_txt_titulo);
        spGenero=view.findViewById(R.id.reg_spingenero);
        lblVer=view.findViewById(R.id.reg_lblgen);
        btnRegistrar=view.findViewById(R.id.reg_btnRegistrar);
        btnLogin=view.findViewById(R.id.reg_lbl_login);
        navegador= Navigation.findNavController(view);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validar();
                if(Campos){
                    clicRegistrar();
                }else{
                   Alerta();
                }

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicLogin();
            }
        });
        spGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    lblVer.setText(""+position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void Alerta() {
        AlertDialog.Builder msg = new AlertDialog.Builder(this.getContext());
        msg.setTitle("CAMPOS INVALIDOS");
        msg.setMessage("Rellena los campos solicitados");
        msg.setPositiveButton("Aceptar",null);
        AlertDialog dialog=msg.create();
        msg.show();
    }

    private void Validar() {
        Campos = true;

        if (txtNombre.getText().toString().isEmpty()) {
            Campos=false;
        }
        if (txtApp.getText().toString().isEmpty()) {
            Campos=false;
        }
        if (txtApm.getText().toString().isEmpty()) {
            Campos=false;
        }
        if (txtContrasenia.getText().toString().isEmpty()) {
            Campos=false;
        }
        if (txtCorreo.getText().toString().isEmpty()) {
            Campos=false;
        }
        if (txtGrado.getText().toString().isEmpty()) {
            Campos=false;
        }
        if (txtTitulo.getText().toString().isEmpty()) {
            Campos=false;
        }
        if (txtNumTrabajador.getText().toString().isEmpty()) {
            Campos=false;
        }
    }

    private void clicLogin() {
        navegador.navigate(R.id.action_frg_Registro_to_login);

    }

    private void clicRegistrar() {

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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.REGISTARDOC,
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
                param.put("numTrabajador",txtNumTrabajador.getText().toString());
                param.put("nombre",txtNombre.getText().toString());
                param.put("app",txtApp.getText().toString());
                param.put("apm",txtApm.getText().toString());
                param.put("correo",txtCorreo.getText().toString());
                param.put("grado",txtGrado.getText().toString());
                param.put("titulo",txtTitulo.getText().toString());
                param.put("genero",lblVer.getText().toString());
                param.put("contrasenia",txtContrasenia.getText().toString());
                param.put("rol","0");

                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);


    }

    public frg_Registro() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frg_Registro.
     */
    // TODO: Rename and change types and number of parameters
    public static frg_Registro newInstance(String param1, String param2) {
        frg_Registro fragment = new frg_Registro();
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
        return inflater.inflate(R.layout.fragment_frg__registro, container, false);
    }
}