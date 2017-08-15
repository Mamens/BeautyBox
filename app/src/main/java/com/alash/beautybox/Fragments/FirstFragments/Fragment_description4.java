package com.alash.beautybox.Fragments.FirstFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.alash.beautybox.DataBaseHelper;
import com.alash.beautybox.Fragments.Fragments;
import com.alash.beautybox.Fragments.Product;
import com.alash.beautybox.R;
import com.alash.beautybox.adapter.CustomVolleyRequest;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import static android.content.Context.MODE_PRIVATE;


public class Fragment_description4 extends Fragment {
    ImageLoader imageloader;
    Fragments fragments;
    DataBaseHelper databaseHelper;
    Product product;
    String token;
    NetworkImageView networkImageView;
    BottomNavigationView bottomNavigationView;
    TextView TextView_fragment_description1;
    TextView TextView_fragment_description2;
    TextView TextView_fragment_description3;
    TextView TextView_fragment_description4;
    TextView TextView_fragment_description5;
    TextView TextView_fragment_description6;
    TextView TextView_fragment_description7;
    TextView TextView_fragment_description8;
    Button Button_fragment_description;
    String product_id, name, photo, price;
    String DATA_URL;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_description, null);

        //fragments.show(this);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Token", MODE_PRIVATE);
        token = sharedPreferences.getString("save_text","");
        databaseHelper = new DataBaseHelper(getActivity());
        product = new Product();
        TabHost tabHost = (TabHost) v.findViewById(R.id.tabhost_fragment_description);
        tabHost.setup();
        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setIndicator("Описание");
        tabSpec.setContent(R.id.tab1);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setIndicator("Состав");
        tabSpec.setContent(R.id.tab2);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setIndicator("Применение");
        tabSpec.setContent(R.id.tab3);
        tabHost.addTab(tabSpec);


        tabHost.setCurrentTabByTag("tag1");

        Button_fragment_description = (Button) v.findViewById(R.id.Button_fragment_description);
        Button_fragment_description.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(product_id !=null){
                    if(!databaseHelper.checkUser(product_id)){
                        product.setProduct_id(product_id);
                        product.setName(name);
                        product.setPhoto(photo);
                        product.setPrice(price);
                        databaseHelper.addProduct(product);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Добавлено в корзину!!!")
                                .setCancelable(false)
                                .setNegativeButton("Ok",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        })
                                .setPositiveButton("В корзину", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
                                        bottomNavigationView.setSelectedItemId(R.id.menu_search);
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();



                    }else
                        Toast.makeText(getContext(), "This product is exist in basket",Toast.LENGTH_LONG).show();

                }
                else Toast.makeText(getContext(), "ошибка при загрузке данных  с сервера \n Просим попробовать позже", Toast.LENGTH_LONG).show();

            }
        });

        TextView_fragment_description1 = (TextView) v.findViewById(R.id.TextView_fragment_description1);
        TextView_fragment_description2 = (TextView) v.findViewById(R.id.TextView_fragment_description2);
        TextView_fragment_description3 = (TextView) v.findViewById(R.id.TextView_fragment_description3);
        TextView_fragment_description4 = (TextView) v.findViewById(R.id.TextView_fragment_description4);
        TextView_fragment_description5 = (TextView) v.findViewById(R.id.TextView_fragment_description5);
        TextView_fragment_description6 = (TextView) v.findViewById(R.id.TextView_fragment_description6);
        TextView_fragment_description6.setMovementMethod(new ScrollingMovementMethod());
        TextView_fragment_description7 = (TextView) v.findViewById(R.id.TextView_fragment_description7);
        TextView_fragment_description8 = (TextView) v.findViewById(R.id.TextView_fragment_description8);

        getData();
        return v;


    }

    private void getData() {
        final ProgressDialog loading = ProgressDialog.show(getContext(),"Пожалуйста поождите....", "Идет загрузка....", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, DATA_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                loading.dismiss();

                showGrid(response);
            }
        },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        loading.dismiss();
                        Toast.makeText(getActivity(), "Ошибка при загрузке данных  с сервера", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer" + token);
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    private  void showGrid(String response){
        try{
            JSONObject obj = new JSONObject(response);
            imageloader = CustomVolleyRequest.getInstance(getContext()).getImageLoader();
            imageloader.get("http://admin-beautybox.kz" + obj.getString("photo"), ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

            networkImageView.setImageUrl("http://admin-beautybox.kz"+obj.get("photo"),imageloader);
            TextView_fragment_description1.setText(obj.getString("name"));
            TextView_fragment_description2.setText(obj.getString("description"));
            TextView_fragment_description3.setText(obj.getString("price")+ "/шт");
            TextView_fragment_description4.setText(obj.getString("brand"));
            TextView_fragment_description5.setText(obj.getString("type"));
            TextView_fragment_description6.setText(obj.getString("sostav"));
            TextView_fragment_description7.setText(obj.getString("priminenie"));
            TextView_fragment_description8.setText("Категория: "+obj.getString("category"));
            product_id = obj.getString("id");
                   name = obj.getString("name");
            photo = "http://admin-beautybox.kz" + obj.getString("photo");
                   price = obj.getString("price");


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void setFragment() {
    }

    public void setFragment(Fragments fragments) {
        this.fragments = fragments;
    }

    public void setID(String s) {
        DATA_URL =  "http://api.admin-beautybox.kz/api/List/Product_detail?id=" + Integer.parseInt(s);

    }

    public void setID(int s) {
    }
}
