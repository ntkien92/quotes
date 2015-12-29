package com.kien.quote;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.kien.quote.helper.VolleyHelper;
import com.kien.quote.model.GridItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by kien on 22/12/2015.
 */
public class UtilsImage {
    private Context mContext;
    private String url = "http://secret-meadow-3565.herokuapp.com/articles.json";
    private ArrayList<GridItem> allImage = new ArrayList<>();
    private getImage listener;
    public ArrayList<GridItem> images;

    public UtilsImage(Context context) {
        this.mContext = context;
    }

    public void setListener(getImage listener) {
        this.listener = listener;
    }

    public interface getImage{
        void getAllImage(ArrayList<GridItem> images);
    }

    public void getJson(){
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String link_flickr = "";
                        try {
                            for (int i = 0; i < response.length(); i++){
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                link_flickr = jsonObject.getString("link_flickr");
                                GridItem item = new GridItem();
                                item.setImage(link_flickr);
                                allImage.add(item);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.hide();
                        listener.getAllImage(allImage);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("KIENDU", error.getMessage());
            }
        });
        VolleyHelper.getInstance(mContext.getApplicationContext()).addToRequestQueue(req);
    }
}
