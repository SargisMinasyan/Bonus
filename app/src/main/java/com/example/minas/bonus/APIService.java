package com.example.minas.bonus;

import com.example.minas.bonus.client.Client;
import com.example.minas.bonus.client.ResponsePOJO;
import com.example.minas.bonus.client.Seller;
import com.example.minas.bonus.client.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
 
public interface APIService {
 
    @POST("/loginClient")
    @FormUrlEncoded
    Call<ResponsePOJO<Client>> login(@Field("username") String username,
                                     @Field("password") String password);



    @POST("/loginSeller")
    @FormUrlEncoded
    Call<ResponsePOJO<Seller>> loginSeller(@Field("username") String username,
                                           @Field("password") String password);



    @POST("/getClientInfo")
    @FormUrlEncoded
    Call<ResponsePOJO<Client>> getClientInfo(@Field("username") String username);

    @POST("/setClientNewInfo")
    @FormUrlEncoded
    Call<ResponsePOJO<Client>> setClientNewInfo(@Field("username") String username,
                                                @Field("data") String data,
                                                @Field("amount") Integer amount);

    @POST("/registration")
    @FormUrlEncoded
    Call<ResponsePOJO<Client>> registretion(@Field("username") String username,
                     @Field("password") String password,
                     @Field("email") String email,
                     @Field("phone") String phone
    );
}