package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataSepeda extends AppCompatActivity {

    private RecyclerView recyclerView;
    private adapterViewSepeda adapter;

    ArrayList<modelSepeda> datalist;

    CardView cvInbox;

    TextView tvJenis, tvMerk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sepeda);

        tvJenis = findViewById(R.id.txtJenisSepeda);
        tvMerk = findViewById(R.id.txtMerkSepeda);

        cvInbox = findViewById(R.id.cvInbox);
        recyclerView = findViewById(R.id.listSepeda);

        getDataSepeda();
    }

    private void getDataSepeda() {
        datalist = new ArrayList<>();
        Log.d("geo", "onCreate: ");

        AndroidNetworking.post(BaseUrl.url + "phpsepedabaru/getdatasepeda.php")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray data = response.getJSONArray("result");

                            for (int i = 0; i < data.length(); i++) {

                                JSONObject object = data.getJSONObject(i);
                                modelSepeda model = new modelSepeda();
                                model.setId(object.getString("id"));
                                model.setKode(object.getString("kode"));
                                model.setMerk(object.getString("merk"));
                                model.setJenis(object.getString("jenis"));
                                model.setWarna(object.getString("warna"));
                                model.setHargaSewa(object.getString("hargasewa"));

                                datalist.add(model);
                            }
                            adapter = new adapterViewSepeda(datalist);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(DataSepeda.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

                            if (response.getJSONArray("result").length() == 0) {
                                recyclerView.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(DataSepeda.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                        Log.d("geo", "onResponse: " + anError.toString());
                        Log.d("geo", "onResponse: " + anError.getErrorBody());
                        Log.d("geo", "onResponse: " + anError.getErrorCode());
                        Log.d("geo", "onResponse: " + anError.getErrorDetail());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 23 && data.getStringExtra("refresh") != null) {
            //refresh list
            getDataSepeda();
            Toast.makeText(this, "data's..", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DataSepeda.this, Admin.class);
        startActivity(intent);
        finish();
        finishAffinity();
    }
}