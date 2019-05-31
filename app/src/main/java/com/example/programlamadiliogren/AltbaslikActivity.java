package com.example.programlamadiliogren;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.ColorRes;
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
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AltbaslikActivity extends AppCompatActivity {

    int sayac = 0;
    ListView listviewBaslik;
    ArrayAdapter<String> adapter;
    FirebaseListAdapter<AltBaslik> adapterbaslik;
    ArrayList<String> adapterList;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> myKeys = new ArrayList<String>();
    ArrayList<String> arrayListAltBaslik = new ArrayList<>();
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("AltBaslik");


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altbaslik);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        listviewBaslik = (ListView) findViewById(R.id.listviewbaslik);

        final Bundle gelenVeri = getIntent().getExtras();
        final String gelenkonu = gelenVeri.get("anahtar").toString();
        final Query query = FirebaseDatabase.getInstance().getReference().child("AltBaslik").orderByChild("konuAdi").equalTo(gelenkonu);
        final FirebaseListOptions<AltBaslik> options = new FirebaseListOptions.Builder<AltBaslik>()
                .setLayout(R.layout.altbaslik)
                .setLifecycleOwner(AltbaslikActivity.this)
                .setQuery(query, AltBaslik.class)
                .build();
        adapterbaslik = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                AltBaslik baslk = (AltBaslik) model;

                TextView basliklar = v.findViewById(R.id.altbaslk);
                ImageView image = v.findViewById(R.id.baslkView);
                basliklar.setText(baslk.getBaslikAdi());
            }
        };
        listviewBaslik.setAdapter(adapterbaslik);
        listviewBaslik.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem1 = ((TextView) view.findViewById(R.id.altbaslk)).getText().toString();
                final Intent intent = new Intent(view.getContext(), IcerikActivity.class);
                CharSequence bslk = selectedItem1;
                intent.putExtra("anahtar", bslk);
                System.out.println(bslk);
                startActivityForResult(intent, 0);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterbaslik.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterbaslik.stopListening();
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
                startActivity(new Intent(AltbaslikActivity.this, HomeActivity.class));
                mesaj = "tıklandı";
                break;

        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }
}