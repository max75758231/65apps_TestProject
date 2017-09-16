package com.example.a65apps_testproject.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a65apps_testproject.R;

import java.util.ArrayList;

public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.MyViewHolder> {

    private View.OnClickListener clickListener;

    public void setClickListener(View.OnClickListener callback) {
        clickListener = callback;
    }

    private Context context;
    private ArrayList<String> specialties = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textSpecialty;

        private MyViewHolder(View view) {
            super(view);

            textSpecialty = view.findViewById(R.id.tv_specialty_item);
        }
    }

    public SpecialtyAdapter(Context context, ArrayList<String> objects) {
        this.specialties = objects;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.specialty_item_view, parent, false);
        MyViewHolder holder = new MyViewHolder(itemView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v);
            }
        });
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textSpecialty.setText(specialties.get(position));
    }

    @Override
    public int getItemCount() {
        return specialties.size();
    }
}