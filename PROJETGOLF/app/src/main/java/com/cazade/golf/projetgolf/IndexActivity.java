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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import layout.IndexFragment;
import layout.LoginFragment;
import layout.SignUpFragment;

public class IndexActivity extends AppCompatActivity implements IndexFragment.OnFragmentInteractionListener, SignUpFragment.OnFragmentInteractionListener, LoginFragment.OnFragmentInteractionListener {

    Fragment signUp, login, welcome;
    FragmentManager FM;

    ArrayList<String> result_tab = new ArrayList<String>();

    final String EXTRA_EMAIL = "user_email";
    final String EXTRA_HANDICAP = "null";
    final String EXTRA_USERNAME = "user_username";

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(IndexActivity.this, UserNavActivity.class);
                        intent.putExtra(EXTRA_EMAIL, mail);
                        intent.putExtra(EXTRA_USERNAME, username);
                        intent.putExtra(EXTRA_HANDICAP, "null");
                        startActivity(intent);
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
                params.put(Config.KEY_USERNAME, username);
                params.put(Config.KEY_PASSWORD, password);
                params.put(Config.KEY_EMAIL, mail);
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            result_tab = getJSON(response);

                            Intent intent = new Intent(IndexActivity.this, UserNavActivity.class);
                            intent.putExtra(EXTRA_EMAIL, result_tab.get(0));
                            intent.putExtra(EXTRA_USERNAME, result_tab.get(1));
                            intent.putExtra(EXTRA_HANDICAP, result_tab.get(2));
                            startActivity(intent);
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
                    params.put(Config.KEY_EMAIL, mail);
                    params.put(Config.KEY_PASSWORD, password);
                    return params;
                }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private ArrayList getJSON(String response){
        String email="";
        String handicap="";
        String username = "";
        ArrayList<String> result_tab = new ArrayList<String>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            email = collegeData.getString(Config.KEY_EMAIL);
            handicap = collegeData.getString(Config.KEY_HANDICAP);
            username = collegeData.getString(Config.KEY_USERNAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        result_tab.add(email);
        result_tab.add(username);
        result_tab.add(handicap);

        return result_tab;
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
