package com.example.mohamedanter.popularmovies;

import java.util.Comparator;

/**
 * Created by Mohamed Anter on 8/29/2015.
 */
public class PopularComparator implements Comparator<Movie>
{
    @Override
    public int compare(Movie o1, Movie o2)
    {
        return o2.popularity.compareTo(o1.popularity);
    }
}