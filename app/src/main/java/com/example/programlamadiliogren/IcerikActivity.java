package com.example.programlamadiliogren;

import android.app.Dialog;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IcerikActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listdataChild;
    ExpandableList expandableList;
    TabHost tabHost;
    TextView icerikDetay, tv;
    Button yorum, yorumListe;
    FirebaseAuth auth;
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
   ArrayAdapter<String> adapter;

    final ArrayList<String> arrayList = new ArrayList<>();
    //private View TabIndicator;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icerik);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        icerikDetay = (TextView) findViewById(R.id.icerikDetay);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        yorum = (Button) findViewById(R.id.yorum);
        //yorumListe = (Button) findViewById(R.id.yorumListe);


        //denemee
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        // TabIndicator = LayoutInflater.from ( this ) .inflate (R.layout.activity_icerik, tabHost.getTabWidget(), false );
        final Bundle gelenVeri = getIntent().getExtras();
        final String gelenbaslik = gelenVeri.get("anahtar").toString();
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("İçerik");
        spec.setContent(R.id.icerik);
        spec.setIndicator("İçerik");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Uygulamalar");
        spec.setContent(R.id.uygulama);
        spec.setIndicator("Uygulamalar");
        tabHost.addTab(spec);

        myref.child("Icerik").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Icerik icerik = dataSnapshot.getValue(Icerik.class);
                    // System.out.println(" veriiiiiii" + gelenbaslik);
                    if (gelenbaslik.equals(icerik.getBaslikAdi())) {
                        System.out.println("içerikk" + icerik.getIcerik());
                        icerikDetay.setText(icerik.getIcerik());
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            icerikDetay.setText(Html.fromHtml(icerik.getIcerik(), Html.FROM_HTML_MODE_COMPACT));
                        } else
                            icerikDetay.setText(Html.fromHtml(icerik.getIcerik()));
                    }
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
        addControl();
        expandableList = new ExpandableList(IcerikActivity.this, listDataHeader, listdataChild);
        expandableListView.setAdapter(expandableList);
    }

    public void yorumEkle() {
        System.out.println("yorum ekle");
        final Dialog dialog = new Dialog(IcerikActivity.this);
        dialog.setContentView(R.layout.yorum_dialog);
        dialog.setTitle("Yorum Ekleyiniz");
        dialog.setCancelable(true);

        final EditText editYorum = (EditText) dialog.findViewById(R.id.editYorum);
        Button btnEkle = (Button) dialog.findViewById(R.id.btnekle);
        Button iptal = (Button) dialog.findViewById(R.id.iptal);
        final TextView email = (TextView) dialog.findViewById(R.id.user);
        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    System.out.println(user.getEmail());
                    String adres = user.getEmail();
                    email.setText(adres.toString());
                    //email=user.getEmail();
                    myref.child("Yorumlar").push().setValue(
                            new Yorum(
                                    editYorum.getText().toString(),
                                    email.getText().toString(),
                                    icerikDetay.getText().toString()
                            )
                    );
                } else
                    System.out.println("kullanıcı yok");
            }
        });
        iptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addControl() {

        listDataHeader = new ArrayList<>();
        listdataChild = new HashMap<String, List<String>>();
        listDataHeader.add("Yorumlar");
       listyrm();
        listdataChild.put(listDataHeader.get(0), arrayList);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_yorum, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String mesaj = "";
        switch (item.getItemId()) {
            case R.id.yorum:
                yorumEkle();
                mesaj = "tıklandı";
                break;

            case R.id.ayarlar:
                startActivity(new Intent(IcerikActivity.this, HomeActivity.class));
                mesaj = "tıklandı";
                break;
            case R.id.cikis:
                startActivity(new Intent(IcerikActivity.this, MainActivity.class));
                mesaj = "tıklandı";
                break;
        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }
    public void listyrm(){
        myref.child("Yorumlar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Yorum yrm =dataSnapshot.getValue(Yorum.class);
                System.out.println(yrm.getYorum());
                arrayList.add(yrm.getYorum()+" " + yrm.getUser());
                listdataChild.put(listDataHeader.get(0), arrayList);
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
