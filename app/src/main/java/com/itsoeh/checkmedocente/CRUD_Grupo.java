package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itsoeh.checkmedocente.modelo.MAsignatura;
import com.itsoeh.checkmedocente.modelo.MDocente;
import com.itsoeh.checkmedocente.modelo.MGrupo;
import com.itsoeh.checkmedocente.modelo.MPeriodo;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CRUD_Grupo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CRUD_Grupo extends Fragment {
    private EditText txtClaveGrupo, txtIdAsignatura,txtIdDocente,txtIdPeriodo;
    private CardView btnGuardar,btnEliminar;
    private Bundle paquete;
    private MGrupo grupo;
    private int op;
    private Spinner spinAsig,spinPer;
    private ArrayList<MAsignatura> listaAsig;
    private ArrayList<MPeriodo> listaPer;
    private MAsignatura asigSelect;
    private MPeriodo perSelect;
    private MDocente docSelect;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CRUD_Grupo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CRUD_Grupo.
     */
    // TODO: Rename and change types and number of parameters
    public static CRUD_Grupo newInstance(String param1, String param2) {
        CRUD_Grupo fragment = new CRUD_Grupo();
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
        return inflater.inflate(R.layout.fragment_c_r_u_d__grupo, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtClaveGrupo = view.findViewById(R.id.crudgpo_txtclavegpo);
        txtIdAsignatura = view.findViewById(R.id.frgcrudgpo_txtidasig);
        txtIdDocente = view.findViewById(R.id.crudgpo_txt_iddocente);
        txtIdPeriodo = view.findViewById(R.id.crudgpo_txt_idperiodo);
        btnEliminar=view.findViewById(R.id.crud_gpo_btn_eliminar);
        btnGuardar=view.findViewById(R.id.crudgpo_btnguardar);
        spinAsig=view.findViewById(R.id.frgcrud_spinnerAsig);//Spinner de asignaturas
        spinPer=view.findViewById(R.id.frgcrud_spiner_per);
        txtClaveGrupo.setEnabled(true);
        listaAsig= new ArrayList<MAsignatura>();//Para las asignaturas
        listaPer= new ArrayList<MPeriodo>();
        paquete=getArguments();
        // txtIdDocente.setText(grupo.getIdDocente() + "" + grupo.getNombreDoc());
        if (paquete!=null){
            grupo= (MGrupo) paquete.getSerializable("objeto");
            docSelect= (MDocente) paquete.getSerializable("user");
            //Log.d("Paquete Docente",docSelect.toString());
            op=paquete.getInt("op");
            txtClaveGrupo.setText(grupo.getClave());
            txtIdAsignatura.setText(grupo.getIdAsignatura() + "  "+ grupo.getNombreAsig());
            txtIdDocente.setText(grupo.getIdDocente()+"");
            txtIdPeriodo.setText(grupo.getIdPeriodo()+""+grupo.getNombrePer());
            if (op==1){//Opcion 1 es para ELIMINAR
                btnEliminar.setVisibility(View.VISIBLE);
                btnGuardar.setVisibility(View.GONE);
                txtClaveGrupo.setEnabled(false);
            }
            if (op==2){//Opcion 2 es para EDITAR
                btnEliminar.setVisibility(View.GONE);
                btnGuardar.setVisibility(View.VISIBLE);
                txtClaveGrupo.setEnabled(false);
            }
        }
        this.cargarPeriodos(view);
        this.cargarAsignatura(view);
        spinPer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                perSelect = (MPeriodo) parent.getItemAtPosition(position);
                txtIdPeriodo.setText(perSelect.getIdPeriodo() + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinAsig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                asigSelect = (MAsignatura) parent.getItemAtPosition(position);
                txtIdAsignatura.setText(asigSelect.getIdAsignatura()+ "");
                Log.e("",txtIdAsignatura.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicEliminar();
            }
        });
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicGuardar();
            }
        });
    }
    private void cargarPeriodos(View v) {
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

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.LISTAR_PER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        int posi = 0;
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            JSONObject contenido=new JSONObject(response);//convierte la respuesta en un objeto JSON
                            JSONArray array=contenido.getJSONArray("contenido");//

                            MPeriodo obj =new MPeriodo();

                            for(int i=0;i<array.length();i++){//recorre el arreglo
                                obj=new MPeriodo();
                                JSONObject pos=new JSONObject(array.getString(i));//convierte la posicion en un objeto JSON
                                obj.setIdPeriodo(pos.getInt("idPeriodo"));
                                obj.setNombre(pos.getString("nombre"));
                                obj.setFechaI(pos.getString("fechaI"));
                                obj.setFechaF(pos.getString("fechaF"));



                                listaPer.add(obj);
                                if(grupo!=null)
                                    if(obj.getIdPeriodo()==grupo.getIdPeriodo()){
                                        posi=i;
                                    }
                            }

                            // Crear un ArrayAdapter utilizando el array de objetos

                            ArrayAdapter<MPeriodo> adapter2 = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, listaPer);


                            // Especificar el layout a usar
                            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                            // Asignar el adapter al Spinner
                            spinPer.setAdapter(adapter2);
                            spinPer.setSelection(posi);




                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON
                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo leer");
                            msg.setPositiveButton("Aceptar",null);
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
                msg.setPositiveButton("Aceptar",null);
                AlertDialog dialog = msg.create();
                dialog.show();
            }
        });
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);

    }
    private void cargarAsignatura(View v){
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

        RequestQueue colaDeSolicitudes= VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.LISTAR_ASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        int posi=0;
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            JSONObject contenido=new JSONObject(response);//convierte la respuesta en un objeto JSON
                            JSONArray array=contenido.getJSONArray("contenido");//
                            MAsignatura obj=new MAsignatura();

                            for(int i=0;i<array.length();i++){//recorre el arreglo
                                obj=new MAsignatura();
                                JSONObject pos=new JSONObject(array.getString(i));//convierte la posicion en un objeto JSON
                                obj.setIdAsignatura(pos.getInt("idAsignatura"));
                                obj.setNombreCorto(pos.getString("nombreCorto"));
                                obj.setNombreLargo(pos.getString("nombreLargo"));
                                obj.setClave(pos.getString("clave"));
                                obj.setIdCarrera(pos.getInt("idCarrera"));
                                listaAsig.add(obj);

                                if(grupo!=null)
                                    if(obj.getIdAsignatura()==grupo.getIdAsignatura()){
                                        posi=i;
                                    }
                            }

                            // Crear un ArrayAdapter utilizando el array de objetos
                            ArrayAdapter<MAsignatura> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_spinner_item, listaAsig);


                            // Especificar el layout a usar
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);;


                            // Asignar el adapter al Spinner
                            spinAsig.setAdapter(adapter);
                            spinAsig.setSelection(posi);





                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON
                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo leer");
                            msg.setPositiveButton("Aceptar",null);
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
                msg.setPositiveButton("Aceptar",null);
                AlertDialog dialog = msg.create();
                dialog.show();
            }
        });
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);
    }
    private void clicGuardar() {
        if(op==2){
            editar();
        }
        else{
            Guardar();
        }
    }
    private void editar() {
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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.EDITARGPO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("Guardado");
                            msg.setMessage("La información se modifico correctamente");
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
                param.put("idGrupo",grupo.getIdGrupo() + "");
                param.put("clave",txtClaveGrupo.getText().toString());
                param.put("idAsignatura",txtIdAsignatura.getText().toString());
                param.put("idDocente",txtIdDocente.getText().toString());
                param.put("idPeriodo",txtIdPeriodo.getText().toString());
                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);




    }
    private void Guardar() {

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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.GUARDARGPO,
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

                param.put("clave",txtClaveGrupo.getText().toString());
                param.put("idAsignatura",txtIdAsignatura.getText().toString());
                param.put("idDocente",grupo.getIdDocente() + "");
                param.put("idPeriodo",perSelect.getIdPeriodo() + "");

                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);



    }
    private void clicEliminar() {
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
        StringRequest solicitud= new StringRequest(Request.Method.POST, API.ELIMINARGPO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();//apaga el cuadro de dialogo
                        try {
                            //LEER AQUI EL CONTENIDO DE LA VARIABLE response
                            msg.setTitle("Eliminando");
                            msg.setMessage("La información se elimino correctamente");
                            msg.setPositiveButton("Aceptar",null);
                            AlertDialog dialog=msg.create();
                            msg.show();
                            txtClaveGrupo.setText("");
                            txtIdAsignatura.setText("");
                            txtIdDocente.setText("");
                            txtIdPeriodo.setText("");



                        }catch (Exception ex){
                            //DETECTA ERRORES EN LA LECTURA DEL ARCHIVO JSON

                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo eliminar");
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
                param.put("idGrupo",grupo.getIdGrupo() + "");


                return param;
            }
        };
        //ENVIA LA SOLICITUD
        colaDeSolicitudes.add(solicitud);
    }

}