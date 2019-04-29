package com.example.sistemas.taihengnavdrawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

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

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_documentos);

        lvdocumentos = findViewById(R.id.lvDocumentos);
        listaEstadoDocumento = new ArrayList<>();
        Id_cliente = getIntent().getExtras().getString("Id_Cliente");
        numhojaaux = getIntent().getExtras().getString("numHojaRuta1");
        usuario = (Usuario)getIntent().getSerializableExtra("Usuario");
        listahojaruta = (ArrayList<HojaRuta> ) getIntent().getSerializableExtra("listahojaruta");
        listadetallehojaruta = (ArrayList<DetalleHojaRuta> ) getIntent().getSerializableExtra("Lista");

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
                        " \n" +      "Estado             :    " + listadetallehojaruta.get(i).getEstado() + " - " + "El estado es nulo \n" );

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
                                           listadetallehojaruta.get(position).getNumerodocumento()+"|"+ESTADO+"|"+numhojaaux+"|"+USUARIO;
                                        EnviarTrama(Trama,ESTADO,position);
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

                        /*
                        Id_cliente = getIntent().getExtras().getString("Id_Cliente");
                        numhojaaux = getIntent().getExtras().getString("numHojaRuta1");
                        Intent intent = new Intent(ListaDocumentosActivity.this,ListaDocumentosActivity.class);
                        intent.putExtra("Id_Cliente",Id_cliente);
                        intent.putExtra("numHojaRuta1",numhojaaux);
                        intent.putExtra("lvp1",  lvp1);
                        intent.putExtra("lvp2",  lvp2);
                        intent.putExtra("lvp3",  lvp3);
                        intent.putExtra("lvp4",  lvp4);
                        Bundle bundle = new Bundle();
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("listahojaruta",  listahojaruta);
                        intent.putExtras(bundle1);
                        bundle.putSerializable("Lista",  listadetallehojaruta);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                        */
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

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        url =  "http://www.taiheng.com.pe:8494/oracle/ejecutaFuncionTest.php?funcion=" +
                "PKG_MOVIL_FUNCIONES.FN_ACTUALIZA_DHRUTA&variables='"+Trama+"'";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response1) {

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
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }
}
