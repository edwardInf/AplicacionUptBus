package com.example.gcoaquira.aplicacionuptbus;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.gcoaquira.aplicacionuptbus.api.ConductorResp;
import com.example.gcoaquira.aplicacionuptbus.api.ConductorService;
import com.example.gcoaquira.aplicacionuptbus.api.Conexion;
import com.example.gcoaquira.aplicacionuptbus.api.EstudianteResp;
import com.example.gcoaquira.aplicacionuptbus.api.EstudianteService;
import com.example.gcoaquira.aplicacionuptbus.modelo.Conductores;
import com.example.gcoaquira.aplicacionuptbus.modelo.Estudiantes;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    public static ProgressDialog progressDialog;
    public static boolean ingreso;
    public static Context context;
    static ArrayList<Conductores> conductor;
    static ArrayList<Estudiantes> estudiante;

    Button ingresar;
    EditText usuario,password;
    RadioButton conduc,est;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ingresar=(Button)findViewById(R.id.button_ingresar);
        usuario=(EditText) findViewById(R.id.edt_codigo);
        password=(EditText) findViewById(R.id.edt_clave);
        context = LoginActivity.this.getApplicationContext();
        conduc = (RadioButton)findViewById(R.id.rdConductor);
        est = (RadioButton)findViewById(R.id.rdEstudiante);

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = usuario.getText().toString();
                String clave = password.getText().toString();

                if(camposVacios(codigo, clave)){

                    if (conduc.isChecked()) {
                        ingresarConductor(codigo,clave);
                        password.setText("");

                    } else{
                        ingresarEstudiante(codigo,clave);
                        password.setText("");
                    }
                }
            }
        });
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Validando...");
    }



    private boolean camposVacios(String usuario, String password){
        if(usuario == null || usuario.trim().length() == 0){
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
    static Intent intent;

    private static ArrayList<Conductores> conductores;
    private static ArrayList<Estudiantes> estudiantes;

    public static void ingresarConductor(final String codigo,final  String clave) {

        LoginActivity.ingreso=false;
        LoginActivity.progressDialog.show();
        intent = new Intent(LoginActivity.context,MenuActivity.class);
        try{
            Conexion usuario = new Conexion();
            ConductorService apiService = usuario.getApi().create(ConductorService.class);
            Call<ConductorResp> call = apiService.getJSON();
            call.enqueue(new Callback<ConductorResp>() {
                @Override
                public void onResponse(Call<ConductorResp> call, Response<ConductorResp> response) {
                    ConductorResp jsonResponse = response.body();
                    conductores = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    int i=0;
                    while ((i<conductores.size())){
                        if (codigo.equals(conductores.get(i).getCodigo()) &&
                                clave.equals(conductores.get(i).getClave())){
                            LoginActivity.ingreso = true;
                            i = conductores.size();
                        }else { i++; }
                    }
                    if (LoginActivity.ingreso){
                        conductorSN = true;
                        intent.putExtra("usuario", "Conductor");
                        context.startActivity(intent);
                    }else {
                        conductorSN = false;
                        Toast.makeText(context, "Usuario No Encontrado", Toast.LENGTH_SHORT).show();

                    }
                    progressDialog.dismiss();

                }
                @Override
                public void onFailure(Call<ConductorResp> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    progressDialog.dismiss();
                    Toast.makeText(context, "No hay Conexion", Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(LoginActivity.context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean conductorSN;


    public static void ingresarEstudiante(final String codigo,final  String clave) {

        LoginActivity.ingreso=false;
        LoginActivity.progressDialog.show();
        intent = new Intent(LoginActivity.context,MenuActivity.class);

        try{
            Conexion usuario = new Conexion();
            EstudianteService apiService = usuario.getApi().create(EstudianteService.class);
            Call<EstudianteResp> call = apiService.getDatos();
            call.enqueue(new Callback<EstudianteResp>() {

                @Override
                public void onResponse(Call<EstudianteResp> call, Response<EstudianteResp> response) {
                    EstudianteResp jsonResponse = response.body();
                    estudiantes = new ArrayList<>(Arrays.asList(jsonResponse.getData()));
                    conductorSN = false;
                    int i=0;
                    while ((i<estudiantes.size())){
                        if (codigo.equals(estudiantes.get(i).getCodigo()) &&
                                clave.equals(estudiantes.get(i).getClave())){
                            ingreso = true;
                            i = estudiantes.size();
                        }else {
                            i++;
                        }
                    }
                    if (LoginActivity.ingreso){
                        conductorSN = true;
                        intent.putExtra("usuario", "Estudiante");
                        context.startActivity(intent);
                    }else {
                        conductorSN = false;
                        Toast.makeText(context, "Usuario No Encontrado", Toast.LENGTH_SHORT).show();

                    }
                    progressDialog.dismiss();

                }
                @Override
                public void onFailure(Call<EstudianteResp> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.context, "No hay Conexion", Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(LoginActivity.context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


}
