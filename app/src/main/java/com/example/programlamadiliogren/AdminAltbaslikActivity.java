package com.example.programlamadiliogren;

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
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AdminAltbaslikActivity extends AppCompatActivity {
    EditText textBaslik, editTextBaslik;
    Spinner sBaslik;

    Button baslikEkle;
    ListView listBaslik;
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
    ArrayList<String> arrayListBaslik  = new ArrayList<>();
    ArrayList<String> myKeys = new ArrayList<String>();
    List<String> arrayListKonu = new ArrayList<String>();
    ArrayAdapter<String> adapterKonu;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_altbaslik);

        textBaslik = (EditText) findViewById(R.id.textBaslik);
        sBaslik = (Spinner) findViewById(R.id.sBaslik);
        //editTextBaslik = (EditText) findViewById(R.id.editTextBaslik);
        baslikEkle = (Button) findViewById(R.id.btnBaslik);
        listBaslik = (ListView) findViewById(R.id.listBaslik);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListBaslik );

         adapterKonu= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListKonu);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        sBaslik.setAdapter(adapterKonu);

        myref.child("Konular").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Konular string = dataSnapshot.getValue(Konular.class);
                arrayListKonu.add(string.getKonuAdi());
                adapterKonu.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                int index = myKeys.indexOf(key);
                arrayListKonu.set(index, value);
                adapterKonu.notifyDataSetChanged();
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
       // sBaslik.setOnItemSelectedListener(onItemSelectedListener);

        listBaslik.setAdapter(adapter);
        myref.child("AltBaslik").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AltBaslik string = dataSnapshot.getValue(AltBaslik.class);
                arrayListBaslik.add(string.getBaslikAdi() + " " + string.getKonuAdi());
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

        baslikEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myref.child("AltBaslik").push().setValue(
                        new AltBaslik(
                                textBaslik.getText().toString(),
                                sBaslik.getSelectedItem().toString()
                        )
                );
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item=menu.findItem(R.id.ara);
        SearchView searchView= (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);

                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String mesaj = "";
        switch (item.getItemId()) {
            case R.id.Diller:
                startActivity(new Intent(AdminAltbaslikActivity.this, AdminHomeActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.Konular:
                startActivity(new Intent(AdminAltbaslikActivity.this, AdminKonularActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.baslik:
                startActivity(new Intent(AdminAltbaslikActivity.this, AdminAltbaslikActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.icerik:
                startActivity(new Intent(AdminAltbaslikActivity.this, AdminIcerikActivity.class));
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
   /* OnItemSelectedListener onItemSelectedListener=new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String s1=String.valueOf(arrayListBaslik.get(position));
            //editTextBaslik.setText(s1);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };*/
   @Override
   public void onBackPressed() {
       AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AdminAltbaslikActivity.this);
       // set title
       alertDialogBuilder.setTitle("Uyarı");

       // set dialog message
       alertDialogBuilder
               .setMessage("Uygulamadan Çıkış Yapmak mı İstiyorsunuz ? ")
               .setCancelable(false)
               .setPositiveButton("Evet",new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog,int id) {
                       AdminAltbaslikActivity.this.finish();
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
