package com.kien.quote;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.kien.quote.helper.VolleyHelper;
import com.kien.quote.model.Quote;
import com.kien.quote.model.Item;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kien on 22/12/2015.
 */
public class UtilsImage {
    private Context mContext;
    private getImage listener;

    public UtilsImage(Context context) {
        this.mContext = context;
    }

    public void setListener(getImage listener) {
        this.listener = listener;
    }

    public interface getImage{
        void getAllImage(ArrayList<Item> images);
    }

    public void getJson(String url){
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    Quote quote = gson.fromJson(response.toString(), Quote.class);

                    ArrayList<Item> items = quote.getItems();
                    progressDialog.hide();
                    listener.getAllImage(items);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
        });
        VolleyHelper.getInstance(mContext.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
