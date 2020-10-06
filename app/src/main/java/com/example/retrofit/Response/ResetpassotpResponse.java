package com.example.retrofit.Response;

import org.json.JSONObject;

public class ResetpassotpResponse {
    String msg;
Object result;

    public JSONObject getResult() {
        return (JSONObject) result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public ResetpassotpResponse(String msg, String result) {
        this.msg = msg;
        this.result = result;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
