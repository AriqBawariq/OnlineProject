package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Admin extends AppCompatActivity {

    SharedPreferences sp;
    Button btnLogout, btnDataCustomer, btnRegisterData, btnListDataSepeda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnLogout = (Button) findViewById(R.id.BtnLogOut);
        btnDataCustomer = (Button) findViewById(R.id.BtnDataUser);
        btnRegisterData = (Button) findViewById(R.id.BtnRegisterSepeda);
        btnListDataSepeda = (Button)findViewById(R.id.BtnDataSepeda);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        btnDataCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, DataCustomer.class));
                finish();
            }
        });

        btnRegisterData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, registerSepeda.class));
                finish();
            }
        });

        btnListDataSepeda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Admin.this, DataSepeda.class));
                finish();
            }
        });
    }


    private void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title dialog
        alertDialogBuilder.setTitle("Really Logout?");

        // set pesan dari dialog
        alertDialogBuilder
                .setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // jika tombol diklik, maka akan menutup activity ini
                        sp = getSharedPreferences("RENTALSEPEDA", MODE_PRIVATE);
                        sp.edit()
                                .putString("USERID", "")
                                .putString("USERNAME", "")
                                .putString("ROLEUSER", "")
                                .putString("EMAIL", "")
                                .putString("KTP", "")
                                .putString("PHONE", "")
                                .putString("ALAMAT", "")
                                .apply();
                        Intent intent = new Intent(Admin.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // jika tombol ini diklik, akan menutup dialog
                        // dan tidak terjadi apa2
                        dialog.cancel();
                    }
                });

        // membuat alert dialog dari builder
        AlertDialog alertDialog = alertDialogBuilder.create();

        // menampilkan alert dialog
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //if (item.getItemId() == R.id.miCompose) {
        new AlertDialog.Builder(this)
                .setTitle("Really Logout?")
                .setMessage("Are you sure you want to logout?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        sp = getSharedPreferences("login",MODE_PRIVATE);
                        sp.edit().putBoolean("logged",false).apply();
                        Intent intent = new Intent(Admin.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
        return true;
        //}
        //  return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Admin.super.onBackPressed();
                    }
                }).create().show();
    }
}