package com.itshiteshverma.hhh_material_test_01;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.itshiteshverma.hhh_material_test_01.MyFragment;
import com.itshiteshverma.hhh_material_test_01.R;
import com.itshiteshverma.hhh_material_test_01.fragments.FragmentBoxOffice;
import com.itshiteshverma.hhh_material_test_01.fragments.FragmentSearch;
import com.itshiteshverma.hhh_material_test_01.fragments.FragmentUpComming;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by Nilesh Verma on 9/10/2015.
 * <p/>
 * //cahnge the import to android.support.v7.widget.Toolbar
 */
public class TabViewLibraryWithImages extends ActionBarActivity implements MaterialTabListener {

    Toolbar toolbar;
    private MaterialTabHost tabHost;
    private ViewPager viewPager;
    public static final int MOVIES_SEARCH_RESULT = 0;
    public static final int MOVIES_HIT = 1;
    public static final int MOVIES_UPCOMMING = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabviewlibrarywithimages);
        Log.d("HIT", "1");
        toolbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Log.d("HIT","2");


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        Log.d("HIT", "3");


        ///to update the tabs
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setSelectedNavigationItem(position);

            }
        });

        Log.d("HIT", "4");


        //adding the pages to the tab host
        for (int i = 0; i < adapter.getCount(); i++) {
            tabHost.addTab(
                    tabHost.newTab()
                            .setIcon(adapter.getIcon(i))
                            .setTabListener(this)
            );
        }
        Log.d("HIT","5");

    }

    @Override
    public void onTabSelected(MaterialTab materialTab) {

        viewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {


        int icons[] = {R.drawable.gmail, R.drawable.aa, R.drawable.vector_image};

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case MOVIES_SEARCH_RESULT:
                    return new FragmentSearch();
                case MOVIES_HIT:
                    return new FragmentBoxOffice();
                case MOVIES_UPCOMMING:
                    return new FragmentUpComming();
            }

            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int pos) {
            return getResources().getDrawable(icons[pos]);
        }
    }
}
