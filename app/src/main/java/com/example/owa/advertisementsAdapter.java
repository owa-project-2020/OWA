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

public class advertisementsAdapter extends RecyclerView.Adapter<advertisementsAdapter.ViewHolder> {

    //public List<uploadinfo> adsList;
    public ArrayList<uploadinfo> adsList;
    public Context context;
    //related to search..
    //public int item1;

    public advertisementsAdapter(ArrayList<uploadinfo> advertisements, Context c) {
        adsList = advertisements;
        context = c;
        //item1=ad;
    }

    public advertisementsAdapter(int ads_item, ArrayList<uploadinfo> mUploads) {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ads_item, parent, false);
        //ViewHolder vh =new ViewHolder(view);
        return new ViewHolder(view);
        //return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        uploadinfo upin = adsList.get(position);

        holder.imgDetails.setText(upin.getImageName());
        Picasso.get()
                .load(upin.imageURL)
                .fit()
                .centerCrop()
                .into(holder.img);
        //holder.imgDetails.setText("Category: "+adsList.get(position).imageName);


    }

    @Override
    public int getItemCount() {
        if (adsList == null)
            return 0;
        else
            return adsList.size();
    }

    public long getItemid(int position) {
        return position;
    }

    public void update(ArrayList<uploadinfo> items) {
        adsList = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView img;
        public TextView imgDetails;

        public ViewHolder(View view) {
            super(view);

            img = view.findViewById(R.id.image_view_upload);
            imgDetails = view.findViewById(R.id.text_view_name);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(), uploadinfo.class);

            i.putExtra("imageName", imgDetails.getText().toString());
            v.getContext().startActivity(i);
        }
    }
}
