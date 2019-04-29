package com.example.sistemas.taihengnavdrawer;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PasaEstadoActivity extends AppCompatActivity {

    String codcliente,numHojaRuta,url;
    ArrayList<DetalleHojaRuta> listadetallehojaruta;
    ArrayList<String> lvp1,lvp2,lvp3,lvp4;
    ArrayList<HojaRuta> listahojaruta;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasa_estado);

        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");

        lvp1 = new ArrayList<>();
        lvp2 = new ArrayList<>();
        lvp3 = new ArrayList<>();
        lvp4 = new ArrayList<>();
        listadetallehojaruta = new ArrayList<>();
        codcliente = (String)getIntent().getExtras().get("Id_Cliente");
        numHojaRuta = (String)getIntent().getExtras().get("numHojaRuta1");
        listadetallehojaruta = (ArrayList<DetalleHojaRuta> ) getIntent().getSerializableExtra("Lista");
        listahojaruta = (ArrayList<HojaRuta> ) getIntent().getSerializableExtra("listahojaruta");
        Recorelista();
        Toast.makeText(this, listahojaruta.get(0).getFechaRegistro(), Toast.LENGTH_SHORT).show();

    }

    public void Recorelista (){

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionCursorTestMovil.php?funcion=pkg_movil_funciones.fn_obtener_motivos_hruta&variables='9'";


        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

                        try {
                            JSONObject jsonObject=new JSONObject(response1);
                            boolean success = jsonObject.getBoolean("success");

                            if (success){
                                JSONArray jsonArray = jsonObject.getJSONArray("hojaruta");


                                for(int i=0;i<jsonArray.length();i++) {
                                    jsonObject = jsonArray.getJSONObject(i);


                                    if (jsonObject.getString("COD_CAMPO").substring(0,2).equals("RP")){
                                        lvp1.add(jsonObject.getString("DESC_CAMPO"));
                                        lvp2.add(jsonObject.getString("COD_CAMPO"));

                                    }else if(jsonObject.getString("COD_CAMPO").substring(0,2).equals("RT")) {
                                        lvp3.add(jsonObject.getString("DESC_CAMPO"));
                                        lvp4.add(jsonObject.getString("COD_CAMPO"));
                                    }
                                }

                                Intent intent = new Intent(PasaEstadoActivity.this,ListaDocumentosActivity.class);
                                intent.putExtra("lvp1",  lvp1);
                                intent.putExtra("lvp2",  lvp2);
                                intent.putExtra("lvp3",  lvp3);
                                intent.putExtra("lvp4",  lvp4);
                                intent.putExtra("Id_Cliente",codcliente);
                                intent.putExtra("numHojaRuta1",numHojaRuta);
                                // Se envia el Arraylist
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

                            }else{

                                AlertDialog.Builder build1 = new AlertDialog.Builder(PasaEstadoActivity.this);
                                build1.setTitle("No se encontro el registro")
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
