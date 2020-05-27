package com.example.owa;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

class ImageListViewAdapter extends BaseAdapter {

    public int[] imageArray = {
            R.drawable.g1, R.drawable.g2, R.drawable.g3, R.drawable.g4


    };
    // ArrayList<img> photolist;
    int layout;
    private Context context;

    public ImageListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageArray.length;
    }

    @Override
    public Object getItem(int position) {
        return imageArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView image = new ImageView(context);
        image.setImageResource(imageArray[position]);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setLayoutParams(new GridView.LayoutParams(340, 350));

        return image;
    }
}