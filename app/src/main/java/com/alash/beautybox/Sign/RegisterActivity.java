package com.alash.beautybox.Sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    String URL_POST="http://api.admin-beautybox.kz/api/Account/Register";

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutSurname;
    private TextInputLayout textInputLayoutPhone;
    private TextInputLayout textInputLayoutSpec;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextSurname;
    private TextInputEditText textInputEditTextPhone;
    private TextInputEditText textInputEditTextSpec;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    //private DataBaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutSurname = (TextInputLayout) findViewById(R.id.textInputLayoutSurname);
        textInputLayoutPhone = (TextInputLayout) findViewById(R.id.textInputLayoutPhone);
        textInputLayoutSpec = (TextInputLayout) findViewById(R.id.textInputLayoutSpec);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextSurname = (TextInputEditText) findViewById(R.id.textInputEditTextSurname);
        textInputEditTextPhone = (TextInputEditText) findViewById(R.id.textInputEditTextPhone);
        textInputEditTextPhone.addTextChangedListener(new widget(textInputEditTextPhone));
        textInputEditTextSpec = (TextInputEditText) findViewById(R.id.textInputEditTextSpec);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }

    /**d
     * This metho is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        //databaseHelper = new DataBaseHelper(activity);
        user = new User();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:
                postData();
                break;

            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private  void volley(){
        //RequestQueue rq = Volley.newRequestQueue(this);

        StringRequest postReq = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                Toast.makeText(getApplication(), s,Toast.LENGTH_SHORT).show();

            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,"Введите другие данные",Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                String phone = textInputEditTextPhone.getText().toString();
                final String newPhoneNumber = phone.replaceAll("[-()]","");
                String pass = textInputEditTextPassword.getText().toString();
                String passcon = textInputEditTextConfirmPassword.getText().toString();
                String name = textInputEditTextName.getText().toString();
                String surname = textInputEditTextSurname.getText().toString();
                String spec = textInputEditTextSpec.getText().toString();
                params.put("PhoneNumber", newPhoneNumber);
                params.put("Password", pass);
                params.put("ConfirmPassword",passcon);
                params.put("Surname",surname);
                params.put("Name",name);
                params.put("speciality",spec);
                //params.put("grant_type","Password");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postReq);
    }
    private void postData() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextSurname, textInputLayoutSurname, getString(R.string.error_message_surname))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(textInputEditTextPhone, textInputLayoutPhone, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextPhone(textInputEditTextPhone, textInputLayoutPhone, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextSpec, textInputLayoutSpec, getString(R.string.error_message_spec))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return;}
        else{
            volley();

            Snackbar.make(nestedScrollView, "Работает воллей", Snackbar.LENGTH_LONG).show();
        }


        }


//        if (!databaseHelper.checkUser(textInputEditTextPhone.getText().toString().trim(), textInputEditTextPhone.getText().toString().trim())) {
//
//            user.setName(textInputEditTextName.getText().toString().trim());
//            user.setSurname(textInputEditTextSurname.getText().toString().trim());
//            user.setPhone(textInputEditTextPhone.getText().toString().trim());
//            user.setSpec(textInputEditTextSpec.getText().toString().trim());
//            user.setPassword(textInputEditTextPassword.getText().toString().trim());
//
//            databaseHelper.addUser(user);
//
//            // Snack Bar to show success message that record saved successfully
//            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
//            emptyInputEditText();
//
//
//        } else {
//            // Snack Bar to show error message that record already exists
//            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
//        }
//
//

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextSurname.setText(null);
        textInputEditTextPhone.setText(null);
        textInputEditTextSpec.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }

}