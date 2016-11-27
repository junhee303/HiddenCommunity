package com.example.junhe.hiddencommunity.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by HongjunLim on 11/27/2016.
 */

public class CustomRequest extends Request<JSONObject> {

    private Response.Listener<JSONObject> listener;
    private Map<String, String> params; //Params 값

    public CustomRequest(int method, String url, Map<String, String> params,
                         Response.Listener<JSONObject> reponseListener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    /**
     * URL의 Param값을 Handling 할 수 있다.
     */
    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    };

    /**
     * Header를 Handling 할 수 있다. 그러나 Post방식에서 Header에서 charset를 변경하면 error가 발생한다.(이유 모름)
     */
    @Override
    public Map<String, String> getHeaders()
            throws com.android.volley.AuthFailureError {
        return super.getHeaders();
    }

    @Override
    public String getBodyContentType() {
        Log.d("Volley Post-call", "getBodyContentType");
        return "application/x-www-form-urlencoded; charset=" + "utf-8";
    }

    @Override
    public byte[] getBody() {
        Log.d("Volley Post-call", "getBody");
        Map<String, String> params = null;
        try {
            params = getParams();
        } catch (AuthFailureError e) {
            e.printStackTrace();
        }
        if (params != null && params.size() > 0) {
            return encodeParameters(params, "utf-8");
        }
        return null;
    }

    /**
     * URL의 param값들의 encoding을 한다.
     * @param params URL의 Param 값
     * @param paramsEncoding 인코딩방식
     * @return
     */
    private byte[] encodeParameters(Map<String, String> params,
                                    String paramsEncoding) {

        Log.d("Volley Post-call", "encodeParameters - " + paramsEncoding);
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(),
                        paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(),
                        paramsEncoding));
                encodedParams.append('&');

            }
            Log.i("encodeParameters", encodedParams.toString());
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: "
                    + paramsEncoding, uee);
        }
    }

    /**
     * Request의 요청 결과를 받는 콜백 함수
     * 결과데이터에 대해서  Handling이 가능하다. 주로 인코딩할때 쓰임
     */
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        // Log.d("post_headers", response.data.toString());
        try {
            String jsonString = new String(response.data, "utf-8");

            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));

			/*String jsonString = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));

			return Response.success(new JSONObject(jsonString),
					HttpHeaderParser.parseCacheHeaders(response));
*/		} catch (UnsupportedEncodingException e) {

            Log.d("EncodingException", e.getMessage());
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            Log.d("JSONException", je.getMessage());
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}
