package org.rb.qaandro.resfulclient;

import org.rb.qa.model.KNBase;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface IKNBaseService {
    @GET("id")
    Call<String> getIT();

    @GET("knb")
    Call<KNBase> getKNBase(@Query("file") String file);

    @GET("date")
    Call<Long> getDateTimeLong(@Query("file") String file);

    @POST("knb")
    Call<Void> postKNBase(@Body KNBase knb, @Query("file") String file);

}
