package pharmascout.devhacks.pharmascout.singletons;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import pharmascout.devhacks.pharmascout.model.FarmacieModel;

/**
 * Created by robert.damian on 11/20/2016.
 */

public class DataHandler {

    private static DataHandler dataHandler;

    private static final String ROWS = "rows";
    private static final String ELEMENTS = "elements";
    private static final String DISTANCE = "distance";
    private static final String VALUE = "value";

    protected DataHandler() {

    }

    public static DataHandler getInstance() {
        if (dataHandler == null) {
            dataHandler = new DataHandler();
        }

        return dataHandler;
    }

    public List<FarmacieModel> filterOutFarmaciiInchise(List<FarmacieModel> farmaciiGasite) {
        Calendar now = Calendar.getInstance();
        Calendar oraInchidere = Calendar.getInstance();
        Calendar oraDeschidere = Calendar.getInstance();

        List<FarmacieModel> result = new ArrayList<>();

        for (FarmacieModel farmacie : farmaciiGasite) {
            oraInchidere.set(Calendar.HOUR_OF_DAY, getOraInchidere(farmacie.getProgram()));
            oraInchidere.set(Calendar.MINUTE, getMinutInchidere(farmacie.getProgram()));

            oraDeschidere.set(Calendar.HOUR_OF_DAY, getOraDeschidere(farmacie.getProgram()));
            oraDeschidere.set(Calendar.MINUTE, getMinutDeschidere(farmacie.getProgram()));

            if (now.after(oraDeschidere) && now.before(oraInchidere)) {
                result.add(farmacie);
            }

        }

        return result;
    }

    private int getOraDeschidere(String program) {
        return Integer.valueOf(program.split(":")[0]);
    }

    private int getMinutDeschidere(String program) {
        return Integer.valueOf(program.split("-")[0].split(":")[1]);
    }

    private int getOraInchidere(String program) {
        return Integer.valueOf(program.split("-")[1].split(":")[0]);
    }

    private int getMinutInchidere(String program) {
        return Integer.valueOf(program.split(":")[2]);
    }


    //https://maps.googleapis.com/maps/api/directions/json?origin=41.43206,-81.38992?&destination=44.419144, 26.081745&key=AIzaSyAA4P_7CK7s_bk0U-MsvjrYiPuJDjoLhHg
    public String getDistanceFromAsString(final double longitudine, final double latitudine,
                                          final double myLongitudine, final double myLatitudine) throws InterruptedException,
                            ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call(){
                String stringUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+
                        myLatitudine+","+myLongitudine+
                        "&destinations="+
                        latitudine+","+longitudine+
                        "&mode=driving&key=AIzaSyAzceIDmZrbXpfbctDo_ZSuwV47f4B-gCY";

                InputStream is = null;
                int length = 9000;
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


                        StringWriter writer = new StringWriter();
                        IOUtils.copy(is, writer, "UTF-8");
                        String result = writer.toString();
                        return result;
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
    private String convertInputStreamToString(InputStream stream, int length) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[length];
        reader.read(buffer);
        return new String(buffer);
    }

    public int getDistanceTo ( double longitudine, double latitudine, double myLongitudine, double myLatitudine) {
        JsonElement jsonElement = null;
        try {
            String jsonAsString = getDistanceFromAsString(longitudine, latitudine, myLongitudine, myLatitudine);
            JSONObject jsonObject = new JSONObject(jsonAsString);

            return getDistanceFromObject(jsonObject);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  0;//Integer.valueOf(resultAsString);
    }


    private int getDistanceFromObject(JSONObject jsonObject) throws JSONException {

        Iterator<String> keysIterator = jsonObject.keys();
        String currentKey;
        int result;
        JSONArray jsonArray;
        while (keysIterator.hasNext()) {
            currentKey = keysIterator.next();
            if (jsonObject.get(currentKey) instanceof JSONObject) {

                if (currentKey.equals(DISTANCE)) {
                    return Integer.valueOf(jsonObject.getJSONObject(currentKey).getString(VALUE));
                }

                result = getDistanceFromObject(jsonObject.getJSONObject(currentKey));

                if (result != 0) {
                    return result;
                }
            }
            else if (jsonObject.get(currentKey) instanceof JSONArray) {
                jsonArray = jsonObject.getJSONArray(currentKey);

                result = arrayTraversal(jsonArray);

                if (result != 0) {
                    return result;
                }
            }

        }

        return 0;
    }

    private int arrayTraversal(JSONArray jsonArray) throws JSONException{
        int result;

        for (int i = 0; i < jsonArray.length(); i++) {
            if (jsonArray.get(i) instanceof JSONObject) {
                result = getDistanceFromObject(jsonArray.getJSONObject(i));

                if (result != 0) {
                    return result;
                }
            }
            else if (jsonArray.get(i) instanceof JSONArray) {
                result = arrayTraversal(jsonArray.getJSONArray(i));

                if (result != 0) {
                    return result;
                }
            }
        }

        return 0;
    }

}
