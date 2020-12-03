package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;
public class InfoSepeda extends AppCompatActivity {

    TextView tvIdSepeda;
    EditText etKode, etMerk, etJenis, etWarna, etHarga;
    Button btnEditSepeda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_sepeda);

        tvIdSepeda = findViewById(R.id.tvIdSepeda);
        etKode = findViewById(R.id.etKodeSepeda);
        etMerk = findViewById(R.id.etMerkSepeda);
        etJenis = findViewById(R.id.etJenisSepeda);
        etWarna = findViewById(R.id.etWarna);
        etHarga = findViewById(R.id.etHargaSewa);
        btnEditSepeda = findViewById(R.id.btnEditSepeda);

        Toolbar toolbar = findViewById(R.id.toolbarSepeda);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Data Sepeda");

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");
        final String kode = extras.getString("kode");
        final String merk = extras.getString("merk");
        final String jenis = extras.getString("jenis");
        final String warna = extras.getString("warna");
        final String hargaSewa = extras.getString("hargasewa");

        tvIdSepeda.setText("Id :" + id);
        etKode.setText(kode);
        etMerk.setText(merk);
        etJenis.setText(jenis);
        etWarna.setText(warna);
        etHarga.setText(hargaSewa);

        btnEditSepeda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidNetworking.post( BaseUrl.url+ "phpsepedabaru/updatesepeda.php")
                        .addBodyParameter("id",id)
                        .addBodyParameter("kode", etKode.getText().toString())
                        .addBodyParameter("merk", etMerk.getText().toString())
                        .addBodyParameter("jenis", etJenis.getText().toString())
                        .addBodyParameter("warna", etWarna.getText().toString())
                        .addBodyParameter("hargasewa", etHarga.getText().toString())
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    JSONObject hasil = response.getJSONObject("hasil");
                                    boolean sukses = hasil.getBoolean("respon");
                                    if (sukses) {
                                        Intent returnIntent = new Intent(InfoSepeda.this, DataSepeda.class);
                                        returnIntent.putExtra("refresh", "refresh");
                                        startActivityForResult(returnIntent, 23);
                                        finish();
                                        Toast.makeText(InfoSepeda.this, "Update Sukses", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(InfoSepeda.this, "Update gagal", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(InfoSepeda.this, "Edit gagal", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}