package com.example.popia.myflickr;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MyFrag3 extends Fragment {

    String finalJson = null;
    ArrayList<Picture> picturesList = new ArrayList<>();
    PictureAdapter adapter;
    String myDataFromActivity;
    private   DatabaseHelper myDb ;

    public MyFrag3() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        if(context instanceof Activity)
            a=(Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        myDb= new DatabaseHelper(getActivity());
        viewAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_frag3, container, false);
        MainActivity mainActivity = (MainActivity)getActivity();
        myDataFromActivity = mainActivity.getMydata();
        picturesList = new ArrayList<Picture>();
        doUrTask();
        ListView listview = (ListView)view.findViewById(R.id.list);
        adapter = new PictureAdapter(getActivity(), R.layout.rowx, picturesList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub

                Toast.makeText(getActivity(), picturesList.get(position).getTitle(), Toast.LENGTH_LONG).show();
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                // TODO Auto-generated method stub
                String title =  picturesList.get(position).getTitle();
                String media =  picturesList.get(position).getMedia();
                AddData( title,media);
                viewAll();
                Toast.makeText(getActivity(), "Added to favourite !", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected void  doUrTask() {
        Picture picture;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(myDataFromActivity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray("items");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = null;
            try {
                object = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            picture = new Picture();
            try {
                String title =object.getString("title");
                String media = (object.getJSONObject("media")).getString("m");
                picture.setTitle(title);
                picture.setMedia(media);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            picturesList.add(picture);
        }
    }

    public void AddData(String title, String media){
        boolean isInserted= myDb.insertData(title,media);
        if(isInserted==true) Log.d("data","Inserted");
        else Log.d("data","notInserted");

    }
    public void viewAll(){
        Cursor res = myDb.getAllData();
        if(res.getCount() ==0){
            Log.d("resultat","walou");
           // showMessage("Error","No data ");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()) {
            buffer.append("id:"+res.getString(0)+"\n");
            buffer.append("title:"+res.getString(1)+"\n");
            buffer.append("media:"+res.getString(2)+"\n");
        }
       // showMessage("Data found",buffer.toString());
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}

