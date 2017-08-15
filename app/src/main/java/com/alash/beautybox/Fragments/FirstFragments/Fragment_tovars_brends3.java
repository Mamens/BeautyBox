package com.alash.beautybox.Fragments.FirstFragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alash.beautybox.Fragments.Fragments;
import com.alash.beautybox.R;
import com.alash.beautybox.adapter.CustomVolleyRequest;
import com.alash.beautybox.adapter.GridViewAdapter;
import com.alash.beautybox.disain.ExpandableGridview;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_tovars_brends3 extends Fragment {
    ImageLoader imageLoader;
    String DATA_URL, s1, description, photo_1, token;
    Fragments fragments;
    ArrayList<String> name, name1, name2, id_number, id_number1, id_number2, head1, photo, photo1, photo2;
    NetworkImageView networkImageView1;
    TextView tovars_brends_TextView1,tovars_brends_TextView2, tovars_brends_TextView3, tovars_brends_TextView4;
    ExpandableGridview ExpandableGridview1, ExpandableGridview2, ExpandableGridview3;
    RelativeLayout  tovars_brends_Textview2_RL, tovars_brends_Textview3_RL, tovars_brends_Textview4_RL;


    public static Fragment_tovars_brends3 newInstance() {
        Fragment_tovars_brends3 fragment = new Fragment_tovars_brends3();
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.activity_fragment_tovars_brends, null);
        final Fragment_description4 fragment_description = new Fragment_description4();
        fragment_description.setFragment(fragments);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Token", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("save_text","");

        ExpandableGridview1 = (ExpandableGridview) v.findViewById(R.id.ExpandableGridview1);
        ExpandableGridview2 = (ExpandableGridview) v.findViewById(R.id.ExpandableGridview2);
        ExpandableGridview3 = (ExpandableGridview) v.findViewById(R.id.ExpandableGridview3);
        tovars_brends_Textview2_RL = (RelativeLayout) v.findViewById(R.id.tovars_brends_TextView2_RL);
        tovars_brends_Textview3_RL = (RelativeLayout) v.findViewById(R.id.tovars_brends_TextView3_RL);
        tovars_brends_Textview3_RL = (RelativeLayout) v.findViewById(R.id.tovars_brends_TextView4_RL);
        photo = new ArrayList<>();
        photo1 = new ArrayList<String>();
        photo2 = new ArrayList<String>();
        name = new ArrayList<>();
        name1 = new ArrayList<String>();
        name2 = new ArrayList<String>();
        id_number = new ArrayList<String>();
        id_number1 = new ArrayList<String>();
        id_number2 = new ArrayList<String>();
        head1 = new ArrayList<>();
        tovars_brends_TextView1 = (TextView) v.findViewById(R.id.tovars_brends_TextView1);
        tovars_brends_TextView2 = (TextView) v.findViewById(R.id.tovars_brends_TextView2);
        tovars_brends_TextView3 = (TextView) v.findViewById(R.id.tovars_brends_TextView3);
        tovars_brends_TextView4 = (TextView) v.findViewById(R.id.tovars_brends_TextView4);
        networkImageView1 = (NetworkImageView) v.findViewById(R.id.tovars_brends_networkimagview1);

        ExpandableGridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fragments != null) {
                    fragment_description.setID(id_number.get(position));
                    fragments.show(fragment_description);
                }
                else Toast.makeText(getContext(), "Fragment is null", Toast.LENGTH_SHORT).show();
            }
        });

        ExpandableGridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fragments != null) {
                    fragment_description.setID(id_number.get(position));
                    fragments.show(fragment_description);
                }
                else Toast.makeText(getContext(), "Fragment is null", Toast.LENGTH_SHORT).show();
            }
        });
        ExpandableGridview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (fragments != null) {
                    fragment_description.setID(id_number.get(position));
                    fragments.show(fragment_description);
                }
                else Toast.makeText(getContext(), "Fragment is null", Toast.LENGTH_SHORT).show();
            }
        });

        getData();
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getData() {
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Пожалуйста подождите идет....", "Идет загрузка....", false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DATA_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    s1 = new String(s.getBytes("ISO-8859-1"),"UTF-8");
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();

                }
                showGrid(s);
                loading.dismiss();
            }
        }, new Response.ErrorListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(),"Error", Toast.LENGTH_LONG).show();
                loading.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization","Bearer" + token);
                return super.getHeaders();
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }



    public void getId(int a){
        DATA_URL = "http://api.admin-beautybox.kz/api/List/Product?brandid="+a+"&name=&orderby=&type=";
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showGrid(String s) {
        //Looping through all the elements of json array

        JSONObject jsonObject = null;
//        new String(jo.getString("name").getBytes("ISO-8859-1"), "UTF-8");
        try {
            jsonObject = new JSONObject(s);

            description = jsonObject.getString("description");
            photo_1 = "http://admin-beautybox.kz" + jsonObject.getString("photo");

            JSONArray jsonArray2 = jsonObject.getJSONArray("products");


            JSONObject obj = jsonArray2.getJSONObject(0);
            head1.add(obj.getString("Groupname"));
            JSONArray jsonArray1 = obj.getJSONArray("Elements");
            for (int u = 0; u < jsonArray1.length(); u++) {
                JSONObject obj1 = jsonArray1.getJSONObject(u);
                id_number.add(obj1.getString("id"));
                name.add(obj1.getString("name"));
                photo.add("http://admin-beautybox.kz" + obj1.getString("sphoto"));
            }
            if (jsonArray1.length() < 1) {
                tovars_brends_Textview2_RL.setVisibility(View.INVISIBLE);
                ExpandableGridview1.setVisibility(View.INVISIBLE);
            }


            JSONObject obj01 = jsonArray2.getJSONObject(1);
            head1.add(obj01.getString("Groupname"));
            JSONArray jsonArray01 = obj01.getJSONArray("Elements");
            for (int u = 0; u < jsonArray01.length(); u++) {
                JSONObject obj1 = jsonArray01.getJSONObject(u);
                id_number1.add(obj1.getString("id"));
                name1.add(obj1.getString("name"));
                photo1.add("http://admin-beautybox.kz" + obj1.getString("sphoto"));
            }
            if (jsonArray01.length() < 1) {
                tovars_brends_Textview3_RL.setVisibility(View.INVISIBLE);
                ExpandableGridview2.setVisibility(View.INVISIBLE);
            }

            JSONObject obj02 = jsonArray2.getJSONObject(2);
            head1.add(obj02.getString("Groupname"));
            JSONArray jsonArray02 = obj02.getJSONArray("Elements");
            for (int u = 0; u < jsonArray02.length(); u++) {
                JSONObject obj1 = jsonArray02.getJSONObject(u);
                id_number2.add(obj1.getString("id"));
                name2.add(obj1.getString("name"));
                photo2.add("http://admin-beautybox.kz" + obj1.getString("sphoto"));
            }
            if (jsonArray02.length() < 1) {
                tovars_brends_Textview4_RL.setVisibility(View.INVISIBLE);
                ExpandableGridview3.setVisibility(View.INVISIBLE);
            }

        } catch (JSONException e1) {
            e1.printStackTrace();

        }
        GridViewAdapter gridViewAdapter = new GridViewAdapter(getContext(),photo, name);
        GridViewAdapter gridViewAdapter01 = new GridViewAdapter(getContext(),photo1, name1);
        GridViewAdapter gridViewAdapter02 = new GridViewAdapter(getContext(),photo2, name2);

        ExpandableGridview1.setAdapter(gridViewAdapter);
        ExpandableGridview1.setExpanded(true);

        ExpandableGridview2.setAdapter(gridViewAdapter01);
        ExpandableGridview2.setExpanded(true);

        ExpandableGridview3.setAdapter(gridViewAdapter02);
        ExpandableGridview3.setExpanded(true);

        tovars_brends_TextView1.setText(""+description);
        tovars_brends_TextView2.setText(head1.get(0));
        tovars_brends_TextView3.setText(head1.get(1));
        tovars_brends_TextView4.setText(head1.get(2));

        imageLoader = CustomVolleyRequest.getInstance(getContext()).getImageLoader();
        imageLoader.get(photo_1, ImageLoader.getImageListener(networkImageView1, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        networkImageView1.setImageUrl(photo_1, imageLoader);

    }

    public void setFragment(Fragments fragments) {
        this.fragments = fragments;
    }



    public void setID(int s) {
    }
}
