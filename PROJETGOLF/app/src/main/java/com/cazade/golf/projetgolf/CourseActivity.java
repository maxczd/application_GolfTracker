package com.cazade.golf.projetgolf;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import layout.HoleFragment;

public class CourseActivity extends AppCompatActivity implements HoleFragment.OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    final String EXTRA_EMAIL = "user_email";
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        Intent intent = getIntent();
        if (intent != null) {
            email = intent.getStringExtra(EXTRA_EMAIL);
            System.out.println(intent.getStringExtra(EXTRA_EMAIL));
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course, menu);
        getSupportActionBar().hide();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void submitCourse() {
        ArrayList<Fragment> holes = mSectionsPagerAdapter.getHole();
        final ArrayList<Integer> scores = new ArrayList<Integer>();
        HoleFragment temp;
        for(int i=0; i<9; i++){
            temp = (HoleFragment) holes.get(i);
            scores.add(temp.getScore());
        }

        final JSONObject jsonObject=new JSONObject();
        for(int i=0;i<9;i++)
        {
            try {
                jsonObject.put("hole_"+i, scores.get(i).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        HashMap<String ,String> params=new HashMap<String, String>();
        params.put("params",jsonObject.toString());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.NEW_PARCOURS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CourseActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(CourseActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(Config.KEY_EMAIL, email);
                params.put("params", jsonObject.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_course, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        HoleFragment hole_1 = HoleFragment.newInstance("1","3", "160m", "hole1");
        HoleFragment hole_2 = HoleFragment.newInstance("2","3", "160m", "hole2");
        HoleFragment hole_3 = HoleFragment.newInstance("3","3", "125m", "hole3");
        HoleFragment hole_4 = HoleFragment.newInstance("4","3", "90m", "hole4");
        HoleFragment hole_5 = HoleFragment.newInstance("5","3", "150m", "hole5");
        HoleFragment hole_6 = HoleFragment.newInstance("6","3", "155", "hole6");
        HoleFragment hole_7 = HoleFragment.newInstance("7","3", "80m", "hole7");
        HoleFragment hole_8 = HoleFragment.newInstance("8","3", "150m", "hole8");
        HoleFragment hole_9 = HoleFragment.newInstance("9","3", "120m", "hole9");


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position){
                case 0 : return hole_1;
                case 1 : return hole_2;
                case 2 : return hole_3;
                case 3 : return hole_4;
                case 4 : return hole_5;
                case 5 : return hole_6;
                case 6 : return hole_7;
                case 7 : return hole_8;
                case 8 : return hole_9;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 9;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }

        public ArrayList getHole(){
            ArrayList<HoleFragment> holes = new ArrayList<HoleFragment>();
            holes.add(hole_1);
            holes.add(hole_2);
            holes.add(hole_3);
            holes.add(hole_4);
            holes.add(hole_5);
            holes.add(hole_6);
            holes.add(hole_7);
            holes.add(hole_8);
            holes.add(hole_9);
            return holes;
        }
    }
}
