package com.example.mohamedanter.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailActivity extends ActionBarActivity {

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