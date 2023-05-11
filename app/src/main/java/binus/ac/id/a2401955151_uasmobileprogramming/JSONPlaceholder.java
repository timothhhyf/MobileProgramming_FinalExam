package binus.ac.id.a2401955151_uasmobileprogramming;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface JSONPlaceholder {
    @GET("api/fruit/all")
    Call<List<Data>> getData();
}
