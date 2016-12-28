package com.example.popia.myflickr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ListView navList;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private String finalJson;
    public  String favTag ;
    public  SharedPreferences sharedPreferences;
    private String URL = "https://www.flickr.com/services/feeds/photos_public.gne?tags=cats&format=json" ;
    private boolean bool = false ;
    int state = 0;
    String autoRefresh ;
    int autoRefreshInt=10000;

    private Handler handler = new Handler();
    private Runnable myRUnnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this,autoRefreshInt);
            Log.d("task","repetitive");
            new JSONTask().execute(URL);
        }
    };


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        autoRefresh = sharedPreferences.getString("refresh","10000");
        autoRefreshInt = Integer.parseInt(autoRefresh);

        handler = new Handler();
        handler.postDelayed(myRUnnable,autoRefreshInt);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

        navList = (ListView)findViewById(R.id.navList);
        ArrayList<String> navArray = new ArrayList<String>();
        navArray.add("Home");
        navArray.add("Request");
        navArray.add("Title's list");
        navArray.add("Picture's list");
        navArray.add("More details");
        navArray.add("Favourite list");
        navList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_activated_1,navArray);

        navList.setAdapter(adapter);
        navList.setOnItemClickListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.opendrawer,R.string.closedrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLUE));

        fragmentManager = getSupportFragmentManager();
        loadSelection(0);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        favTag = sharedPreferences.getString("tag","birds");
        updateUrl(favTag);
        Log.d("urll",URL);
        new JSONTask().execute(URL);
        //on ne peut pas mettre loadpreferences ici ,frag2 se lance avec que Jsontask ne se termine !
        PreferenceScreen p = null;
    }

    private  void loadSelection(int i){
        navList.setItemChecked(i,true);
        switch (i) {
            case 0:
                Homefrag homefrag = new Homefrag();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,homefrag);
                fragmentTransaction.commit();
                state = 0;
                break;
            case 1:
                MyFrag1 myFrag1 = new MyFrag1();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,myFrag1);
                fragmentTransaction.commit();
                state = 1;
                break;
            case 2:
                MyFrag2 myFrag2 = new MyFrag2();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,myFrag2);
                fragmentTransaction.commit();
                state = 2 ;
                break;
            case 3:
                MyFrag3 myFrag3 = new MyFrag3();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,myFrag3);
                fragmentTransaction.commit();
                state = 3 ;
                break;
            case 4:
                MyFrag4 myFrag4 = new MyFrag4();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,myFrag4);
                fragmentTransaction.commit();
                state = 4 ;
                break;
            case 5:
                MyFrag5 myFrag5 = new MyFrag5();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentholder,myFrag5);
                fragmentTransaction.commit();
                state = 5 ;
               // handler.removeCallbacks(myRUnnable);

                break;

        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        handler.removeCallbacks(myRUnnable);

        int id = item.getItemId();

        if ( id == R.id.action_settings) {
            Intent intent = new Intent(this,AppPreferences.class);
            startActivity(intent);

            return true;
        }else if ( id == android.R.id.home){
            if(drawerLayout.isDrawerOpen(navList)){
                drawerLayout.closeDrawer(navList);
            }else{
                drawerLayout.openDrawer(navList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadPreferences(){
//Tag
        favTag = sharedPreferences.getString("tag","birds");
        setTitle(favTag);
//Activity
        String favActivity = sharedPreferences.getString("favou","2");

        int i = Integer.parseInt(favActivity);
           if(!bool)

                   loadSelection(i);
           else
               loadSelection(state);
// Auto refresh
      // up
    }
    public void updateUrl(String tag){
       URL = "https://www.flickr.com/services/feeds/photos_public.gne?tags="+tag+"&format=json";
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        loadSelection(position);
        drawerLayout.closeDrawer(navList);
    }

    public String getMydata() {
        return finalJson;
    }

    // Ma classe TACHE qui interroge le serveur
    public class JSONTask extends AsyncTask<String,String, String> {

        @Override
        protected String doInBackground(String... params) {

            //Recuperation des donnees du serveur
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line="";
                while ((line = reader.readLine()) != null){
                    buffer.append((line));
                }
                finalJson = buffer.toString();
                finalJson = dls(finalJson.substring(15));
                return finalJson;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();;
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null ;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            loadPreferences();
            bool=true ;
        }
    }

    public String dls(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length()-1)==')') {
            str = str.substring(0, str.length()-1);
        }
        return str;
    }


}
