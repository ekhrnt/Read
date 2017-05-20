package com.ardan.readartikel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Intent artikel;
    Intent tentang;
    private Button btnArtikel;
    private Button btnTentang;
    private boolean pressTwice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnArtikel = (Button) findViewById(R.id.btnArtikel);
        btnTentang = (Button) findViewById(R.id.btnTentang);

        btnArtikel.setOnClickListener(this);
        btnTentang.setOnClickListener(this);

        tentang = new Intent(MainActivity.this, TentangActivity.class);
        artikel = new Intent(MainActivity.this, ArtikelActivity.class);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (btnArtikel.isPressed()) {
            startActivity(artikel);
        } else if (btnTentang.isPressed()) {
            startActivity(tentang);
        } else {
            Toast.makeText(this, "Menu Belum Tersedia", Toast.LENGTH_LONG).show();
        }
    }

    public void onBackPressed() {
        if (pressTwice == true) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
        }

        pressTwice = true;
        Toast.makeText(this, "Tekan Back Lagi untuk Keluar.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pressTwice = false;
            }
        }, 5000);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
