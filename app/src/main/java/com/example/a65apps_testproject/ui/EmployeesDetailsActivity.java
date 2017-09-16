package com.example.a65apps_testproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a65apps_testproject.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

public class EmployeesDetailsActivity extends AppCompatActivity {

    TextView textName, textSurname, textBirthday, textAge, textSpecialty;
    ImageView imageAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_details_layout);

        textName = (TextView) findViewById(R.id.tv_employee_details_name);
        textSurname = (TextView) findViewById(R.id.tv_employee_details_surname);
        textBirthday = (TextView) findViewById(R.id.tv_employee_details_birthday);
        textAge = (TextView) findViewById(R.id.tv_employee_details_age);
        textSpecialty = (TextView) findViewById(R.id.tv_employee_details_specialty);
        imageAvatar = (ImageView) findViewById(R.id.iv_employee_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            textName.setText(DataHandlingAdapter.formatString(extras.getString("name")));
            textSurname.setText(DataHandlingAdapter.formatString(extras.getString("surname")));
            textSpecialty.setText(extras.getString("specialty"));
            try {
                if (extras.getString("age") != null && !extras.getString("age").equals("")) {
                    textAge.setText(DataHandlingAdapter.calculatingAge
                            (DataHandlingAdapter.fixBirthday(extras.getString("age"))));
                } else {
                    textAge.setText("-");
                }
                textBirthday.setText(DataHandlingAdapter.fixBirthday(extras.getString("birthday")));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            showImage(extras.getString("image"));
        } else {
            Toast.makeText(getApplication(), "No data have been obtained", Toast.LENGTH_SHORT).show();
        }
    }

    //The function for showing avatars with Picasso
    private void showImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.equals("")) {
            try {
                Picasso.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.no_avatar_placeholder)
                        .error(R.drawable.no_avatar_placeholder)
                        .noFade()
                        .into(imageAvatar);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
