package com.example.hamiltonrent;


import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * A class to use as an adapter to set the strings of a string array in the spinner.
 * It will use the last item of string array as the hint of spinner while hiding it
 * in the dropdown menu.
 */
public class HintAdapter extends ArrayAdapter<String> {

    public HintAdapter(Context context, int layoutResId, String[] strings) {
        super(context, layoutResId, strings);
    }

    @Override
    public int getCount() {
        // Do not display last item. It is used as hint.
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}