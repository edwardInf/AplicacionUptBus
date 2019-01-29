package com.example.gcoaquira.aplicacionuptbus.api;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gcoaquira.aplicacionuptbus.LoginActivity;
import com.example.gcoaquira.aplicacionuptbus.MenuActivity;
import com.example.gcoaquira.aplicacionuptbus.modelo.BusesConductores;
import com.example.gcoaquira.aplicacionuptbus.modelo.Conductores;
import com.example.gcoaquira.aplicacionuptbus.modelo.Estudiantes;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Conexion{

    public static Retrofit getApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://uptbus.tk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

    static Intent intent;


    public static void obtenerLocalizacionBuses(Callback<BusConductorResp> callback) {
        try{
            Conexion usuario = new Conexion();
            BusConductorService apiService = usuario.getApi().create(BusConductorService.class);
            Call<BusConductorResp> call = apiService.getDatos();
            call.enqueue(callback);


        }catch (Exception e){
            Log.d("Error", e.getMessage());
            Toast.makeText(LoginActivity.context, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}
