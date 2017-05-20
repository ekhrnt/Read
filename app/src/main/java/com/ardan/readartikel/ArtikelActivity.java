package com.ardan.readartikel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.ardan.readartikel.control.JSONHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Ardan on 18/05/2017.
 */
public class ArtikelActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    //Untuk Emulator
    //private static final String URL = "http://10.0.2.2:8888/JogjaTourismWeb/webservice.php?get=lokasi_wisata";
    //Untuk Handphone
    private static final String URL = "http://www.latihanphp.ga/artikel/pages/webservice.php?get=artikel";
    private static final String id_news = "id_artikel";
    private JSONHelper json;
    private ArtikelAdapter adapter;
    private ListView lv_Artikel;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artikel);

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        lv_Artikel = (ListView) findViewById(R.id.lstArtikel);
        json = new JSONHelper();

        new AsyncData().execute(URL);

        lv_Artikel.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String kode = ((TextView) view.findViewById(R.id.tv_idArtikel)).getText().toString();
                Intent in = new Intent(ArtikelActivity.this, DetailArtikel.class);
                in.putExtra(id_news, kode);
                startActivity(in);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
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

    private class AsyncData extends AsyncTask<String, Void, List<ModelArtikel>> {
        @Override
        protected List<ModelArtikel> doInBackground(String... params) {
            JSONObject obj = json.getJSONFromURL(params[0]);
            return json.getAllArtikel(obj);
        }

        @Override
        protected void onPostExecute(List<ModelArtikel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            adapter = new ArtikelAdapter(ArtikelActivity.this, R.layout.item_artikel, result);
            lv_Artikel.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }
    }
}
