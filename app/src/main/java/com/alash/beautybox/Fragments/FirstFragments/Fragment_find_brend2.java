package com.alash.beautybox.Fragments.FirstFragments;

import android.app.ProgressDialog;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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

public class Fragment_find_brend2 extends Fragment {
    static int a;
    public static final String DATA_URL = "https://private-05da16-beautybox.apiary-mock.com/api/List/BrandWithPhoto?id="+a;
    public static final String TAG_IMAGE_URL = "photo";
    public static final String TAG_NAME = "name";
    public static final String TAG_ID = "id";
    private ArrayList<String> images;
    private ArrayList<String> names;
    private GridView gridView_brend;
    int s[];
    Fragments fragments;
    Fragment_tovars_brends3 fragment_tovars_brends;
    Fragment_description4 fragment_description;



//    public static Fragment_find_brend2 newInstance() {
//        Fragment_find_brend2 fragment = new Fragment_find_brend2();
//        return fragment;
//    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_find_brend, null);
        fragment_tovars_brends = new Fragment_tovars_brends3();
        fragment_tovars_brends.setFragment(fragments);
        //fragment_description = new Fragment_description4();
//       fragment_description.setFragment(fragments);
        gridView_brend = (GridView) v.findViewById(R.id.gridView_brend);
        images = new ArrayList<>();
        names = new ArrayList<>();
        gridView_brend.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(fragments !=null){
//                    fragment_tovars_brends.setID(s[position]);
//                    fragments.show(fragment_tovars_brends);
//                }
                if(fragments != null){
                    fragment_tovars_brends.setID(s[position]);
                    //fragments.show(fragment_tovars_brends);
                }
            }
        });
        getData();
        return v;
    }
    private void getData(){
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Please wait...","Fetching data....",false, false);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL ,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing the progressdialog on response
                        loading.dismiss();

                        //Displaying our grid
                        //showGrid(response);
                        try {
                            showGrid(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
//    private String getApi() {
//        return "http://api.admin-beautybox.kz/api/List/Product?brandid=" + this.brandId + "&name=&orderby=name&type=";
//    }
    private void showGrid(JSONArray jsonArray)throws JSONException {
        //Looping through all the elements of json array
        s = new int[jsonArray.length()];
        for(int i = 0; i<jsonArray.length(); i++){
            //Creating a json object of the current index
            JSONObject obj = null;

                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object
                images.add( "http://admin-beautybox.kz" + obj.getString(TAG_IMAGE_URL));
                names.add(obj.getString(TAG_NAME));
                //String a2 = obj.getString(TAG_ID);
                s[i] = obj.getInt("id");
            }

        //Creating GridViewAdapter Object
        GridViewAdapter gridViewAdapter = new GridViewAdapter(getContext(),images,names );

        //Adding adapter to gridview
        gridView_brend.setAdapter(gridViewAdapter);
    }

    public static void getId(int s) {
        a = s;
    }




    public void setFragment(Fragments fragment) {
        this.fragments = fragment;
    }


}


