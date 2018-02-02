package com.example.IbrahimSayed.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ibrahim on 10/7/2015.
 */
public class tailer implements Parcelable
{
    String id;
    String name;
    String site;
    String size;
    public  tailer(Parcel in)
    {
        String tailer_data[]=new String[4];
        in.readStringArray(tailer_data);
        this.id=tailer_data[0];
        this.name=tailer_data[1];
        this.site=tailer_data[2];
        this.size=tailer_data[3];


    }

    public tailer() {

    }

    public  void writetoparcel(Parcel dest,int flags)
    {
        dest.writeStringArray(new String[]{this.id,this.name,this.site,
                this.size});
    }

    public tailer(String id,String name,String site,String size)
    {

        this.id=id;
        this.name=name;
        this.site=site;
        this.size=size;

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<tailer> CREATOR= new Creator<tailer>() {

        @Override
        public tailer createFromParcel(Parcel source) {
            return new tailer(source);  //using parcelable constructor
        }
        @Override
        public tailer[] newArray(int size) {
            return new tailer[size];
        }
    };
}
