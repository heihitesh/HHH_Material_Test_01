package com.itshiteshverma.hhh_material_test_01;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.itshiteshverma.hhh_material_test_01.network.VolleySingleton;

/**
 * Created by Nilesh Verma on 9/10/2015.
 */public class MyFragment extends Fragment {

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

        RequestQueue requestQueue = VolleySingleton.getsInstance().getmRequestQueue();
        StringRequest request = new StringRequest(Request.Method.GET, "http://www.google.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(), "RESPONSE" + response.toString(), Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(),"ERROR"+error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

       // requestQueue.add(request);
        return layout;
    }
}
