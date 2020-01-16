package com.example.hamiltonrent;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
                if (textViewItem.getText().equals(context.getResources().getString(R.string.oneBedroom))){
                    Toast.makeText(context, getGroup(i) + " : 1 bedroom clicked" , Toast.LENGTH_SHORT ).show();
                }
                else if (textViewItem.getText().equals(context.getResources().getString(R.string.twoBedroom))){
                    Toast.makeText(context, getGroup(i) + " : 2 bedrooms clicked" , Toast.LENGTH_SHORT ).show();
                }
                else if (textViewItem.getText().equals(context.getResources().getString(R.string.threeBedroom))){
                    Toast.makeText(context, getGroup(i) + " : 3 bedrooms clicked" , Toast.LENGTH_SHORT ).show();
                }
                else if (textViewItem.getText().equals(context.getResources().getString(R.string.fourBedroom))){
                    Toast.makeText(context, getGroup(i) + " : 4 bedrooms clicked" , Toast.LENGTH_SHORT ).show();
                }
                else{
                    Toast.makeText(context, getGroup(i) + " : 5 bedrooms clicked" , Toast.LENGTH_SHORT ).show();
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
