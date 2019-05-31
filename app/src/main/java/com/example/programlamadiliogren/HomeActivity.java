package com.example.programlamadiliogren;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {
    ListView lv;
    ImageView image;
    FirebaseListAdapter adapter;
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lv = findViewById(R.id.lv);
        Query query = FirebaseDatabase.getInstance().getReference().child("Diller");
        FirebaseListOptions<Diller> options = new FirebaseListOptions.Builder<Diller>()
                .setLayout(R.layout.diller)
                //.setLifecycleOwner(HomeActivity.this)
                .setQuery(query, Diller.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
             final   TextView diller = v.findViewById(R.id.diller);
                ImageView image=v.findViewById(R.id.imageView3);
                Diller dil = (Diller) model;
                diller.setText((dil.getDilAdi().toString()));
                Picasso.with(HomeActivity.this)
                        .load(dil.getImage().toString())
                        .into(image);
              }
        };
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String selectedItem1 = ((TextView) view.findViewById(R.id.diller)).getText().toString();
                System.out.println("deneme"+ selectedItem1);
                final Intent intent = new Intent(view.getContext(), KonularActivity.class);

                CharSequence dil =selectedItem1;
           //     System.out.println(diller.getText().toString());
                intent.putExtra("anahtar", dil);
                startActivityForResult(intent, 0);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    public void Java(View view) {
        Intent intent = new Intent(this, KonularActivity.class);
        startActivity(intent);

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
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.cikis:
                //startActivity(new Intent(HomeActivity.this, MainActivity.class));
                onBackPressed();
                mesaj = "tıklandı";
                break;


        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
        // set title
        alertDialogBuilder.setTitle("Uyarı");

        // set dialog message
        alertDialogBuilder
                .setMessage("Uygulamadan Çıkış Yapmak mı İstiyorsunuz ? ")
                .setCancelable(false)
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
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
