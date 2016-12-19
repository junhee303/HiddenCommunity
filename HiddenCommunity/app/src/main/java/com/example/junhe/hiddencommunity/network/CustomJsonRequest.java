package com.example.junhe.hiddencommunity.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomJsonRequest extends JsonObjectRequest {
    public CustomJsonRequest(int method, String url, JSONObject jsonRequest,
                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    /**
     * Request의 요청 결과를 받는 콜백 함수
     * 결과데이터에 대해서  Handling이 가능하다. 주로 인코딩할때 쓰임
     */
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        Log.d("post_headers", response.headers.get("Content-Type")+"");
        try {
            String jsonString = new String(response.data);
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (JSONException je) {
            Log.d("JSONException", je.getMessage());
            return Response.error(new ParseError(je));
        }
    }
}
