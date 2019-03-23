package com.example.programlamadiliogren;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminSoruCevapActivity extends AppCompatActivity {
    EditText textSoru, secenek1, secenek2, secenek3, cevap;
    TextView deneme;
    Spinner spinnericerik;
    Button btnSoruCevap;
    ListView listSoruCevap;
    ArrayAdapter<String> adaptericerik;
    List<String> arrayListicerik = new ArrayList<String>();
    ArrayList<String> arrayListsorucevap = new ArrayList<>();
    ArrayAdapter<String> adaptersorucevap;
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_soru_cevap);
        textSoru = (EditText) findViewById(R.id.textSoru);
        secenek1 = (EditText) findViewById(R.id.secenek1);
        secenek2 = (EditText) findViewById(R.id.secenek2);
        secenek3 = (EditText) findViewById(R.id.secenek3);
        cevap = (EditText) findViewById(R.id.cevap);
        spinnericerik = (Spinner) findViewById(R.id.spinnericerik);
        btnSoruCevap = (Button) findViewById(R.id.btnSoruCevap);
        listSoruCevap = (ListView) findViewById(R.id.listSoruCevap);

        //Spinner içine içerikleri atama -----
        adaptericerik = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListicerik);
        adaptericerik.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnericerik.setAdapter(adaptericerik);

        //-----

        //Soru ve cevapların listelenmesi
        adaptersorucevap = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListsorucevap);
        listSoruCevap.setAdapter(adaptersorucevap);
        //---
        listele();
        btnSoruCevap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myref.child("SoruCevap").push().setValue(
                        new SoruCevap(
                                textSoru.getText().toString(),
                                secenek1.getText().toString(),
                                secenek2.getText().toString(),
                                secenek3.getText().toString(),
                                cevap.getText().toString(),
                                spinnericerik.getSelectedItem().toString())
                );
            }
        });




    }
    public void listele(){
        myref.child("Icerik").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //   DataSnapshot ds= (DataSnapshot) dataSnapshot.child("Icerik").getChildren();
                Icerik icerik = dataSnapshot.getValue(Icerik.class);
                arrayListicerik.add(icerik.getIcerik());
                adaptericerik.notifyDataSetChanged();

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
        myref.child("SoruCevap").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    SoruCevap soruCevap=dataSnapshot.getValue(SoruCevap.class);
                    System.out.println("sdgfhjk"+soruCevap);
                    arrayListsorucevap.add(soruCevap.getSoru()+" " + soruCevap.getCevap());
                    adaptersorucevap.notifyDataSetChanged();

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
}
