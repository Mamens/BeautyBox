package com.alash.beautybox.Sign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alash.beautybox.DisplayScreen;
import com.alash.beautybox.Fragments.MainActivity;
import com.alash.beautybox.R;
import com.alash.beautybox.disain.widget;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    String URL_POS_LOGIN = "http://api.admin-beautybox.kz/Token";
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutPhone;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextPhone;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatButton textViewLinkRegister;

    private InputValidation inputValidation;
    //private DataBaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutPhone = (TextInputLayout) findViewById(R.id.textInputLayoutPhone);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextPhone = (TextInputEditText) findViewById(R.id.textInputEditTextPhone);

        textInputEditTextPhone.addTextChangedListener(new widget(textInputEditTextPhone));

        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatButton) findViewById(R.id.textViewLinkRegister);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        //databaseHelper = new DataBaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
        public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                postlogin();
                Intent MainFragments = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(MainFragments);

                break;
            case R.id.appCompatApi:
                postlogin();
                Intent displayScreen = new Intent(LoginActivity.this, DisplayScreen.class);
                startActivity(displayScreen);

                break;






            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private  void volley(){

        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_POS_LOGIN, new Response.Listener<String>(){

            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
                save_text(s);
            }
            private void save_text(String a) {
                SharedPreferences sp = getSharedPreferences("Token",MODE_APPEND);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("Tok",a);
                ed.commit();
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LoginActivity.this, "Введите другие данные", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String phone = textInputEditTextPhone.getText().toString();
                final String newPhoneNumber = phone.replaceAll("[-()]","");
                String pass = textInputEditTextPassword.getText().toString();

                Log.d("TAG", "Onclick" + newPhoneNumber + pass);
                params.put("username", newPhoneNumber);
                params.put("password", pass);
                params.put("grant_type", "password");
                return params;


            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }



    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void postlogin() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPhone, textInputLayoutPhone, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextPhone(textInputEditTextPhone, textInputLayoutPhone, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

         else {
            volley();
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextPhone.setText(null);
        textInputEditTextPassword.setText(null);
    }
}