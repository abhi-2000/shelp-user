package com.example.retrofit.ModelClass;

import android.widget.RatingBar;
import android.widget.Toast;

public class firstmodelclass {
    String imageurl, title, tutor;
    float rating;

    public firstmodelclass(String imageurl, String title, String tutor, float rating) {
        this.imageurl = imageurl;
        this.title = title;
        this.tutor = tutor;
        this.rating = rating;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}