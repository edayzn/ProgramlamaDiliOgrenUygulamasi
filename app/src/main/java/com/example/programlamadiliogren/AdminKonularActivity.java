package com.example.programlamadiliogren;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.List;

public class AdminKonularActivity extends AppCompatActivity {
    EditText textKonu, text, image;
    Spinner spinner;
    Button btnKonu;
    ListView listView;
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
    List<String> arrayList = new ArrayList<String>();
    ArrayList<String> arrayListkonu = new ArrayList<>();
    ArrayList<String> myKeys = new ArrayList<>();
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_konular);
        listView = (ListView) findViewById(R.id.listView);
        textKonu = (EditText) findViewById(R.id.textKonu);
        // text=(EditText) findViewById(R.id.textdill) ;
        btnKonu = (Button) findViewById(R.id.btnKonu);
        spinner = (Spinner) findViewById(R.id.spinnerKonu);
        image = (EditText) findViewById(R.id.konuresm);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListkonu);

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        myref.child("Diller").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Diller string = dataSnapshot.getValue(Diller.class);
                final String id = dataSnapshot.getKey();
                arrayList.add(string.getDilAdi());
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                int index = myKeys.indexOf(key);
                arrayList.set(index, value);
                dataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setAdapter(adapter);
        myref.child("Konular").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Konular string = dataSnapshot.getValue(Konular.class);
                arrayListkonu.add(string.getKonuAdi() + " " + string.getDilAdi());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                int index = myKeys.indexOf(key);
                arrayListkonu.set(index, value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Konular string = dataSnapshot.getValue(Konular.class);
                arrayListkonu.remove(string.getKonuAdi() + " " + string.getDilAdi());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnKonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(" key" + spinner.getSelectedItem());
                myref.child("Konular").push().setValue(
                        new Konular(

                                textKonu.getText().toString(),
                                spinner.getSelectedItem().toString(),
                                image.getText().toString()
                        )
                );
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String mesaj = "";
        switch (item.getItemId()) {
            case R.id.Diller:
                startActivity(new Intent(AdminKonularActivity.this, AdminHomeActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.Konular:
                startActivity(new Intent(AdminKonularActivity.this, AdminKonularActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.baslik:
                startActivity(new Intent(AdminKonularActivity.this, AdminAltbaslikActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.icerik:
                startActivity(new Intent(AdminKonularActivity.this, AdminIcerikActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.user:
                mesaj = "tıklandı";
                break;
            case R.id.cikis:
                onBackPressed();
                mesaj = "tıklandı";
                break;


        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminKonularActivity.this);
        // set title
        alertDialogBuilder.setTitle("Uyarı");

        // set dialog message
        alertDialogBuilder
                .setMessage("Uygulamadan Çıkış Yapmak mı İstiyorsunuz ? ")
                .setCancelable(false)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AdminKonularActivity.this.finish();
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}