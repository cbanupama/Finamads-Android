package com.finamads.finamads.utilities;




import com.finamads.finamads.model.AddVehicleInput;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    /*
    Retrofit get annotation with our URL
    And our method that will return us the List of ContactList
    */


/* @Headers({
            "Authorization: " + token,
            "Accept: application/json"
    })*/
   @POST("/api/vehicles")
    Call<AddVehicleInput> uploadPostFiles(@HeaderMap Map<String, String> token, @Body RequestBody form);

    @PUT("/api/vehicles/{id}")
    Call<AddVehicleInput> uploadPutFiles(@HeaderMap Map<String, String> token, @Body RequestBody form,@Path("id") int vId);


}
