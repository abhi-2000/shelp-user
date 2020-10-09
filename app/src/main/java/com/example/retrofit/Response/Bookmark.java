package com.example.retrofit.Response;

public class Bookmark {
String _id,_userID;

    public Bookmark(String _id, String _userID) {
        this._id = _id;
        this._userID = _userID;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_userID() {
        return _userID;
    }

    public void set_userID(String _userID) {
        this._userID = _userID;
    }
}
