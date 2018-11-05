package com.dushyant.retrofitandvolleydemo.Volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dushyant.retrofitandvolleydemo.R;
import com.dushyant.retrofitandvolleydemo.Retrofit.UserListModel;
import com.dushyant.retrofitandvolleydemo.Retrofit.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VolleyActivity extends AppCompatActivity {

    private static final String TAG = "VolleyTAG";
    TextView responseServer;
    RequestQueue queue;
    String url = "https://reqres.in";
    List<UserModel> userModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        responseServer = findViewById(R.id.responseServer);
        // Instantiate the RequestQueue.
//        queue = Volley.newRequestQueue(this);

        // Instantiate the cache
//        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
//
//        // Set up the network to use HttpURLConnection as the HTTP client.
//        Network network = new BasicNetwork(new HurlStack());
//
//        // Instantiate the RequestQueue with the cache and network.
//        queue = new RequestQueue(cache, network);
//
//        // Start the queue
//        queue.start();

        queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();

        findViewById(R.id.usersList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userModelList.clear();
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url + "/api/users?page=2", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response: ", response);

                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        UserListModel userListModel = mGson.fromJson(response, UserListModel.class);
                        userModelList.addAll(userListModel.getData());

                        Log.d("UserModel: ", "" + userListModel.getTotalPages());
                        Log.d("UserModelName: ", userListModel.getData().get(0).getFirstName());

                        responseServer.setText(String.format("Response: %s", userListModel.getData().get(0).getFirstName()));
                    }
                }, errorListener);

                // Set the tag on the request.
                stringRequest.setTag(TAG);
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });


        findViewById(R.id.createUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();

                VolleyLog.DEBUG = true;
                String uri = url + "/api/users";

                //To POST parameters in a StringRequest, we need to override getParams() and pass the parameters as a key value pair.
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show()
//                    }
//                }, errorListener) {
//                    @Override
//                    public Priority getPriority() {
//                        return Priority.LOW;
//                    }
//
//                    @Override
//                    public Map getParams() {
//                        Map params = new HashMap();
//                        params.put("name", "Dushyant");
//                        params.put("job", "Android Developer");
//
//                        return params;
//                    }
//
//                    @Override
//                    public Map getHeaders() throws AuthFailureError {
//                        HashMap headers = new HashMap();
//                        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
//                        return headers;
//                    }
//                };
//
//
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("name", "JournalDev.com");
//                    jsonObject.put("job", "To teach you the best");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
                //To POST parameters in a JsonObjectRequest we pass the parameters inside a JSONObject
                // and set them in the second parameter of the constructor.
//                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(uri, jsonObject, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }, errorListener) {
//
//                    @Override
//                    public int getMethod() {
//                        return Method.POST;
//                    }
//
//                    @Override
//                    public Priority getPriority() {
//                        return Priority.NORMAL;
//                    }
//                };


                //To POST a JSON request body in a StringRequest, we override the method getBody().
                StringRequest stringRequestPOSTJSON = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    }
                }, errorListener) {
                    @Override
                    public Priority getPriority() {
                        return Priority.HIGH;
                    }

                    @Override
                    public Map getHeaders() throws AuthFailureError {
                        HashMap headers = new HashMap();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("name", "Android Tutorials");
                            jsonObject.put("job", "To implement Volley in an Android Application.");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String requestBody = jsonObject.toString();


                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }


                };

//                queue.add(stringRequest);
//                queue.add(jsonObjectRequest);
                queue.add(stringRequestPOSTJSON);


            }
        });

        findViewById(R.id.createUserField).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue mRequestQueue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
                String urlPost = url + "/api/users?page=2";

                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

                GsonRequest gsonRequest = new GsonRequest(urlPost, UserListModel.class, headers, new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        UserListModel listModel = (UserListModel) response;
                        Toast.makeText(VolleyActivity.this, "Res: " + listModel.getData().get(0).getFirstName(), Toast.LENGTH_SHORT).show();
                    }
                }, errorListener);

                mRequestQueue.add(gsonRequest);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };
}
