package com.cashmanager.frontend.interfaces;

import com.cashmanager.frontend.entites.Assurance;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AssuranceInterface {
    @GET("/api/assurance")
    Call<List<Assurance>> geAssurances();

    @POST("/api/assurance")
    Call<Assurance> createPost(@Body Assurance assurance);
}
