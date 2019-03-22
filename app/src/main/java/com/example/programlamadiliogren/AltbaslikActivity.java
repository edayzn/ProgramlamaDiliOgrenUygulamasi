package com.example.programlamadiliogren;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AltbaslikActivity extends AppCompatActivity {

    ListView listviewBaslik;
    //TextView tv;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList = new ArrayList<>();
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altbaslik);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        listviewBaslik = (ListView) findViewById(R.id.listviewbaslik);
        // tv = (TextView) findViewById(R.id.textb);
        final Bundle gelenVeri = getIntent().getExtras();
        final String gelenkonu = gelenVeri.get("anahtar").toString();
        // tv.setText(gelenVeri.getCharSequence("anahtar").toString());

        myref.child("AltBaslik").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AltBaslik baslik1 = dataSnapshot.getValue(AltBaslik.class);
                if (gelenkonu.equals(baslik1.getKonuAdi())) {
                    arrayList.add(baslik1.getBaslikAdi());
                    adapter.notifyDataSetChanged();
                }
                listviewBaslik.setAdapter(adapter);
                listviewBaslik.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final String selectedItem = (String) parent.getItemAtPosition(position);
                        final Intent intent = new Intent(view.getContext(), IcerikActivity.class);
                        myref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot ds : dataSnapshot.child("Icerik").getChildren()) {
                                    Icerik icerik = dataSnapshot.getValue(Icerik.class);
                                    AltBaslik baslik = dataSnapshot.getValue(AltBaslik.class);

                                    //if (baslik.getBaslikAdi().equals(icerik.getBaslikAdi())) {
                                        CharSequence icerikDetay = selectedItem;
                                        intent.putExtra("anahtar", icerikDetay);
                                        startActivityForResult(intent, 0);
                                   // }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
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
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String mesaj = "";
        switch (item.getItemId()) {
            case R.id.ayarlar:
                startActivity(new Intent(AltbaslikActivity.this, HomeActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.cikis:
                startActivity(new Intent(AltbaslikActivity.this, MainActivity.class));
                mesaj = "tıklandı";
                break;
        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }
}
