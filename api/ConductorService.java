package com.example.gcoaquira.aplicacionuptbus.api;

import android.support.annotation.Nullable;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface ConductorService {

    @GET("api/conductores")
    Call<ConductorResp> getJSON();

}
