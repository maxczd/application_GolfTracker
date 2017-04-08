package com.cazade.golf.projetgolf;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import layout.IndexFragment;
import layout.LoginFragment;
import layout.SignUpFragment;

public class IndexActivity extends AppCompatActivity implements IndexFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

    Fragment signUp, login, welcome;
    FragmentManager FM;

    public static final String REGISTER_URL = "http://82.64.9.145/userRegister.php";
    public static final String LOGIN_URL = "http://82.64.9.145/userLogIn.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";

    String USER_PSEUDO = "user_login";
    String USER_EMAIL = "user_password";

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
        FT.replace(R.id.index_content, signUp).addToBackStack("signUp");
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
        final String username = pseudo;
        final String mail = email;
        final String password = pass1;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(IndexActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IndexActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME, username);
                params.put(KEY_PASSWORD, password);
                params.put(KEY_EMAIL, mail);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onLoginFragmentInteraction(Uri uri) {

    }

    @Override
    public void onLoginSubmit(String email, String pass) {
        final String mail = email;
        final String password = pass;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                       if(response.equals("success")){
                           Intent intent = new Intent(IndexActivity.this, UserActivity.class);
                           intent.putExtra(USER_EMAIL, mail);
                           intent.putExtra(USER_PSEUDO, password);
                           startActivity(intent);
                       }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IndexActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_EMAIL, mail);
                params.put(KEY_PASSWORD, password);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

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
