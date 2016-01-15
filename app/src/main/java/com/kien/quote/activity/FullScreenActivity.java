package com.kien.quote.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.kien.quote.HackyViewPager.HackyViewPager;
import com.kien.quote.Helper.DatabaseHandler;
import com.kien.quote.helper.ConnectionDirector;
import com.kien.quote.R;
import com.kien.quote.adapter.FullScreenImageAdapter;
import com.kien.quote.model.Item;

import java.util.ArrayList;

public class FullScreenActivity extends Activity {

    private FullScreenImageAdapter adapter;
    private ViewPager viewPaper;
    private int position;
    private int status;
    private Boolean isConnectInternet;
    private ConnectionDirector connectionDirector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);
        viewPaper = (HackyViewPager) findViewById(R.id.pager);
        Intent myIntent = getIntent();
        position = myIntent.getIntExtra("position", 0);
        status = myIntent.getIntExtra("status", 1);


        connectionDirector = new ConnectionDirector(this);
        isConnectInternet = connectionDirector.isConnectingToInternet();
        if (isConnectInternet){
            DatabaseHandler db = new DatabaseHandler(this);
            ArrayList<Item> items = db.getAllItems(status);
            loadDataToGridView(items);
        }
        else {
            showDialog(this, "Bạn chưa kết nối mạng", "Vui lòng kết nối mạng");
        }
    }

    private void loadDataToGridView(ArrayList<Item> gridItems) {
        adapter = new FullScreenImageAdapter(FullScreenActivity.this, gridItems);
        viewPaper.setPageTransformer(false, new FadePageTransformer(FadePageTransformer.TransformType.SLIDE_OVER));
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

    public static class FadePageTransformer implements ViewPager.PageTransformer {
        static enum TransformType {
            FLOW,
            DEPTH,
            ZOOM,
            SLIDE_OVER
        }
        private final TransformType mTransformType;

        FadePageTransformer(TransformType transformType) {
            mTransformType = transformType;
        }

        private static final float MIN_SCALE_DEPTH = 0.75f;
        private static final float MIN_SCALE_ZOOM = 0.85f;
        private static final float MIN_ALPHA_ZOOM = 0.5f;
        private static final float SCALE_FACTOR_SLIDE = 0.85f;
        private static final float MIN_ALPHA_SLIDE = 0.35f;

        public void transformPage(View page, float position) {
            final float alpha;
            final float scale;
            final float translationX;

            switch (mTransformType) {
                case FLOW:
                    page.setRotationY(position * -30f);
                    return;

                case SLIDE_OVER:
                    if (position < 0 && position > -1) {
                        // this is the page to the left
                        scale = Math.abs(Math.abs(position) - 1) * (1.0f - SCALE_FACTOR_SLIDE) + SCALE_FACTOR_SLIDE;
                        alpha = Math.max(MIN_ALPHA_SLIDE, 1 - Math.abs(position));
                        int pageWidth = page.getWidth();
                        float translateValue = position * -pageWidth;
                        if (translateValue > -pageWidth) {
                            translationX = translateValue;
                        } else {
                            translationX = 0;
                        }
                    } else {
                        alpha = 1;
                        scale = 1;
                        translationX = 0;
                    }
                    break;

                case DEPTH:
                    if (position > 0 && position < 1) {
                        // moving to the right
                        alpha = 1;
                        scale = 1;
                        translationX = 0;
                    } else {
                        // use default for all other cases

                        alpha = (1 - position);
                        scale = MIN_SCALE_DEPTH + (1 - MIN_SCALE_DEPTH) * (1 - Math.abs(position));
                        translationX = (page.getWidth() * -position);
                    }
                    break;

                case ZOOM:
                    if (position >= -1 && position <= 1) {
                        scale = Math.max(MIN_SCALE_ZOOM, 1 - Math.abs(position));
                        alpha = MIN_ALPHA_ZOOM +
                                (scale - MIN_SCALE_ZOOM) / (1 - MIN_SCALE_ZOOM) * (1 - MIN_ALPHA_ZOOM);
                        float vMargin = page.getHeight() * (1 - scale) / 2;
                        float hMargin = page.getWidth() * (1 - scale) / 2;
                        if (position < 0) {
                            translationX = (hMargin - vMargin / 2);
                        } else {
                            translationX = (-hMargin + vMargin / 2);
                        }
                    } else {
                        alpha = 1;
                        scale = 1;
                        translationX = 0;
                    }
                    break;

                default:
                    return;
            }

            page.setAlpha(alpha);
            page.setTranslationX(translationX);
            page.setScaleX(scale);
            page.setScaleY(scale);
        }
    }
}

