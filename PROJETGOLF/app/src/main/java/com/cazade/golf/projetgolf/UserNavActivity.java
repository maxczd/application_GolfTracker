package com.cazade.golf.projetgolf;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import layout.DashBoardFragment;
import layout.ProfileFragment;
import layout.SearchFragment;

public class UserNavActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener, DashBoardFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    Fragment profile, dashboard, search;
    FragmentManager FM;
    FragmentTransaction FT;
    ArrayList<ArrayList> result_tab = new ArrayList<ArrayList>();

    final String EXTRA_EMAIL = "user_email";
    final String EXTRA_HANDICAP = "null";
    final String EXTRA_USERNAME = "user_username";

    String email, username, handicap;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    System.out.println(result_tab);
                    FM = getSupportFragmentManager();
                    FT = FM.beginTransaction();
                    FT.replace(R.id.content, profile);
                    FT.commit();
                    return true;
                case R.id.navigation_dashboard:
                    FM = getSupportFragmentManager();
                    FT = FM.beginTransaction();
                    FT.replace(R.id.content, dashboard);
                    FT.commit();
                    return true;
                case R.id.navigation_notifications:
                    FM = getSupportFragmentManager();
                    FT = FM.beginTransaction();
                    FT.replace(R.id.content, search);
                    FT.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra(EXTRA_EMAIL);
            username = intent.getStringExtra(EXTRA_USERNAME);
            handicap = intent.getStringExtra(EXTRA_HANDICAP);
            if(handicap == null){
                handicap = "null";
            }
            System.out.println(username);
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.GET_COURSES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        result_tab = getJSON(response);
                        setResult(result_tab);
                        profile = ProfileFragment.newInstance(email,username,handicap, result_tab);

                        FM = getSupportFragmentManager();
                        FT = FM.beginTransaction();
                        FT.replace(R.id.content, profile);
                        FT.commit();
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserNavActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(Config.KEY_EMAIL, email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



        dashboard = new DashBoardFragment();

        search = new SearchFragment();

        setContentView(R.layout.activity_user_nav);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private ArrayList getJSON(String response){
        String handicap="";
        ArrayList<ArrayList> parcours = new ArrayList<ArrayList>();
        ArrayList<String> parcours_data = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            for(int x=0;x<result.length();x++) {

                JSONObject collegeData = result.getJSONObject(x);
                parcours_data.clear();
                parcours_data.add(collegeData.getString("member_id"));
                parcours_data.add(collegeData.getString("score_final"));
                parcours_data.add(collegeData.getString("handicap"));
                parcours_data.add(collegeData.getString("created_at"));
                System.out.println(parcours_data);
                System.out.println(x);
                parcours.add(x, parcours_data);
                System.out.println(parcours);
        }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return parcours;
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    public void dispatchTakePictureIntent(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast toast = Toast.makeText(this, "erreur", Toast.LENGTH_SHORT);
                toast.show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    @Override
    public void onProfileFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void goCourse(View v){
        Intent intent = new Intent(UserNavActivity.this, CourseActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        intent.putExtra(EXTRA_USERNAME, username);
        intent.putExtra(EXTRA_HANDICAP, handicap);
        startActivity(intent);
    }

    public void setResult (ArrayList result){
        result_tab = result;
    }
}
