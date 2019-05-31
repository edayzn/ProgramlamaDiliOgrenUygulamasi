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
import android.text.method.ScrollingMovementMethod;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IcerikActivity extends AppCompatActivity {
    ArrayList<String> myKeys = new ArrayList<String>();
    ExpandableListView expandableListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listdataChild;
    ExpandableList expandableList;
    private String baslikAdiGelen;
    TabHost tabHost;
    TextView icerikDetay, tv;
    Button yorum, yorumListe;
    FirebaseAuth auth;
    DatabaseReference myref = FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    ArrayAdapter<String> adapter;
    //SoruCevap-----
    Button answerButton;
    RadioGroup radioGroup;
    RadioButton a_option, b_option, c_option, d_option, e_option, rb_selected;
    int currentQuestionIndex = 0;
    private TextView questionText;
    static ArrayList<SoruCevap> questionArrayList;
    private int scoreNum;

    private TextView scoreText;
    private TextView gameState;
    //-------
    final ArrayList<String> arrayList = new ArrayList<>();
    //private View TabIndicator;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icerik);

        this.setScoreNum(0);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        icerikDetay = (TextView) findViewById(R.id.icerikDetay);
        tabHost = (TabHost) findViewById(R.id.tabHost);
        yorum = (Button) findViewById(R.id.yorum);
        icerikDetay.setMovementMethod(new ScrollingMovementMethod());
        Init();

        questionArrayList = new ArrayList<>();

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
                /* for (DataSnapshot ds : dataSnapshot.getChildren()) {*/
                Icerik icerik = dataSnapshot.getValue(Icerik.class);
                // final String id = dataSnapshot.getKey();
                if (gelenbaslik.equals(icerik.getBaslikAdi())) {
                    System.out.println("içerikk" + icerik.getIcerik());
                    icerikDetay.setText(icerik.getIcerik());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        icerikDetay.setText(Html.fromHtml(icerik.getIcerik(),
                                Html.FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE));
                    } else
                        icerikDetay.setText(Html.fromHtml(icerik.getIcerik()));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                String key = dataSnapshot.getKey();
                int index = myKeys.indexOf(key);
                arrayList.set(index, value);
                adapter.notifyDataSetChanged();
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


        myref.child("SoruCevap").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                SoruCevap sorucevap = dataSnapshot.getValue(SoruCevap.class);
                if (gelenbaslik.equals(sorucevap.getBaslikAdi())) {
                    setBaslikAdiGelen(gelenbaslik);
                    System.out.println((" buraya geldi" + sorucevap.getBaslikAdi()));
                    questionArrayList.add(sorucevap);
                    System.out.println(questionArrayList.size());
                    displayQuestion(currentQuestionIndex);
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
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (radioGroup.getCheckedRadioButtonId() != -1) {
                    controlAnswers();
                } else {
                    Toast.makeText(getApplicationContext(), "Lütfen bir seçim yapınız!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public int getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(int scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getBaslikAdiGelen() {
        return baslikAdiGelen;
    }

    public void setBaslikAdiGelen(String baslikAdiGelen) {
        this.baslikAdiGelen = baslikAdiGelen;
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
                    myref.child("Yorumlar").push().setValue(
                            new Yorum(
                                    editYorum.getText().toString(),
                                    email.getText().toString(),
                                    icerikDetay.getText().toString()
                            )

                    );dialog.dismiss();
                } else
                    System.out.println("Lütfen Giriş Yapınız");
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

    public void listyrm() {
        myref.child("Yorumlar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Yorum yrm = dataSnapshot.getValue(Yorum.class);
                if (yrm.getIcerik().equals(icerikDetay.getText().toString())) {
                    arrayList.add(yrm.getYorum() + " " + yrm.getUser());
                    listdataChild.put(listDataHeader.get(0), arrayList);
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
    }

    private void controlAnswers() {
        if (answerCheck()) {
            //scoreNum = scoreNum + 5;
            this.setScoreNum(this.getScoreNum() + 5);
            scoreText.setText("PUANINIZ: " + " " + String.valueOf(this.getScoreNum()));
            Toast.makeText(getApplicationContext(), "TEBRİKLER DOĞRU CEVAP", Toast.LENGTH_SHORT).show();
            int sayi = (questionArrayList.size() / 1);
            boolean kontrol = false;
            if (sayi * 5 == this.getScoreNum()) {
                kontrol = true;
            }
            if (user != null && this.getBaslikAdiGelen() != null && kontrol) {
                TestSonuc ts = new TestSonuc();
                ts.setUser(user.getEmail());
                ts.setBaslikAdi(this.getBaslikAdiGelen());
                ts.setSonuc(kontrol);
                this.sonucEkle(ts);
            }
        } else {
            Toast.makeText(getApplicationContext(), "ÜZGÜNÜM YANLIŞ CEVAP", Toast.LENGTH_SHORT).show();
        }
        goOn();
    }
    public void sonucEkle(TestSonuc ts) {
        if (!user.equals(ts.getUser()) & !getBaslikAdiGelen().equals(ts.getBaslikAdi())) {
            myref.child("TestSonuc").push().setValue(
                    new TestSonuc(
                            ts.getBaslikAdi(),
                            ts.getUser(),
                            ts.isSonuc()
                    )
            );
        }
    }
    private void goOn() {
        currentQuestionIndex = (currentQuestionIndex + 1) % questionArrayList.size();
        System.out.println("Soru sayisi = " + questionArrayList.size());
        if (currentQuestionIndex == 0) {
            gameState.setVisibility(View.VISIBLE);
            radioGroup.clearCheck();
            answerButton.setEnabled(false);
            return;
        }
        displayQuestion(currentQuestionIndex);
    }

    private boolean answerCheck() {
        String answer = "";
        int id = radioGroup.getCheckedRadioButtonId();
        rb_selected = (RadioButton) findViewById(id);
        if (rb_selected == a_option) {
            answer = "a";
        } else if (rb_selected == b_option) {
            answer = "b";
        } else if (rb_selected == c_option) {
            answer = "c";
        }
        return questionArrayList.get(currentQuestionIndex).isCorrectAnswer(answer);
    }

    private void Init() {
        answerButton = (Button) findViewById(R.id.answerButton);
        questionText = (TextView) findViewById(R.id.questionText);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        a_option = (RadioButton) findViewById(R.id.a_option);
        b_option = (RadioButton) findViewById(R.id.b_option);
        c_option = (RadioButton) findViewById(R.id.c_option);
        scoreText = (TextView) findViewById(R.id.scoreText);
        gameState = (TextView) findViewById(R.id.gameState);
        radioGroup.clearCheck();
    }

    private void displayQuestion(int pos) {
        radioGroup.clearCheck();
        questionText.setText(questionArrayList.get(pos).getSoru());
        a_option.setText(questionArrayList.get(pos).getSecenek1());
        b_option.setText(questionArrayList.get(pos).getSecenek2());
        c_option.setText(questionArrayList.get(pos).getSecenek3());
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

        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }
}
