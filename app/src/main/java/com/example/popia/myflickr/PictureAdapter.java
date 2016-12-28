package com.example.popia.myflickr;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by popia on 10/1/16.
 */
public class PictureAdapter extends ArrayAdapter<Picture> {
    ArrayList<Picture> pictureList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public PictureAdapter(Context context, int resource, ArrayList<Picture> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        pictureList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            holder.tvTitle = (TextView) v.findViewById(R.id.tvTitle);
           // holder.tvDescription = (TextView) v.findViewById(R.id.tvDescriptionn);
            holder.tvPub = (TextView) v.findViewById(R.id.tvPub);
            holder.tvTag = (TextView) v.findViewById(R.id.tvTag);
            holder.tvDate = (TextView) v.findViewById(R.id.tvDate);
            holder.tvAuthor = (TextView) v.findViewById(R.id.tvAuthor);
            holder.tvLink = (TextView) v.findViewById(R.id.tvLink);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }


        holder.imageview.setImageResource(R.drawable.ic_launcher);
        new DownloadImageTask(holder.imageview).execute(pictureList.get(position).getMedia());
        if(holder.tvTitle !=null) holder.tvTitle.setText(pictureList.get(position).getTitle());
        if(holder.tvPub !=null) holder.tvPub.setText("Published: " + pictureList.get(position).getPublished());
        if(holder.tvTag!=null) holder.tvTag.setText("Tags : "+pictureList.get(position).getTags());
        if(holder.tvDate !=null) holder.tvDate.setText("Date Taken: " + pictureList.get(position).getDate_taken());
        if(holder.tvAuthor !=null) holder.tvAuthor.setText("Author: " + pictureList.get(position).getAuthor());
        if(holder.tvLink !=null)holder.tvLink.setText("Link: " + pictureList.get(position).getLink());
        return v;

    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvTitle;
        public TextView tvPub;
        public TextView tvTag;
        public TextView tvDate;
        public TextView tvAuthor;
        public TextView tvLink;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

        }
    }



}
