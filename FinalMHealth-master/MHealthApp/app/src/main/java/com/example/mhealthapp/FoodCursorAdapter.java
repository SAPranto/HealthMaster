package com.example.mhealthapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class FoodCursorAdapter extends CursorAdapter {
    public FoodCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_list_food_item, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView textViewListName = (TextView) view.findViewById(R.id.textViewListName);
        TextView textViewListNumber = (TextView) view.findViewById(R.id.textViewListNumber);
        TextView textViewListSubName = (TextView) view.findViewById(R.id.textViewListSubName);

        // Extract properties from cursor
        int getID = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        String getName = cursor.getString(cursor.getColumnIndexOrThrow("food_title"));
        String getcal = cursor.getString(cursor.getColumnIndexOrThrow("food_cal"));
        String getDescription = cursor.getString(cursor.getColumnIndexOrThrow("food_text"));

        String subLine = getDescription;

        // Populate fields with extracted properties
        textViewListName.setText(getName);
        textViewListNumber.setText(String.valueOf(getcal));
        textViewListSubName.setText(subLine);

    }

}
