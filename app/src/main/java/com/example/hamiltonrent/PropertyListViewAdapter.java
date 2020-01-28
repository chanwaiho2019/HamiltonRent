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

        Picasso.get().load(getItem(position).getImageURL()).resize(600, 500).into(viewHolder.image);
        viewHolder.title.setText(getItem(position).getTitle());
        viewHolder.address.setText(getItem(position).getAddress());
        viewHolder.rent.setText("$" + getItem(position).getRent() + " p.w.");
        viewHolder.rooms.setText("Bedrooms: " + getItem(position).getNumBedroom()
                + "     Bathrooms: " + getItem(position).getNumBathroom()
                + "     Car space: " + getItem(position).getNumCarSpace());
        viewHolder.link.setText(getItem(position).getlink());

        return view;
    }

    private class ViewHolder {

        public ImageView image;
        public TextView title;
        public TextView address;
        public TextView rent;
        public TextView rooms;
        public TextView link;

        public void setView(View view) {
            image = view.findViewById(R.id.imageViewProperty);
            title = view.findViewById(R.id.textViewTitle);
            address = view.findViewById(R.id.textViewAddress);
            rent = view.findViewById(R.id.textViewRent);
            rooms = view.findViewById(R.id.textViewRooms);
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
