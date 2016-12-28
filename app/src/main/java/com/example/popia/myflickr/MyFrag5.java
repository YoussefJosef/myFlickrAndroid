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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFrag5 extends Fragment {

    String finalJson = null;
    ArrayList<Picture> picturesList = new ArrayList<>();
    PictureAdapter adapter;
    String myDataFromActivity;
    View view;
    private DatabaseHelper myDb ;


    public MyFrag5() {
        // Required empty public constructor

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
        View view = inflater.inflate(R.layout.fragment_my_frag5, container, false);
        MainActivity mainActivity = (MainActivity)getActivity();
        myDataFromActivity = mainActivity.getMydata();
        picturesList = new ArrayList<Picture>();
        doUrTask();
        ListView listview = (ListView)view.findViewById(R.id.list);
        adapter = new PictureAdapter(getActivity(), R.layout.rowy, picturesList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                viewAll();
                Toast.makeText(getActivity(), picturesList.get(position).getTitle(), Toast.LENGTH_LONG).show();
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
    public void AddData(){
        boolean isInserted= myDb.insertData("tortue","ninja");
        if(isInserted==true) Log.d("data","Inserted");
        else Log.d("data","notInserted");

    }
    public void viewAll(){
        Cursor res = myDb.getAllData();
        if(res.getCount() ==0){
            Log.d("resultat","walou");
            showMessage("Error","No data ");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()) {
            buffer.append("id:"+res.getString(0)+"\n");
            buffer.append("Url:"+res.getString(1)+"\n");
            buffer.append("Tag:"+res.getString(2)+"\n");
        }
     //   showMessage("Data found",buffer.toString());
    }
    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    protected void  doUrTask() {
        Cursor res = myDb.getAllData();
        if(res.getCount() ==0){
            Log.d("resultat","walou");
            showMessage("Error","No data ");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while(res.moveToNext()) {
            buffer.append("id:"+res.getString(0)+"\n");
            buffer.append("Url:"+res.getString(1)+"\n");
            buffer.append("Tag:"+res.getString(2)+"\n");
            Picture picture = new Picture();
            picture.setTitle(res.getString(1));
            picture.setMedia(res.getString(2));
            picturesList.add(picture);
        }
       // showMessage("Data found",buffer.toString());

        }






}
