package com.example.gcoaquira.aplicacionuptbus.api;

import com.example.gcoaquira.aplicacionuptbus.modelo.BusesConductores;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BusConductorService {
    @GET("api/buses_conductors")
    Call<BusConductorResp> getDatos();
}
