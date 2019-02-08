package com.workman.pokemonitor;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;

/**
 * This is a static class that may be accessed from many different threads to deliver and retrieve data.
 */
public class SpeciesCollector {

    private static Object obj = new Object();

    private volatile static TreeMap<Integer, ArrayList<String>> chain_tree = new TreeMap<>();

    public static TreeMap<Integer, ArrayList<String>> getChain_tree(){
        return chain_tree;
    }

    /**
     * This method receives the results of the sorter tasks and collects them in a tree map
     * for later evaluation
     * @param list ArrayList containing the names of Pokemons of one evolution chain.
     */
    public static void fillChainTree(ArrayList<String> list){
        synchronized(obj) {
            Integer x = new Integer(list.get(0));
            list.remove(0);
            chain_tree.put(x, list);
        }
    } //end of fillChainTree()

    // This is just a temporary method and will be deleted soon.
    public static String getListOfPokemons(){
        String text = "";
        ArrayList<String> temp = null;
        int max_index = 0;
        Set<Integer> set = chain_tree.keySet();
        for(Integer i : set){
            temp = chain_tree.get(i);
            max_index = temp.size()-1;
            for(int j = max_index; j >=0 ; j--){
                text += "Pokemon #"+((max_index - j)+1) +" "+ temp.get(j);
            }
        }
        return text;
    } // end of getLIstOfPokemons()

    public static ArrayList<String> reverseOrder(ArrayList<String> var){
        ArrayList<String> temp = new ArrayList<>();
        if(var.size()==0) {
            temp.add("Does not evolve ");
            return temp;
        }
        for(int i = var.size()-1; i >= 0; i--){
            temp.add(var.get(i));
        }
        return temp;
    }

} //end of class
