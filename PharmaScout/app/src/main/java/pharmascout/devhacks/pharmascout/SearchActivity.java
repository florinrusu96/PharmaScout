package pharmascout.devhacks.pharmascout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import pharmascout.devhacks.pharmascout.API.RestDBApi;
import pharmascout.devhacks.pharmascout.model.FarmacieModel;
import pharmascout.devhacks.pharmascout.singletons.DataHandler;

public class SearchActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    public static final int MAX_DISPLAY_NUMBER = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        progressDialog = new ProgressDialog(this);
        //comment pus de Nelu

        ListView listView = (ListView) findViewById(R.id.listView );

        ArrayList<String> listItems=new ArrayList<String>();

        listItems.add("Clicked : "+ 1);
        listItems.add("Clicked : "+ 12);
        listItems.add("Clicked : "+ 3);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                                    android.R.layout.simple_list_item_1,
                                    listItems);

        listView.setAdapter(adapter);

        adapter.add("New Item");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle coords = new Bundle();
                Random rand = new Random();
                coords.putDouble("latitude", 90 * rand.nextDouble());
                coords.putDouble("longitude", 90 * rand.nextDouble());

                Intent intent = new Intent(SearchActivity.this, MapsActivity.class );
                intent.putExtras(coords);
                startActivity(intent);
            }
        });
    }


//    int number = 0;
//    public void checkNet(View obj){
//        TextView test = (TextView) findViewById(R.id.textView);
//        test.setText("as expected " + number++ );
//
//        try {
//            test.setText(getDistanceFromAsString());
//        }
//        catch (InterruptedException ie){
//
//        }
//        catch (ExecutionException ee){
//
//        }
//    }

    public void searchButton(View view) {
        EditText searchText = (EditText) findViewById(R.id.searchText);

        String text = searchText.getText().toString();
        Toast msg = Toast.makeText(getBaseContext(), text, Toast.LENGTH_LONG);
        msg.show();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            search(text);
        } else {
            Toast.makeText(this, "Nu ai net boss!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setLoading(boolean loading) {
        if (progressDialog != null) {
            if (loading) {
                progressDialog.setMessage("Se executa cautarea");
                progressDialog.setCancelable(false);
                progressDialog.show();
            } else {
                progressDialog.dismiss();
            }
        }
    }

    private void search(String query) {
        setLoading(true);
        (new RestDBApi()).getFarmacii(query, new RestDBApi.CallBack(){
            @Override
            public void onSuccess(List<FarmacieModel> listaFarmacii) {
                setLoading(false);
                FarmacieModel primaFarmacie;
                Toast.makeText(SearchActivity.this, "Boss! Chiar am luat ceva", Toast.LENGTH_SHORT).show();
                listaFarmacii = DataHandler.getInstance().filterOutFarmaciiInchise(listaFarmacii);
                if (listaFarmacii != null && !listaFarmacii.isEmpty()) {

                    if (listaFarmacii.size() > MAX_DISPLAY_NUMBER) {
                        handleTooManyResults(listaFarmacii);
                    }

                }
            }

            @Override
            public void onError(String message) {
                setLoading(false);
                Toast.makeText(SearchActivity.this, "Mare eroare ce ai luat", Toast.LENGTH_SHORT).show();
                Log.e(SearchActivity.class.getName(), message);
            }
        });
    }

    private void handleTooManyResults(List<FarmacieModel> listaFarmacii) {
        HashMap<FarmacieModel, Integer> farmacieToDistanceMap = new HashMap<>();

        for (FarmacieModel farmacie : listaFarmacii) {
            farmacieToDistanceMap.put(farmacie, DataHandler.getInstance().getDistanceTo(farmacie.getLongitudine(),
                    farmacie.getLatitudine()));
        }
    }
}

