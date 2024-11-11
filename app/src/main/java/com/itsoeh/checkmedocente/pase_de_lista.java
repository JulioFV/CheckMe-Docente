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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link pase_de_lista#newInstance} factory method to
 * create an instance of this fragment.
 */
public class pase_de_lista extends Fragment {
    private CardView btnPerfil, btnGrupos, back;
    private NavController navegador;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public pase_de_lista() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pase_de_lista.
     */
    // TODO: Rename and change types and number of parameters
    public static pase_de_lista newInstance(String param1, String param2) {
        pase_de_lista fragment = new pase_de_lista();
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
        return inflater.inflate(R.layout.fragment_pase_de_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnPerfil = view.findViewById(R.id.PaseDeLista_btn_miPerfil);
        btnGrupos = view.findViewById(R.id.PaseDeLista_btn_misGrupos);
        back = view.findViewById(R.id.PaseDeLista_btn_back);

        navegador = Navigation.findNavController(view);

        btnPerfil.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                clicPerfil();
            }
        });
        btnGrupos.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {clicGrupos();}
        });
        back.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v) {clicBack();}
        });
    }

    private void clicBack() {
        navegador.navigate(R.id.action_pase_de_lista_to_docente);
    }

    private void clicGrupos() {
        navegador.navigate(R.id.action_pase_de_lista_to_grupos_docente);
    }

    private void clicPerfil() {
        navegador.navigate(R.id.action_pase_de_lista_to_perfil_docente);
    }
}