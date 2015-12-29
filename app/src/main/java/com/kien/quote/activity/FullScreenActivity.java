package com.kien.quote.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.kien.quote.helper.ConnectionDirector;
import com.kien.quote.model.GridItem;
import com.kien.quote.R;
import com.kien.quote.UtilsImage;
import com.kien.quote.adapter.FullScreenImageAdapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

public class FullScreenActivity extends Activity implements UtilsImage.getImage {

    private FullScreenImageAdapter adapter;
    private ViewPager viewPaper;
    private UtilsImage allImage;
    private int position;
    private Boolean isConnectInternet;
    private ConnectionDirector connectionDirector;
    private Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);
        viewPaper = (ViewPager) findViewById(R.id.pager);
        Intent myIntent = getIntent();
        position = myIntent.getIntExtra("position", 0);

        btnClose = (Button) findViewById(R.id.btnClose);


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED
                ))
                {
                    File sdCard = Environment.getExternalStorageDirectory();
                    File dir = new File(sdCard.getAbsolutePath()+ "/Pictures");
                    dir.mkdirs();
                    File file = new File(dir, "Kien");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                }

            }
        });

        connectionDirector = new ConnectionDirector(this);
        isConnectInternet = connectionDirector.isConnectingToInternet();
        if (isConnectInternet){
            allImage = new UtilsImage(this);
            allImage.setListener(this);
            allImage.getJson();
        }
        else {

            showDialog(this, "Bạn chưa kết nối mạng", "Vui lòng kết nối mạng");
        }

    }

    private void loadDataToGridView(ArrayList<GridItem> gridItems) {
        adapter = new FullScreenImageAdapter(FullScreenActivity.this, gridItems);
        viewPaper.setAdapter(adapter);
        viewPaper.setCurrentItem(position);
    }

    public void showDialog(final Context context, String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(FullScreenActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        });

        alertDialog.show();

    }

    @Override
    public void getAllImage(ArrayList<GridItem> images) {
        loadDataToGridView(images);
    }
}
