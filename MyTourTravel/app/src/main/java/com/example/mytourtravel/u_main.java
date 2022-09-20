package com.example.mytourtravel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mytourtravel.ApiClient.ApiClient;
import com.example.mytourtravel.ApiClient.ApiInterface;
import com.example.mytourtravel.ApiClient.Commons;
import com.example.mytourtravel.ui.gallery.ModelListView;
import com.example.mytourtravel.ui.gallery.MyInterface;
import com.example.mytourtravel.ui.gallery.RetroAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.mytourtravel.ui.gallery.MyInterface.*;


public class u_main extends AppCompatActivity {
    EditText searchcntn;
    Button sbmtbutn;
    String sercht,PasswordHolder;
    private ProgressBar progressBar;
    private int progressBarStatus = 0;
    private ListView listView;
    private RetroAdapter retroAdapter;
    String[] heroes;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    int PERMISSION_ID = 44;
    String message;
    FusedLocationProviderClient mFusedLocationClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_u_main);
        sbmtbutn=(Button)findViewById(R.id.sbmtbtn);
        searchcntn=(EditText)findViewById(R.id.searchtxt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);
        listView = findViewById(R.id.lv);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Adding click listener to register button.
        sbmtbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                sercht=searchcntn.getText().toString();
                String link="";
                getLastLocation();
                if(message==null)
                {
                    Toast.makeText(u_main.this, "Please Click Button once again", Toast.LENGTH_LONG).show();
                }
                else {
                    link = Commons.BASE_URL + "simg.php?srchctnt=" + sercht + "&latlon=" + message;
                    Log.v("Link", link);
                    Toast.makeText(u_main.this, "My Current Location is : "+message, Toast.LENGTH_LONG).show();
                    getJSON(link);
                }


            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> l, View v, int position, long id){
                Log.i("menuItems", "You clicked Item: " + id + " at position:" + position+" Value: "+heroes[position]);


                String uri = "http://maps.google.com/maps?q=" + heroes[position] ;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
    }

    private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        Toast.makeText(getApplicationContext(), json, Toast.LENGTH_SHORT).show();
        JSONArray jsonArray = new JSONArray(json);
        String mark = jsonArray.getString(0);
        heroes = new String[jsonArray.length()];
        Log.i("TAG", "Json Length :" +jsonArray.length());
        ArrayList<ModelListView> modelListViewArrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            ModelListView modelListView = new ModelListView();
            JSONObject obj = jsonArray.getJSONObject(i);

            heroes[i] = obj.getString("imlatlon");

            modelListView.setImgURL(obj.getString("imgpath"));
            modelListView.setName(obj.getString("lname"));
            modelListView.setCountry(obj.getString("imdesc"));
            modelListView.setType(obj.getString("imtype"));

            modelListViewArrayList.add(modelListView);

           // Log.i("TAG", "Im Path: :" +obj.getString("imgpath"));
            //  break;

            retroAdapter = new RetroAdapter(this, modelListViewArrayList);
            listView.setAdapter(retroAdapter);
        }




    }
    private void writeListView(String response){

        try {
            //getting the whole json object from the response
            JSONObject obj = new JSONObject(response);
            if(obj.optString("status").equals("true")){

                ArrayList<ModelListView> modelListViewArrayList = new ArrayList<>();
                JSONArray dataArray  = obj.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    ModelListView modelListView = new ModelListView();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    modelListView.setImgURL(dataobj.getString("imgpath"));
                    modelListView.setName(dataobj.getString("lname"));
                    modelListView.setCountry(dataobj.getString("imdesc"));
                    modelListView.setType(dataobj.getString("imtype"));

                    modelListViewArrayList.add(modelListView);

                }

                retroAdapter = new RetroAdapter(this, modelListViewArrayList);
                listView.setAdapter(retroAdapter);

            }else {
                Toast.makeText(this, obj.optString("message")+"", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {

                                    message=location.getLatitude()+","+location.getLongitude();


                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }


    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            // latTextView.setText(mLastLocation.getLatitude()+" "+mLastLocation.getLongitude());
            //lonTextView.setText(mLastLocation.getLongitude()+"");
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onDestroy()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}