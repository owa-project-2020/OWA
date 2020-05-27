package com.example.owa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class userworkshopAdapter extends RecyclerView.Adapter<userworkshopAdapter.ViewHolder> {

    public ArrayList<workshopsView> workList;
    public Context context;

    public userworkshopAdapter(ArrayList<workshopsView> work, Context c) {
        workList = work;
        context = c;
    }

    @NonNull
    @Override
    public userworkshopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_workshop_layout, parent, false);
        userworkshopAdapter.ViewHolder vh = new userworkshopAdapter.ViewHolder(view);

        return vh;
    }

    public void onBindViewHolder(@NonNull userworkshopAdapter.ViewHolder holder, int position) {

        holder.workshopName.setText(workList.get(position).getWorkName());
        holder.workshopDdate.setText(workList.get(position).getWdate());
        holder.workshopPrice.setText(workList.get(position).getWfee());
    }

    @Override
    public int getItemCount() {
        if (workList == null)
            return 0;
        else
            return workList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //public TextView workshopName,workshopDdate,workshopCategory;
        public TextView workshopName, workshopDdate, workshopPrice;


        public ViewHolder(@NonNull View itemView) {
            // super(itemView);
            super(itemView);

            workshopName = itemView.findViewById(R.id.tv1);
            workshopDdate = itemView.findViewById(R.id.tv2);
            workshopPrice = itemView.findViewById(R.id.tv3);

            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {

            Intent i = new Intent(v.getContext(), userRegiseterToWorkshop.class);

            i.putExtra("workName", workshopName.getText().toString());
            i.putExtra("wdate", workshopDdate.getText().toString());
            i.putExtra("wfee", workshopPrice.getText().toString());

            v.getContext().startActivity(i);
        }
    }
}
