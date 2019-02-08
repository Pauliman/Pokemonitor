package com.workman.pokemonitor;


import android.util.Log;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class SingleTask implements Runnable {

    private static final String TAG = "SingleTask";
    private String baseUrl;
    private String endpoint;
    private String response_string;

    public String getResult(){
        return this.response_string;
    }

    public SingleTask(String url, int num){
        this.baseUrl = url;
        this.endpoint = String.valueOf(num);
    }

    @Override
    public void run() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl+endpoint)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            response_string = response.body().string();
        } catch (Exception e) {
            Log.d(TAG, "Text: " + e);
        }
        Log.d(TAG, "run: Finished fetching data for chain #" +endpoint);
    } // end of run()
} // end of class
