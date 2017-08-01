package com.mjsearch.emma.mjsearch.es;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.RequestBody;
import okhttp3.MediaType;

//function similar to org.json
//import com.google.gson.JsonObject;
//import com.google.gson.JsonSyntaxException;
//import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonSyntaxException;
import com.mjsearch.emma.mjsearch.models.mjThread;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;


public class ESClient {
    // mjsearch loads everything in a 12-per-page manner
    public static final int COUNT_PER_PAGE = 12;
    private static final String SP_KEY = "keyword";
    private static final String TAG = "ES_API";
    private static final String BASE_URL = "http://10.0.2.2:9200/";
    private static final String CLASS_NAME = ESClient.class.getSimpleName();
   // private static final TypeToken<List<mjThread>> THREAD_LIST_TYPE = new TypeToken<List<mjThread>>() {
  //  };

    public static List<mjThread> post(@NonNull Context context, int page) {
        Log.d("checkpoint1", "HERE-->post");
        String url = getAbsoluteUrl(context, page);
        String ES_INDEX = "onem3point";
        String ES_TYPE = "mj";
        String apiString = "_search";
        return searchPost(METHOD.POST, ES_INDEX, ES_TYPE, apiString, url);
    }

    private static List<mjThread> searchPost(METHOD httpMethod, String ES_INDEX, String ES_TYPE, String apiString, String url) {
        Log.d("checkpoint1", "HERE-->postOrPut");
        String responseBody = null;
        ArrayList<mjThread> result = new ArrayList<mjThread>();

        try {
            OkHttpClient httpClient = new OkHttpClient();
            //RequestBody body = RequestBody.create(JSON, params);
            //String url="http://10.0.2.2:9200/_search?source={\"query\" : {\"match_phrase\" : {\"title\": \"hard\"} }, \"from\" : 0, \"size\" : 10}";
            //without .post(postbody) is a GET request, otherwise is a POST request
            Request request = new Request.Builder()
                    .addHeader("Content-Type", "application/json")
                    .url(url)
                    .build();

            Log.d("checkpoint1", "HERE-->" + "start response");
            Response response = httpClient.newCall(request).execute();
            //result(java JSONArray)
            responseBody = response.body().string();
            Log.d("checkpoint1", responseBody);
            JSONObject obj = new org.json.JSONObject(responseBody);
            Boolean test1 = obj.optBoolean("timed_out"); // false
            JSONArray items = obj.optJSONObject("hits").optJSONArray("hits");
            for(int i=0;i<items.length();i++) {
                mjThread cur = new mjThread();
                JSONObject item = items.getJSONObject(i);
                JSONObject source = item.getJSONObject("_source");
                if(item.getString("_index").equals("glassdoor")) {
                    cur.company=source.getString("company")+" : "+source.getString("position");
                    cur.thread_title=source.getString("content");
                    cur.post_date=source.getString("post_date");
                    cur.article=source.getString("content")+"\n"+source.getString("answer");
                    cur.html_url=source.getString("url");
                }
                else if(item.getString("_index").equals("onem3point")) {
                    cur.company=source.getString("tags");
                    cur.thread_title=source.getString("title");
                    cur.post_date=source.getString("post_date");
                    cur.article=source.getString("content");
                    cur.html_url=source.getString("url");
                }
                result.add(cur);
            }

          //  return new ArrayList<mjThread>() {
           // };

        } catch (Exception e) {
            Log.e(CLASS_NAME, e.getLocalizedMessage());
            responseBody = e.getLocalizedMessage();
        } finally {
            return result;
        }
    }

    private static String getAbsoluteUrl(@NonNull Context context, int page) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(
                SP_KEY, Context.MODE_PRIVATE);
        //sp.edit().putString("keyword", "OA").apply();

        String keyword = loadKeyword(context, "keyword");
        if (keyword == "")
            keyword = "interview";
        //concatenation + --> will use stringbuilder automatically
        String relativeUrl = "_search?source={\"query\":{\"multi_match\":{\"query\":\"" + keyword + "\",\"fields\":[\"tags\", \"title\", \"content\", \"company\", \"answer\"]}}," +
                "\"from\":" + page * COUNT_PER_PAGE + ",\"size\":" + COUNT_PER_PAGE + "}";
        return BASE_URL + relativeUrl;
    }

    //sp 必须通过 context 获取，任一都行，把getSharedPreferences第一个参数的名字指定对即可取到相应的SharedPreferences
    public static void storeKeyword(@NonNull Context context, @Nullable String token) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(
                SP_KEY, Context.MODE_PRIVATE);
        sp.edit().putString("keyword", token).apply();
        //String saved = sp.getString("keyword", "");
    }

    private static String loadKeyword(Context context, String key) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(
                SP_KEY, Context.MODE_PRIVATE);
        //String saved2 = sp.getString("keyword","");
        try {
            return sp.getString(key, "");
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}

