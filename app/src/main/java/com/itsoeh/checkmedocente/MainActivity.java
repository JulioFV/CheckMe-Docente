package com.itsoeh.checkmedocente;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.itsoeh.checkmedocente.utils.SessionManager;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ImageView btnMenu;
    private NavController nav;
    private CardView btnPerfil,btnCerrarSesion,btnGrupos,btnClasesArchivadas,btnSalir,btnTutorados;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.main);
        btnMenu = findViewById(R.id.main_btn_nav);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View menuView = navigationView.getHeaderView(0);
        btnPerfil = menuView.findViewById(R.id.menu_btnperfil);
        btnGrupos = menuView.findViewById(R.id.menu_btngrupos);
        btnTutorados = menuView.findViewById(R.id.menu_btntutorados);
        btnClasesArchivadas = menuView.findViewById(R.id.menu_btn_clases_archivadas);
        btnCerrarSesion = menuView.findViewById(R.id.menu_btnlogout);
        btnSalir = menuView.findViewById(R.id.menu_btnsalir);

        btnPerfil.setOnClickListener(v -> {
            nav.navigate(R.id.perfil_docente);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        btnGrupos.setOnClickListener(v -> {
            nav.navigate(R.id.grupos_docente);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        btnTutorados.setOnClickListener(v -> {
            nav.navigate(R.id.frg_Tutorados);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        btnClasesArchivadas.setOnClickListener(v -> {

        });
        btnCerrarSesion.setOnClickListener(v ->{
            sessionManager.logout();
            nav.navigate(R.id.login);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        btnSalir.setOnClickListener(v ->{
            nav.navigate(R.id.docente);
            drawerLayout.closeDrawer(GravityCompat.START);
        });



        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView4);
        nav = navHostFragment.getNavController();
        btnMenu.setOnClickListener(v ->{
            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START);
            }else{
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationUI.setupWithNavController(navigationView, nav);
        setupDrawer();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }
    private void setupDrawer(){
        nav.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == R.id.login || destination.getId()== R.id.splashScreen || destination.getId() == R.id.docente
                    || destination.getId() == R.id.frg_Registro || destination.getId() == R.id.asistencias_Alumno
            || destination.getId() == R.id.asistencias_tutorado || destination.getId() == R.id.perfil_Alumno
            || destination.getId() == R.id.historialAlumn || destination.getId() == R.id.materiasTutorado){

                btnMenu.setVisibility(View.GONE);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            }else{

                btnMenu.setVisibility(View.VISIBLE);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            }
        });
    }
}