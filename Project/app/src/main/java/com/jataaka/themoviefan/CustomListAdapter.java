package com.jataaka.themoviefan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.jataaka.themoviefan.model.Movie;

import java.util.List;

/**
 * Created by Martin on 16/01/2016.
 */
//public class CustomListAdapter extends ArrayAdapter<String> {
//    private final MainActivity mainActivity;
//    private final List<String> itemname;
//    private final List<String> rating;
//    private final List<Bitmap> image;
//
//
//    public CustomListAdapter(MainActivity mainActivity, List<String> itemname, List<String> rating,List<Bitmap> image) {
//        super(mainActivity, R.layout.movielist, itemname);
//        // TODO Auto-generated constructor stub
//
//        this.mainActivity = mainActivity;
//        this.itemname = itemname;
//        this.rating = rating;
//        this.image = image;
//    }
//
//    public View getView(int position, View view, ViewGroup parent) {
//        LayoutInflater inflater = mainActivity.getLayoutInflater();
//        View rowView = inflater.inflate(R.layout.movielist, null, true);
//
//        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.thumbnail);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);
//
//        txtTitle.setText(itemname.get(position));
//        imageView.setImageBitmap(image.get(position));
//        extratxt.setText("Rating: " + rating.get(position));
//        return rowView;
//    }
//}


public class CustomListAdapter extends BaseAdapter {
    private MainActivity mainActivity;
    private LayoutInflater inflater;
    private List<Movie> movieItems;
    ImageLoader imageLoader = HttpRequestQueue.getInstance(mainActivity).getImageLoader();

    public CustomListAdapter(MainActivity mainActivity, List<Movie> movieItems) {
        this.mainActivity = mainActivity;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) mainActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.movielist, null);

        if (imageLoader == null)
            imageLoader = HttpRequestQueue.getInstance(mainActivity).getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView overview = (TextView) convertView.findViewById(R.id.overview);

        // getting movie data for the row
        Movie m = movieItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());

        // rating
        rating.setText(String.valueOf(m.getRating()));
        overview.setText(m.getOverview());

        return convertView;
    }

}
