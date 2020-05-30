package com.example.owa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public List<uploadinfo> newsList;
    public Context context;

    public NewsAdapter(List<uploadinfo> news, Context c) {
        newsList = news;
        context = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        uploadinfo upin = newsList.get(position);
        holder.imgDetails.setText(upin.getImageName());
        Picasso.get()
                .load(upin.imageURL)
                .fit()
                .centerCrop()
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        /*if(adsList==null)
            return 0;
        else*/
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView imgDetails;

        public ViewHolder(View view) {
            super(view);

            img = view.findViewById(R.id.image_view_upload);
            imgDetails = view.findViewById(R.id.text_view_name);
        }
    }
}
