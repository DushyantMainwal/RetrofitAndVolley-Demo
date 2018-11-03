package com.dushyant.retrofitandvolleydemo.Retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dushyant.retrofitandvolleydemo.R;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends AppCompatActivity {

    TextView responseServer;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        responseServer = findViewById(R.id.responseServer);
        apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);

        findViewById(R.id.usersList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<UserListModel> call = apiInterface.getListUsers(2);
                call.enqueue(new Callback<UserListModel>() {
                    @Override
                    public void onResponse(Call<UserListModel> call, Response<UserListModel> response) {
                        Log.d("TAG", response.code() + "");
                        System.out.println("Response: " + response.body());

                        String displayResponse = "";

                        UserListModel resource = response.body();
                        List<UserModel> list = resource.getData();

                        displayResponse += resource.getPage() + " Page\n" + resource.getTotal() + " Total\n" + resource.getTotalPages() + " Total Pages\n";

                        for (UserModel datum : list) {
                            displayResponse += datum.getId() + " " + datum.getFirstName() + " " + datum.getLastName() + " " + datum.getAvatar() + "\n";
                        }

                        responseServer.setText(displayResponse);
                    }

                    @Override
                    public void onFailure(Call<UserListModel> call, Throwable t) {
                        call.cancel();
                    }
                });

            }
        });

        findViewById(R.id.singleUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = apiInterface.getSingleUser(23);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("TAG", response.code() + "");
                        System.out.println("Response: " + response.body());

                        if (response.code() != 200) {
                            Toast.makeText(RetrofitActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        ResponseBody responseBody = response.body();
                        String displayResponse = "";
                        try {
                            if (responseBody != null) {
                                JSONObject jsonObject = new JSONObject(responseBody.string());
                                JSONObject dataObj = jsonObject.getJSONObject("data");
                                UserModel userModel = new UserModel(
                                        dataObj.getInt("id"),
                                        dataObj.getString("first_name"),
                                        dataObj.getString("last_name"),
                                        dataObj.getString("avatar")
                                );

                                displayResponse += userModel.getId() + " " + userModel.getFirstName() + " "
                                        + userModel.getLastName() + " " + userModel.getAvatar() + "\n";

                                responseServer.setText(displayResponse);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        responseServer.setText(displayResponse);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

        findViewById(R.id.createUser).findViewById(R.id.createUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddUserModel user = new AddUserModel("awesome", "dude");
                Call<AddUserModel> call1 = apiInterface.addUserModel(user);
                call1.enqueue(new Callback<AddUserModel>() {
                    @Override
                    public void onResponse(Call<AddUserModel> call, Response<AddUserModel> response) {
                        AddUserModel user1 = response.body();

                        responseServer.setText(String.format("%s %s %s %s", user1.name, user1.job, user1.id, user1.createdAt));
                    }

                    @Override
                    public void onFailure(Call<AddUserModel> call, Throwable t) {
                        call.cancel();
                    }
                });

            }
        });

        findViewById(R.id.createUserField).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddUserModel user = new AddUserModel("awesome", "dude");
                Call<AddUserModel> call1 = apiInterface.doCreateUserWithField("awesome", "dude");
                call1.enqueue(new Callback<AddUserModel>() {
                    @Override
                    public void onResponse(Call<AddUserModel> call, Response<AddUserModel> response) {
                        AddUserModel user1 = response.body();

                        responseServer.setText(String.format("%s %s %s %s", user1.name, user1.job, user1.id, user1.createdAt));
                    }

                    @Override
                    public void onFailure(Call<AddUserModel> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

        findViewById(R.id.putUserData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<PutDataModel> call1 = apiInterface.updateUser(2, "awesome", null);
                call1.enqueue(new Callback<PutDataModel>() {
                    @Override
                    public void onResponse(Call<PutDataModel> call, Response<PutDataModel> response) {
                        PutDataModel user1 = response.body();

                        responseServer.setText(String.format("%s %s %s", user1.getName(), user1.getJob(), user1.getUpdatedAt()));
                    }

                    @Override
                    public void onFailure(Call<PutDataModel> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

        findViewById(R.id.patchUserData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<PutDataModel> call1 = apiInterface.patchUser(2, "awesome", null);
                call1.enqueue(new Callback<PutDataModel>() {
                    @Override
                    public void onResponse(Call<PutDataModel> call, Response<PutDataModel> response) {
                        PutDataModel user1 = response.body();

                        responseServer.setText(String.format("%s %s %s", user1.getName(), user1.getJob(), user1.getUpdatedAt()));
                    }

                    @Override
                    public void onFailure(Call<PutDataModel> call, Throwable t) {
                        call.cancel();
                    }
                });
            }
        });

        findViewById(R.id.deleteData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<ResponseBody> call = apiInterface.deleteUser(2);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.code() < 300 && response.code() >= 200) {
                            Toast.makeText(RetrofitActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

            }
        });

    }
}
