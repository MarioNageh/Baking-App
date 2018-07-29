package com.marionageh.bakingapp.Retrofit;

import com.marionageh.bakingapp.Module.Foods;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitApiInterface {
    @GET("baking.json")
    Call<List<Foods>> getAllRecipes();
}
