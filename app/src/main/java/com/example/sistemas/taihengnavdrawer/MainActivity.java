package com.example.sistemas.taihengnavdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import com.example.sistemas.taihengnavdrawer.Entidades.Usuario;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

        TextView tvfecha,tvhora;
        Usuario usuario;
        Button btnhojaRuta,btnsalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnhojaRuta = findViewById(R.id.btnhojaRuta);
        btnsalir = findViewById(R.id.btnsalir);
        setSupportActionBar(toolbar);
        tvfecha = findViewById(R.id.tvdate);
        tvhora = findViewById(R.id.tvHora);
        Calendar fecha =  Calendar.getInstance();
        Integer dia = fecha.get(Calendar.DAY_OF_MONTH);
        Integer mes = fecha.get(Calendar.MONTH);
        Integer anio = fecha.get(Calendar.YEAR);
        String fechaActual =Formatotiempo(dia) +"/"+Formatotiempo( mes) +"/"+anio;
        tvfecha.setText(fechaActual);
        Timer timer;

        usuario = (Usuario)getIntent().getExtras().getSerializable("Usuario");

        btnsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        final Runnable updateTask = new Runnable() {
            public void run() {

                Calendar hora1 = Calendar.getInstance();
                Integer hora = hora1.get(Calendar.HOUR_OF_DAY);
                Integer minuto = hora1.get(Calendar.MINUTE);
                Integer segundo =  hora1.get(Calendar.SECOND);

                final String horaActual = Formatotiempo(hora) +":"+Formatotiempo(minuto)+":"+Formatotiempo(segundo);
                tvhora.setText(horaActual);

            }
        };
        timer = new Timer("DigitalClock");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(updateTask);
            }
        }, 1, 1000);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnhojaRuta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ValidaHojaRutaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_hojaRuta) {

            Intent intent = new Intent(MainActivity.this,ValidaHojaRutaActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Usuario",usuario);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_Articulos) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String Formatotiempo(Integer numero){

        String resultado;

        if (9>=numero){
            resultado = "0" + numero.toString();
        }else{
            resultado = numero.toString();
        }

        return resultado;
    }
}
