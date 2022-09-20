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
import com.example.mytourtravel.ApiClient.Commons;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String link;
    EditText Email, Password;
    Button Register,login;
    String email,PasswordHolder;
    private ProgressBar progressBar;
    private int progressBarStatus = 0;
    int backButtonCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Register=(Button)findViewById(R.id.register);
        login=(Button)findViewById(R.id.submit);
        Email = (EditText)findViewById(R.id.editText);
        Password = (EditText)findViewById(R.id.editText3);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_cyclic);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, signup.class);

                startActivity(i);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                email=Email.getText().toString();
                PasswordHolder=Password.getText().toString();

                if (email.isEmpty()||PasswordHolder.isEmpty())
                {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Enter Username or Password!", Toast.LENGTH_LONG).show();
                }
                else if(email.equals("Admin")&&PasswordHolder.equals("Admin"))
                {
                    progressBar.setVisibility(View.GONE);
                    Intent i = new Intent(MainActivity.this, userhome.class);

                    // Sending Email to Dashboard Activity using intent.
                    i.putExtra("uname", email);
                    startActivity(i);

                }
                else {
                    updateService();
                }

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
            object.put("emailid",email);
            object.put("password",PasswordHolder);



            //  jsonArray.put(object);
            //object.put(  "pimg", body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody ProfileRequest = RequestBody.create(MediaType.parse("text/plain"), "" + object.toString());


        Call<Login_Response> call = apiInterface.LoginActivation(object.toString());
        call.enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                if (response.isSuccessful()) {
                    response.body().getMessage();
                    response.body().getStatus();
                    if(response.body().getStatus().toString().equals("1"))
                    {
                        progressBar.setVisibility(View.GONE);
                            Intent i = new Intent(MainActivity.this, userdash.class);

                            // Sending Email to Dashboard Activity using intent.
                            i.putExtra("uname", email);

                            Commons.username=email;
                            startActivity(i);

                          //  Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    else
                    {
                        progressBar.setVisibility(View.GONE);
                        // updateService();
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }


                    }


                }


            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
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
        if (isTaskRoot()){
            if (backButtonCount >= 1){
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
                backButtonCount++;
            }
        }else{
            super.onBackPressed();
        }
    }
}
