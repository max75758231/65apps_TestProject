package com.example.a65apps_testproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.a65apps_testproject.data.database.model.DBSpecialtyModel;
import com.example.a65apps_testproject.internet.InternetConnection;
import com.example.a65apps_testproject.retrofit.adapter.SpecialtyAdapter;
import com.example.a65apps_testproject.data.datamodel.CollectionModel;
import com.example.a65apps_testproject.data.datamodel.Employee;
import com.example.a65apps_testproject.data.datamodel.ResponseData;
import com.example.a65apps_testproject.retrofit.ApiService;
import com.example.a65apps_testproject.retrofit.RetrofitClient;
import com.example.a65apps_testproject.ui.EmployeesActivity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final int FIRST_SPEC_VIEW_TOUCHED_ID = 100;
    public static final int SECOND_SPEC_VIEW_TOUCHED_ID = 101;

    private RecyclerView recyclerView;
    private DividerItemDecoration itemDecoration;

    private ArrayList<Employee> allEmployeeList, employeeFirstSpec, employeeSecondSpec;

    private SpecialtyAdapter specialtyAdapter;
    private ApiService apiService;

    private LinkedHashMap<Integer, String> specsEliminationMap;
    private ArrayList<String> specialtiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allEmployeeList = new ArrayList<>();
        employeeFirstSpec = new ArrayList<>();
        employeeSecondSpec = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.rv_specialty);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        itemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        apiService = RetrofitClient.getApiService();

        specsEliminationMap = new LinkedHashMap<>();
        specialtiesList = new ArrayList<>();

        getData();
    }

    //--------------------------------------------------------------------------------------------

    private void getData() {
        if (InternetConnection.isNetworkAvailable(getApplication())) {

            DBSpecialtyModel.deleteAll(DBSpecialtyModel.class);

            Call<ResponseData> call = apiService.getJSON();
            call.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    allEmployeeList = response.body().getEmployees();

                    fillingMapBySpecs(allEmployeeList, specsEliminationMap);
                    fillingListBySpecs(specsEliminationMap);
                    fillingEmployeesArrayByOwnSpec(allEmployeeList);

                    specialtyAdapter = new SpecialtyAdapter(getApplication(), specialtiesList);
                    specialtyAdapter.setClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            sendingIntentToTouchHandling(v);
                        }
                        });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            recyclerView.setAdapter(specialtyAdapter);
                            recyclerView.addItemDecoration(itemDecoration);
                        }
                    });
                }
                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Toast.makeText(getApplication(), "Response Error", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplication(), "Internet Connection Error", Toast.LENGTH_SHORT).show();

            List<DBSpecialtyModel> dbSpecialtyModels = DBSpecialtyModel.listAll(DBSpecialtyModel.class);
            for (DBSpecialtyModel item : dbSpecialtyModels) {
                specialtiesList.add(item.toString());
            }

            specialtyAdapter = new SpecialtyAdapter(getApplication(), specialtiesList);
            specialtyAdapter.setClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendingIntentToTouchHandling(v);
                }
            });
            recyclerView.addItemDecoration(itemDecoration);
            recyclerView.setAdapter(specialtyAdapter);
        }
    }

    //---------------------------------------------------------------------------------------------

    //Allocations employees items to 2 arrays by their specs
    private void fillingEmployeesArrayByOwnSpec(ArrayList<Employee> employee) {
        for (Employee item : employee) {
            Integer currentSpecFirstId = item.getSpecialties().get(0).getSpecialtyId();
            Integer size = item.getSpecialties().size();
            if (size == 1) {
                if (currentSpecFirstId.equals(101)) {
                    employeeFirstSpec.add(item);
                } else if (currentSpecFirstId.equals(102)) {
                    employeeSecondSpec.add(item);
                }
            } else {
                employeeFirstSpec.add(item);
                employeeSecondSpec.add(item);
            }

        }
        CollectionModel.setEmployeesFirstSpec(employeeFirstSpec);
        CollectionModel.setEmployeesSecondSpec(employeeSecondSpec);
    }

    //Filling the map by specialties to find out all of them
    private void fillingMapBySpecs(ArrayList<Employee> employee, LinkedHashMap<Integer, String> map) {
        for (Employee item : employee) {
            Integer key = item.getSpecialties().get(0).getSpecialtyId();
            String value = item.getSpecialties().get(0).getSpecialtyName();
            map.put(key, value);
        }
    }

    private void fillingListBySpecs(LinkedHashMap<Integer, String> map) {
        for (Map.Entry<Integer, String> pair : map.entrySet()) {
            specialtiesList.add(pair.getValue());

            savingInDbSpecList(pair.getValue());
        }
    }

    private void savingInDbSpecList(String spec) {
        DBSpecialtyModel specialtyModel = new DBSpecialtyModel(spec);
        specialtyModel.save();
    }

    private void sendingIntentToTouchHandling(View v) {
        int itemView = recyclerView.indexOfChild(v);
        Intent intent = new Intent(MainActivity.this, EmployeesActivity.class);
        switch (itemView) {
            case 0:
                intent.putExtra("touched_id", FIRST_SPEC_VIEW_TOUCHED_ID);
                startActivity(intent);
                break;
            case 1:
                intent.putExtra("touched_id", SECOND_SPEC_VIEW_TOUCHED_ID);
                startActivity(intent);
                break;
        }
    }
}