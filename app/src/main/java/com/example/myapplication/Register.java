package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
public class Register extends AppCompatActivity {

    Button btnNext, btnRegister;
    EditText tfNama, tfEmail, tfPassword,  tfNoktp, tfAlamat, tfNohp;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.BtnRegister);
        tfNama = findViewById(R.id.txtNewName);
        tfEmail = findViewById(R.id.txtNewEmail);
        tfPassword = findViewById(R.id.txtNewPassword);
        tfNoktp = findViewById(R.id.txtNewKTP);
        tfAlamat = findViewById(R.id.txtNewAddress);
        tfNohp = findViewById(R.id.txtNewNoHP);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    AndroidNetworking.post("http://192.168.42.40/Sepeda/registrasi.php")
                            .addBodyParameter("email", tfEmail.getText().toString())
                            .addBodyParameter("nama", tfNama.getText().toString())
                            .addBodyParameter("password", tfPassword.getText().toString())
                            .addBodyParameter("nohp", tfNohp.getText().toString())
                            .addBodyParameter("noktp", tfNoktp.getText().toString())
                            .addBodyParameter("alamat", tfAlamat.getText().toString())
                            .addBodyParameter("roleuser", "1")
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
                                            Toast.makeText(Register.this, "Sukses Register", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(), MainActivity.class));

                                        } else {
                                            Toast.makeText(Register.this, "Gagal Reister", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    Toast.makeText(Register.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                                }
                            });

            }
        });
     ;

    }
}