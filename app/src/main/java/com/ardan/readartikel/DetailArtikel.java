package com.ardan.readartikel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ardan.readartikel.control.JSONHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Ardan on 18/05/2017.
 */
public class DetailArtikel extends AppCompatActivity {

    private static final String TAG_DETAIL = "detailArtikel";
    private static final String TAG_ID_ARTIKEL = "id_artikel";
    private static final String TAG_JUDUL = "judul";
    private static final String TAG_ISI = "isi_artikel";
    private static final String TAG_TGL = "tanggal_post";
    private static final String TAG_PENULIS = "penulis";
    private static final String TAG_FOTO = "foto";
    private final String id_news = "id_artikel";
    private String link_url = "";
    private JSONHelper json;
    private JSONArray detailArtikel = null;
    private String myJSON;
    private TextView judulArt, isiArt, tglArt, penulisArt;
    private List<ModelArtikel> ArtikelModelList;
    private ImageView fotoArtikel;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_artikel);

        judulArt = (TextView) findViewById(R.id.tv_judul);
        isiArt = (TextView) findViewById(R.id.tv_isi);
        tglArt = (TextView) findViewById(R.id.tv_tgl);
        penulisArt = (TextView) findViewById(R.id.tv_penulis);
        fotoArtikel = (ImageView) findViewById(R.id.ivIcon);

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

        json = new JSONHelper();

        Intent detailIntent = getIntent();
        String kode = detailIntent.getStringExtra(id_news);
        link_url = "http://www.latihanphp.ga/artikel/pages/webservice.php?artikel=" + kode;

        new AsyncData().execute();
    }

    protected void showData() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            detailArtikel = jsonObj.getJSONArray(TAG_DETAIL);
            String judul = "";
            String isi = "";
            String tanggal = "";
            String penulis = "";
            String foto = "";

            for (int i = 0; i < detailArtikel.length(); i++) {
                JSONObject c = detailArtikel.getJSONObject(i);
                String idB = c.getString(TAG_ID_ARTIKEL);
                String judulB = c.getString(TAG_JUDUL);
                String isiB = c.getString(TAG_ISI);
                String penulisB = c.getString(TAG_PENULIS);
                String tglB = c.getString(TAG_TGL);
                String fotoB = c.getString(TAG_FOTO);

                judul = judulB;
                isi = isiB;
                penulis = penulisB;
                tanggal = tglB;
                foto = fotoB;
            }

            judulArt.setText(judul);
            isiArt.setText(isi);
            penulisArt.setText(penulis);
            tglArt.setText(tanggal);

            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            ImageLoader.getInstance().displayImage(foto, fotoArtikel, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });
            //imageLoader.displayImage(imgWisata, gambar, options);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_artikel, menu);
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

    private class AsyncData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
            HttpGet httpGet = new HttpGet(link_url);

            InputStream inputStream = null;
            String result = null;

            try {
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity entity = response.getEntity();

                inputStream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                result = sb.toString();

            } catch (Exception e) {

            } finally {
                try {
                    if (inputStream != null) inputStream.close();
                } catch (Exception Squish) {

                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            myJSON = result;
            Log.i("tagconvertstr", "[" + result + "]");
            showData();
        }
    }
}


