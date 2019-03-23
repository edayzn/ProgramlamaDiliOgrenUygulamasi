package com.example.programlamadiliogren;

import android.content.Context;
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
    EditText textKonu,text;
    Spinner spinner;
    Button btnKonu;
    ListView listView;
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
    List<String> arrayList = new ArrayList<String>();
    ArrayList<String> arrayListkonu = new ArrayList<>();
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

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListkonu);

        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        myref.child("Diller").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String string = dataSnapshot.getValue(String.class);

               final String id=dataSnapshot.getKey();
               /* System.out.println("---------");
                System.out.println("id=="+id);
                System.out.println("String=="+string);
                System.out.println("---------");*/
                arrayList.add(string);
                dataAdapter.notifyDataSetChanged();


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
        listView.setAdapter(adapter);
        myref.child("Konular").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Konular string = dataSnapshot.getValue(Konular.class);
                arrayListkonu.add(string.getKonuAdi() +" "+ string.getDilAdi());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Konular string = dataSnapshot.getValue(Konular.class);
                arrayListkonu.remove(string.getKonuAdi() +" "+ string.getDilAdi());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

      //  spinner.setOnItemSelectedListener(onItemSelectedListener);

        btnKonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            System.out.println(" key" +spinner.getSelectedItem() )  ;
                //System.out.println("id----"+spinner.getSelectedItemId());
                myref.child("Konular").push().setValue(
                        new Konular(

                                textKonu.getText().toString(),
                                //text.getText().toString()
                              spinner.getSelectedItem().toString()

                        )
                );
            }
        });
    }
   /*OnItemSelectedListener onItemSelectedListener=new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String s1=String.valueOf(arrayList.get(position));
            text.setText((s1);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };*/
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
                mesaj="tıklandı";
                break;
            case R.id.user:
                mesaj = "tıklandı";
                break;
            case R.id.cikis:
                mesaj = "tıklandı";
                break;


        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }
}