package com.workman.pokemonitor;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncRetriever extends AsyncTask<String,String,String> {

    private int number_endpoints;

    private static final String TAG = "AsyncRetriever";

    private ConcurrentLinkedQueue<SingleTask> queue;

    private String baseUrl;

    private MainActivity main;


    public AsyncRetriever(int num, Context con){
        this.number_endpoints = num;
        this.main = (MainActivity) con;
        this.queue = new ConcurrentLinkedQueue<>();
    }

    @Override
    protected String doInBackground(String... strings) {
        baseUrl = strings[0];
        // ++++++ fill the queue with tasks +++++++
        for(int i = 1; i <= number_endpoints; i++){
            Log.d(TAG, "doInBackground: Filling queue...");
            queue.add(new SingleTask(baseUrl,i));
        }
        // ++++++++ do the work +++++++++++++++++++
        Iterator iter = queue.iterator();
        int counter = 0;
        while(iter.hasNext()){
            counter++;
            ((SingleTask)iter.next()).run();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            publishProgress(String.valueOf(counter)+" of "+number_endpoints + " Species downloaded.");
        }
        return "Finished retrieving data.";
    } //end of doInBackground()

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        main.writeToView(values[0]);
    } // end of onProgressUpdate()

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        main.writeToView(s);
        main.receiveDataSet(queue);
    } // end of onPOstExecute()
} //end of class
