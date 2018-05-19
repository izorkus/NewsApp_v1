package com.example.android.newsapp_v1;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import android.graphics.drawable.GradientDrawable;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    // Override getView to populate new list.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get position of actual news item
        News currentNews = getItem(position);

        //Put section name and title into list item.
        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.section_view);
        TextView titleTextView = (TextView) listItemView.findViewById(R.id.title_view);

        sectionTextView.setText(currentNews.getSectionName());
        titleTextView.setText(currentNews.getWebTitle());

        TextView authorTextView = (TextView) listItemView.findViewById(R.id.author_view);
        authorTextView.setText(currentNews.getAuthor());

        // Deal with data format
        String rawDate = currentNews.getRawDataStr();
        String hmString;
        String dateString;
        int indexOf;

        if (rawDate.contains("T")) {
            indexOf = rawDate.indexOf("T");
            dateString = rawDate.substring(0, indexOf);
            hmString = rawDate.substring(indexOf + 1, rawDate.length() - 4);
        } else {
            dateString = "N/A";
            hmString = "N/A";
        }

        //Split data and time
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_view);
        dateTextView.setText(dateString);
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_view);
        timeTextView.setText(hmString);

        //Return complete item.
        return listItemView;
    }
}
