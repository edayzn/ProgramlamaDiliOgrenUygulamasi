package com.example.programlamadiliogren;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void Java(View view) {
        Intent intent=new Intent(this,KonularActivity.class);
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
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
                mesaj = "tıklandı";
                break;


        }
        Toast.makeText(this, mesaj, Toast.LENGTH_LONG).show();
        return super.onOptionsItemSelected(item);
    }

    public void deneme(View view) {
        Intent intent=new Intent(this,KonularYeniActivity.class);
        startActivity(intent);
    }
}
