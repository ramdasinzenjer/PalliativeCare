package com.example.sreelekshmi.palliativecare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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

public class RegisterActivity extends AppCompatActivity {
    EditText user,pass,conpass,email,phn;
    Button admn,signup;
    Spinner role;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String[] rol={"Driver","Viewer"};
    String seected_role;
    String us,pa,cp,em,pn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        role=(Spinner)findViewById(R.id.rol);
        user=(EditText)findViewById(R.id.uuser);
        pass=(EditText)findViewById(R.id.ppass);
        conpass=(EditText)findViewById(R.id.cpass);
        email=(EditText)findViewById(R.id.eeml);
        phn=(EditText)findViewById(R.id.pphn);
        admn=(Button) findViewById(R.id.btn1);
        signup=(Button) findViewById(R.id.btn2);

        final ArrayAdapter<String> s = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,rol);
        role.setAdapter(s);
        role.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seected_role = rol[position];
                constants.select_rol=seected_role.toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constants.username = user.getText().toString();
                us = user.getText().toString();
                pa = pass.getText().toString();
                cp = conpass.getText().toString();
                em = email.getText().toString();
                pn = phn.getText().toString();


                if (us.equals("")||pa.equals("")||cp.equals("")||em.equals("")||pn.equals("")){
                    Toast.makeText(RegisterActivity.this, "Field Vaccant", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if (pn.length()!=10){
                        Toast.makeText(RegisterActivity.this, "Enter valid phoneno", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (!(em.matches(emailPattern))){
                            Toast.makeText(RegisterActivity.this, "Enter Valid emailid", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if (pa.equals(cp)){
                                reg();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "Coudn't match password", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }


                }
            }
        });
    }


    public void reg(){

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
        //TODO url
        String S_URL = constants.regg;
        Toast.makeText(this, "passing", Toast.LENGTH_SHORT).show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, S_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();


                        try {
                            JSONObject ss=new JSONObject(response);
                            String rt=ss.getString("success");
                            if (rt.equals("Successfully Registered"))
                            {
                                if (rt.equals(constants.select_rol="Driver"))
                                {
                                    Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this,DriverActivity.class));
                                }
                                else if (rt.equals(constants.select_rol="Viewer")){
                                    Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this,ViewerActivity.class));
                                }

                            }
                        }
                        catch (JSONException sr){
                            Log.e("hg","User Exists");
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
                params.put("password", pa);
                params.put("email", em);
                params.put("phoneno", pn);
                params.put("select_role", constants.select_rol);
                return params;
            }
        };

        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);



    }
}
