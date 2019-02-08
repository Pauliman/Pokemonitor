package com.workman.pokemonitor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class ListActivity extends AppCompatActivity {

    ExpandableListAdapter adapter;
    ExpandableListView expListView;
    List<String> headers;
    HashMap<String, List<String>> children;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);
        init();
    }

    private void init(){
        expListView = this.findViewById(R.id.list_main_elv);
        headers = new ArrayList<>();
        children = new HashMap<>();
        adapter = new ListAdapter(this, headers,children);
        prepareListData();
        expListView.setAdapter(adapter);

    } // end of init()

    private void prepareListData(){
        TreeMap<Integer, ArrayList<String>> tree_chain = SpeciesCollector.getChain_tree();
       ArrayList<String> temp = null;
       int index_origin = 0;
       String name_origin ="";
        //+++++++++++++++ fill the 'headers' & 'children' list ++++++++++++++++
        Set<Integer> set = tree_chain.keySet();
        for(Integer i : set){
            temp = tree_chain.get(i);
            index_origin = temp.size()-1;
            name_origin = temp.get(index_origin);
            temp.remove(index_origin);
            temp = SpeciesCollector.reverseOrder(temp);
            headers.add(name_origin);
            children.put(name_origin,temp);
        }
    }// end of prepareListData()

    @Override
    public void onBackPressed(){
        setResult(RESULT_CANCELED);
        this.finish();
    }

} // end of class
