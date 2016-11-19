package pharmascout.devhacks.pharmascout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //comment pus de Nelu
    }

    class Point2f{
        public double x;
        public double y;
    }

    public String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }
    //https://maps.googleapis.com/maps/api/directions/json?origin=41.43206,-81.38992?&destination=44.419144, 26.081745&key=AIzaSyAA4P_7CK7s_bk0U-MsvjrYiPuJDjoLhHg
    public String getDistanceFromAsString() throws InterruptedException, ExecutionException
    {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call(){
                String stringUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+
                        "44.468447"+","+"26.14348300000006"+
                        "&destinations="+
                        "44.419144"+","+"26.081745"+
                        "&mode=driving&key=AIzaSyAzceIDmZrbXpfbctDo_ZSuwV47f4B-gCY";

                InputStream is = null;
                int length = 500;
                try {
                    try {
                        URL url = new URL(stringUrl);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(10000 /* milliseconds */);
                        conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setDoInput(true);
                        conn.connect();
                        int response = conn.getResponseCode();

                        is = conn.getInputStream();
                        String contentAsString = convertInputStreamToString(is, length);
                        return contentAsString;
                    } catch (MalformedURLException MEx) {
                        return MEx.toString() + " EX 1";
                    } catch (IOException IOEx) {
                        return IOEx.toString() + "EX 2";
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                    }
                }
                catch (IOException IOEx) {
                    return IOEx.toString() + "EX 3";
                }
            }
        };
        Future<String> future = executor.submit(callable);
        // future.get() returns 2 or raises an exception if the thread dies, so safer
        executor.shutdown();

        return future.get();
    }

    int number = 0;
    public void checkNet(View obj){
        TextView test = (TextView) findViewById(R.id.textView);
        test.setText("as expected " + number++ );

        try {
            test.setText(getDistanceFromAsString());
        }
        catch (InterruptedException ie){

        }
        catch (ExecutionException ee){

        }
    }

    public void mapStartButton(View obj){
        Bundle coords = new Bundle();
        Random rand = new Random();
        coords.putDouble("latitude", 90 * rand.nextDouble());
        coords.putDouble("longitude", 90 * rand.nextDouble());

        Intent intent = new Intent(SearchActivity.this, MapsActivity.class );
        intent.putExtras(coords);
        startActivity(intent);
    }

    public void searchButton(View view) {
        EditText searchText = (EditText) findViewById(R.id.searchText);

        String text = searchText.getText().toString();
        Toast msg = Toast.makeText(getBaseContext(),text,Toast.LENGTH_LONG);
        msg.show();
    }
}
