package com.workman.pokemonitor;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This task sorts out all the available names of Pokemons from a chain of evolution.
 * It iterates over the JSON object to and builds a short ArrayList.
 */
public class SorterTask implements Runnable {
    private static final String TAG = "SorterTask";
    private static final String TARGET = "name";
    private AsyncSorter ast;
    private String json;
    private ArrayList<String> species;
    private JSONObject JObj;


    public SorterTask(String var_1, AsyncSorter var_2){
        this.ast = var_2;
        this.json = var_1;
        this.species = new ArrayList<>();
        try {
            this.JObj = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    } // end of constructor()

    /**
     * This thread takes the JSON object rextracts the evolution_chain_id and puts it as the
     * first value of the ArrayList for later identification. Then it starts the recursive
     * extraction of the available Pokemon names in the JSON object.
     */
    @Override
    public void run() {
        try {
            String id = JObj.getString("id");
            Log.d(TAG, "run: Sorting JSON string #"+id);
            species.add(id);
            findNames(JObj, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SpeciesCollector.fillChainTree(species);

    }

    /**
     * Recursive method that iterates through the entire JSON object and finds
     * all the 'name' keys in all 'species' objects.
     * @param obj  Either Array or Object
     * @param isSpecies Flag indicating that the search is inside a 'species' object.
     * @throws JSONException
     */
    private void findNames(Object obj, boolean isSpecies) throws JSONException {
        String temp = null;
        // +++++++++++++ Checking all JSON objects ++++++++++++
        if (obj instanceof JSONObject) {
            JSONObject o = (JSONObject) obj;
            if(o.length() < 1) return;
            Iterator<String> iter = o.keys();
            while (iter.hasNext()) {
                temp = iter.next();
                if (o.get(temp) instanceof JSONObject){
                    if(((String)temp).equals("species")){
                        findNames(o.get(temp), true);
                    } else{
                        findNames(o.get(temp), false);
                    }
                }
                if (o.get(temp) instanceof JSONArray)
                    findNames(o.get(temp), false);
                if (o.get(temp) instanceof String && isSpecies && ((String)temp).equals(TARGET))
                    species.add((String) o.get(temp));
            }
        }
        // ++++++++++ Iterating over all JSON arrays +++++++++++
        if (obj instanceof JSONArray) {                         // Since the content of array does not contain the target, no search is conducted here.
            JSONArray a = (JSONArray) obj;
            if (a.length() < 1) return;
            for (int i = 0; i < a.length(); i++) {
                if (a.get(i) instanceof JSONObject || a.get(i) instanceof JSONArray)
                    findNames(a.get(i), false);         // non-collection types are ignored and only arrays and objects are passed on for further iteration.
            }
        }
    } // end of findNames()
}// end of class
