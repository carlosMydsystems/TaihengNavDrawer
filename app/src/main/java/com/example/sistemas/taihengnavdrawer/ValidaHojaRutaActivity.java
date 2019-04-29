package com.example.sistemas.taihengnavdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sistemas.taihengnavdrawer.Entidades.HojaRuta;
import com.example.sistemas.taihengnavdrawer.Entidades.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ValidaHojaRutaActivity extends AppCompatActivity {

    EditText etnumerohojaruta;
    Button btnbuscar;
    String numeroHojaRuta,url;
    ArrayList<HojaRuta> listahojaruta;
    HojaRuta hojaRuta;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valida_hoja_ruta);

        usuario = (Usuario)getIntent().getExtras().getSerializable("Usuario");
        Toast.makeText(this, "" + usuario.getNombre(), Toast.LENGTH_SHORT).show();
        etnumerohojaruta = findViewById(R.id.etNumeroHojaRuta);
        btnbuscar = findViewById(R.id.btnBuscar);
        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            numeroHojaRuta = etnumerohojaruta.getText().toString();
            CapturarJson(numeroHojaRuta);
            }
        });
    }

    public void CapturarJson(String clave){

        final ProgressDialog progressDialog = new ProgressDialog(ValidaHojaRutaActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("... Cargando");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=" +
                "PKG_MOVIL_FUNCIONES.FN_OBTENER_HRUTA&variables='"+clave+"'";
        listahojaruta = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject=new JSONObject( response);
                            boolean success = jsonObject.getBoolean("success");

                            JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");

                            if (success){

                                for(int i=0;i<jsonArray.length();i++) {
                                    hojaRuta = new HojaRuta();

                                    jsonObject = jsonArray.getJSONObject(i);
                                    hojaRuta.setNumeroHojaRuta(jsonObject.getString("HRUTA"));
                                    hojaRuta.setChofer(jsonObject.getString("CHOFER"));
                                    hojaRuta.setPlaca(jsonObject.getString("PLACA"));
                                    hojaRuta.setFechaRegistro(jsonObject.getString("FECHA_HR"));
                                    hojaRuta.setId_Clliente(jsonObject.getString("COD_CLIENTE"));
                                    hojaRuta.setNombre(jsonObject.getString("NOMBRE_CLIENTE"));
                                    hojaRuta.setLugarEntrega(jsonObject.getString("LUGAR_ENTREGA"));
                                    hojaRuta.setDistrito(jsonObject.getString("DISTRITO"));
                                    listahojaruta.add(hojaRuta);
                                }

                                Intent pantalla1 = new Intent(ValidaHojaRutaActivity.this, BuscarActivity.class);
                                pantalla1.putExtra("numeroHojaRuta", numeroHojaRuta);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("listahojaruta",  listahojaruta);
                                pantalla1.putExtras(bundle);
                                Bundle bundle1 = new Bundle();
                                bundle1.putSerializable("Usuario",usuario);
                                pantalla1.putExtras(bundle1);
                                startActivity(pantalla1);
                                finish();

                            }else{

                                progressDialog.dismiss();
                                AlertDialog.Builder  builder = new AlertDialog.Builder(
                                        ValidaHojaRutaActivity.this);
                                builder.setMessage("No se encontró hoja de Ruta")
                                        .setNegativeButton("Regresar",null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}

