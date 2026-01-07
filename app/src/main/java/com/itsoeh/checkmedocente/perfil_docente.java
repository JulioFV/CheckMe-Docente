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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.itsoeh.checkmedocente.modelo.MDocente;
import com.itsoeh.checkmedocente.utils.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link perfil_docente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class perfil_docente extends Fragment {

    private NavController navegador;

    private  Bundle paquete;
    private CardView btnBack;
    private MDocente obj;
    private TextView txtNombre, txtTitulo, txtCorreo, txtNumT,txtGenero;
    private ImageView btnMenu,btnGrupos,btnTutorados,btnAddTut,imgPerfil;
    private int genero;
    private String gen;
    private SessionManager sessionManager;

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
        sessionManager = new SessionManager(this.getActivity().getApplicationContext());

        btnGrupos= view.findViewById(R.id.perfil_btn_grupos);
        btnMenu= view.findViewById(R.id.perfil_btn_menu);
        btnAddTut= view.findViewById(R.id.perfil_btn_addtut);
        btnTutorados= view.findViewById(R.id.perfil_btn_tutorados);
        btnBack=view.findViewById(R.id.btn_Perfil_back);
        imgPerfil = view.findViewById(R.id.perfil_doc_img);
        btnBack.setVisibility(View.GONE);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicMenu();
            }
        });
        btnAddTut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicAddTut();
            }
        });
        btnTutorados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicTutorados();
            }
        });

        navegador = Navigation.findNavController(view);
        txtNombre = view.findViewById(R.id.perfil_nombre);
        txtTitulo = view.findViewById(R.id.miPerfil_titulo);
        txtCorreo = view.findViewById(R.id.miPerfil_correo);
        txtNumT = view.findViewById(R.id.miPerfil_NumTrabajador);
        txtGenero = view.findViewById(R.id.per_doc_txtgenero);

        paquete= this.getArguments();
            obj= sessionManager.getDoc();
            txtNombre.setText(obj.getGrado()+". "+obj.getNombre()+" "+obj.getApp()+" " + obj.getApm());
            txtTitulo.setText("Titulo: "+obj.getGrado() +" "+obj.getTitulo());
            txtCorreo.setText("Correo: "+obj.getCorreo());
            txtNumT.setText("Numero de trabajador: "+obj.getNumTrabajador());
           genero = obj.getGenero();
            if (genero ==  0) {
                imgPerfil.setImageResource(R.drawable.perfil_mtro);
                gen="Masculino";
            } else if (genero == 1) {
               imgPerfil.setImageResource(R.drawable.perfil_mtra);
                gen="Femenino";
            }
            txtGenero.setText("Genero:  "+gen);


        btnGrupos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clicGrupos();
            }
        });

    }



    private void clicTutorados() {
        if(paquete!=null){
            paquete.putSerializable("user",obj);
        }
        navegador.navigate(R.id.action_perfil_docente_to_frg_Tutorados,paquete);
    }

    private void clicAddTut() {
        if(paquete!=null){
            paquete.putSerializable("user",obj);
        }
        navegador.navigate(R.id.action_perfil_docente_to_frg_AgregarTutorado,paquete);
    }

    private void clicMenu() {
        if(paquete!=null){
            paquete.putSerializable("user",obj);
        }
        navegador.navigate(R.id.action_perfil_docente_to_docente, paquete);
    }
    private void clicGrupos() {
        if(paquete!=null){
            paquete.putSerializable("user",obj);
        }
        navegador.navigate(R.id.action_perfil_docente_to_grupos_docente,paquete);
    }
}