package com.example.minas.bonus;

public class ApiUtils {
 
    private ApiUtils() {

    }
    public static final String BASE_URL = "http://192.168.0.103:8080";
 
    public static APIService getAPIService() {
 
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}