package com.kien.quote.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kien.quote.R;
import com.kien.quote.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kien on 22/12/2015.
 */
public class FullScreenImageAdapter extends PagerAdapter {
    private Activity activity;
    private ArrayList<Item> mGridata = new ArrayList<Item>();
    private LayoutInflater inflater;

    public FullScreenImageAdapter(Activity activity, ArrayList<Item> gridata) {
        this.activity = activity;
        this.mGridata = gridata;
    }
    @Override
    public int getCount() {
        return mGridata.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imgDisplay;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);

        Item item = mGridata.get(position);

        Picasso.with(activity).load(item.getImage()).into(imgDisplay);
        ((ViewPager) container).addView(viewLayout);
        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
    }
}
