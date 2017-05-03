package com.tec.lucius.network;

import com.tec.lucius.response.Context;
import com.tec.lucius.response.ResponseBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by z3603 on 2017/5/1.
 */
public interface ApiService {

    @FormUrlEncoded
    @POST("{path}")
    Flowable<Response<ResponseBean<Context>>> post(@Path(value = "path", encoded = true) String path, @FieldMap HashMap<String, Object> params);

    @FormUrlEncoded
    @GET("{path}")
    Flowable<Response<ResponseBean<Context>>> get(@Path(value = "path", encoded = true) String path, @QueryMap HashMap<String, Object> queryMap);

}
