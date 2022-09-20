package com.example.mytourtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mytourtravel.ApiClient.Commons;
import com.example.mytourtravel.ui.gallery.ModelListView;
import com.example.mytourtravel.ui.gallery.RetroAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class imgresult extends AppCompatActivity {
    Button sbmtbutn;
    String sercht,PasswordHolder;
    private ListView listView;
    String link="";
    private RetroAdapter retroAdapter;
    String[] heroes;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    int PERMISSION_ID = 44;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgresult);

        sbmtbutn=(Button)findViewById(R.id.sbmtbtn);
        listView = findViewById(R.id.lv);
        link = Commons.BASE_URL + "imgresult.php";
        Log.v("Link", link);
        //Toast.makeText(imgresult.this, "My Current Location is : "+message, Toast.LENGTH_LONG).show();
        getJSON(link);
        sbmtbutn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(imgresult.this, imgsrch.class);

                startActivity(i);

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

    @Override
    public void onDestroy()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}