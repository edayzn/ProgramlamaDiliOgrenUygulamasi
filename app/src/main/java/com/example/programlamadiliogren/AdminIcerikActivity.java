package com.example.programlamadiliogren;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminIcerikActivity extends AppCompatActivity {
    EditText icerik;
    Spinner sIcerik;
    TextView texticerik;
    Button icerikEkle;
    ListView listicerik;
    ArrayAdapter<String> adapterIcerik;
    List<String> arrayListIcerik = new ArrayList<>();
    ArrayList<String> arrayListBaslik = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    DatabaseReference myref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_icerik);
        icerik = (EditText) findViewById(R.id.icerik);
        sIcerik = (Spinner) findViewById(R.id.sIcerik);
        //texticerik = (TextView) findViewById(R.id.texticerik);
        //texticerik.setMovementMethod(new ScrollingMovementMethod());
        icerikEkle = (Button) findViewById(R.id.icerikEkle);
        listicerik = (ListView) findViewById(R.id.listicerik);
        myref = FirebaseDatabase.getInstance().getReference();

        adapterIcerik = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListIcerik);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListBaslik);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        icerikEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("key" + sIcerik.getSelectedItemId());
                myref.child("Icerik").push().setValue(
                        new Icerik(
                                icerik.getText().toString(),
                                sIcerik.getSelectedItem().toString()
                        )
                );
            }
        });

        sIcerik.setAdapter(adapter);
        myref.child("AltBaslik").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AltBaslik baslik = dataSnapshot.getValue(AltBaslik.class);
                arrayListBaslik.add(baslik.getBaslikAdi());
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
        sIcerik.setOnItemSelectedListener(onItemSelectedListener);
        listicerik.setAdapter(adapterIcerik);
        myref.child("Icerik").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Icerik icerik = dataSnapshot.getValue(Icerik.class);
                adapterIcerik.add(icerik.getIcerik());
                adapterIcerik.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Icerik icerik = dataSnapshot.getValue(Icerik.class);
                adapterIcerik.remove(icerik.getIcerik());
                adapterIcerik.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

  AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String s1 = String.valueOf(arrayListBaslik.get(position));
            //texticerik.setText(s1);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

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
                startActivity(new Intent(AdminIcerikActivity.this, AdminHomeActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.Konular:
                startActivity(new Intent(AdminIcerikActivity.this, AdminKonularActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.baslik:
                startActivity(new Intent(AdminIcerikActivity.this, AdminAltbaslikActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.icerik:
                startActivity(new Intent(AdminIcerikActivity.this, AdminIcerikActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.user:
                mesaj = "tıklandı";
                break;
            case R.id.cikis:
                startActivity(new Intent(AdminIcerikActivity.this, MainActivity.class));
                mesaj = "tıklandı";
                break;


        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);

    }
}
