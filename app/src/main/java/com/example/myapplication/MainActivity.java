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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button btnLogin, btnRegister;
    EditText username;
    EditText password;
    ProgressDialog progressDialog;
    SharedPreferences sp;
    private String sroleuser, sgmail, susername, sid, snoktp, snotlp, salamat;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);

        btnLogin = (Button) findViewById(R.id.BtnLogin);
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);
        btnRegister = (Button) findViewById(R.id.BtnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setTitle("Logging In...");
                progressDialog.show();
                HashMap<String, String> body = new HashMap<>();
                body.put("email", username.getText().toString());
                body.put("password", password.getText().toString());
                AndroidNetworking.post( BaseUrl.url+ "login.php")
                        .addBodyParameter(body)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("RBA", "respon : " + response);
                                String message = response.optString("message");
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
//                                    String login = response.optString("login");
                                if (message.equalsIgnoreCase("success")) {
                                    JSONArray loginArray = response.optJSONArray("login");
                                    if (loginArray == null) return;
                                    for (int i = 0; i <loginArray.length(); i++) {
                                        JSONObject aLogin = loginArray.optJSONObject(i);
                                        sroleuser = aLogin.optString("roleuser");
                                        sgmail = aLogin.optString("email");
                                        susername = aLogin.optString("nama");
                                        sid = aLogin.optString("id");
                                        snoktp = aLogin.optString("noktp");
                                        snotlp = aLogin.optString("notlp");
                                        salamat = aLogin.optString("alamat");
                                    }
                                    Log.d("AGG", "respon : " + sroleuser);
                                    sp = getSharedPreferences("RENTALSEPEDA", Context.MODE_PRIVATE);
                                    sp.edit()
                                            .putString("USERID", sid)
                                            .putString("USERNAME", susername)
                                            .putString("ROLEUSER", sroleuser)
                                            .putString("EMAIL", sgmail)
                                            .putString("KTP", snoktp)
                                            .putString("PHONE", snotlp)
                                            .putString("ALAMAT", salamat)
                                            .apply();
                                    if (sroleuser.equalsIgnoreCase("2")) {
                                        Intent intent = new Intent(MainActivity.this, Admin.class);
                                        startActivity(intent);
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                        finish();
                                        finishAffinity();
                                    }else {
                                        Intent intent = new Intent(MainActivity.this, Dashboard.class);
                                        startActivity(intent);
                                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                        finish();
                                        finishAffinity();
                                    }
                                }
                                else {
                                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(MainActivity.this, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                                Log.d("e", "onError: " + anError.getErrorBody());
                                Log.d("e", "onError: " + anError.getLocalizedMessage());
                                Log.d("e", "onError: " + anError.getErrorDetail());
                                Log.d("e", "onError: " + anError.getResponse());
                                Log.d("e", "onError: " + anError.getErrorCode());
                                progressDialog.cancel();
                            }
                        });

            }
        });
    }

    ;}
