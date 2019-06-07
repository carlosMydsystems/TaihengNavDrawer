package com.example.sistemas.taihengnavdrawer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sistemas.taihengnavdrawer.Entidades.DetalleHojaRuta;
import com.example.sistemas.taihengnavdrawer.Entidades.HojaRuta;
import com.example.sistemas.taihengnavdrawer.Entidades.Usuario;

import java.util.ArrayList;

public class BuscarActivity extends AppCompatActivity {

    ArrayList<String> listaInformacion;
    ListView lvclientes;
    String tipobusqueda, numeroHojaRuta;
    ArrayList<HojaRuta> listahojaruta;
    ArrayList<DetalleHojaRuta> listacliente;
    Button btncancelar;
    TextView tvchofer, tvnumrtoplaca, tvfecharegistro, tvplaca, tvhojaRuta;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);

        usuario = (Usuario)getIntent().getExtras().getSerializable("Usuario");
        listahojaruta = (ArrayList<HojaRuta>) getIntent().getSerializableExtra("listahojaruta");
        lvclientes = findViewById(R.id.listClientes);

        tvchofer = findViewById(R.id.tvChofer);
        tvnumrtoplaca = findViewById(R.id.tvPlaca);
        tvfecharegistro = findViewById(R.id.tvFechaRegistro);
        tvhojaRuta = findViewById(R.id.tvNumHruta);
        btncancelar = findViewById(R.id.btnCancelar);
        tvplaca = findViewById(R.id.tvPlaca);
        tipobusqueda = "";
        numeroHojaRuta = getIntent().getExtras().getString("numeroHojaRuta");
        usuario = new Usuario();
        usuario = (Usuario) getIntent().getExtras().getSerializable("Usuario");
        tvfecharegistro.setText(listahojaruta.get(0).getFechaRegistro());
        tvchofer.setText(listahojaruta.get(0).getChofer());
        ObtenerLista();
        String cadena = "Nro Hoja Ruta : " + listahojaruta.get(0).getNumeroHojaRuta();
        tvhojaRuta.setText(cadena);
        tvplaca.setText(listahojaruta.get(0).getPlaca());

        listacliente = new ArrayList<>();

        btncancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuscarActivity.this, MainActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ObtenerLista() {
        listaInformacion = new ArrayList<>();

        for (int i = 0; i < listahojaruta.size(); i++) {

            listaInformacion.add(listahojaruta.get(i).getId_Clliente() + " - " + listahojaruta.get(i).getNombre() +
                    " \n" + "Direccion : " + listahojaruta.get(i).getLugarEntrega() +
                    " \n" + listahojaruta.get(i).getDistrito() + " \n");
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaInformacion);
        lvclientes.setAdapter(adaptador);
        lvclientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(BuscarActivity.this, DetalleBusquedaActivity.class);
            intent.putExtra("Id_Cliente", listahojaruta.get(position).getId_Clliente());
            intent.putExtra("numHojaRuta", listahojaruta.get(position).getNumeroHojaRuta());
            Bundle bundle = new Bundle();
            bundle.putSerializable("listahojaruta", listahojaruta);
            intent.putExtras(bundle);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("Usuario",usuario);
            intent.putExtras(bundle1);
            startActivity(intent);
            finish();
            }
        });
    }
}