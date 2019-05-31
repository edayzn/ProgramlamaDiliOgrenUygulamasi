package com.example.programlamadiliogren;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<String> myKeys = new ArrayList<String>();
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
        myref.child("AltBaslik").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //   DataSnapshot ds= (DataSnapshot) dataSnapshot.child("Icerik").getChildren();
                AltBaslik bslk = dataSnapshot.getValue(AltBaslik.class);
                arrayListicerik.add(bslk.getBaslikAdi());
                adaptericerik.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                int index = myKeys.indexOf(key);
                arrayListicerik.set(index, value);
                adaptericerik.notifyDataSetChanged();
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
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                int index = myKeys.indexOf(key);
                arrayListsorucevap.set(index, value);
                adaptersorucevap.notifyDataSetChanged();
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
                startActivity(new Intent(AdminSoruCevapActivity.this, AdminHomeActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.Konular:
                startActivity(new Intent(AdminSoruCevapActivity.this, AdminKonularActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.baslik:
                startActivity(new Intent(AdminSoruCevapActivity.this, AdminAltbaslikActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.icerik:
                startActivity(new Intent(AdminSoruCevapActivity.this, AdminIcerikActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.user:
                mesaj = "tıklandı";
                break;
            case R.id.cikis:
                //startActivity(new Intent(AdminIcerikActivity.this, MainActivity.class));
                onBackPressed();
                mesaj = "tıklandı";
                break;


        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminSoruCevapActivity.this);
        // set title
        alertDialogBuilder.setTitle("Uyarı");

        // set dialog message
        alertDialogBuilder
                .setMessage("Uygulamadan Çıkış Yapmak mı İstiyorsunuz ? ")
                .setCancelable(false)
                .setPositiveButton("Evet",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        AdminSoruCevapActivity.this.finish();
                    }
                })
                .setNegativeButton("Hayır",new DialogInterface.OnClickListener() {
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
