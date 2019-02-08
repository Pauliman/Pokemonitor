package com.workman.pokemonitor;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ListAdapter extends BaseExpandableListAdapter {

    private Context con;
    private List<String> listHeader;
    private HashMap<String, List<String>> listChildren;

    public ListAdapter(Context var_1, List<String> var_2, HashMap<String,List<String>> var_3){
        this.con = var_1;
        this.listHeader = var_2;
        this.listChildren = var_3;
    }

    @Override
    public int getGroupCount() {
        return this.listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listChildren.get(this.listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listChildren.get(this.listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infl = (LayoutInflater) this.con
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infl.inflate(R.layout.activity_list_group, null);
        }
        TextView header = (TextView) convertView
            .findViewById(R.id.list_group_tv);
        header.setTypeface(null, Typeface.BOLD);
        header.setText(headerTitle);
        return convertView;
    }// end of getGroupView()

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            final String childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infl = (LayoutInflater) this.con
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infl.inflate(R.layout.activity_list_child, null);
            }
            TextView child = (TextView) convertView
                    .findViewById(R.id.list_child_tv);
            child.setText(childText);
            return convertView;
    }//end of getChildView()

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}// end of class
