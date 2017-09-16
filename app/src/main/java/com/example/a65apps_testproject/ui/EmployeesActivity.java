package com.example.a65apps_testproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.a65apps_testproject.R;
import com.example.a65apps_testproject.data.database.model.DBEmployeeFirstSpecModel;
import com.example.a65apps_testproject.data.database.model.DBEmployeeSecondSpecModel;
import com.example.a65apps_testproject.data.database.model.DBSpecialtyModel;
import com.example.a65apps_testproject.internet.InternetConnection;
import com.example.a65apps_testproject.retrofit.adapter.EmployeeFirstSpecAdapter;
import com.example.a65apps_testproject.retrofit.adapter.EmployeeSecondSpecAdapter;
import com.example.a65apps_testproject.data.datamodel.CollectionModel;
import com.example.a65apps_testproject.data.datamodel.Employee;
import com.example.a65apps_testproject.data.datamodel.ResponseData;
import com.example.a65apps_testproject.retrofit.ApiService;
import com.example.a65apps_testproject.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DividerItemDecoration itemDecoration;

    private EmployeeFirstSpecAdapter employeeFirstSpecAdapter;
    private EmployeeSecondSpecAdapter employeeSecondSpecAdapter;
    private ApiService apiService;

    private ArrayList<Employee> employeeFirstSpecList, employeeSecondSpecList;

    private ArrayList<String> nameFirstList, surnameFirstList, birthdayFirstList, imageFirstList,
            nameSecondList, surnameSecondList, birthdaySecondList, imageSecondList;

    List<DBEmployeeFirstSpecModel> dbEmployeeFirstSpecModels =
            DBEmployeeFirstSpecModel.listAll(DBEmployeeFirstSpecModel.class);
    List<DBEmployeeSecondSpecModel> dbEmployeeSecondSpecModels =
            DBEmployeeSecondSpecModel.listAll(DBEmployeeSecondSpecModel.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employees_layout);

        nameFirstList = new ArrayList<>();
        surnameFirstList = new ArrayList<>();
        birthdayFirstList = new ArrayList<>();
        imageFirstList = new ArrayList<>();

        nameSecondList = new ArrayList<>();
        surnameSecondList = new ArrayList<>();
        birthdaySecondList = new ArrayList<>();
        imageSecondList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.rv_employees);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);

        apiService = RetrofitClient.getApiService();

        getData();
    }

    private void getData() {

        if (InternetConnection.isNetworkAvailable(getApplication()))
        {
            //When online: Clean db's to save new data
            DBEmployeeFirstSpecModel.deleteAll(DBEmployeeFirstSpecModel.class);
            DBEmployeeSecondSpecModel.deleteAll(DBEmployeeSecondSpecModel.class);

            Call<ResponseData> call = apiService.getJSON();
            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                    employeeFirstSpecList = new ArrayList<>(CollectionModel.getEmployeesFirstSpec());
                    employeeSecondSpecList = new ArrayList<>(CollectionModel.getEmployeesSecondSpec());

                    //Initialize two adapters for employees of both specialties
                    employeeFirstSpecAdapter = new EmployeeFirstSpecAdapter(getApplicationContext(),
                            nameFirstList, surnameFirstList,
                            birthdayFirstList, imageFirstList);
                    employeeSecondSpecAdapter = new EmployeeSecondSpecAdapter(getApplicationContext(),
                            nameSecondList, surnameSecondList,
                            birthdaySecondList, imageSecondList);

                    fillingListsByEmployeesSpecsOnline(employeeFirstSpecList, employeeSecondSpecList);
                    fillingDbs(employeeFirstSpecList, employeeSecondSpecList);

                    Intent intent = getIntent();
                    final Integer touchedId = intent.getIntExtra("touched_id", 0);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setAdaptersByIntentHandling(touchedId);
                            recyclerView.addItemDecoration(itemDecoration);
                        }
                    });
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Toast.makeText(getApplication(), "response error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            employeeFirstSpecAdapter = new EmployeeFirstSpecAdapter(getApplicationContext(),
                    nameFirstList, surnameFirstList,
                    birthdayFirstList, imageFirstList);
            employeeSecondSpecAdapter = new EmployeeSecondSpecAdapter(getApplicationContext(),
                    nameSecondList, surnameSecondList,
                    birthdaySecondList, imageSecondList);
            Intent intent = getIntent();
            Integer touchedId = intent.getIntExtra("touched_id", 0);

            fillingListsByEmployeesSpecsOffline(dbEmployeeFirstSpecModels, dbEmployeeSecondSpecModels);
            setAdaptersByIntentHandling(touchedId);
        }
    }

    //Handling view's id touching
    private void setAdaptersByIntentHandling(int touchedId) {
        final List<DBSpecialtyModel> dbSpecialtyList = DBSpecialtyModel.listAll(DBSpecialtyModel.class);

        if (touchedId == 100) {
            recyclerView.setAdapter(employeeFirstSpecAdapter);
            employeeFirstSpecAdapter.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = recyclerView.indexOfChild(v);
                    sendDataToDetailsActivity(pos, nameFirstList, surnameFirstList,
                            birthdayFirstList, imageFirstList, dbSpecialtyList.get(0).toString());
                }
            });
        } else {
            recyclerView.setAdapter(employeeSecondSpecAdapter);
            employeeSecondSpecAdapter.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = recyclerView.indexOfChild(v);
                    sendDataToDetailsActivity(pos, nameSecondList, surnameSecondList,
                            birthdaySecondList, imageSecondList, dbSpecialtyList.get(1).toString());
                }
            });
        }
        recyclerView.addItemDecoration(itemDecoration);
    }

    //Sending info to details activity
    private void sendDataToDetailsActivity(int position, ArrayList<String> name,
                                           ArrayList<String> surname, ArrayList<String> birthday,
                                           ArrayList<String> image, String specialtyName) {
        Intent intent = new Intent(EmployeesActivity.this, EmployeesDetailsActivity.class);

        if (position < name.size()) {

            intent.putExtra("name", name.get(position));
            intent.putExtra("surname", surname.get(position));
            intent.putExtra("birthday", birthday.get(position));
            intent.putExtra("age", birthday.get(position));
            intent.putExtra("specialty", specialtyName);
            intent.putExtra("image", image.get(position));
            startActivity(intent);
        }
    }

    private void fillingDbs(ArrayList<Employee> employees1, ArrayList<Employee> employees2) {
        String name, surname, birthday, image;

        for (Employee item : employees1) {
            name = item.getName();
            surname = item.getSurname();
            birthday = item.getBirthday();
            image = item.getImage();

            DBEmployeeFirstSpecModel employeeFirstModel =
                    new DBEmployeeFirstSpecModel(name, surname, birthday, image);
            employeeFirstModel.save();
        }

        for (Employee item : employees2) {
            name = item.getName();
            surname = item.getSurname();
            birthday = item.getBirthday();
            image = item.getImage();

            DBEmployeeSecondSpecModel employeeSecondModel =
                    new DBEmployeeSecondSpecModel(name, surname, birthday, image);
            employeeSecondModel.save();
        }
    }

    private void fillingListsByEmployeesSpecsOnline(ArrayList<Employee> employees1,
                                              ArrayList<Employee> employees2) {

        for (Employee item : employees1) {
            nameFirstList.add(item.getName());
            surnameFirstList.add(item.getSurname());
            birthdayFirstList.add(item.getBirthday());
            imageFirstList.add(item.getImage());
        }

        for (Employee item : employees2) {
            nameSecondList.add(item.getName());
            surnameSecondList.add(item.getSurname());
            birthdaySecondList.add(item.getBirthday());
            imageSecondList.add(item.getImage());
        }
    }

    private void fillingListsByEmployeesSpecsOffline(List<DBEmployeeFirstSpecModel> employeeFirstModels,
                                                     List<DBEmployeeSecondSpecModel> employeeSecondModels) {

        for (DBEmployeeFirstSpecModel item : employeeFirstModels) {
            nameFirstList.add(item.name);
            surnameFirstList.add(item.surname);
            birthdayFirstList.add(item.birthday);
            imageFirstList.add(item.image);
        }

        for (DBEmployeeSecondSpecModel item : employeeSecondModels) {
            nameSecondList.add(item.name);
            surnameSecondList.add(item.surname);
            birthdaySecondList.add(item.birthday);
            imageSecondList.add(item.image);
        }
    }
}