package com.rayhaan.receipt_generator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<Item> {

    private static final String TAG = "CustomListAdapter";
    private Context mContext;
    private int mResource;


    public CustomListAdapter(Context context, int resource, ArrayList<Item> items) {
        super(context, resource, items);
        this.mContext = context;
        this.mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String description = getItem(position).getDescription();
        Double price = getItem(position).getPrice();

        Item item = new Item(price, description);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tvPrice = (TextView) convertView.findViewById(R.id.price);
        TextView tvDescription = (TextView) convertView.findViewById(R.id.description);

        tvPrice.setText(price.toString());
        tvDescription.setText(description);

        return convertView;
    }
}
