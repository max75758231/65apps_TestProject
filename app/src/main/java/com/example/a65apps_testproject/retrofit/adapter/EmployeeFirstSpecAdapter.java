package com.example.a65apps_testproject.retrofit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a65apps_testproject.R;
import com.example.a65apps_testproject.ui.DataHandlingAdapter;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.ArrayList;

public class EmployeeFirstSpecAdapter extends RecyclerView.Adapter<EmployeeFirstSpecAdapter.MyViewHolder> {

    private View.OnClickListener clickListener;

    public void setClickListener(View.OnClickListener callback) {
        clickListener = callback;
    }

    private ArrayList<String> employeeName = new ArrayList<>();
    private ArrayList<String> employeeSurname = new ArrayList<>();
    private ArrayList<String> employeeBirthday = new ArrayList<>();
    private ArrayList<String> employeeImage = new ArrayList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textName;
        private TextView textSurname;
        private TextView textBirthday;
        private ImageView imageAvatar;

        private MyViewHolder(View view) {
            super(view);

            textName = view.findViewById(R.id.tv_employees_item_name);
            textSurname = view.findViewById(R.id.tv_employees_item_surname);
            textBirthday = view.findViewById(R.id.tv_employees_item_age);
            imageAvatar = view.findViewById(R.id.iv_employees_item);
        }
    }

    public EmployeeFirstSpecAdapter(Context context, ArrayList<String> name, ArrayList<String> surname,
                                    ArrayList<String> birthday, ArrayList<String> image) {
        this.employeeName = name;
        this.employeeSurname = surname;
        this.employeeBirthday = birthday;
        this.employeeImage = image;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.employees_item_view, parent, false);
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
        String name = employeeName.get(position);
        String surname = employeeSurname.get(position);
        String imageUrl = employeeImage.get(position);
        String birthday = "";
        try {
            birthday = DataHandlingAdapter.fixBirthday(employeeBirthday.get(position));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.textName.setText(DataHandlingAdapter.formatString(name));
        holder.textSurname.setText(DataHandlingAdapter.formatString(surname));
        if (!birthday.equals("-")) {
            holder.textBirthday.setText(DataHandlingAdapter.calculatingAge(birthday));
        } else {
            holder.textBirthday.setText(birthday);
        }

        if (imageUrl != null && !imageUrl.equals("")) {
            try {
                Picasso.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.no_avatar_placeholder)
                        .error(R.drawable.no_avatar_placeholder)
                        .noFade()
                        .into(holder.imageAvatar);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return employeeName.size();
    }
}

