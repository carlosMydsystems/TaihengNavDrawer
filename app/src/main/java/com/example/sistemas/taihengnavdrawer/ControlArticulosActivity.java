package com.example.sistemas.taihengnavdrawer;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.sistemas.taihengnavdrawer.Utilitarios.Utilitario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import static com.example.sistemas.taihengnavdrawer.LoginActivity.ejecutaFuncionCursorTestMovil;
import static com.example.sistemas.taihengnavdrawer.LoginActivity.ejecutaFuncionTestMovil;

public class ControlArticulosActivity extends AppCompatActivity {

    TextView tvdetallearticulo,tvstock,tvprecio,tv4,tv6,tvcontrol;
    Button btnbuscar,btngrabar;
    EditText etarticulo,etcantidad;
    String TRAMA,numeroArticulo,url,codigoArticulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_articulos);

        etarticulo = findViewById(R.id.etArticulo);
        etcantidad = findViewById(R.id.etCantidad);
        tvdetallearticulo = findViewById(R.id.tvDetalle);
        tvprecio = findViewById(R.id.tvPrecio);
        tvstock = findViewById(R.id.tvStock);
        btnbuscar = findViewById(R.id.btnBuscar);
        btngrabar = findViewById(R.id.btnGrabar);
        tv4 = findViewById(R.id.textView4);
        tv6 = findViewById(R.id.textView6);
        tvcontrol = findViewById(R.id.tvControl);

        etarticulo.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    btnbuscar.setActivated(false);
                    if (etarticulo.getText().toString().equals("")) {
                        etarticulo.requestFocus();
                    }else {
                        etcantidad.requestFocus();
                        numeroArticulo = etarticulo.getText().toString();
                        TRAMA = "T07|LIMA|"+numeroArticulo+"||11111111||000|1|999";  //Se genera la trama
                        tvdetallearticulo.setText("");
                        tvprecio.setText("");
                        tvstock.setText("");
                        btnbuscar.setVisibility(View.GONE);
                        EnviarTrama(TRAMA);
                    }
                    return true;
                }
                return false;
            }
        });

        btngrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numerost = etcantidad.getText().toString();
                if (numerost.equals("")||numerost.equals("0")||numerost.equals("00")){
                    Toast.makeText(ControlArticulosActivity.this, "Por favor ingrese una cantidad valida", Toast.LENGTH_SHORT).show();
                    etcantidad.setText("");
                }else{
                    String trama = tvcontrol.getText().toString()+"|"+numerost;
                    tvdetallearticulo.setText("");
                    tvprecio.setText("");
                    tvstock.setText("");
                    etarticulo.requestFocus();
                    etcantidad.setText("");
                    ActualizarArticulo(trama);
                }
            }
        });

        btnbuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etarticulo.getText().toString().equals("")) {

                }else{
                    btnbuscar.setActivated(false);
                    numeroArticulo = etarticulo.getText().toString();
                    TRAMA = "T07|LIMA|" + numeroArticulo + "||11111111||000|1|999";  //Se genera la trama
                    tvdetallearticulo.setText("");
                    tvprecio.setText("");
                    tvstock.setText("");
                    btnbuscar.setVisibility(View.GONE);

                    if(Utilitario.isOnline(getApplicationContext())){

                        EnviarTrama(TRAMA);

                    }else{

                        AlertDialog.Builder build = new AlertDialog.Builder(ControlArticulosActivity.this);
                        build.setTitle("Atención .. !");
                        build.setMessage("El Servicio de Internet no esta Activo, por favor revisar");
                        build.setCancelable(false);
                        build.setNegativeButton("ACEPTAR",null);
                        build.create().show();

                    }


                }
            }
        });

    }

    private void ActualizarArticulo(String trama) {

        // TODO se debe realizar la actualizacion del articulo

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url = ejecutaFuncionTestMovil+"FN_INSERTA_ART&variables='"+trama+"'";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        try {

                            JSONObject jsonObject = new JSONObject(response1);
                            //Boolean success = jsonObject.getBoolean("success");
                            AlertDialog.Builder bulider = new AlertDialog.Builder(ControlArticulosActivity.this);
                            bulider.setMessage("Se ha actualizado el registro");
                            bulider.setPositiveButton("Regresar",null)
                                    .create()
                                    .show();

                            tv4.setVisibility(View.GONE);
                            tv6.setVisibility(View.GONE);
                            btngrabar.setVisibility(View.GONE);
                            etcantidad.setVisibility(View.GONE);

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

    public void EnviarTrama (String trama ){
        final ProgressDialog progressDialog = new ProgressDialog(ControlArticulosActivity.this);
        progressDialog.setMessage("Cargando...");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  ejecutaFuncionCursorTestMovil+ "PKG_MOVIL_FUNCIONES.FN_CONSULTAR_PRODUCTO_WS_SP&variables='"+trama+"'";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {
                        String Mensaje = "";
                            try {
                                progressDialog.dismiss();
                                btnbuscar.setVisibility(View.VISIBLE);
                                response1 = response1.trim();
                                    if(response1.equals("{\"success\":false}")){
                                        progressDialog.dismiss();
                                        etarticulo.setText("");
                                        btnbuscar.setVisibility(View.VISIBLE);
                                        etarticulo.requestFocus();
                                        etcantidad.setText("");
                                    }
                                JSONObject jsonObject = new JSONObject(response1);
                                Boolean success = jsonObject.getBoolean("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");
                                Boolean condicion = false,error = false;
                                btnbuscar.setVisibility(View.VISIBLE);

                                if (success) {
                                    etcantidad.setText("");
                                    etarticulo.setText("");
                                    etcantidad.requestFocus();
                                    tv4.setVisibility(View.VISIBLE);
                                    tv6.setVisibility(View.VISIBLE);
                                    btngrabar.setVisibility(View.VISIBLE);
                                    etcantidad.setVisibility(View.VISIBLE);
                                    etcantidad.requestFocus();
                                    etcantidad.append("1");

                                    String Aux = response1.replace("{","|");
                                    Aux = Aux.replace("}","|");
                                    Aux = Aux.replace("[","|");
                                    Aux = Aux.replace("]","|");
                                    Aux = Aux.replace("\"","|");
                                    Aux = Aux.replace(","," ");
                                    Aux = Aux.replace("|","");
                                    Aux = Aux.replace(":"," ");
                                    String partes[] = Aux.split(" ");
                                    for (String palabras : partes){
                                        if (condicion){ Mensaje += palabras+" "; }
                                        if (palabras.equals("ERROR")){
                                            condicion = true;
                                            error = true;
                                        }
                                    }
                                    if (error) {
                                        tvdetallearticulo.setText("");
                                        tvprecio.setText("");
                                        tvstock.setText("");
                                        tv4.setVisibility(View.GONE);
                                        tv6.setVisibility(View.GONE);
                                        btngrabar.setVisibility(View.GONE);
                                        etcantidad.setVisibility(View.GONE);

                                        AlertDialog.Builder dialog = new AlertDialog.Builder(
                                                ControlArticulosActivity.this);
                                        dialog.setMessage(Mensaje)
                                                .setNegativeButton("Regresar",null)
                                                .create()
                                                .show();
                                    }else {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            jsonObject = jsonArray.getJSONObject(i);
                                            codigoArticulo = jsonObject.getString("COD_ARTICULO");
                                            tvcontrol.setText(codigoArticulo);
                                            String descripcion = jsonObject.getString(
                                                    "COD_ARTICULO") +" - "+ jsonObject.getString("DESCRIPCION");
                                            tvdetallearticulo.setText(descripcion);
                                            Double stock = Double.parseDouble(jsonObject.getString("STOCK"));

                                            // Segundo metodo
                                            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                                            simbolos.setDecimalSeparator('.'); // Se define el simbolo para el separador decimal
                                            simbolos.setGroupingSeparator(',');// Se define el simbolo para el separador de los miles
                                            DecimalFormat formateador = new DecimalFormat("###,###.00",simbolos); // Se crea el formato del numero con los simbolo
                                            tvstock.setText(formateador.format(stock));

                                        }
                                    }
                                } else {

                                    progressDialog.dismiss();
                                    AlertDialog.Builder builder = new AlertDialog.Builder(
                                            ControlArticulosActivity.this);
                                    builder.setMessage("No se encontró Articulo")
                                            .setNegativeButton("Regresar", null)
                                            .create()
                                            .show();
                                    etarticulo.requestFocus();
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
