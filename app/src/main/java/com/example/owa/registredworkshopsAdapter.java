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

public class registredworkshopsAdapter extends RecyclerView.Adapter<registredworkshopsAdapter.ViewHolder> {

    public ArrayList<userRegisterWorkshop> registredworkList;
    public Context context;

    public registredworkshopsAdapter(ArrayList<userRegisterWorkshop> registred, Context c) {
        registredworkList = registred;
        context = c;
    }

    @NonNull
    @Override
    public registredworkshopsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.admin_user_registred_view, parent, false);
        registredworkshopsAdapter.ViewHolder vh = new registredworkshopsAdapter.ViewHolder(view);

        return vh;
    }

    public void onBindViewHolder(@NonNull registredworkshopsAdapter.ViewHolder holder, int position) {

        holder.participantName.setText(registredworkList.get(position).getUser_Name());
        holder.participantEmaile.setText(registredworkList.get(position).getUser_email());
        holder.participantactivity.setText(registredworkList.get(position).getWorkshop_name());
    }

    @Override
    public int getItemCount() {
        if (registredworkList == null)
            return 0;
        else
            return registredworkList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView participantName, participantEmaile, participantactivity;


        public ViewHolder(@NonNull View itemView) {
            // super(itemView);
            super(itemView);

            participantName = itemView.findViewById(R.id.tv1);
            participantEmaile = itemView.findViewById(R.id.tv2);
            participantactivity = itemView.findViewById(R.id.tv3);

            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {

            Intent i = new Intent(v.getContext(), admin_del_registered_user.class);

            i.putExtra("user_Name", participantName.getText().toString());
            i.putExtra("user_email", participantEmaile.getText().toString());
            i.putExtra("workshop_name", participantactivity.getText().toString());

            v.getContext().startActivity(i);
        }
    }
}
