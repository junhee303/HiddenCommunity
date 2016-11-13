package com.example.junhe.hiddencommunity;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPInstance{
    //싱글턴 인터페이스.
    private static HTTPInstance _instance;
    public static HTTPInstance Instance(){
        if(_instance == null){
            _instance = new HTTPInstance();
        }
        return _instance;
    }

    public String Post(String _url) throws IOException {
        //응답 response
        Log.d("[HTTPResponse]","TryPost : "+_url);
        String r = null;
        URL u = new URL(_url);
        HttpURLConnection c = (HttpURLConnection)u.openConnection();
        try{
            c.setRequestProperty("Accept-Charset", "UTF-8");
            c.setRequestMethod("GET");
            c.setConnectTimeout(10000);
            c.setDoInput(true);
            c.setDoOutput(true);
            c.connect();

            if(c.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
                String inputline = null;
                StringBuffer response = new StringBuffer();

                while ((inputline = in.readLine()) != null){
                    response.append(inputline);
                }
                in.close();

                try{
                    JSONObject res = new JSONObject(response.toString());
                    r = (String)res.get("response");
                    Log.d("JSON",r);
                }catch (org.json.JSONException e){
                    r = e.toString();
                }
            }
        }
        catch (IOException e){
            Log.d("[HTTPResponse]",e.toString());
        }
        finally {
            c.disconnect();
        }

        return r;
    }
}
