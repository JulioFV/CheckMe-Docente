package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.modelo.MDocente;
import com.itsoeh.checkmedocente.utils.Alert;
import com.itsoeh.checkmedocente.utils.SessionManager;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {
    private CardView btnEntrar;
    private EditText txtUsuario,txtContrasenia;
    private TextView btnRegistro;
    private NavController navegador;
    private Bundle paquete;
    private TextView btnRecupera;
    private Alert alerta;
    private MDocente obj;
    private SessionManager sessionManager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navegador = Navigation.findNavController(view);
        sessionManager = new SessionManager(this.getActivity().getApplicationContext());
        alerta = new Alert(this.getContext());
        btnEntrar=view.findViewById(R.id.loginButton);
        txtContrasenia=view.findViewById(R.id.Login_txt_Contrasenia);
        txtUsuario=view.findViewById(R.id.login_txtNombre);
        btnRegistro = view.findViewById(R.id.login_registro);
        btnRecupera=view.findViewById(R.id.login_Recuperar_contra);
        txtUsuario.setText("21011178@itsoeh.edu.mx");
        txtContrasenia.setText("12345");
        btnRecupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navegador.navigate(R.id.action_login_to_recupera_Contrasenia);
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicRegistro();
            }
        });
        paquete = new Bundle();

        btnEntrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                clicEntrar();
            }
        });
    }

    private void clicRegistro() {
        navegador.navigate(R.id.action_login_to_frg_Registro);
    }

    private void clicEntrar() {
        String correo = this.txtUsuario.getText().toString();
        this.recuperarDocente(correo);
    }

    private void recuperarDocente(String correo){
        MDocente obj = new MDocente();
        Log.e("PASO 0", correo);
        alerta.mostrarDialogoProgress("Por favor espere","Conectandose con el servidor...");

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.BUSCARDOC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                            alerta.cerrarDialogo();
                            try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            Log.e("PASO 1", response);
                            JSONObject contenido = new JSONObject(response);

                            JSONArray array=contenido.getJSONArray("contenido");
                            MDocente obj = new MDocente();
                            for (int i = 0; i < array.length(); i++) {
                                obj = new MDocente();
                                Log.e("PASO 2", obj.toString());
                                JSONObject pos = new JSONObject(array.getString(i));
                                obj.setIdDocente(pos.getInt("idDocente"));
                                Log.e("id",obj.getIdDocente()+"");
                                obj.setNumTrabajador(pos.getString("numTrabajador"));
                                obj.setNombre(pos.getString("nombre"));
                                obj.setApp(pos.getString("app"));
                                obj.setApm(pos.getString("apm"));
                                obj.setCorreo(pos.getString("correo"));
                                obj.setGrado(pos.getString("grado"));
                                obj.setTitulo(pos.getString("titulo"));
                                //obj.setGenero(pos.getInt("genero"));
                                obj.setContrasenia(pos.getString("contrasenia"));
                                obj.setRol(pos.getInt("rol"));
                                Log.e("PASO 3", obj.toString());
                            }

                            if (obj.getCorreo()==null){// Esto es en caso de que el usuario no exista
                                alerta.mostrarDialogoBoton("Error","El usuario no existe");
                            }
                            if(txtContrasenia.getText().toString().equals(obj.getContrasenia())){
                                paquete.putSerializable("user",obj);
                                sessionManager.saveSession(obj);
                                navegador.navigate(R.id.action_login_to_docente,paquete);
                            }
                            else{// Esto es en caso de que la contraseña sea incorrecta

                                alerta.mostrarDialogoBoton("Error","La contraseña es incorrecta");
                            }

                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON
                            Log.e("PASO 5", ex.getMessage());
                            alerta.mostrarDialogoBoton("Error","No se pudo leer la información");

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alerta.mostrarDialogoBoton("Error","No se pudo conectar con el servidor");
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> param=new HashMap<String,String>();
                //PASA PARAMETROS A LA SOLICITUD
                param.put("correo",correo);
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);


    }
}