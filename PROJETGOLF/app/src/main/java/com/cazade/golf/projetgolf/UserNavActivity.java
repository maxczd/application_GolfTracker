package com.cazade.golf.projetgolf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import layout.DashBoardFragment;
import layout.ProfileFragment;
import layout.SearchFragment;

import static android.R.attr.value;

public class UserNavActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener, DashBoardFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    Fragment profile, dashboard, search;
    FragmentManager FM;
    FragmentTransaction FT;

    final String EXTRA_EMAIL = "user_email";
    final String EXTRA_HANDICAP = "user_handicap";
    final String EXTRA_USERNAME = "user_username";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
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
            System.out.println(intent.getStringExtra(EXTRA_USERNAME));
            profile = ProfileFragment.newInstance(intent.getStringExtra(EXTRA_EMAIL),intent.getStringExtra(EXTRA_USERNAME),intent.getStringExtra(EXTRA_HANDICAP));
        }
        FM = getSupportFragmentManager();
        FT = FM.beginTransaction();
        FT.replace(R.id.content, profile);
        FT.commit();

        dashboard = new DashBoardFragment();

        search = new SearchFragment();

        setContentView(R.layout.activity_user_nav);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private File imageFile;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "profil.jpg");
        Uri tempuri= Uri.fromFile(imageFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempuri);
        takePictureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        if(takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView mImageView = (ImageView) findViewById(R.id.user_profile_photo);
            mImageView.setImageBitmap(imageBitmap);
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
        startActivity(intent);
    }
}
