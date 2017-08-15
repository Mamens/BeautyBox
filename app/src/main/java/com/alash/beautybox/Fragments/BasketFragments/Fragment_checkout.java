package com.alash.beautybox.Fragments.BasketFragments;

import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alash.beautybox.DataBaseHelper;
import com.alash.beautybox.Fragments.Fragments;
import com.alash.beautybox.Fragments.Product;
import com.alash.beautybox.Fragments.Total_cost;
import com.alash.beautybox.R;
import com.alash.beautybox.adapter.GridViewAdapter2;
import java.util.ArrayList;
import java.util.List;

public class Fragment_checkout extends Fragment implements Total_cost, Fragments {
    Fragments fragments;
    final public String TAG = "Fragment_basket";
    ListView listView;
    ArrayList<String> names;
    ArrayList<String> price;
    ArrayList<String> photo;
    ArrayList<String> product_id;
    ArrayList<String> amount;
    int total_price;
    int count;
    DataBaseHelper dataBaseHelper;
    public List<Product> AllProducts;
    GridView gridView4;
    TextView TextView2_from_basket;
    Button button2_from_fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Product product;
    RelativeLayout fragment_checkout_relative_layout2, relativelayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_checkout, null);
        final  Fragment_ordering fragment_ordering = new Fragment_ordering();
        total_price = 0;
        final Fragment_checkout fragment_checkout = new Fragment_checkout();
        fragment_checkout.setFragment(fragments);
        dataBaseHelper = new DataBaseHelper(getActivity());
        product = new Product();
        names = new ArrayList<>();
        price = new ArrayList<>();
        photo = new ArrayList<>();
        product_id = new ArrayList<>();
        amount = new ArrayList<>();
        fragment_checkout_relative_layout2 = (RelativeLayout) v.findViewById(R.id.fragment_checkout_relative_layout2);
        relativelayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);
        final BottomNavigationView bottomNavigationview = (BottomNavigationView) getActivity().findViewById(R.id.navi);
        AllProducts = new ArrayList<>();
        dataBaseHelper = new DataBaseHelper(getActivity());
        parse_data();

        GridViewAdapter2 gridViewAdapter2 = new GridViewAdapter2(getContext(), photo, names, price, amount,this, this);
        gridView4 =(GridView) v.findViewById(R.id.gridView_checkout4);
        gridView4.setAdapter(gridViewAdapter2);

        TextView2_from_basket = (TextView) v.findViewById(R.id.TextView2_fragment_checkout);
        Total_cost();

        button2_from_fragment = (Button) v.findViewById(R.id.button_fragment_checkout);
        button2_from_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragments !=null){
                    fragments.show(fragment_ordering);
                }
                else Toast.makeText(getActivity(), "Fragment is null", Toast.LENGTH_LONG).show();
            }
        });

        fragment_checkout_relative_layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_checkout fragmentCheckout = new Fragment_checkout();
                Fragment_Basket fragmentBasket = new Fragment_Basket();
                if(dataBaseHelper.getAllUser().size() >=1)fragments.show(fragmentCheckout);
                else fragments.show(fragmentBasket);

            }
        });
        return v;

    }

    private void parse_data() {
        for(int i = 0; i<dataBaseHelper.getAllUser().size(); i++){
            names.add(dataBaseHelper.getAllUser().get(i).getName());
            price.add(dataBaseHelper.getAllUser().get(i).getPrice());
            photo.add(dataBaseHelper.getAllUser().get(i).getPhoto());
            product_id.add(dataBaseHelper.getAllUser().get(i).getProduct_id());
            amount.add(dataBaseHelper.getAllUser().get(i).getAmount());
        }
    }

    public void setFragment(Fragments fragments) {
        this.fragments = fragments;
    }
    public  void Total_cost(){
        for (int a=0; a<price.size(); a++){
         total_price += Integer.parseInt(price.get(a))* Integer.parseInt(amount.get(a));
        }
        TextView2_from_basket.setText(""+total_price);
    }


    @Override
    public void refresh(String b) {
        DataBaseHelper dataBaseHelper2 = new DataBaseHelper(getActivity());
        if (b.contentEquals("go")){
            total_price =0;
            for (int a =0; a<dataBaseHelper2.getAllUser().size();a++){
                total_price += Integer.parseInt(dataBaseHelper2.getAllUser().get(a).getPrice()) * Integer.parseInt(dataBaseHelper2.getAllUser().get(a).getAmount());

            }
            TextView2_from_basket.setText(""+total_price);

        }

    }

    @Override
    public void show(Fragment data) {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, data);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
}
