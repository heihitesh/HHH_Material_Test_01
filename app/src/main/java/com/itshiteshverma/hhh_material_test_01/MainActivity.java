package com.itshiteshverma.hhh_material_test_01;


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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itshiteshverma.hhh_material_test_01.Vector.VectorDrawable;
import com.itshiteshverma.hhh_material_test_01.fragments.NavigationDrawerFragment;
import com.itshiteshverma.hhh_material_test_01.network.VolleySingleton;
import com.itshiteshverma.hhh_material_test_01.tabs.SlidingTabLayout;


public class MainActivity extends AppCompatActivity {



    private Toolbar toolbar;
    ViewPager mPager;
    Button im;
    SlidingTabLayout mTabs;
    public static final int MOVIES_SEARCH_RESULT =0;
    public static final int MOVIES_HIT = 1;
    public static final int MOVIES_UPCOMMING= 2;
    public static final String themoviedbAPI="016a5d901dfcfe7a4c5a854b4c8e29a9";

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

        im = (Button) findViewById(R.id.bimage);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, VectorDrawable.class);
                startActivity(i);
            }
        });

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
        else if (id == R.id.tabview){
            Intent i = new Intent(MainActivity.this,TabViewLibrary.class);
            startActivity(i);
        }


        else if (id == R.id.tabviewwithimages){
            Intent i = new Intent(MainActivity.this,TabViewLibraryWithImages.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    class MyPageViewer extends FragmentPagerAdapter {

        //for images
        int icons[] = {R.drawable.gmail, R.drawable.drive, R.drawable.vector_image};


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
        } //3 means total no of tab is 3
    }


}
