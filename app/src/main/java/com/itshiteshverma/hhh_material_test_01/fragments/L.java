package com.itshiteshverma.hhh_material_test_01.fragments;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

/**
 * Created by Nilesh Verma on 9/11/2015.
 */
public class L {
    public static void toast(Context activity, String s) {
        Toast.makeText(activity,s+" ",Toast.LENGTH_LONG).show();
    }
}
