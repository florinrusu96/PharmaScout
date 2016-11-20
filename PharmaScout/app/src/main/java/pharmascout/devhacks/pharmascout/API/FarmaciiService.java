package pharmascout.devhacks.pharmascout.API;

import java.util.List;
import java.util.Map;

import pharmascout.devhacks.pharmascout.model.FarmacieModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by robert.damian on 11/19/2016.
 */

public interface FarmaciiService {

    @GET("/rest/farmacii")
    Call<List<FarmacieModel>> getFarmacii(@QueryMap Map<String, String> query);
}
