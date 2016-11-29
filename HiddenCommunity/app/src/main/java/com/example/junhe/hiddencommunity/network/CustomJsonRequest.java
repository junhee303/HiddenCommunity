package com.example.junhe.hiddencommunity.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HongjunLim on 11/27/2016.
 */

public class CustomJsonRequest extends JsonObjectRequest {
    public CustomJsonRequest(int method, String url, JSONObject jsonRequest,
                             Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

/*	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		//Log.d("headers", response.headers.get("Content-Type")+"");
		try {

            String EUC_KR_String = new String(response.data, "EUC-KR");//EUC-KR로 인코딩 방식 변경
            return Response.success(new JSONObject(EUC_KR_String), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            // log error
        } catch (JSONException e) {
            // log error
        }
		return null;
		//return super.parseNetworkResponse(response);
	}*/



    /**
     * Request의 요청 결과를 받는 콜백 함수
     * 결과데이터에 대해서  Handling이 가능하다. 주로 인코딩할때 쓰임
     */
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        Log.d("post_headers", response.headers.get("Content-Type")+"");
        try {
            //String jsonString = new String(response.data, "utf-8");
            String jsonString = new String(response.data);
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } /*catch (UnsupportedEncodingException e) {
            Log.d("EncodingException", e.getMessage());
            return Response.error(new ParseError(e));
        } */catch (JSONException je) {
            Log.d("JSONException", je.getMessage());
            return Response.error(new ParseError(je));
        }
    }
}
