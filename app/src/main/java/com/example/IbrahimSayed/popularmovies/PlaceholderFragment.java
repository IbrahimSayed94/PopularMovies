package com.example.IbrahimSayed.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;


public class PlaceholderFragment extends Fragment {
    CustomAdaptor myadp;
    private ArrayList<Movie> MovieList;
    private GridView gridView;
    public PlaceholderFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        myadp = new CustomAdaptor(getActivity(), new ArrayList<Movie>());
        gridView = (GridView) rootView.findViewById(R.id.gridview);
        gridView.setAdapter(myadp);
        MovieList = new ArrayList<Movie>();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie SelectedItem= (Movie)myadp.getItem(position);

                Intent Detail_Activity=new Intent(getActivity(),DetailActivity.class).putExtra("Selected",SelectedItem);
                startActivity(Detail_Activity);
            }
        });
        return rootView;
    }

    private void updateMovie() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = prefs.getString(getString(R.string.pref_selection_key),
                getString(R.string.pref_selection_default));
        String URL_STR = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=e5b018c8aaed0519202781c4ac9510a8";
        GetDataFromServer Task = new GetDataFromServer();
        Task.execute(URL_STR, sort);
    }
    @Override
    public void onStart() {
        super.onStart();
        updateMovie();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);


    }
    public class GetDataFromServer extends AsyncTask<String, Void, String> {
        private final String LOG_TAG = GetDataFromServer.class.getSimpleName();
        String Sort_Method;
        private String forecastJsonStr = null;

        public String GetStr() {
            return forecastJsonStr;
        }
        @Override
        protected String doInBackground(String ... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            try {
                URL url = new URL(params[0]);
                Sort_Method = params[1];
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
            ParseJSONstr parser = new ParseJSONstr();
            MovieList = new ArrayList<Movie>();
            MovieList = parser.ParseJSON_To_TweetObject(forecastJsonStr);
            SortMovies(Sort_Method, MovieList);
            myadp = new CustomAdaptor(getActivity(), MovieList);
            gridView.setAdapter(myadp);
        }

        private ArrayList<Movie> SortMovies(String Sort, ArrayList<Movie> Arr) {
            if (Sort.equals(getString(R.string.pref_selection_highest))) {
                Collections.sort(Arr, new HighestComparator());
            } else if (Sort.equals(getString(R.string.pref_selection_popular))) {
                Collections.sort(Arr, new PopularComparator());
            } else {
                Log.d(LOG_TAG, "Sort type not found: " + Sort);
            }
            return Arr;
        }
    }
}