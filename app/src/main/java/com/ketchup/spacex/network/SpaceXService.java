package com.ketchup.spacex.network;

import com.ketchup.spacex.data.Launch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpaceXService {

    @GET("launches")
    Call<List<Launch>> getLaunches(@Query("offset") int page, @Query("limit") int size);

    @GET("launches/{flight_number}")
    Call<Launch> getLaunch(@Path("flight_number") int flightNumber);

}
