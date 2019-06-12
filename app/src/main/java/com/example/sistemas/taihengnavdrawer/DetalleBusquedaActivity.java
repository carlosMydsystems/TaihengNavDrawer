package com.example.sistemas.taihengnavdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.sistemas.taihengnavdrawer.LoginActivity.ejecutaFuncionCursorTestMovil;

public class DetalleBusquedaActivity extends AppCompatActivity {

    TextView tvcliente,tvdireccion,tvdistrito,tvDetalleCliente;
    Button btnListarDocumento, btnCtaCte,btnRegresar;
    String codcliente, numHojaRuta,url,cadena;
    ArrayList<DetalleHojaRuta> listadetallehojaruta;
    DetalleHojaRuta detallehojaruta;
    ArrayList<HojaRuta> listahojaruta;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_busqueda);

        tvcliente = findViewById(R.id.tvRazonSocial);
        tvdireccion = findViewById(R.id.tvDireccion);
        tvdistrito = findViewById(R.id.tvdistrito);
        btnRegresar = findViewById(R.id.btnRegresar);
        tvDetalleCliente = findViewById(R.id.tvdetalleCliente);
        listahojaruta = (ArrayList<HojaRuta> ) getIntent().getSerializableExtra("listahojaruta");

        // Recibe los parametros del Intent MainActivity
        codcliente = (String)getIntent().getExtras().get("Id_Cliente");
        numHojaRuta = (String)getIntent().getExtras().get("numHojaRuta");
        usuario = (Usuario)getIntent().getExtras().getSerializable("Usuario");

        // Se hace la captura de la cadena en JSON - por medio de un request con método GET

        if(Utilitario.isOnline(getApplicationContext())){

            CapturarJson();

        }else{

            AlertDialog.Builder build = new AlertDialog.Builder(DetalleBusquedaActivity.this);
            build.setTitle("Atención .. !");
            build.setMessage("El Servicio de Internet no esta Activo, por favor revisar");
            build.setCancelable(false);
            build.setNegativeButton("ACEPTAR",null);
            build.create().show();

        }

        // se le da funcionalidad al boton Regresar

        btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(DetalleBusquedaActivity.this,BuscarActivity.class);
                intent.putExtra("Id_Cliente",codcliente);
                intent.putExtra("numeroHojaRuta",numHojaRuta);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listahojaruta",  listahojaruta);
                intent.putExtras(bundle);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Usuario",usuario);
                intent.putExtras(bundle1);
                startActivity(intent);
                finish();
            }
        });


        /** Parte del codigo para colocar el boton de cuenta corriente
        // Se define la funcionalidad del Boton de Cta  Cte
        btnCtaCte = findViewById(R.id.btnCtacte);
        btnCtaCte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se debe de ingresa el codigo para poder darle funcionalidad

            }
        });
        */

        btnListarDocumento = findViewById(R.id.btnListarDocumento);
        btnListarDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetalleBusquedaActivity.this,PasaEstadoActivity
                        .class);
                intent.putExtra("Id_Cliente",codcliente);
                intent.putExtra("numHojaRuta1",numHojaRuta);

                Bundle bundle = new Bundle();
                bundle.putSerializable("Lista",  listadetallehojaruta);
                intent.putExtras(bundle);

                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("listahojaruta",  listahojaruta);
                intent.putExtras(bundle1);

                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("Usuario",usuario);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });
    }

    public void CapturarJson(){

        final ProgressDialog progressDialog = new ProgressDialog(DetalleBusquedaActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("... Cargando");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url =  ejecutaFuncionCursorTestMovil + "PKG_MOVIL_FUNCIONES.FN_OBTENER_DHRUTA&variables='"+numHojaRuta+"|"+codcliente+"'";

        listadetallehojaruta = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        cadena = response;

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                            for(int i=0;i<jsonArray.length();i++) {
                                detallehojaruta = new DetalleHojaRuta();

                                jsonObject = jsonArray.getJSONObject(i);
                                detallehojaruta.setNumerohojaruta(jsonObject.getString("HRUTA"));
                                detallehojaruta.setCodCliente(jsonObject.getString("COD_CLIENTE"));
                                detallehojaruta.setCliente(jsonObject.getString("NOMBRE_CLIENTE"));
                                detallehojaruta.setLugarEntrega(jsonObject.getString("LUGAR_ENTREGA"));
                                detallehojaruta.setCodDocumento(jsonObject.getString("COD_DOCUMENTO"));
                                detallehojaruta.setFechadocumento(jsonObject.getString("FCH_DOCUMENTO"));
                                detallehojaruta.setNumerodocumento(jsonObject.getString("NRO_DOCUMENTO"));
                                detallehojaruta.setMoneda(jsonObject.getString("MONEDA"));
                                detallehojaruta.setDistrito(jsonObject.getString("DISTRITO"));
                                detallehojaruta.setImportedocumento(jsonObject.getString("IMPORTE_DOCUMENTO"));
                                detallehojaruta.setEstado(jsonObject.getString("SITUACION_CD"));
                                listadetallehojaruta.add(detallehojaruta);
                            }

                            tvcliente.setText(listadetallehojaruta.get(0).getCodCliente()+" - "+listadetallehojaruta.get(0).getCliente());
                            tvdireccion.setText(listadetallehojaruta.get(0).getLugarEntrega());
                            tvdistrito.setText(listadetallehojaruta.get(0).getDistrito());

                            progressDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(DetalleBusquedaActivity.this, "Se ha producido el error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                AlertDialog.Builder build = new AlertDialog.Builder(DetalleBusquedaActivity.this);
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
}
