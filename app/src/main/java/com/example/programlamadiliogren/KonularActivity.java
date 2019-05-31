package com.example.programlamadiliogren;

import android.app.Dialog;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class KonularActivity extends AppCompatActivity {
    ListView listKonu;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();
    ArrayList<String> myKeys = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    FirebaseListAdapter adapterkonu;
    ArrayAdapter<String> adapter2;
    static ArrayList<Konular> questionArrayList;
    static ArrayList<TestSonuc> testArrayList;
    static ArrayList<SoruCevap> sonucArrayList;
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konular);
        listKonu = (ListView) findViewById(R.id.listKonu);
        questionArrayList = new ArrayList<>();
        testArrayList = new ArrayList<>();
        sonucArrayList = new ArrayList<>();
        final Bundle gelenVeri = getIntent().getExtras();
        final String gelenkonu = gelenVeri.get("anahtar").toString();


        final Query query = FirebaseDatabase.getInstance().getReference().child("Konular").orderByChild("dilAdi").equalTo(gelenkonu);
        final FirebaseListOptions<Konular> options = new FirebaseListOptions.Builder<Konular>()
                .setLayout(R.layout.konular)
                //.setLifecycleOwner(HomeActivity.this)
                .setQuery(query, Konular.class)
                .build();

        adapterkonu = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView konlr = v.findViewById(R.id.konuTxt);
                TextView konl = v.findViewById(R.id.tc);
                ImageView image = v.findViewById(R.id.konuView);
                Konular konu = (Konular) model;
                konlr.setText((konu.getKonuAdi().toString()));
                Picasso.with(KonularActivity.this)
                        .load(konu.getImage().toString())
                        .into(image);
                for (int i = 0; i <= position; i++) {
                    String sizw = String.valueOf(questionArrayList.size());
                    konl.setText((i + 1) + "/" + sizw);

                }
            }

        };
        myref.child("Konular").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Konular kn = dataSnapshot.getValue(Konular.class);
                if (gelenkonu.equals(kn.getDilAdi())) {
                    questionArrayList.add(kn);
                    System.out.println(questionArrayList.size());
                }
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

        listKonu.setAdapter(adapterkonu);
        listKonu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem1 = ((TextView) view.findViewById(R.id.konuTxt)).getText().toString();

                final Intent intent = new Intent(view.getContext(), AltbaslikActivity.class);

                CharSequence dil = selectedItem1;
                intent.putExtra("anahtar", dil);
                startActivityForResult(intent, 0);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterkonu.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterkonu.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_ekstra, menu);
        MenuItem item = menu.findItem(R.id.ara);
        SearchView searchView = (SearchView) item.getActionView();
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
            case R.id.ayarlar:
                startActivity(new Intent(KonularActivity.this, HomeActivity.class));
                mesaj = "tıklandı";
                break;

        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

}
