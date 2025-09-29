package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.itsoeh.checkmedocente.volley.API;
import com.itsoeh.checkmedocente.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Grafica_Grupo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Grafica_Grupo extends Fragment {

    private PieChart pieChart;
    private ArrayList<PieEntry> valoresY;
    private ArrayList<Integer> colores;
    private Bundle paquete;
    private int idGrupo;
    private int contador0 ;
    private int contador1 ;
    private int contador2 ;
    private int contador3 ;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        paquete=this.getArguments();
        if(paquete != null){
            paquete=this.getArguments();
            idGrupo= paquete.getInt("idGrupo");
        }

        pieChart = view.findViewById(R.id.frg_grafica_asistencia_grupo);
        pieChart.setUsePercentValues(true);

        obtenerEstados();
    }

    public void obtenerEstados() {
        // Crea un AlertDialog
        AlertDialog.Builder msg = new AlertDialog.Builder(this.getContext());

        // Crear un ProgressBar
        ProgressBar progressBar = new ProgressBar(this.getContext());
        progressBar.setIndeterminate(true); // Estilo de carga indeterminada

        // Crear el AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Por favor, espera");
        builder.setMessage("Conectándose con el servidor...");
        builder.setView(progressBar);
        builder.setCancelable(false); // Evitar que se pueda cancelar
        AlertDialog dialog = builder.create();
        dialog.show();

        RequestQueue colaDeSolicitudes = VolleySingleton.getInstance(this.getContext()).getRequestQueue();
        StringRequest solicitud = new StringRequest(Request.Method.POST, API.LISTARESTADO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss(); // Cierra el cuadro de diálogo
                        try {
                            // Procesar el contenido de la variable response
                            JSONObject contenido = new JSONObject(response);
                            Log.e("respuesta", String.valueOf(contenido));

                            JSONArray array = contenido.getJSONArray("contenido");

                            // Reiniciar contadores
                            contador0 = 0;
                            contador1 = 0;
                            contador2 = 0;
                            contador3 = 0;

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject pos = array.getJSONObject(i);
                                int estado = pos.getInt("estado"); // Asegúrate de que "estado" es un número en el JSON

                                // Incrementar el contador correspondiente
                                switch (estado) {
                                    case 0:
                                        contador0++;
                                        break;
                                    case 1:
                                        contador1++;
                                        break;
                                    case 2:
                                        contador2++;
                                        break;
                                    case 3:
                                        contador3++;
                                        break;
                                    default:
                                        Log.e("Estado inválido", "Estado: " + estado);
                                        break;
                                }
                            }

                            // Opcional: Mostrar resultados en un Log
                            Log.d("Contadores", "0: " + contador0 + ", 1: " + contador1 + ", 2: " + contador2 + ", 3: " + contador3);
                            graficarPastel();
                        } catch (Exception ex) {
                            // Detecta errores en la lectura del JSON
                            msg.setTitle("Error");
                            msg.setMessage("La información no se pudo leer");
                            msg.setPositiveButton("Aceptar", null);
                            AlertDialog dialog = msg.create();
                            msg.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        // Detecta errores en la comunicación
                        msg.setTitle("Error");
                        msg.setMessage("No se pudo conectar con el servidor");
                        msg.setPositiveButton("Aceptar", null);
                        AlertDialog dialog = msg.create();
                        msg.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                // Pasa parámetros a la solicitud
                param.put("idGrupo", idGrupo + "");

                return param;
            }
        };

        // Envía la solicitud
        colaDeSolicitudes.add(solicitud);
    }


    public void graficarPastel(){
        //DECLARANDO LOS ARREGLOS
        valoresY=new ArrayList<>();
        colores=new ArrayList<>();
        //AGREGANDO DATOS
        valoresY.add(new PieEntry(contador0,"Asistencia"));
        valoresY.add(new PieEntry(contador1,"Faltas"));
        valoresY.add(new PieEntry(contador3,"Justificaciones"));
        valoresY.add(new PieEntry(contador2,"Retardos"));
        //AGREGANDO COLORES
        //colores.add(ContextCompat.getColor(getContext(),R.color.amarillo));
        colores.add(ContextCompat.getColor(getContext(),R.color.fondo));
        colores.add(ContextCompat.getColor(getContext(),R.color.fondo2));
        colores.add(ContextCompat.getColor(getContext(),R.color.tertiary));
        colores.add(ContextCompat.getColor(getContext(),R.color.fifty));

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(getContext(), "Valor seleccionado: " + e.getY(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected() {

            }
        });
        //ASIGNANDO DATOS AL OBJETO SET Y CONFIGURANDO PROPIEDADES
        PieDataSet set=new PieDataSet(valoresY, "Porcentajes de asistencia");
        set.setColors(colores);//ASIGNA COLOR A LAS rebanadas;
        set.setValueTextSize(15f);//ASIGNA TAMAÑO AL TEXTO DE LOS VALORES
        PieData data=new PieData(set);// ASIGNA LA DATA AL OBJETO PIE
        set.setDrawValues(true);
        pieChart.setDrawHoleEnabled(true);//con forma de dona o no
        pieChart.animateY(2000);
        pieChart.setData(data);
        pieChart.invalidate();
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Grafica_Grupo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Grafica_Grupo.
     */
    // TODO: Rename and change types and number of parameters
    public static Grafica_Grupo newInstance(String param1, String param2) {
        Grafica_Grupo fragment = new Grafica_Grupo();
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
        return inflater.inflate(R.layout.fragment_grafica__grupo, container, false);
    }
}