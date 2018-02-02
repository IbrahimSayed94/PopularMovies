package com.example.IbrahimSayed.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;


public class DetailActivity extends ActionBarActivity {
    ArrayList<tailer> tailerlist=new ArrayList<tailer>();
    ArrayList<review> reviewlist=new ArrayList<review>();
    boolean temp=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {

        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.detail_item, container, false);
            Intent intent=getActivity().getIntent();
            Movie ItemSelected =intent.getParcelableExtra("Selected");
            TextView t1= (TextView)rootView.findViewById(R.id.Movie_name);
            t1.setText((CharSequence) ItemSelected.title);
            ImageView img=(ImageView)rootView.findViewById(R.id.image);
            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185//"+ItemSelected.poster_path).into(img);
            TextView t2= (TextView)rootView.findViewById(R.id.Year);
            String Time[]=ItemSelected.release_date.split("-");
            t2.setText((CharSequence) Time[0]);
            TextView t3= (TextView)rootView.findViewById(R.id.Lenth);
            t3.setText("120min");
            TextView t4= (TextView)rootView.findViewById(R.id.rate);
            ListView tailerList=(ListView)rootView.findViewById(R.id.tailer);
            ListView reviewList=(ListView)rootView.findViewById(R.id.review);

            t4.setText(ItemSelected.vote_average+"/10");

            return rootView;
        }
    }
    public class GetDataFromServer extends AsyncTask<String, Void, String> {
        private final String LOG_TAG = GetDataFromServer.class.getSimpleName();
        private String forecastJsonStr = null;
        @Override
        protected String doInBackground(String ... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            try {
                URL url = new URL(params[0]);
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
                Log.v(LOG_TAG, "JSON string" + forecastJsonStr);
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                forecastJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }
            return forecastJsonStr;
        }
        @Override
        protected void onPostExecute(String result)
        {
            if (temp)
            {
                Parse_tailer parser = new Parse_tailer();
                tailerlist = new ArrayList<tailer>();
                tailerlist = parser.ParseJSON_To_Tailer(forecastJsonStr);
                temp=false;
            }
            else
            {
                Parse_tailer parser = new Parse_tailer();
                tailerlist = new ArrayList<tailer>();
                tailerlist = parser.ParseJSON_To_Tailer(forecastJsonStr);
                temp=true;
            }
        }
    }

}




/**public static class PlaceholderFragment extends Fragment {
    public PlaceholderFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        View headerView = inflater.inflate(R.layout.detail_item, container, false);
        Intent intent=getActivity().getIntent();
        Movie ItemSelected =intent.getParcelableExtra("Selected");
        ListView d=(ListView)rootView.findViewById(R.id.detail_list_view);

        d.addHeaderView(headerView);

        TextView t1= (TextView)headerView.findViewById(R.id.Movie_name);

        t1.setText((CharSequence) ItemSelected.title);
        ImageView img=(ImageView)headerView.findViewById(R.id.image);
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185//"+ItemSelected.poster_path).into(img);

        TextView t2= (TextView)headerView.findViewById(R.id.Year);
        String Time[]=ItemSelected.release_date.split("-");
        t2.setText((CharSequence) Time[0]);

        TextView t3= (TextView)headerView.findViewById(R.id.Lenth);
        t3.setText("120min");
        TextView t4= (TextView)headerView.findViewById(R.id.rate);
        t4.setText(ItemSelected.vote_average+"/10");


        return rootView;
    }*/

/**
 public static class PlaceholderFragment extends Fragment {
 public PlaceholderFragment() {
 }
 @Override
 public View onCreateView(LayoutInflater inflater, ViewGroup container,
 Bundle savedInstanceState) {
 View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
 Intent intent=getActivity().getIntent();
 Movie ItemSelected =intent.getParcelableExtra("Selected");
 TextView t1= (TextView)rootView.findViewById(R.id.Movie_name);

 t1.setText((CharSequence) ItemSelected.title);
 ImageView img=(ImageView)rootView.findViewById(R.id.image);
 Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185//"+ItemSelected.poster_path).into(img);

 TextView t2= (TextView)rootView.findViewById(R.id.Year);
 String Time[]=ItemSelected.release_date.split("-");
 t2.setText((CharSequence) Time[0]);

 TextView t3= (TextView)rootView.findViewById(R.id.Lenth);
 t3.setText("120min");

 TextView t4= (TextView)rootView.findViewById(R.id.rate);
 t4.setText(ItemSelected.vote_average+"/10");
 return rootView;
 }
 }
 }
 */