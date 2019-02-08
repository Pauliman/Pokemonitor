package com.workman.pokemonitor;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AsyncSorter extends AsyncTask<ConcurrentLinkedQueue<SingleTask>, String, String> {

    private ConcurrentLinkedQueue<SingleTask> queue;

    private MainActivity main;

    public AsyncSorter(Context var) {
        this.main = (MainActivity) var;
    } //end of constructor

    /**
     * Iterates over a queue of SingleTask objects that contain a JSON string each.
     * It sets up a recursive search task for every object.
     * @param concurrentLinkedQueues The queue containing the objects containing the JSON string
     * @return A message to be passed on to the onPostExecute()-method.
     */
    @Override
    protected String doInBackground(ConcurrentLinkedQueue<SingleTask>... concurrentLinkedQueues) {
        queue = concurrentLinkedQueues[0];
        SingleTask temp = null;
        Iterator<SingleTask> iter = queue.iterator();
        while (iter.hasNext()) {
            temp = iter.next();
            new SorterTask(temp.getResult(), this).run();
        }
        return "Finished sorting.";
    } // end of doInBackground()

    /**
     * Writes the status back to the Application view elements.
     * @param values Current status
     */
    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        main.writeToView(values[0] + " of " + queue.size() + "JSON-strings processed.");
    } // end of onProgressUpdate()

    /**
     * Writes a message to the Application view element and calles a method on the UI-thread to continue
     * the app process.
     * @param s The message from doInBackground()
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        main.writeToView(s);
        main.receiveSortedChains();
    } // end of onPostExecute()
}// end of class
