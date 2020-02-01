package com.example.hamiltonrent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1);   //i = group item, i1 = child item
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group, null);
        }
        TextView textViewHeader = view.findViewById(R.id.textViewHeader);
        textViewHeader.setTypeface(null, Typeface.BOLD);
        textViewHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String childText = (String) getChild(i, i1);
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }
        TextView textViewItem = view.findViewById(R.id.textViewItem);
        textViewItem.setText(childText);

        //if the textView is being clicked
        textViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //The sublist of Harcourts is selected
                if (getGroup(i).equals(context.getResources().getString(R.string.harcourts))) {
                    //Pass the text of textView being clicked to the new activity
                    Intent intent = new Intent(context, HarcourtsByBedroom.class);
                    intent.putExtra("numBedroom", (String) getChild(i, i1));
                    context.startActivity(intent);
                }
                //The sublist of Waikato Real Estate is selected
                else if (getGroup(i).equals(context.getResources().getString(R.string.waikatoRealEstate))){
                    //Pass the text of textView being clicked to the new activity
                    Intent intent = new Intent(context, WaikatoREByBedroom.class);
                    intent.putExtra("numBedroom", (String) getChild(i, i1));
                    context.startActivity(intent);
                }
                //The sublist of Lodge is selected
                else if (getGroup(i).equals(context.getResources().getString(R.string.lodge))){
                    //Pass the text of textView being clicked to the new activity
                    Intent intent = new Intent(context, LodgeByBedroom.class);
                    intent.putExtra("numBedroom", (String) getChild(i, i1));
                    context.startActivity(intent);
                }
                //The sublist of RayWhite is selected
                else if (getGroup(i).equals(context.getResources().getString(R.string.rayWhite))){
                    //Pass the text of textView being clicked to the new activity
                    Intent intent = new Intent(context, RayWhiteByBedroom.class);
                    intent.putExtra("numBedroom", (String) getChild(i, i1));
                    context.startActivity(intent);
                }
                //The sublist of EVES is selected
                else if (getGroup(i).equals(context.getResources().getString(R.string.eves))) {
                    //Pass the text of textView being clicked to the new activity
                    Intent intent = new Intent(context, EvesByBedroom.class);
                    intent.putExtra("numBedroom", (String) getChild(i, i1));
                    context.startActivity(intent);
                }

            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

}
