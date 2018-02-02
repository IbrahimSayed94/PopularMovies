package com.example.IbrahimSayed.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ibrahim on 10/7/2015.
 */
public class review implements Parcelable
{
    String id;
    String author;
    String content;

    public  review(Parcel in)
    {
        String review_data[]=new String[3];
        in.readStringArray(review_data);
        this.id=review_data[0];
        this.author=review_data[1];
        this.content=review_data[2];



    }

    public  void writetoparcel(Parcel dest,int flags)
    {
        dest.writeStringArray(new String[]{this.id,this.author,this.content,
                });
    }

    public review(String id, String name, String site)
    {

        this.id=id;
        this.author=name;
        this.content=site;


    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<review> CREATOR= new Creator<review>() {

        @Override
        public review createFromParcel(Parcel source) {
            return new review(source);  //using parcelable constructor
        }
        @Override
        public review[] newArray(int size) {
            return new review[size];
        }
    };
}
