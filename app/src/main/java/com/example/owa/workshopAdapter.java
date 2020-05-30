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

public class workshopAdapter extends RecyclerView.Adapter<workshopAdapter.ViewHolder> {

    public ArrayList<workshopsView> workList;
    public Context context;

    public workshopAdapter(ArrayList<workshopsView> work, Context c) {
        workList = work;
        context = c;
    }

    public workshopAdapter(ArrayList<workshopsView> list, int workshop_layout) {
    }

    @NonNull
    @Override
    public workshopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.admin_workshop_itemview, parent, false);
        workshopAdapter.ViewHolder vh = new workshopAdapter.ViewHolder(view);

        return vh;
    }

    public void onBindViewHolder(@NonNull workshopAdapter.ViewHolder holder, int position) {

        holder.workshopName.setText(workList.get(position).getWorkName());
        holder.workshopDdate.setText(workList.get(position).getWdate());
        holder.workshopstime.setText(workList.get(position).getWstartTime());
        holder.workshopntime.setText(workList.get(position).getWendTime());
    }

    @Override
    public int getItemCount() {
        if (workList == null)
            return 0;
        else
            return workList.size();
    }

    public void update(ArrayList<workshopsView> temp) {
        workList = temp;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView workshopName, workshopDdate, workshopstime, workshopntime;


        public ViewHolder(@NonNull View itemView) {
            // super(itemView);
            super(itemView);

            workshopName = itemView.findViewById(R.id.tv1);
            workshopDdate = itemView.findViewById(R.id.tv2);
            workshopstime = itemView.findViewById(R.id.tv3);
            workshopntime = itemView.findViewById(R.id.tv4);

            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {

            Intent i = new Intent(v.getContext(), update_workshop.class);

            i.putExtra("workName", workshopName.getText().toString());
            i.putExtra("wdate", workshopDdate.getText().toString());
            i.putExtra("wstartTime", workshopstime.getText().toString());
            i.putExtra("wendTime", workshopntime.getText().toString());

            v.getContext().startActivity(i);
        }
    }
}
