package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itsoeh.checkmedocente.modelo.MDocente;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link perfil_docente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class perfil_docente extends Fragment {
    private CardView btnGrupos, btnPaseDL, back;
    private NavController navegador;
    private  Bundle paquete;
    private MDocente obj;
    private TextView txtNombre, txtTitulo, txtCorreo, txtNumT;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public perfil_docente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment perfil_docente.
     */
    // TODO: Rename and change types and number of parameters
    public static perfil_docente newInstance(String param1, String param2) {
        perfil_docente fragment = new perfil_docente();
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
        return inflater.inflate(R.layout.fragment_perfil_docente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnGrupos = view.findViewById(R.id.btn_misGrupos);
        btnPaseDL = view.findViewById(R.id.btn_paseDeLista);
        back = view.findViewById(R.id.btn_Perfil_back);
        navegador = Navigation.findNavController(view);
        txtNombre = view.findViewById(R.id.perfil_nombre);
        txtTitulo = view.findViewById(R.id.miPerfil_titulo);
        txtCorreo = view.findViewById(R.id.miPerfil_correo);
        txtNumT = view.findViewById(R.id.miPerfil_NumTrabajador);

        paquete= this.getArguments();
        if(paquete!=null){
            obj=(MDocente) paquete.getSerializable("user");
            txtNombre.setText(obj.getGrado()+" "+obj.getApp()+" "+obj.getApm()+" "+obj.getNombre());
            txtTitulo.setText("Titulo: "+obj.getTitulo());
            txtCorreo.setText("Correo: "+obj.getCorreo());
            txtNumT.setText("Numero de trabajador: "+obj.getNumTrabajador());
        }
        btnGrupos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicGrupos();
            }
        });
        btnPaseDL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicPaseDL();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicBack();
            }
        });
    }

    private void clicBack() {
        navegador.navigate(R.id.action_perfil_docente_to_docente, paquete);
    }

    private void clicPaseDL() {
        navegador.navigate(R.id.action_perfil_docente_to_pase_de_lista);
    }

    private void clicGrupos() {
        navegador.navigate(R.id.action_perfil_docente_to_grupos_docente);
    }
}