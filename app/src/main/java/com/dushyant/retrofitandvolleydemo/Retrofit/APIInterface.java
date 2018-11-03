package com.dushyant.retrofitandvolleydemo.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("/api/users")
    Call<UserListModel> getListUsers(@Query("page") int page);

    @GET("/api/users/{id}")
    Call<ResponseBody> getSingleUser(@Path("id") int userId);

    @POST("/api/users")
    Call<AddUserModel> addUserModel(@Body AddUserModel user);

    @FormUrlEncoded
    @POST("/api/users")
    Call<AddUserModel> doCreateUserWithField(@Field("name") String name, @Field("job") String job);

    @FormUrlEncoded
    @PUT("/api/users/{id}")
    Call<PutDataModel> updateUser(@Path("id") int userId, @Field("name") String name, @Field("job") String job);

    @FormUrlEncoded
    @PATCH("/api/users/{id}")
    Call<PutDataModel> patchUser(@Path("id") int userId, @Field("name") String name, @Field("job") String job);

    @DELETE("/api/users/{id}")
    Call<ResponseBody> deleteUser(@Path("id") int userId);
}
