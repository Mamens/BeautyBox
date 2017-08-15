package com.alash.beautybox.Fragments;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


import com.alash.beautybox.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Fragment_contacts extends Fragment implements View.OnClickListener{
    public String URL_DATA = "https://private-05da16-beautybox.apiary-mock.com/api/User/Callme";
    public Button btn_zakazat;
    SharedPreferences sharedPreferences;
    String token;
    String message;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getActivity().getSharedPreferences("Token",MODE_PRIVATE);
        token = sharedPreferences.getString("saved_text", "");
        View v = inflater.inflate(R.layout.activity_fragment_contacts, null);
        btn_zakazat  = (Button) v.findViewById(R.id.btn_zakazat);
        btn_zakazat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final ProgressDialog loading = ProgressDialog.show(getContext(), "Пожалйста подождите...","Идет загрузка...",false, false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_DATA,new Response.Listener<String>(){
                    @Override
                    public void onResponse(String s) {

                        try {
                            JSONArray jsonArray = new JSONArray(s);
                            for (int i = 0 ; i < jsonArray.length(); i++ ){
                                JSONObject obj = null;
                                obj = jsonArray.getJSONObject(i);
                                message += obj.getString("Message")+ "\n";
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getContext(), message,Toast.LENGTH_SHORT).show();
                        //loading.dismiss();
                    }
                }, new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG).show();
                    }


                }){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<String, String>();
                        headers.put("Authorization", "Bearer"+token);
                        return headers;
                    }};
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Вы заказали звонок!")
                        .setMessage("В ближайшиее время с вами свяжется наш менеджер.")
                        .setCancelable(false)
                        .setNegativeButton("Закрыть",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        return v;
    }



    @Override
    public void onClick(View v) {




    }


}