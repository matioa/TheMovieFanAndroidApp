package com.jataaka.themoviefan;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Martin on 16/01/2016.
 */
public class CustomListAdapter extends ArrayAdapter<String> {
    private final MainActivity mainActivity;
    private final String[] itemname;
    private final Integer[] imgid;

    public CustomListAdapter(MainActivity mainActivity, String[] itemname, Integer[] imgid) {
        super(mainActivity, R.layout.movielist, itemname);
        // TODO Auto-generated constructor stub

        this.mainActivity = mainActivity;
        this.itemname = itemname;
        this.imgid = imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.movielist, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);
        extratxt.setText("Description " + itemname[position]);
        return rowView;
    }
}
