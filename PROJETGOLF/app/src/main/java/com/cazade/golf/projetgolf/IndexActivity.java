package com.cazade.golf.projetgolf;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import layout.IndexFragment;
import layout.LoginFragment;
import layout.SignUpFragment;

public class IndexActivity extends AppCompatActivity implements IndexFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

    Fragment signUp, login, welcome;
    FragmentManager FM;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_index);

        signUp = new SignUpFragment();
        login = new LoginFragment();
        welcome = new IndexFragment();

        FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.index_content, welcome);
        FT.commit();


    }

    public void signUp(View v){
        FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.index_content, signUp).addToBackStack("signup");
        FT.commit();
    }

    public void login(View v){
        FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        FT.replace(R.id.index_content, login).addToBackStack("login");
        FT.commit();
    }

    @Override
    public void onIndexFragmentInteraction(Uri uri) {

    }

    @Override
    public void onSignUpFragmentInteraction(Uri uri) {

    }

    @Override
    public void signUpSubmit(String email, String pass1, String pseudo) {
        db = new DatabaseHelper(IndexActivity.this);
        User u = new User(email, pass1, pseudo);
        db.insertUser(u);

        Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed(){
        if (FM.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            FM.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
}
