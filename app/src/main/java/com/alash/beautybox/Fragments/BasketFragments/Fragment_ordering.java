package com.alash.beautybox.Fragments.BasketFragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alash.beautybox.DataBaseHelper;
import com.alash.beautybox.Fragments.Product;
import com.alash.beautybox.Fragments.Total_cost;
import com.alash.beautybox.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Fragment_ordering extends Fragment {
    EditText fragment_ordering_name1, fragment_ordering_phone2,
            fragment_ordering_city3, fragment_ordering_street4,fragment_ordering_flat5,
            fragment_ordering_home6, fragment_ordering_additionally7;
    TextInputLayout textInputLayout1,textInputLayout2,
            textInputLayout3, textInputLayout4, textInputLayout5,
            textInputLayout6, textInputLayout7;
    Button button_ordering;
    public String URL_DATA="https://private-05da16-beautybox.apiary-mock.com/api/Product/AddOrder";
    DataBaseHelper dataBaseHelper;
    char[] products1;
    Product product;
    String description, phone, products, count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_fragment_ordering, null);


        dataBaseHelper = new DataBaseHelper(getActivity());
        product = new Product();
        Toast.makeText(getContext(), "" + dataBaseHelper.getAllUser().size(), Toast.LENGTH_LONG).show();

        textInputLayout1 = (TextInputLayout) v.findViewById(R.id.fragment_ordering_inputlayout1);
        textInputLayout2 = (TextInputLayout) v.findViewById(R.id.fragment_ordering_inputlayout2);
        textInputLayout3 = (TextInputLayout) v.findViewById(R.id.fragment_ordering_inputlayout3);
        textInputLayout4 = (TextInputLayout) v.findViewById(R.id.fragment_ordering_inputlayout4);
        textInputLayout5 = (TextInputLayout) v.findViewById(R.id.fragment_ordering_inputlayout5);
        textInputLayout6 = (TextInputLayout) v.findViewById(R.id.fragment_ordering_inputlayout6);
        textInputLayout7 = (TextInputLayout) v.findViewById(R.id.fragment_ordering_inputlayout7);

        fragment_ordering_name1 = (EditText) v.findViewById(R.id.fragment_ordering_name1);
        fragment_ordering_phone2 = (EditText) v.findViewById(R.id.fragment_ordering_phone2);
        fragment_ordering_city3 = (EditText) v.findViewById(R.id.fragment_ordering_city3);
        fragment_ordering_street4 = (EditText) v.findViewById(R.id.fragment_ordering_street4);
        fragment_ordering_flat5 = (EditText) v.findViewById(R.id.fragment_ordering_flat5);
        fragment_ordering_home6 = (EditText) v.findViewById(R.id.fragment_ordering_home6);
        fragment_ordering_additionally7 = (EditText) v.findViewById(R.id.fragment_ordering_additionally7);

        button_ordering = (Button) v.findViewById(R.id.button_ordering);
        button_ordering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              prepare_data();


            }
        });


        return v;



    }

    public String redict(String a, int b){
        char[] a1 = new char [a.length()];
        a1 = a.toCharArray();
        a ="";
        for(int i =0; i< a1.length - b; i++){
            a+=a1[i];
        }
        return a;
    }
    private void prepare_data(){
        phone = fragment_ordering_phone2.getText().toString();
        products = "";
        count ="";

        description = fragment_ordering_street4.getText().toString();
        for(int a = 0; a< dataBaseHelper.getAllUser().size(); a++){
            products += dataBaseHelper.getAllUser().get(a).getProduct_id()+",";
            count += dataBaseHelper.getAllUser().get(a).getAmount()+",";
        }
        products = redict(products,1);
        count = redict(count, 1);
        Toast.makeText(getContext(), products, Toast.LENGTH_LONG).show();

        check_order_by();
    }

    private void check_order_by(){
        if(fragment_ordering_city3.toString().isEmpty()){textInputLayout3.setError("Введите город"); return;}
        else textInputLayout3.setErrorEnabled(false);
        if(fragment_ordering_street4.toString().isEmpty()){textInputLayout4.setError("Введите улицу"); return;}
        else textInputLayout4.setErrorEnabled(false);
        if(fragment_ordering_flat5.toString().isEmpty()){textInputLayout5.setError("Введите Квартиру"); return;}
        else textInputLayout5.setErrorEnabled(false);
        if(fragment_ordering_home6.toString().isEmpty()){textInputLayout6.setError("Введите свой дом"); return;}
        else textInputLayout6.setErrorEnabled(false);
        if(fragment_ordering_additionally7.toString().isEmpty()){textInputLayout7.setError("Введите город"); return;}
        else textInputLayout7.setErrorEnabled(false);
        if (description.isEmpty()) {
            textInputLayout4.setError("Комментарий");return;}
        else textInputLayout4.setErrorEnabled(false);
        if (phone.replace("+","").toCharArray().length<11){textInputLayout2.setError("Номер телефона"); return;} else textInputLayout2.setErrorEnabled(false);
        if (count.isEmpty()) return;
        Toast.makeText(getContext(), "OK3", Toast.LENGTH_LONG).show();


    }
    public void  order_by(){
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Пожалуйста подождите...","Идеть загрузка...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DATA, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                try {
                    s = new String(s.getBytes("ISO-8859-1"), "UTF-8");
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i =0; i<jsonArray.length(); i++){
                        JSONObject obj = null;
                        obj = jsonArray.getJSONObject(i);
                        if(obj.getString("Message").equals("Заказ успешно добавлен")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Спасибо ваш заказ принят!")
                                    .setMessage("В ближайщее время с вами свяжется\n наш менеджер")
                                    .setCancelable(false)
                                    .setNegativeButton("Закрыть",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int id) {
                                                    BottomNavigationView nav  = (BottomNavigationView) getActivity().findViewById(R.id.navigation);
                                                    nav.setSelectedItemId(R.id.menu_home);
                                                    dialog.cancel();
                                                }
                                            });
                            AlertDialog alert  = builder.create();
                            alert.show();
                            String[] delete = products.split(",");
                            for (String aDelete : delete){
                                product.setProduct_id(aDelete);
                                dataBaseHelper.deleteUser1(product);
                            }
                        }
                        else Toast.makeText(getContext(), obj.getString("Message"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loading.dismiss();
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "ZОшибка с сервером попробуте по позже!", Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("city", fragment_ordering_city3.toString());
                params.put("street", fragment_ordering_street4.toString());
                params.put("house_number", fragment_ordering_home6.toString());
                params.put("flat_salon", fragment_ordering_additionally7.toString());
                params.put("description", description);
                params.put("phonenumber", phone);
                params.put("products", products);
                params.put("count", count);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }


}
