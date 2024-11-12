package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itsoeh.checkmedocente.modelo.MDocente;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Docente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Docente extends Fragment {
    private  Bundle paquete;
    private CardView btnPerfil, btnMisGrupos, btnPaseDeLista, Mistutorados, AgrearTutorados;
    private NavController navegador;
    private TextView txtNombre, txtTitulo, txtCorreo, txtNumT;
    private MDocente obj;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Docente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Docente.
     */
    // TODO: Rename and change types and number of parameters
    public static Docente newInstance(String param1, String param2) {
        Docente fragment = new Docente();
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
        return inflater.inflate(R.layout.fragment_docente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navegador = Navigation.findNavController(view);

        btnPerfil = view.findViewById(R.id.docente_btn_MiPerfil);
        btnMisGrupos = view.findViewById(R.id.docente_btn_MisGrupos);
        btnPaseDeLista = view.findViewById(R.id.docente_btn_paseDeLista);
        Mistutorados = view.findViewById(R.id.docente_btn_mis_tutorados);
        AgrearTutorados = view.findViewById(R.id.docente_btn_add_tutorados);
        txtNombre = view.findViewById(R.id.docente_nombre);
        txtTitulo = view.findViewById(R.id.docente_carrera);
        txtCorreo = view.findViewById(R.id.docente_correo);
        txtNumT = view.findViewById(R.id.docente_NumeroTrabajador);

        paquete= this.getArguments();
        if(paquete!=null){
            obj=(MDocente) paquete.getSerializable("user");
            txtNombre.setText(obj.getGrado()+" "+obj.getApp()+" "+obj.getApm()+" "+obj.getNombre());
            txtTitulo.setText("Titulo: "+obj.getTitulo());
            txtCorreo.setText("Correo: "+obj.getCorreo());
            txtNumT.setText("Numero de trabajador: "+obj.getNumTrabajador());
        }

        btnPerfil.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){clicPerfil();}
        });
        btnMisGrupos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){clicMisGrupos();}
        });
        btnPaseDeLista.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){clicPaseDeLista();}
        });
        Mistutorados.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){clicMisTutorados();}
        });
        AgrearTutorados.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){clicAgregarTutorados();}
        });
    }

    private void clicAgregarTutorados() {
        navegador.navigate(R.id.action_docente_to_frg_Tutorados);
    }

    private void clicMisTutorados() {
        navegador.navigate(R.id.action_docente_to_frg_Tutorados);
    }

    private void clicPaseDeLista() {
        navegador.navigate(R.id.action_docente_to_pase_de_lista);
    }

    private void clicMisGrupos() {
        if(paquete != null){
            paquete.putSerializable("user",obj);
        }
        navegador.navigate(R.id.action_docente_to_grupos_docente,paquete);
    }

    private void clicPerfil() {
        navegador.navigate(R.id.action_docente_to_perfil_docente, paquete);
    }
}