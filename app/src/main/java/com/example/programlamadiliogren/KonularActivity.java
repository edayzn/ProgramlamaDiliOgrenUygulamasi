package com.example.programlamadiliogren;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class KonularActivity extends AppCompatActivity {
    ListView listKonu;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
//String[] konu={"dsdsfdsf","dfdfdfdf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konular);
        listKonu = (ListView) findViewById(R.id.listKonu);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList2);
        //CustomAdapter customAdapter = new CustomAdapter();
        listKonu.setAdapter(adapter);
        myref.child("Konular").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Konular konu = dataSnapshot.getValue(Konular.class);
                arrayList.add(konu.getKonuAdi());
                adapter.notifyDataSetChanged();
                listKonu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final String selectedItem = (String) parent.getItemAtPosition(position);
                        final Intent intent = new Intent(view.getContext(), AltbaslikActivity.class);

                            myref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.child("AltBaslik").getChildren()) {
                                        AltBaslik result = ds.getValue(AltBaslik.class);
                                        Konular konu=ds.getValue(Konular.class);
                                        //if (result.getKonuAdi().equals(konu.getKonuAdi())) {
                                            CharSequence baslik =selectedItem;
                                            intent.putExtra("anahtar", baslik);
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
                startActivity(new Intent(KonularActivity.this, HomeActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.cikis:
                startActivity(new Intent(KonularActivity.this, MainActivity.class));
                mesaj = "tıklandı";
                break;
        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }
}

                    /*
                    -----------------------------Custom Layout------------
                       class CustomAdapter extends BaseAdapter {

                            @Override
                            public int getCount() {
                                return 0;
                            }

                            @Override
                            public Object getItem(int position) {
                                return null;
                            }

                            @Override
                            public long getItemId(int position) {
                                return 0;
                            }

                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {

                                convertView = getLayoutInflater().inflate(R.layout.customlayout, null);
                                ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);
                                TextView textKonu = (TextView) convertView.findViewById(R.id.textKonu);
                                imageView.setImageResource(R.drawable.java);
                                textKonu.setText(konu[position]);

                                return convertView;
                            }
                        }
                    */