package com.kien.quote.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kien.quote.Helper.DatabaseHandler;
import com.kien.quote.R;
import com.kien.quote.UtilsImage;
import com.kien.quote.model.Item;

import java.util.ArrayList;

/**
 * Created by kien on 12/01/2016.
 */
public class SplashScreen extends Activity implements UtilsImage.OnSuccessDataBase{
    private UtilsImage allImage;

    private static final String HOME_PAGE = "http://quote-kien.herokuapp.com/v1/quotes";
    private static final String INDEX = "?index=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DatabaseHandler db = new DatabaseHandler(this);
        int index = db.lastIndex();
        String url = HOME_PAGE + INDEX + index + "";
        allImage = new UtilsImage(this);
        allImage.setListener(this);
        allImage.getJson(url);
    }

    public void saveDatabase(ArrayList<Item> items) {
        DatabaseHandler db = new DatabaseHandler(this);
        for (int i = 0; i < items.size(); i++) {
            db.addItem(items.get(i));
        }
    }

    @Override
    public void getAllImage(ArrayList<Item> items) {
        saveDatabase(items);
        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
