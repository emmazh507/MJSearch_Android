package com.mjsearch.emma.mjsearch;

/**
 * Created by emmazhuang on 7/18/17.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.mjsearch.emma.mjsearch.es.*;


import java.io.IOException;

public class MainActivity extends AppCompatActivity  {

    @BindView(R.id.keyword) EditText keyword;
    @BindView(R.id.search_button) TextView search_button;

    private static final int REQ_CODE_SEARCH_RESULT = 100;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Log.d("checkpoint1","HERE");

        /* E.g. of a bulk
        { "update" : {"_id" : "3", "_type" : "tweet", "_index" : "twitter"} }
        { "doc" : {"field" : "value"}, "doc_as_upsert" : true }
        _msearch
        {"index" : "onem3point"}
        {"query" : {"match_phrase": {"title": "hard"}}, "from" : 0, "size" : 10}
        * */
    /*    new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder bulk = new StringBuilder();
                    bulk.append("{\"query\" : {\"match_phrase\" : {\"" + "title" + "\": \""+ "hard" + "\"} }, \"from\" : 0, \"size\" : 10}" + "\n");
                    ESClient.post(getApplicationContext(), 0);
                    //bulk.append("{ \"doc\" : " + jsonValues + ", \"doc_as_upsert\" : true }" + "\n");
                    Log.d("checkpoint1","HERE-->"+bulk.toString());
                    //test2.post("onem3point","mj", "_msearch", bulk.toString());
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/

        TextView search_button_ori = (TextView) findViewById(R.id.search_button);
        search_button_ori.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ESClient.storeKeyword(MainActivity.this, ((EditText) findViewById(R.id.keyword)).getText().toString());
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                //intent.putExtra(ListActivity.KEY_KEYWORD, ((EditText) findViewById(R.id.keyword)).getText().toString());
                startActivity(intent);
                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ESClient.post(getApplicationContext(), 0);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();*/
                finish();
            }
        });

    }


}
