package com.example.sistemas.taihengnavdrawer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.sistemas.taihengnavdrawer.Utilitarios.Utilitario;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import static com.example.sistemas.taihengnavdrawer.LoginActivity.ejecutaFuncionCursorTestMovil;

public class ValidaHojaRutaActivity extends AppCompatActivity {

    EditText etnumerohojaruta;
    Button btnbuscar;
    String numeroHojaRuta,url;
    ArrayList<HojaRuta> listahojaruta;
    HojaRuta hojaRuta;
    Usuario usuario;
    ImageButton ibRegresoMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valida_hoja_ruta);

        usuario = (Usuario)getIntent().getExtras().getSerializable("Usuario");
        etnumerohojaruta = findViewById(R.id.etNumeroHojaRuta);
        btnbuscar = findViewById(R.id.btnBuscar);
        ibRegresoMenu = findViewById(R.id.ibRegresoMenu);
        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            numeroHojaRuta = etnumerohojaruta.getText().toString();
                if (etnumerohojaruta.length()<10){

                    AlertDialog.Builder build = new AlertDialog.Builder(ValidaHojaRutaActivity.this);
                    build.setCancelable(false);
                    build.setTitle("Atención...!");
                    build.setMessage("El Número de hoja de Ruta debe tener 10 dígitos");
                    build.setNegativeButton("Aceptar",null);
                    build.create().show();

                }else {

                //etnumerohojaruta.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});

                    if(Utilitario.isOnline(getApplicationContext())){

                        CapturarJson(numeroHojaRuta);

                    }else{

                        AlertDialog.Builder build = new AlertDialog.Builder(ValidaHojaRutaActivity.this);
                        build.setTitle("Atención .. !");
                        build.setMessage("El Servicio de Internet no esta Activo, por favor revisar");
                        build.setCancelable(false);
                        build.setNegativeButton("ACEPTAR",null);
                        build.create().show();

                    }
                }
            }
        });

        ibRegresoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ValidaHojaRutaActivity.this,MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Usuario",usuario);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    public void CapturarJson(String clave){

        final ProgressDialog progressDialog = new ProgressDialog(ValidaHojaRutaActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("... Cargando");
        progressDialog.show();
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        url =  ejecutaFuncionCursorTestMovil  + "PKG_MOVIL_FUNCIONES.FN_OBTENER_HRUTA&variables='"+clave+"'";

        listahojaruta = new ArrayList<>();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String Mensaje = "";

                try {
                    JSONObject jsonObject=new JSONObject( response);
                    boolean success = jsonObject.getBoolean("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                    Boolean condicion = false,error = false;

                    if (success){

                        String Aux = response.replace("{", "|");
                        Aux = Aux.replace("}", "|");
                        Aux = Aux.replace("[", "|");
                        Aux = Aux.replace("]", "|");
                        Aux = Aux.replace("\"", "|");
                        Aux = Aux.replace(",", " ");
                        Aux = Aux.replace("|", "");
                        Aux = Aux.replace(":", " ");
                        String partes[] = Aux.split(" ");

                        for (String palabras : partes) {
                            if (condicion) {
                                Mensaje += palabras + " ";
                            }
                            if (palabras.equals("ERROR")) {
                                condicion = true;
                                error = true;
                            }
                        }
                        if (error) {

                            progressDialog.dismiss();
                            AlertDialog.Builder dialog = new AlertDialog.Builder(
                                    ValidaHojaRutaActivity.this);
                            dialog.setMessage(Mensaje)
                                    .setNegativeButton("Regresar", null)
                                    .create()
                                    .show();
                        } else {

                            for (int i = 0; i < jsonArray.length(); i++) {
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
                            bundle.putSerializable("listahojaruta", listahojaruta);
                            pantalla1.putExtras(bundle);
                            Bundle bundle1 = new Bundle();
                            bundle1.putSerializable("Usuario", usuario);
                            pantalla1.putExtras(bundle1);
                            startActivity(pantalla1);
                            finish();

                        }
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
            progressDialog.dismiss();
            AlertDialog.Builder build = new AlertDialog.Builder(ValidaHojaRutaActivity.this);
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

