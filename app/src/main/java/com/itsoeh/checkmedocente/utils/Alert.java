package com.itsoeh.checkmedocente.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.itsoeh.checkmedocente.R;

public class Alert {
    private Dialog dialogo;
    private TextView tvtitulo;
    private TextView tvMessage;
    private CardView btnAccept;
    private CardView btnEliminar;
    private CardView btnCancelar;
    private ProgressBar progressBar;
    private Context contexto;
    public Alert(Context contexto) {
        this.contexto = contexto;
        dialogo = new Dialog(contexto);
        dialogo.setContentView(R.layout.dialogo);
        dialogo.setCancelable(false);
        dialogo.setCanceledOnTouchOutside(false);

        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvtitulo = dialogo.findViewById(R.id.msg_titulo);
        tvMessage = dialogo.findViewById(R.id.msg_mensaje);
        btnAccept = dialogo.findViewById(R.id.msg_btnAccept);
        progressBar = dialogo.findViewById(R.id.msg_progres);
        btnEliminar = dialogo.findViewById(R.id.msg_btn_eliminar);
        btnCancelar = dialogo.findViewById(R.id.msg_btn_cancelar);
    }
    public  void mostrarDialogoBoton(String titulo, String mensaje) {
        tvMessage.setText(mensaje);
        tvtitulo.setText(titulo);
        progressBar.setVisibility(View.GONE);
        btnAccept.setVisibility(View.VISIBLE);
        dialogo.show();
        btnAccept.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             dialogo.dismiss();
                                         }
                                     }
        );

    }
    public  void mostrarDialogoProgress(String titulo, String mensaje) {
        tvMessage.setText(mensaje);
        tvtitulo.setText(titulo);
        progressBar.setVisibility(View.VISIBLE);
        btnAccept.setVisibility(View.GONE);
        dialogo.show();

    }
    public void mostrarDialogoConfirmacion(String titulo, String mensaje, Runnable onEliminar, Runnable onCancelar) {
        tvtitulo.setText(titulo);
        tvMessage.setText(mensaje);
        progressBar.setVisibility(View.GONE);
        btnAccept.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.VISIBLE);
        btnCancelar.setVisibility(View.VISIBLE);

        btnEliminar.setOnClickListener(v -> {
            dialogo.dismiss();
            if (onEliminar != null) onEliminar.run();
        });

        btnCancelar.setOnClickListener(v -> {
            dialogo.dismiss();
            if (onCancelar != null) onCancelar.run();
        });

        dialogo.show();
    }
    public  void cerrarDialogo() {
        dialogo.dismiss();
    }
}
