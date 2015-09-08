package com.itshiteshverma.hhh_material_test_01;


import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itshiteshverma.hhh_material_test_01.tabs.SlidingTabLayout;


public class MainActivity extends AppCompatActivity {



    private Toolbar toolbar;
    ViewPager mPager;
    SlidingTabLayout mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //$$ Use R.layout.activity_main_appbar for a different layout
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.appBar);
        // now we have to say android that we are not using ur toolbar
        // we are making our own toolbar
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true); // this will add a home button to the navigation drawer


        //calling the Fragment
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navifation_drawer);

        //self-Defined Method
        drawerFragment.setUp(R.id.fragment_navifation_drawer, (DrawerLayout) findViewById(R.id.Drawer_Layout), toolbar);


        ////for sliding tab layout
        mPager = (ViewPager) findViewById(R.id.ViewPager);
        mPager.setAdapter(new MyPageViewer(getSupportFragmentManager()));
        mTabs = (SlidingTabLayout) findViewById(R.id.SlidingTab);


        mTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        //custome layout for the images

        mTabs.setDistributeEvenly(true);


        //this method will make the tab of the color which is defined in color.xml
        mTabs.setBackgroundColor(getResources().getColor(R.color.primaryColor));
        mTabs.setSelectedIndicatorColors(getResources().getColor(R.color.accentColor));

        mTabs.setViewPager(mPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        // if(id == R.id.navigate){
        //  startActivity(new Intent(this,SubActivity.class));

//        }

        return super.onOptionsItemSelected(item);
    }

    class MyPageViewer extends FragmentPagerAdapter {

        //for images
        int icons[] = {R.drawable.gmail, R.drawable.drive, R.drawable.email};


        String[] tabsText = getResources().getStringArray(R.array.tabs);

        public MyPageViewer(FragmentManager fm) {
            super(fm);
            tabsText = getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            MyFragment myFragment = MyFragment.getInstance(position);

            return myFragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Drawable drawable = getResources().getDrawable(icons[position]);
            drawable.setBounds(0,0,56,56); //setting the icon size
            ImageSpan imageSpan = new ImageSpan(drawable);
            SpannableString spannableString = new SpannableString(" ");
            //this " " represent that we only want Image not text

            spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return spannableString;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public static class MyFragment extends Fragment {

        private TextView textView;

        public static MyFragment getInstance(int position) {
            MyFragment myFragment = new MyFragment();

            Bundle arg = new Bundle();
            arg.putInt("pos", position);
            myFragment.setArguments(arg);
            return myFragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View layout = inflater.inflate(R.layout.fragment_my, container, false);
            textView = (TextView) layout.findViewById(R.id.tvTabNo);
            Bundle bundle = getArguments();
            if (bundle != null) {

                textView.setText("The Page is " + bundle.getInt("pos"));
            }

            ///For VOLLEY API

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            StringRequest request = new StringRequest(Request.Method.GET, "http://www.google.com", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getActivity(),"RESPONSE"+response.toString(),Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getActivity(),"ERROR"+error.getMessage(),Toast.LENGTH_SHORT).show();

                }
            });

            requestQueue.add(request);
            return layout;
        }
    }

}
