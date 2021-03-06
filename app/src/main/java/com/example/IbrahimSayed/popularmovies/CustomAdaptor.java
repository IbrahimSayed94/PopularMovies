package com.example.IbrahimSayed.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdaptor extends BaseAdapter {

    Context context;
    ArrayList<Movie> mydata ;
    LayoutInflater inflater ;
    public CustomAdaptor (Context context, ArrayList<Movie>mydata )
    {
        this.context=context;
        this.mydata=mydata;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mydata.size();
    }

    @Override
    public Object getItem(int position) {
        return mydata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String LOG_TAG = ParseJSONstr.class.getSimpleName();
        View vi = convertView;
        if(vi==null)
            vi=inflater.inflate(R.layout.item,null);
        ImageView img=(ImageView)vi.findViewById(R.id.image);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185//"+mydata.get(position).poster_path).into(img);

        return vi;
    }
}