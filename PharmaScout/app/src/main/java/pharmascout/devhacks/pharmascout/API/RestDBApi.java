package pharmascout.devhacks.pharmascout.API;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pharmascout.devhacks.pharmascout.model.FarmacieModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by robert.damian on 11/19/2016.
 */

public class RestDBApi {
    private Retrofit retrofit;
    private FarmaciiService farmaciiService;

    public RestDBApi () {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pharmascout-22f0.restdb.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        farmaciiService = retrofit.create(FarmaciiService.class);
    }

    public interface CallBack {
        public void onSuccess(List<FarmacieModel> listaFarmacii);
        public void onError(String message);
    }

    public void getFarmacii(String query, final CallBack callBack) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("apikey", "583096995472037d54148828");
        queryMap.put("filter", query);

        farmaciiService.getFarmacii(queryMap).enqueue(new Callback<List<FarmacieModel>>() {
            @Override
            public void onResponse(Call<List<FarmacieModel>> call, Response<List<FarmacieModel>> response) {

                Log.i(RestDBApi.class.getName(), String.valueOf(response.code()));

                callBack.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<FarmacieModel>> call, Throwable t) {

                callBack.onError(t.getMessage());
            }
        });
    }

}
