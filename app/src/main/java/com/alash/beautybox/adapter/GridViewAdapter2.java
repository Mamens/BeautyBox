package com.alash.beautybox.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alash.beautybox.DataBaseHelper;
import com.alash.beautybox.Fragments.BasketFragments.Fragment_Basket;
import com.alash.beautybox.Fragments.BasketFragments.Fragment_checkout;
import com.alash.beautybox.Fragments.Fragments;
import com.alash.beautybox.Fragments.Product;
import com.alash.beautybox.Fragments.Total_cost;
import com.alash.beautybox.R;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;


public class GridViewAdapter2 extends BaseAdapter {

    //Imageloader to load images
    private ImageLoader imageLoader;
    private  View gridViewV;

    //Context
    private Context context;

    //Array List that would contain the urls and the titles for the images
    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> opic;
    private ArrayList<String> count;
    private Total_cost total_cost;
    private Fragments fragments;

    public GridViewAdapter2 (Context context, ArrayList<String> images,ArrayList<String> names ,ArrayList<String> opic, ArrayList<String> count, Total_cost total_cost, Fragments fragments){
        //Getting all the values
        this.context = context;
        this.images = images;
        this.opic = opic;
        this.count = count;
        this.total_cost = total_cost;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        //Creating a linear layout
//        LinearLayout linearLayout = new LinearLayout(context);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView ==null) {
            gridViewV = new View(context);

            gridViewV = inflater.inflate(R.layout.activity_from_basket, null);

            NetworkImageView networkImageView = (NetworkImageView) gridViewV.findViewById(R.id.from_basket_imageview);
            //Initializing ImageLoader
            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            imageLoader.get(images.get(position), ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));


            //Setting the image url to load
            networkImageView.setImageUrl(images.get(position), imageLoader);


            final TextView textView_result = (TextView) gridViewV.findViewById(R.id.form_of_basket1_result);
            textView_result.setTag("result" + position);
            textView_result.setText(count.get(position));

            ImageView from_basket_imageview2 = (ImageView) gridViewV.findViewById(R.id.from_basket_imageview2);
            from_basket_imageview2.setTag("10" + position);
            from_basket_imageview2.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(v.getContext());
                    Product product1 = new Product();
                    if(dataBaseHelper.checkUser(dataBaseHelper.getAllUser().get(position).getProduct_id())){
                        dataBaseHelper.deleteUser1(dataBaseHelper.getAllUser().get(position));
                    }
                    else Toast.makeText(v.getContext(), "Продукт успешно удален", Toast.LENGTH_LONG).show();
                        Fragment_checkout fragment_checkout = new Fragment_checkout();
                        Fragment_Basket fragment_basket = new Fragment_Basket();
                    if(!dataBaseHelper.checkUser1()){
                        fragments.show(fragment_basket);
                    }
                    else {
                        fragment_checkout.setFragment(fragments);
                        fragments.show(fragment_checkout);

                    }
                    total_cost.refresh("go");
                }
            });

            ImageView from_basket_imageview_up = (ImageView) gridViewV.findViewById(R.id.from_basket1_imageview_up);
            from_basket_imageview_up.setTag("up"+position);
            from_basket_imageview_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(v.getContext());
                    Product product1 = new Product();
                    if (dataBaseHelper.getAllUser().get(position).getAmount() == null || Integer.parseInt(dataBaseHelper.getAllUser().get(position).getAmount())<1) {
                        product1.setAmount(""+1);
                        dataBaseHelper.updateUser(product1);
                    }
                    else {
                        product1.setProduct_id(dataBaseHelper.getAllUser().get(position).getProduct_id());
                        product1.setAmount("" + (Integer.parseInt(dataBaseHelper.getAllUser().get(position).getAmount()) + 1));
                        dataBaseHelper.updateUser(product1);

                    }
                        textView_result.setText(dataBaseHelper.getAllUser().get(position).getAmount());

                        total_cost.refresh("go");

                }
            });

            ImageView from_basket_imageview_down = (ImageView) gridViewV.findViewById(R.id.from_basket1_imageview_down);
            from_basket_imageview_down.setTag("down"+position);
            from_basket_imageview_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataBaseHelper dataBaseHelper = new DataBaseHelper(v.getContext());
                    Product product1 = new Product();
                    if (dataBaseHelper.getAllUser().get(position).getAmount() == null || Integer.parseInt(dataBaseHelper.getAllUser().get(position).getAmount()) < 1) {
                        product1.setAmount("" + 1);
                        dataBaseHelper.updateUser(product1);
                    } else {
                        product1.setProduct_id(dataBaseHelper.getAllUser().get(position).getProduct_id());
                        product1.setAmount("" + (Integer.parseInt(dataBaseHelper.getAllUser().get(position).getAmount()) + -1));
                        dataBaseHelper.updateUser(product1);
                        textView_result.setText(count.get(position));
                    }
                    textView_result.setText(dataBaseHelper.getAllUser().get(position).getAmount());
                    total_cost.refresh("go");
                }
            });

                TextView textView = (TextView) gridViewV.findViewById(R.id.from_basket_editText);
                textView.setText(names.get(position));

                TextView textView3 = (TextView) gridViewV.findViewById(R.id.from_basket_editText2);
                textView3.setText(opic.get(position));

            }
                    else{
                gridViewV = (View) convertView;


        }
            return gridViewV;

    }
}