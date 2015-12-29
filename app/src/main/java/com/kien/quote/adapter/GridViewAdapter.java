package com.kien.quote.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.ImageView;

import com.kien.quote.R;
import com.kien.quote.model.GridItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by kien on 20/12/2015.
 */
public class GridViewAdapter extends ArrayAdapter<GridItem> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> mGridData = new ArrayList<GridItem>();
    private OnClickImage listener;

    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<GridItem> mGridData, OnClickImage listener) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
        this.listener = listener;
    }

    public void  setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.ivGridItemImage);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        GridItem item = mGridData.get(position);
        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
        holder.imageView.setOnClickListener(new OnImageClickListener(position));
        return row;

    }

    @Override
    public int getCount()
    {
        return this.mGridData.size();
    }

    @Override
    public GridItem getItem(int position) {
        return this.mGridData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class OnImageClickListener implements View.OnClickListener {
        int _position;

        public OnImageClickListener(int position){
            this._position = position;
        }

        @Override
        public void onClick(View v) {
            listener.changeToPage(_position);
        }
    }
    static class ViewHolder {
        ImageView imageView;
    }

    public interface OnClickImage {
        public void changeToPage(int position);
    }
}
