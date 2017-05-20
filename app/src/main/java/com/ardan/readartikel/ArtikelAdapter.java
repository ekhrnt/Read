package com.ardan.readartikel;

/**
 * Created by Ardan on 18/05/2017.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArtikelAdapter extends BaseAdapter {
    Context mContext;
    private int resource;
    private LayoutInflater inflater;
    private List<ModelArtikel> artikellist;
    private ArrayList<ModelArtikel> arraylist;


    public ArtikelAdapter(Context context, int resource, List<ModelArtikel> artikellist) {
        //super(context, resource, objects);
        mContext = context;
        this.artikellist = artikellist;
        this.resource = resource;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ModelArtikel>();
        this.arraylist.addAll(artikellist);
    }

    @Override
    public int getCount() {
        return artikellist.size();
    }

    @Override
    public Object getItem(int position) {
        return artikellist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(resource, null);
            holder.thumb = (ImageView) convertView.findViewById(R.id.thumb);
            holder.tvJudul = (TextView) convertView.findViewById(R.id.tv_judul);
            holder.tvId = (TextView) convertView.findViewById(R.id.tv_idArtikel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

        ImageLoader.getInstance().displayImage(artikellist.get(position).getFoto(), holder.thumb, new ImageLoadingListener() {
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

        holder.tvJudul.setText(artikellist.get(position).getJudul());
        holder.tvId.setText(artikellist.get(position).getId_artikel());

        return convertView;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        artikellist.clear();
        if (charText.length() == 0) {
            artikellist.addAll(arraylist);
        } else {
            for (ModelArtikel mp : arraylist) {
                if (mp.getJudul().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    artikellist.add(mp);
                }
            }
        }
        notifyDataSetChanged();
    }

    class ViewHolder {
        private ImageView thumb;
        private TextView tvJudul;
        private TextView tvId;
    }
}
