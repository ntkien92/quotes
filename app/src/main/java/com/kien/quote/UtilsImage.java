package com.kien.quote;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.kien.quote.Helper.DatabaseHandler;
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
    public UtilsImage(Context context) {
        this.mContext = context;
    }

    public void setListener(OnSuccessDataBase listener) {
        this.listener = listener;
    }

    private OnSuccessDataBase listener;

    public interface OnSuccessDataBase {
        void getAllImage(ArrayList<Item> items);
    }

    public void getJson(String url){
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Đang tải dữ liệu..\nXin lỗi vì đã để bạn chờ lâu..");
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Gson gson = new Gson();
                    Quote quote = gson.fromJson(response.toString(), Quote.class);

                    ArrayList<Item> items = quote.getItems();
                    listener.getAllImage(items);
//                    Toast.makeText(mContext.getApplicationContext(), "" + total_page, Toast.LENGTH_LONG).show();
                    progressDialog.hide();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
        });
        VolleyHelper.getInstance(mContext.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
