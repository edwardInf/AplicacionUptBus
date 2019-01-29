package com.example.gcoaquira.aplicacionuptbus.api;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EstudianteService {

    @GET("api/estudiantes")
    Call<EstudianteResp> getDatos();

}
