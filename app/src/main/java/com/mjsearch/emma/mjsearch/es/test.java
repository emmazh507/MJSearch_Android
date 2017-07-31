package com.mjsearch.emma.mjsearch.es;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.support.v4.util.Pair;
import android.os.AsyncTask;


import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.auth.AuthenticationException;
import cz.msebera.android.httpclient.auth.UsernamePasswordCredentials;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.methods.HttpEntityEnclosingRequestBase;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.entity.ByteArrayEntity;
import cz.msebera.android.httpclient.impl.auth.BasicScheme;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

/**
 * Created by emmazhuang on 7/23/17.
 */

public class test {

    private static final String TAG = "ES_API";

    // Dribbble loads everything in a 12-per-page manner
    public static final int COUNT_PER_PAGE = 12;

    private static final String BASE_URL = "http://localhost:9200/";

    private static final String CLASS_NAME = test.class.getSimpleName();


/*
    public static void post(final String es_type, final String apiString, final Pair<String, String>... pairs) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... something) {
                JSONObject params = new JSONObject();
                for (Pair<String, String> pair : pairs) {
                    try {
                        params.put(pair.first, pair.second);
                    } catch (JSONException e) {
                        Log.e(CLASS_NAME, e.getLocalizedMessage());
                    }
                }
                postOrPut(METHOD.POST, "onem3point", es_type, apiString, params);
                return null;
            }
        }.execute();
    }*/
    public static String post(String ES_INDEX, String ES_TYPE, String apiString, String params) {
        Log.d("checkpoint1","HERE-->post");

        return postOrPut(METHOD.POST, ES_INDEX, ES_TYPE, apiString, params);
    }

    private static String postOrPut(METHOD httpMethod, String ES_INDEX, String ES_TYPE, String apiString, Object params) {
        String response = null;
        Log.d("checkpoint1","HERE-->postOrPut");

        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            HttpEntityEnclosingRequestBase postMethod = getHttpMethodConfiguration(httpMethod, ES_INDEX, ES_TYPE, apiString, params);
            Log.d("checkpoint1","HERE-->"+"start response");

            response = httpClient.execute(postMethod, responseHandler);

            //Log.i(CLASS_NAME + " response", response);
        } catch (Exception e) {
            Log.e(CLASS_NAME, e.getLocalizedMessage());
            response = e.getLocalizedMessage();
        } finally {
            return response;
        }
    }

    /**
     * Configuration for a POST request
     *
     * @param apiString
     * @param params
     * @return
     * @throws
     */
    @NonNull
    private static HttpEntityEnclosingRequestBase getHttpMethodConfiguration(METHOD httpMethod, String ES_INDEX, String ES_TYPE, String apiString, Object params) {
        HttpEntityEnclosingRequestBase method = null;
        Log.d("checkpoint1","HERE-->getHttpMethodConfiguration");
        try {
            if (httpMethod == METHOD.POST)
                method = new HttpPost(getAbsoluteUrl(ES_INDEX + "/" + ES_TYPE + "/" + apiString));

            else if (httpMethod == METHOD.PUT)
                method = new HttpPut(getAbsoluteUrl(ES_INDEX + "/" + ES_TYPE + "/" + apiString));

            Log.d("checkpoint1","HERE-->"+"method");

            method.setHeader("Content-Type", "application/x-ndjson");
            method.setEntity(new ByteArrayEntity(params.toString().getBytes("UTF8")));

        } catch (Exception e) {
            Log.e(CLASS_NAME, e.getLocalizedMessage());
        }
        return method;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        //return BASE_URL + relativeUrl;
        //http://localhost:9200/onem3point/es_type/_bulk
        //return "http://localhost:9200/onem3point/mj/_msearch";
        return "http://localhost:9200/_search";
    }



}
