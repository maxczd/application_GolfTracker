package com.cazade.golf.projetgolf;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import layout.ProfileFragment;

public class UserActivity extends AppCompatActivity implements ProfileFragment.OnFragmentInteractionListener {

    Fragment profile;
    FragmentManager FM;


    final String USER_PSEUDO = "user_login";
    final String USER_EMAIL = "user_password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();

        profile = new ProfileFragment();
        Bundle args = new Bundle();
        if (intent != null) {
            args.putAll(intent.getExtras());
        }
        profile.setArguments(args);


        FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.index_content, profile);
        FT.commit();
    }

    @Override
    public void onProfileFragmentInteraction(Uri uri) {

    }
}
