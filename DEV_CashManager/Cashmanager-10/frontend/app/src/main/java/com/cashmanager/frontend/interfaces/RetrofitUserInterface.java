package com.cashmanager.frontend.interfaces;

import com.cashmanager.frontend.entites.Assurance;
import com.cashmanager.frontend.entites.LoggedUser;
import com.cashmanager.frontend.entites.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface RetrofitUserInterface {

    @GET("api/auth/users")
    Call<List<User>> getUsers();

    @FormUrlEncoded
    @POST("/api/users/signup")
    Call<User> createUser(
            @Field("lastName") String lastName,
            @Field("firstName") String firstName,
            @Field("email") String email,
            @Field("password") String password,
            @Field("street") String street,
            @Field("city") String city,
            @Field("zipCode") String zipCode,
            @Field("phoneNumber") String phoneNumber

    );

    @FormUrlEncoded
    @POST("/api/signin")
    Call<Void> logUser(
            @Field("username") String username,
            @Field("password") String password

    );
}
