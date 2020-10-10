package com.example.retrofit.ModelClass;

public class Ratecourse {
    String rating,_id;

    public Ratecourse(String rating, String _id) {
        this.rating = rating;
        this._id = _id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
