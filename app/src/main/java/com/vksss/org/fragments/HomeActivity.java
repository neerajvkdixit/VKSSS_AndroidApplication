package com.vksss.org.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import com.vksss.org.R;
import com.vksss.org.R.id;
import com.vksss.org.R.layout;
import com.vksss.org.R.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class HomeActivity extends Fragment {
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	        ViewGroup root = (ViewGroup) inflater.inflate(layout.activity_home, null);
	        return root;
	    }
}
