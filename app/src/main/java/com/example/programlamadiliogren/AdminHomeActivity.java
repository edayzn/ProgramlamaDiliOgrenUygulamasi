package com.example.programlamadiliogren;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdminHomeActivity extends AppCompatActivity {
    EditText textDil, image;
    Button btnDilEkle;
    ListView listView;

    Toolbar toolbar;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<String> myKeys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        listView = (ListView) findViewById(R.id.listDil);
        btnDilEkle = (Button) findViewById(R.id.btnDil);
        textDil = (EditText) findViewById(R.id.textDil);
        image = (EditText) findViewById(R.id.image);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Admin");


        btnDilEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dref.child("Diller").push().setValue(
                        new Diller(
                                textDil.getText().toString(),
                                image.getText().toString()

                        )
                );
            }
        });

        listView.setAdapter(adapter);
        dref.child("Diller").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Diller dil = dataSnapshot.getValue(Diller.class);
                arrayList.add(dil.getDilAdi());
                System.out.println("diller" + dil.getDilAdi());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
                startActivity(new Intent(AdminHomeActivity.this, AdminHomeActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.Konular:
                startActivity(new Intent(AdminHomeActivity.this, AdminKonularActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.baslik:
                startActivity(new Intent(AdminHomeActivity.this, AdminAltbaslikActivity.class));
                mesaj = "tıklandı";
                break;

            case R.id.icerik:
                startActivity(new Intent(AdminHomeActivity.this, AdminIcerikActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.soruCevap:
                startActivity(new Intent(AdminHomeActivity.this, AdminSoruCevapActivity.class));
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminHomeActivity.this);
        // set title
        alertDialogBuilder.setTitle("Uyarı");

        // set dialog message
        alertDialogBuilder
                .setMessage("Uygulamadan Çıkış Yapmak mı İstiyorsunuz ? ")
                .setCancelable(false)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AdminHomeActivity.this.finish();
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
