package com.example.sistemas.taihengnavdrawer;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.taihengnavdrawer.Entidades.DetalleHojaRuta;
import com.example.sistemas.taihengnavdrawer.Entidades.HojaRuta;
import com.example.sistemas.taihengnavdrawer.Entidades.Usuario;
import com.example.sistemas.taihengnavdrawer.Utilitarios.Utilitario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.sistemas.taihengnavdrawer.LoginActivity.ejecutaFuncionTestMovil;

public class ListaDocumentosActivity extends AppCompatActivity {

    Button btnregresar;
    ArrayList<String> listaInformacion, listaEstadoDocumento,lvp1,lvp2,lvp3,lvp4,ListaMemoria;
    ListView lvdocumentos;
    String Id_cliente,numhojaaux, url,Trama,USUARIO = "Daniel", ESTADODESCRIPCION;
    ArrayList<DetalleHojaRuta> listadetallehojaruta;
    String ESTADO;
    View mview;
    ArrayList<HojaRuta> listahojaruta;
    Usuario usuario;
    TextView tvlatitud,tvlongitud,tvdireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_documentos);

        lvdocumentos = findViewById(R.id.lvDocumentos);
        tvlatitud = findViewById(R.id.tvlatitud);
        tvlongitud = findViewById(R.id.tvlongitud);
        tvdireccion = findViewById(R.id.tvdireccion);
        listaEstadoDocumento = new ArrayList<>();
        Id_cliente = getIntent().getExtras().getString("Id_Cliente");
        numhojaaux = getIntent().getExtras().getString("numHojaRuta1");
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        listahojaruta = (ArrayList<HojaRuta> ) getIntent().getSerializableExtra("listahojaruta");
        listadetallehojaruta = (ArrayList<DetalleHojaRuta> ) getIntent().getSerializableExtra("Lista");

        Toast.makeText(this, "" + tvlatitud, Toast.LENGTH_SHORT).show();
        lvp1 = new ArrayList<>();
        lvp2 = new ArrayList<>();
        lvp3 = new ArrayList<>();
        lvp4 = new ArrayList<>();

        lvp1 =(ArrayList<String>)getIntent().getSerializableExtra("lvp1");
        lvp2 =(ArrayList<String>)getIntent().getSerializableExtra("lvp2");
        lvp3 =(ArrayList<String>)getIntent().getSerializableExtra("lvp3");
        lvp4 =(ArrayList<String>)getIntent().getSerializableExtra("lvp4");

        ListaMemoria = new ArrayList<>();
        mview = getLayoutInflater().inflate(R.layout.spinner_dialog,null);
        btnregresar = findViewById(R.id.btnRegresar);

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Id_cliente = getIntent().getExtras().getString("Id_Cliente");
                numhojaaux = getIntent().getExtras().getString("numHojaRuta1");
                Intent intent = new Intent(ListaDocumentosActivity.this,DetalleBusquedaActivity.class);
                intent.putExtra("Id_Cliente",Id_cliente);
                intent.putExtra("numHojaRuta",numhojaaux);

                Bundle bundle = new Bundle();
                bundle.putSerializable("listahojaruta",  listahojaruta);
                intent.putExtras(bundle);

                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("Usuario",usuario);
                intent.putExtras(bundle2);

                startActivity(intent);
                finish();
            }
        });

        ObtenerLista();
    }

    private void ObtenerLista() {

        String detalleEstado = " ";
        listaInformacion = new ArrayList<>();

        for (int i=0; i< listadetallehojaruta.size();i++){

            if (listadetallehojaruta.get(i).getEstado().subSequence(0,2).equals("NE")){
                detalleEstado = "Normal Entregado";
            }else if(listadetallehojaruta.get(i).getEstado().subSequence(0,2).equals("RP")){
                for (int j = 0 ; j<lvp2.size();j++){

                    if(listadetallehojaruta.get(i).getEstado().equals(lvp2.get(j))){
                        detalleEstado = lvp1.get(j);
                    }
                }
            }
            if (listadetallehojaruta.get(i).getEstado().equals("null")){

                listaInformacion.add("Documento     :   " +listadetallehojaruta.get(i).getNumerodocumento()  +
                        " \n" +      "Fecha Doc       :    " + listadetallehojaruta.get(i).getFechadocumento()+
                        " \n" +      "Importe Doc   :    S/ " + listadetallehojaruta.get(i).getImportedocumento() +
                        " \n" +      "Estado             :    " + "   " + " - " + " \n" );
            }else{
                listaInformacion.add("Documento     :   " +listadetallehojaruta.get(i).getNumerodocumento()  +
                        " \n" +      "Fecha Doc       :    " + listadetallehojaruta.get(i).getFechadocumento()+
                        " \n" +      "Importe Doc   :    S/ " + listadetallehojaruta.get(i).getImportedocumento() +
                        " \n" +      "Estado             :    " + listadetallehojaruta.get(i).getEstado() + " - " + detalleEstado.substring(0,12)+" \n" );
            }
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listaInformacion);
        lvdocumentos.setAdapter(adaptador);
        lvdocumentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(ListaDocumentosActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Desea cambiar de estado?");

                final Spinner mspinner = (Spinner)mview.findViewById(R.id.spinner2);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ListaDocumentosActivity.this,android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.Opciones));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mspinner.setAdapter(adapter);
                mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                        final Spinner mspinner1 = (Spinner)mview.findViewById(R.id.spinner4);
                        if (i==0){
                            ESTADO = "NE00";
                            ESTADODESCRIPCION = "NORMAL ENTREGADO";
                            mspinner1.setVisibility(View.GONE);
                        }
                        if (i==1){

                            mspinner1.setVisibility(View.VISIBLE);
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ListaDocumentosActivity.this,
                                    android.R.layout.simple_spinner_item, lvp1);
                            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mspinner1.setAdapter(adapter1);
                            mspinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    ESTADO = lvp2.get(i);
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder  builder = new AlertDialog.Builder(
                                ListaDocumentosActivity.this);
                        builder.setMessage("Esta seguro que desea actualizar el registro del estado?")
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                         Trama = listadetallehojaruta.get(position).getCodDocumento()+"|"+
                                           listadetallehojaruta.get(position).getNumerodocumento()+"|"+ESTADO+
                                                 "|"+numhojaaux+"|"+USUARIO+"|"+tvlatitud+"|"+tvlongitud+"|"+tvdireccion;

                                        if(Utilitario.isOnline(getApplicationContext())){

                                            EnviarTrama(Trama,ESTADO,position);

                                        }else{

                                            AlertDialog.Builder build = new AlertDialog.Builder(ListaDocumentosActivity.this);
                                            build.setTitle("Atención .. !");
                                            build.setMessage("El Servicio de Internet no esta Activo, por favor revisar");
                                            build.setCancelable(false);
                                            build.setNegativeButton("ACEPTAR",null);
                                            build.create().show();

                                        }
                                    }
                                })
                                .create()
                                .show();
                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                    }
                });
                    builder.setView(mview);
                    AlertDialog dialog = builder.create();
                    if(mview.getParent()!=null)
                    ((ViewGroup)mview.getParent()).removeView(mview); // <- fix
                    dialog.show();
            }
        });
    }

    public void EnviarTrama (final String Trama, final String estado, final Integer i){

        final ProgressDialog progressDialog = new ProgressDialog(ListaDocumentosActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("... Cargando");
        progressDialog.create();
        progressDialog.show();

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url =  ejecutaFuncionTestMovil + "PKG_MOVIL_FUNCIONES.FN_ACTUALIZA_DHRUTA&variables='"+Trama+"'";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

                        progressDialog.dismiss();
                        Id_cliente = getIntent().getExtras().getString("Id_Cliente");
                        numhojaaux = getIntent().getExtras().getString("numHojaRuta1");

                        Intent intent = new Intent(ListaDocumentosActivity.this,ListaDocumentosActivity.class);
                        intent.putExtra("Id_Cliente",Id_cliente);
                        intent.putExtra("numHojaRuta1",numhojaaux);
                        intent.putExtra("lvp1",  lvp1);
                        intent.putExtra("lvp2",  lvp2);
                        intent.putExtra("lvp3",  lvp3);
                        intent.putExtra("lvp4",  lvp4);

                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("listahojaruta",  listahojaruta);
                        intent.putExtras(bundle1);

                        Bundle bundle = new Bundle();
                        listadetallehojaruta.get(i).setEstado(estado);
                        bundle.putSerializable("Lista",  listadetallehojaruta);
                        intent.putExtras(bundle);

                        Bundle bundle2 = new Bundle();
                        bundle2.putSerializable("Usuario",usuario);
                        intent.putExtras(bundle2);

                        startActivity(intent);
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //progressDialog.dismiss();
                AlertDialog.Builder build = new AlertDialog.Builder(ListaDocumentosActivity.this);
                build.setTitle("Atención .. !");
                build.setMessage("Error,  el servicio no se encuentra activo en estos momentos");
                build.setCancelable(false);
                build.setNegativeButton("ACEPTAR",null);
                build.create().show();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }


    private void locationStart() {
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Localizacion Local = new Localizacion();
        Local.setFechaPactadaActivity(this);
        final boolean gpsEnabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {

            /**  Se hace la habilitacion del GPS, si se descomenta esta parte del codigo */

            /*
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
            */

        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;

        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 20, (LocationListener) Local);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 20, (LocationListener) Local);
        tvlatitud.setText("ND");
        tvdireccion.setText("ND");
        tvlongitud.setText("ND");
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1000) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationStart();
                return;
            }
        }
    }
    public void setLocation(Location loc) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        loc.getLatitude(), loc.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    tvdireccion.setText("" + DirCalle.getAddressLine(0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* Aqui empieza la Clase Localizacion */
    public class Localizacion implements LocationListener {
        ListaDocumentosActivity fechaPactadaActivity;
        public ListaDocumentosActivity getFechaPactadaActivity() {

            return fechaPactadaActivity;
        }
        public void setFechaPactadaActivity(ListaDocumentosActivity fechaPactadaActivity) {
            this.fechaPactadaActivity = fechaPactadaActivity;
        }
        @Override
        public void onLocationChanged(Location loc) {
            // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
            // debido a la deteccion de un cambio de ubicacion

            loc.getLatitude();
            loc.getLongitude();
            String Text = loc.getLatitude()+"";
            String longitudTxt = loc.getLongitude()+"";
            tvlatitud.setText(Text);
            tvlongitud.setText(longitudTxt);
            this.fechaPactadaActivity.setLocation(loc);
        }

        @Override
        public void onProviderDisabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es desactivado
            tvlatitud.setText("ND");
        }

        @Override
        public void onProviderEnabled(String provider) {
            // Este metodo se ejecuta cuando el GPS es activado
            tvlatitud.setText("GPS%20Activado");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    Toast.makeText(fechaPactadaActivity, "LocationProvider.AVAILABLE", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    Toast.makeText(fechaPactadaActivity, "LocationProvider.OUT_OF_SERVICE", Toast.LENGTH_SHORT).show();
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    Toast.makeText(fechaPactadaActivity, "LocationProvider.TEMPORARILY_UNAVAILABLE", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
