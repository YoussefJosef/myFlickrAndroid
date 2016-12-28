package com.example.popia.myflickr;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MyFrag2 extends Fragment {

    private ArrayAdapter<String> listViewAdapter ;

    public MyFrag2() {
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_frag2, container, false);;
        MainActivity mainActivity = (MainActivity)getActivity();
        String myDataFromActivity = mainActivity.getMydata();

        ListView listView = (ListView)view.findViewById(R.id.listView);
        try {
            listViewAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    parseData(myDataFromActivity)
            );

        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setAdapter(listViewAdapter);
        return view;
    }

    public String[] parseData(String data) throws JSONException {
        ArrayList<String > items =  new ArrayList<String>();
        JSONObject parentObject = new JSONObject(data);
        JSONObject finalObject = null;
        String title ;

        JSONArray parentArray = parentObject.getJSONArray("items");
        for ( int m= 0 ; m < parentArray.length(); m++){
            finalObject = parentArray.getJSONObject(m);
            title = finalObject.getString("title");
            items.add(title);
        }
        String[] stringItems = items.toArray(new String[0]);

        return stringItems;
    }
}
