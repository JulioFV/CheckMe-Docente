package com.itsoeh.checkmedocente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itsoeh.checkmedocente.modelo.MTutor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link perfil_Alumno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class perfil_Alumno extends Fragment {
    private TextView lblNombre,lblColonia,lblMunicipio,lblEstado,lblCorreo,lblMatricula;
    private Bundle paquete;
    private ImageView imgFoto;
    private MTutor tutor;
    private int genero=0;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lblColonia=view.findViewById(R.id.alumn_lblcolonia);
        lblEstado=view.findViewById(R.id.alumn_lblestado);
        lblMunicipio=view.findViewById(R.id.alumn_lblmunicipio);
        lblNombre=view.findViewById(R.id.alumn_lblnombre);
        lblCorreo=view.findViewById(R.id.alumn_lblcorreo);
        lblMatricula=view.findViewById(R.id.alumn_lblmatricula);
        imgFoto=view.findViewById(R.id.alumn_imgperfil);
        paquete=getArguments();

        if (paquete!=null){
            tutor= (MTutor) paquete.getSerializable("objeto");
            //op=paquete.getInt("op");
            lblNombre.setText(tutor.getNombre() +"");
            lblMatricula.setText(tutor.getMatricula() + "");
            lblCorreo.setText(tutor.getCorreo()+"");
            lblMunicipio.setText(tutor.getMuni()+"");
            lblColonia.setText(tutor.getCol()+"");
            lblEstado.setText(tutor.getEdo()+"");
            genero= Integer.parseInt(tutor.getGen());
            if (genero ==  0) {
                imgFoto.setImageResource(R.drawable.perfil_alumna);
            } else if (genero == 1) {
                imgFoto.setImageResource(R.drawable.perfil_alumn);

            }

        }

    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public perfil_Alumno() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment perfil_Alumno.
     */
    // TODO: Rename and change types and number of parameters
    public static perfil_Alumno newInstance(String param1, String param2) {
        perfil_Alumno fragment = new perfil_Alumno();
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
        return inflater.inflate(R.layout.fragment_perfil__alumno, container, false);
    }
}