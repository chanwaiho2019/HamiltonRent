package com.example.hamiltonrent;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PropertyListViewAdapter extends ArrayAdapter<Property> {

    public PropertyListViewAdapter(Context context, List<Property> properties) {
        super(context, -1, properties);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        if (view == null) {
            view = View.inflate(getContext(), R.layout.property_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.setView(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.agent.setText(getItem(position).getAgent());
        Picasso.get().load(getItem(position).getImageURL()).resize(600, 400).into(viewHolder.image);
        viewHolder.title.setText(getItem(position).getTitle());
        viewHolder.address.setText(getItem(position).getAddress());
        viewHolder.rent.setText("$" + getItem(position).getRent() + " p.w.");
        viewHolder.bedroom.setText(Integer.toString(getItem(position).getNumBedroom()));
        viewHolder.bathroom.setText(Integer.toString(getItem(position).getNumBathroom()));
        viewHolder.carSpace.setText(Integer.toString(getItem(position).getNumCarSpace()));
        viewHolder.link.setText(getItem(position).getlink());

        return view;
    }

    private class ViewHolder {

        public TextView agent;
        public ImageView image;
        public TextView title;
        public TextView address;
        public TextView rent;
        public TextView bedroom;
        public TextView bathroom;
        public TextView carSpace;
        public TextView link;

        public void setView(View view) {
            agent = view.findViewById(R.id.textViewAgent);
            image = view.findViewById(R.id.imageViewProperty);
            title = view.findViewById(R.id.textViewTitle);
            address = view.findViewById(R.id.textViewAddress);
            rent = view.findViewById(R.id.textViewRent);
            bedroom = view.findViewById(R.id.textViewBedroom);
            bathroom = view.findViewById(R.id.textViewBathroom);
            carSpace = view.findViewById(R.id.textViewCarSpace);
            link = view.findViewById(R.id.textViewLink);
            //Set the link inside the TextView clickable and open it in browser
            link.setMovementMethod(LinkMovementMethod.getInstance());
            link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                    browserIntent.setData(Uri.parse("http://www.google.com"));
                    getContext().startActivity(browserIntent);
                }
            });
            view.setTag(this);
        }

    }
}
