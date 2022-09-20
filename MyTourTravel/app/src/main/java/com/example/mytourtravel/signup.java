package com.example.mytourtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mytourtravel.ApiClient.ApiClient;
import com.example.mytourtravel.ApiClient.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signup extends AppCompatActivity {
    EditText Email, Password, cpass,Name, mblnumt ;
    Button Register,already_u;
    String mblnums,NameHolder,cpassh, EmailHolder, PasswordHolder,emailholder;
    private ProgressBar progressBar;
    private int progressBarStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Register = (Button)findViewById(R.id.submitbtn);
        already_u = (Button)findViewById(R.id.btn_sinup);
        Name= (EditText)findViewById(R.id.uname);
        Email = (EditText)findViewById(R.id.emailtxt);
        Password = (EditText)findViewById(R.id.pass);
        cpass = (EditText)findViewById(R.id.cpasstxt);
        mblnumt = (EditText)findViewById(R.id.editmblnum);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);


        // Adding click listener to register button.
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                cpassh=cpass.getText().toString();
                PasswordHolder=Password.getText().toString();
                if(PasswordHolder.equals(cpassh)) {
                    updateService();
                }
                else {
                    Toast.makeText(signup.this,"Password Not Match!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        // Adding click listener to register button.
        already_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(signup.this, MainActivity.class);
                startActivity(i);

            }
        });
    }
    public void updateService() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
      /*  Gson gson = new GsonBuilder().setLenient().create();

        OkHttpClient client = new OkHttpClient();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).client(client).addConverterFactory(GsonConverterFactory.create(gson)).build();

   */   //  ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        // Register_modul register_modul=new Register_modul();
        try {




            // create RequestBody instance from file
            // create RequestBody instance from file

            // jsonArray.getJSONObject(object);
            // object.put("id", 1);
            object.put("name",Name.getText().toString());
            object.put("password",Password.getText().toString() );
            object.put( "emailid", Email.getText().toString());
            object.put( "mblno", mblnumt.getText().toString());


            //  jsonArray.put(object);
            //object.put(  "pimg", body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody ProfileRequest = RequestBody.create(MediaType.parse("text/plain"), "" + object.toString());


        Call<Register_Response> call = apiInterface.RegisterActivation(object.toString());
        call.enqueue(new Callback<Register_Response>() {
            @Override
            public void onResponse(Call<Register_Response> call, Response<Register_Response> response) {
                if (response.isSuccessful()) {
                    response.body().getMessage();
                    response.body().getStatus();
                    if(response.body().getMessage().equals("Succes"))
                    {
                        progressBar.setVisibility(View.GONE);
                        Intent i = new Intent(signup.this, MainActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        // updateService();
                        Toast.makeText(signup.this, "Registered Succesfully!!", Toast.LENGTH_LONG).show();


                    }

                } else {
                    Toast.makeText(signup.this, "Failure", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Register_Response> call, Throwable t) {
                Log.v("error", t.getMessage());

            }
        });
    }
    @Override
    public void onDestroy()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
    @Override
    public void onBackPressed(){


                Toast.makeText(this, "Press the button on the application!!", Toast.LENGTH_SHORT).show();



    }
}