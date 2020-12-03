package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;
public class registerSepeda extends AppCompatActivity {

    Button btnBackToDashBoard, btnRegister;
    EditText tfKode, tfMerk, tfJenis,  tfWarna, tfHargaSewa;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_sepeda);

        btnRegister = findViewById(R.id.BtnRegisterNewSepeda);
        tfKode = findViewById(R.id.txtNewCodeSepeda);
        tfMerk = findViewById(R.id.txtNewMerkSepeda);
        tfJenis = findViewById(R.id.txtNewJenisSepeda);
        tfWarna = findViewById(R.id.txtNewWarnaSepeda);
        tfHargaSewa = findViewById(R.id.txtNewHargaSepeda);
        btnBackToDashBoard = findViewById(R.id.BtnBackToDashboard);

        btnBackToDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(registerSepeda.this, Admin.class));
                finish();
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AndroidNetworking.post(BaseUrl.url +"phpsepedabaru/insertSepeda.php")
                        .addBodyParameter("kode", tfKode.getText().toString())
                        .addBodyParameter("merk", tfMerk.getText().toString())
                        .addBodyParameter("jenis", tfJenis.getText().toString())
                        .addBodyParameter("warna", tfWarna.getText().toString())
                        .addBodyParameter("hargasewa", tfHargaSewa.getText().toString())
                        .setTag("test")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    Log.d("RBA", "url: " + hasil.toString());
                                    Boolean respon = hasil.getBoolean("respon");
                                    if (respon) {
                                        Toast.makeText(registerSepeda.this, "Sukses Register", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), Admin.class));

                                    } else {
                                        Toast.makeText(registerSepeda.this, "Gagal Register", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(registerSepeda.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        ;

    }
}