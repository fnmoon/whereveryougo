package com.example.whereveryougo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String strUrlBase="http://wthrcdn.etouch.cn/weather_mini?city=广州";
    int[] tvs={R.id.city,R.id.temp1,R.id.temp2,R.id.wt,R.id.textView11};
    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            String strWeather=msg.getData().getString("strWeather");
            String[] weathers=strWeather.split(",");
            for(int i=0;i<weathers.length;i++){
                String str=weathers[i];
                Log.i("str", str);
                TextView tv=(TextView) findViewById(tvs[i]);
                tv.setText(str);
            }
            String str1=weathers[1];
            Log.i("str1",str1);
            str1=str1.trim();
            String str2="";
            if(str1 != null && !"".equals(str1))
            {
                for(int j=0;j<str1.length();j++){
                    if(str1.charAt(j)>=48 && str1.charAt(j)<=57){
                        str2+=str1.charAt(j);
                    }
                }
            }
            int int1=Integer.parseInt(str2);
            if(int1<10){
                TextView x=(TextView) findViewById(R.id.textView12);
                x.setText("保暖装");}
            if(int1>=10&&int1<20){
                TextView x=(TextView) findViewById(R.id.textView12);
                x.setText("秋装");}
            if(int1>=20){
                TextView x=(TextView) findViewById(R.id.textView12);
                x.setText("夏装");}

        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        SetData();
    }

    public void SetData() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    URL url=new URL(strUrlBase);
                    InputStream is=url.openStream();
                    String strWeather=parseWeather(is);

                    Message msg=new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("strWeather", strWeather);
                    msg.setData(bundle);
                    handler.sendMessage(msg);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


    private String parseWeather(InputStream is) {
        String str=Utils.convertStreamToString(is);
        Log.i("str",str);
        try {
            JSONObject json=new JSONObject(str);
            JSONObject weatherinfo=json.getJSONObject("data");
            JSONObject weatherinfo1=weatherinfo.getJSONObject("yesterday");
            String city=weatherinfo.getString("city");
            String temp1=weatherinfo1.getString("low");
            String temp2=weatherinfo1.getString("high");
            String weather=weatherinfo1.getString("type");
            String wind=weatherinfo1.getString("fx");
            Log.i("city",city );
            Log.i("low",temp1);
            Log.i("high",temp2);
            Log.i("type",weather);
            Log.i("fx",wind);
            str=city+","+temp1+","+temp2+","+weather+","+wind;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }


    public void click(View v)
    {
        switch (v.getId()) {
            case R.id.wt:
                Intent intent=new Intent(MainActivity.this,Weather.class);
                startActivity(intent);
                break;
            case R.id.textView12:
                Intent intent1=new Intent(MainActivity.this,cloth.class);
                startActivity(intent1);
                break;
            case R.id.textView13:
                Intent intent2=new Intent(MainActivity.this,place.class);
                startActivity(intent2);
                break;
            case R.id.city:
                Intent intent3=new Intent(MainActivity.this,RoutePlanDemo.class);
                startActivity(intent3);
                break;

        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.boy) {
            return true;
        }
        else   if (id == R.id.girl) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.weather) {
            startActivity(new Intent(getApplicationContext(),Weather.class));

        } else if (id == R.id.cloth) {
            startActivity(new Intent(getApplicationContext(),cloth.class));

        } else if (id == R.id.outdoor) {
            startActivity(new Intent(getApplicationContext(),place.class));

        } else if (id == R.id.whereamI) {
            startActivity(new Intent(getApplicationContext(),whereamI.class));


        } else if (id == R.id.route) {
            Intent intent=new Intent(MainActivity.this,RoutePlanDemo.class);
            startActivity(intent);



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
