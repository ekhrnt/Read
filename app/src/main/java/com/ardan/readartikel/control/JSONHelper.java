package com.ardan.readartikel.control;

import com.ardan.readartikel.ModelArtikel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ardan on 09/03/2016.
 */
public class JSONHelper {
    private static final String TAG = "JSONHelper";

    private InputStream is = null;
    private JSONObject jsonObject = null;
    private String json = "";

    private static final String TAG_ARTIKEL      = "artikel";
    private static final String TAG_ID_ARTIKEL   = "id_artikel";
    private static final String TAG_JUDUL       = "judul";
    private static final String TAG_ISI         = "isi_artikel";
    private static final String TAG_TGL         = "tanggal_post";
    private static final String TAG_PENULIS     = "penulis";
    private static final String TAG_FOTO        = "foto";

    public JSONObject getJSONFromURL(String url)
    {
        try
        {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        } catch (ClientProtocolException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = reader.readLine())!= null)
            {
                sb.append(line + "\n");
            }

            is.close();
            json = sb.toString();
        } catch (Exception e)
        {
            Utils.TRACE(TAG, "error buffered reader");
        }

        try
        {
            Utils.TRACE("jsonadapter","hasil json ->" + json);
            jsonObject = new JSONObject(json);

        } catch (JSONException e)
        {
            Utils.TRACE(TAG, "Error jsonObject");
        }

        return jsonObject;
    }

    public List<ModelArtikel> getAllArtikel(JSONObject obj)
    {
        List<ModelArtikel> listArtikel = new ArrayList<>();

        try {
            JSONArray jsonArray = obj.getJSONArray(TAG_ARTIKEL);
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jObj = jsonArray.getJSONObject(i);

                ModelArtikel mArtikel = new ModelArtikel();
                mArtikel.setId_artikel(jObj.getString(TAG_ID_ARTIKEL));
                mArtikel.setJudul(jObj.getString(TAG_JUDUL));
                mArtikel.setIsi_artikel(jObj.getString(TAG_ISI));
                mArtikel.setTanggal_post(jObj.getString(TAG_TGL));
                mArtikel.setPenulis(jObj.getString(TAG_PENULIS));
                mArtikel.setFoto(jObj.getString(TAG_FOTO));

                listArtikel.add(mArtikel);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return listArtikel;
    }

}
