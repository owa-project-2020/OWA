package com.example.owa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public ArrayList<uploadinfo> newsList;
    public ArrayList<newsView> dataList;
    public Context context;

    public NewsAdapter(ArrayList<uploadinfo> news, Context c, ArrayList<newsView> dat) {
        newsList = news;
        context = c;
        dataList = dat;
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
        newsView unv = dataList.get(position);
        holder.imgDetails.setText(upin.getImageName());
        Picasso.get()
                .load(upin.imageURL)
                .fit()
                .centerCrop()
                .into(holder.img);
        holder.tittle.setText(dataList.get(position).getTitle());
        holder.article.setText(dataList.get(position).getArticle());

    }

    @Override
    public int getItemCount() {
        if (newsList == null)
            return 0;
        else
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView img;
        public TextView imgDetails, tittle, article;

        public ViewHolder(View view) {
            super(view);

            img = view.findViewById(R.id.image_view_upload);
            imgDetails = view.findViewById(R.id.text_view_name);

            tittle = view.findViewById(R.id.t1);
            article = view.findViewById(R.id.t2);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), fullNews.class);
            i.putExtra("imageName", imgDetails.getText().toString());
            i.putExtra("title", tittle.getText().toString());
            i.putExtra("article", tittle.getText().toString());

            v.getContext().startActivity(i);
        }
    }
}
