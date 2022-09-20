package com.example.mytourtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mytourtravel.ApiClient.ApiClient;
import com.example.mytourtravel.ApiClient.ApiInterface;
import com.example.mytourtravel.ui.home.FarmerResponse;
import com.example.mytourtravel.ui.home.Register_modul;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adminhome extends AppCompatActivity {
    EditText locname, Descrip, latlon, imgpath1;
    Button tmgbtn, sbmtbtn;
    private ImageView imageView;
    LinearLayout layout_browsefile;
    Bitmap bitmap;
    private static final int REQUEST_CODE_READ_EXTERNAL_PERMISSION = 2;
    String picturePath;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private Uri fileUri; // file url to store image/video
    public static final int MEDIA_TYPE_IMAGE = 1;
    String imgpath;
    MultipartBody.Part body;
    String s1;
    private ProgressBar progressBar;
    private int progressBarStatus = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);

        locname = findViewById(R.id.lcname);
        Descrip = findViewById(R.id.ldesc);
        latlon = findViewById(R.id.latlon);
        imgpath1 = findViewById(R.id.imgpath);
        imageView = (ImageView) findViewById(R.id.imageView2);
        tmgbtn = findViewById(R.id.imgbtns);
        sbmtbtn = findViewById(R.id.sbmtbtns);

        tmgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pick Image
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 0);
            }
        });

        sbmtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
        Register_modul register_modul=new Register_modul();
        try {



            if (picturePath != null) {
                File file = new File(picturePath);

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
                Log.v("file", "" + file);
                Log.v("body", "" + body);

            } else {

                Toast.makeText(adminhome.this, "Your profile is empty"+imgpath, Toast.LENGTH_LONG).show();
            }




            // create RequestBody instance from file
            // create RequestBody instance from file

            // jsonArray.getJSONObject(object);
            // object.put("id", 1);

            object.put("locname",locname.getText().toString() );
            //object.put( "LastName", editlastname.getText().toString());
            object.put( "ldesc", Descrip.getText().toString());

            object.put( "lat_lo", latlon.getText().toString());
            object.put( "image",   picturePath );
            //  jsonArray.put(object);
            //object.put(  "pimg", body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //RequestBody ProfileRequest = RequestBody.create(MediaType.parse("text/plain"), "" + object.toString());

        RequestBody ProfileRequest = RequestBody.create(MediaType.parse("text/plain"), "" + object.toString());
        Call<FarmerResponse> call = apiInterface.UpdateProfile(ProfileRequest,body);
        call.enqueue(new Callback<FarmerResponse>() {
            @Override
            public void onResponse(Call<FarmerResponse> call, Response<FarmerResponse> response) {
                if (response.isSuccessful()) {
                    response.body().getMessage();
                    response.body().getResult();
                    // response.body().getId();
                    if(response.body().getMessage().equals("Succes"))
                    {
                        Toast.makeText(adminhome.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(adminhome.this, response.body().getMessage(), Toast.LENGTH_LONG).show();



                    }

                } else {
                    Toast.makeText(adminhome.this
                            , "Failure", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<FarmerResponse> call, Throwable t) {
                Log.v("error", t.getMessage());

            }
        });
    }

    private void selectImage() {

        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                    // start the image capture Intent
                    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "IMAGE_DIRECTORY_NAME");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
                picturePath = fileUri.getPath();
                BitmapFactory.Options options = new BitmapFactory.Options();

                // down sizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 8;

                final Bitmap bitmap = BitmapFactory.decodeFile(picturePath, options);
                imageView.setImageBitmap(bitmap);

            } else if (requestCode == 2) {

                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                android.content.CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                picturePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                BitmapFactory.decodeFile(picturePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(picturePath, options);
                imageView.setImageBitmap(bm);
            }

        }
    }

    @Override
    public void onDestroy()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}
//radioSexButton = (RadioButton) findViewById(selectedId);
//    CharSequence gender=radioSexButton.getText();
//    myDb.insertData(fname,lname,Email,ph,pwd,cpwd,address,gender.toString());
//    Toast.makeText(Register.this,
//   radioSexButton.getText(), Toast.LENGTH_SHORT).show();
