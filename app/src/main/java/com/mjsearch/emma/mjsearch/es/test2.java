package com.mjsearch.emma.mjsearch.es;

import android.support.annotation.NonNull;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;


/**
 * Created by emmazhuang on 7/23/17.
 */

public class test2 {

    private static final String TAG = "ES_API";

    // Dribbble loads everything in a 12-per-page manner
    public static final int COUNT_PER_PAGE = 12;

    private static final String BASE_URL = "http://localhost:9200/";

    private static final String CLASS_NAME = test2.class.getSimpleName();


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

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static String postOrPut(METHOD httpMethod, String ES_INDEX, String ES_TYPE, String apiString, String params) {
        Log.d("checkpoint1","HERE-->postOrPut");
        String response = null;
        try {

            OkHttpClient httpClient = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, params);
            String url="http://10.0.2.2:9200/_search?source={\"query\" : {\"match_phrase\" : {\"title\": \"hard\"} }, \"from\" : 0, \"size\" : 10}";
            //String url="http://10.0.2.2:9200";
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(url)
                    .build();

            //url="http://localhost:9200/_search?source={%22query%22%20:%20{%22match_phrase%22%20:%20{%22title%22:%20%22hard%22}%20},%20%22from%22%20:%200,%20%22size%22%20:%2010}";

            //method.setEntity(new ByteArrayEntity(params.toString().getBytes("UTF8")));
            Log.d("checkpoint1","HERE-->"+"start response");
            Response responseOri = httpClient.newCall(request).execute();
            response = responseOri.body().string();
            Log.d("checkpoint1", response);

        } catch (Exception e) {
            Log.e(CLASS_NAME, e.getLocalizedMessage());
            response = e.getLocalizedMessage();
        } finally {
            return response;
        }
    }


    private static String getAbsoluteUrl(String relativeUrl) {
        //return BASE_URL + relativeUrl;
        //http://localhost:9200/onem3point/es_type/_bulk
        //return "http://localhost:9200/onem3point/mj/_msearch";
        return "http://localhost:9200/_search";
    }



}
