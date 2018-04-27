package com.example.sreelekshmi.palliativecare;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText user,pass;
    Button log,reg;
    String us,p,sh;
    RadioGroup radioRoleGroup;
    RadioButton radioRoleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user=findViewById(R.id.uuser);
        pass=findViewById(R.id.ppass);
        reg=findViewById(R.id.btn1);
        log=findViewById(R.id.btn2);


        radioRoleGroup = (RadioGroup) findViewById(R.id.radioSex);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));


            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constants.username = user.getText().toString();
                // get selected radio button from radioGroup
                int selectedId = radioRoleGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioRoleButton = (RadioButton) findViewById(selectedId);

                Toast.makeText(LoginActivity.this,
                        radioRoleButton.getText(), Toast.LENGTH_SHORT).show();
                us = user.getText().toString();
                p = pass.getText().toString();
                constants.select_rol=radioRoleButton.getText().toString();

                if (us.equals("")||p.equals("")){
                    Toast.makeText(LoginActivity.this, "Field Vaccant", Toast.LENGTH_SHORT).show();
                }
                else if (constants.select_rol.equals("Viewer")){
                    viewer();
                }
                else {
                    driv();
                }

            }

        });
    }

    public void driv(){
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        //TODO url
        String S_URL =constants.loggg_driv;
        // Toast.makeText(this, "passing", Toast.LENGTH_SHORT).show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, S_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("ee",response);

                        try {
                            JSONObject ss=new JSONObject(response);
                            String rt=ss.getString("error");
                            if (rt.equals("success"))
                            {
                                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(LoginActivity.this,DriverActivity.class));

                            }
                            else {
                                Toast.makeText(LoginActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException sr){
                            Log.e("ee", "No data");
                            // Toast.makeText(LoginActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("ErrorResponse", error.toString());
                        // Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", us);
                params.put("password", p);
                return params;
            }
        };
        // postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }

    public void viewer(){
        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        //TODO url
        String S_URL =constants.loggg_view;
        // Toast.makeText(this, "passing", Toast.LENGTH_SHORT).show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, S_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("ee",response);

                        try {
                            JSONObject ss=new JSONObject(response);
                            String rt=ss.getString("error");
                            if (rt.equals("success"))
                            {
                                Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,ViewerActivity.class));

                            }
                            else {
                                Toast.makeText(LoginActivity.this, "No data", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException sr){
                            Log.e("ee", "No data");
                            // Toast.makeText(LoginActivity.this, "No Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.e("ErrorResponse", error.toString());
                        // Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", us);
                params.put("password", p);
                return params;
            }
        };
        // postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }
}
