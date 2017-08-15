package com.alash.beautybox.Fragments.FirstFragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.alash.beautybox.Fragments.Fragments;
import com.alash.beautybox.R;
import com.alash.beautybox.adapter.GridViewAdapter;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class Fragment_kategory_tovars1 extends Fragment {


    public Fragment_kategory_tovars1() {
    }

    public static Fragment_kategory_tovars1 newInstance() {
        Fragment_kategory_tovars1 fragment = new Fragment_kategory_tovars1();
        return fragment;
    }
    int s[] ;
    public static final String DATA_URL = "http://api.admin-beautybox.kz/api/List/CategoryWithPhoto";
    public static final String TAG_IMAGE_URL = "photo";
    public static final String TAG_NAME = "name";
    public static final String TAG_ID = "id";
    private ArrayList<String> images;
    private ArrayList<String> names;
    private GridView gridView;
    public static final String TAG = "Fragment_kategory_tovars1";
    Fragments fragments;
    //public int[] a;
    Fragment_find_brend2 fragment_find_brend;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_kategory_tovars, null);
        fragment_find_brend = new Fragment_find_brend2();
        fragment_find_brend.setFragment(fragments);
        gridView = (GridView) v.findViewById(R.id.gridView);
        images = new ArrayList<>();
        names = new ArrayList<>();
        //Calling the getData method
        getData();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(fragments !=null){
                    Fragment_find_brend2.getId(s[position]);
                    fragments.show(fragment_find_brend);
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    String token = sharedPreferences.getString("TOKEN", "123");
                    Log.d("MYTOKEN:","TOKEN:" + token);
                }
                else Toast.makeText(getContext(), "Fragment is null",Toast.LENGTH_LONG).show();
            }
        });


        getData();
        return v;
    }

    private void getData() {
        //Showing a progress dialog while our app fetches the data from url
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...", "Fetching data...", false, false);

        //Creating a json array request to get the json from our api
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing the progressdialog on response
                        loading.dismiss();

                        //Displaying our grid
                        showGrid(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //Adding our request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    private void showGrid(JSONArray jsonArray) {
        s = new int[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);
                images.add("http://api.admin-beautybox.kz" + obj.getString(TAG_IMAGE_URL));
                names.add(obj.getString(TAG_NAME));
                //String a1 = obj.getString(TAG_ID);
                s[i] =obj.getInt(TAG_ID);
            } catch (JSONException e) {
                e.printStackTrace();
                //System.out.println("Error in ShowGrid");
            }
        }
        //Creating GridViewAdapter Object
        GridViewAdapter gridViewAdapter = new GridViewAdapter(getContext(), images, names);

        //Adding adapter to gridview
        gridView.setAdapter(gridViewAdapter);
    }
    public void setFragment(Fragments fragments){
        this.fragments = fragments;
    }


}

