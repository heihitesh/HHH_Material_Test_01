package com.itshiteshverma.hhh_material_test_01.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import Adapter.HitAdapter;
import com.itshiteshverma.hhh_material_test_01.Information;
import com.itshiteshverma.hhh_material_test_01.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
///$$$$ always import import android.support.v4.app.Fragment; this fragment package
//$$ and for toolbar  import android.support.v7.widget.Toolbar;

public class NavigationDrawerFragment extends Fragment {



    private RecyclerView recyclerView;

    private static final String PREF_FILE_NAME = "test";
    private static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";
    private ActionBarDrawerToggle mdrawerToogle;
    private DrawerLayout mDrawerlayout;
    private boolean mUserLearnedDrawer;  //indicates weather the user is aware of the drawer or not
    private boolean mFromSavedInstanceState; //indicates wheater the fragment is started for the very first time or orientation is changed

    private HitAdapter adapter;

    private  View  ContainerView;
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //reading the value form the preferences
      mUserLearnedDrawer= Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,"false"));
        //1)context ,2)Name of the Pref ,3)default value

        if(savedInstanceState != null){ // this means that the app is comming back from a roatation
            mFromSavedInstanceState = true;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        //adding the recycler view to the java code to manupulate it
        //recycler view needs a layoutmanager
       recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);

        adapter = new HitAdapter(getActivity(),getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));  //very important this sets the layout of the list to be vertical Linear layout

         return layout;
       // return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

    }


    public List<Information> getData() {
        //load only static data inside a drawer
        List<Information> data = new ArrayList<>();
        int[] icons = {R.drawable.gmail, R.drawable.drive,R.drawable.phone};
        String[] titles = {"Gmail","Drive","Phone"};
        for (int i = 0; i < titles.length; i++) {
            Information information = new Information();
            information.title = titles[i];
            information.iconId = icons[i];
            data.add(information);
        }
        return data;
    }


    public void setUp(int FragmentID ,DrawerLayout drawerLayout, final Toolbar toolbar) {
        ContainerView = getActivity().findViewById(FragmentID);

        mDrawerlayout = drawerLayout;
        mdrawerToogle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!mUserLearnedDrawer){ // if the user has never seen the navigationdrawer
                    mUserLearnedDrawer = true;
                    savedToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER,mUserLearnedDrawer+"");

                }
                getActivity().invalidateOptionsMenu(); // it will partially hide the actionbar

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu(); // it will partially hide the actionbar
            }
            // this will change the alpha of the action bar acc to the slide

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if(slideOffset<0.6){
                    toolbar.setAlpha(1-slideOffset);
                }
            }
        };
        //takes the parameter Activity , Layout, toolbar , open and close

        if(!mUserLearnedDrawer) // the user has never seen the drawer and if it isthe veryfirst time in that case display the drawer
        {
            mDrawerlayout.openDrawer(ContainerView);
        }

        //implemnt the listner veryImportant
        mDrawerlayout.setDrawerListener(mdrawerToogle);

        mDrawerlayout.post(new Runnable() { // this method is for the HAMBURGER SYMBOL

            @Override
            public void run() {
                mdrawerToogle.syncState();
            }
        });
    }

    public static void savedToPreferences(Context context, String preferencesName, String preferenceValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(preferencesName, preferenceValue);
        editor.apply(); // using apply() instead of commit(); ,because its much more fast
    }

    public static String readFromPreferences(Context context, String preferencesName, String preferenceValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        return sharedPref.getString(preferencesName, preferenceValue);
    }
}
