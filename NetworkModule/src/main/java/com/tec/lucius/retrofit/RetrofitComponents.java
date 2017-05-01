package com.tec.lucius.retrofit;

import android.text.TextUtils;

import com.tec.lucius.Network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by z3603 on 2017/5/1.
 */

public class RetrofitComponents {

    public static RetrofitComponents INSTANCE = null;

    public static RetrofitComponents ComponentsFactory() {
        if (INSTANCE == null) {
            synchronized (RetrofitComponents.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitComponents();
                }
            }
        }
        return INSTANCE;
    }

    public static String DOMAIN_IS_EMPTY = "Domain should be init";
    public static String OK_HTTP_CLIENT_IS_NULL = "OkHttpClient should be init";
    public static int INVAILDATA = -1;



    private Retrofit mRetrofitClient;
    private OkHttpClient mOkHttpClient;


    private String mDomain;
    private int mConnectTimeout;
    private int mReadTimeout;
    private int mWriteTimeout;

    private RetrofitComponents() {
        mConnectTimeout = INVAILDATA;
        mReadTimeout = INVAILDATA;
        mWriteTimeout = INVAILDATA;
    }

    public void initDomain(String domain) {
        this.mDomain = TextUtils.isEmpty(domain) ? Network.DOMAIN : domain;
    }

    public void initConnectTimeout(int timeout) {
        this.mConnectTimeout = timeout > 0 ? timeout : INVAILDATA;
    }

    public void initReadTimeout(int timeout) {
        this.mReadTimeout = timeout > 0 ? timeout : INVAILDATA;
    }

    public void initWriteTimeout(int timeout) {
        this.mWriteTimeout = timeout > 0 ? timeout : INVAILDATA;
    }

    private String getDomain() {
        return mDomain;
    }

    private OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    private int getConnectTimeout() {
        return mConnectTimeout;
    }

    private int getReadTimeout() {
        return mReadTimeout;
    }

    private int getWriteTimeout() {
        return mWriteTimeout;
    }

    public OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder builder = null;
        if (mOkHttpClient == null) {
            builder = new OkHttpClient.Builder();
            if (getConnectTimeout()>0) {
                builder.connectTimeout(getConnectTimeout(), TimeUnit.SECONDS);
            }
            if (getWriteTimeout()>0) {
                builder.writeTimeout(getWriteTimeout(), TimeUnit.SECONDS);
            }
            if (getReadTimeout()>0) {
                builder.readTimeout(getReadTimeout(), TimeUnit.SECONDS);
            }
            //TODO addInterceptor
//            builder.addInterceptor(new CustomInterceptor());
            mOkHttpClient = builder.build();
        }
        return mOkHttpClient;
    }

    public Retrofit buildRetrofit() {
        checkDomain();
        checkOkHttp();
        if (mRetrofitClient == null) {
            mRetrofitClient = new Retrofit.Builder()
                    .baseUrl(getDomain())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return mRetrofitClient;
    }

    public void checkDomain() {
        if (TextUtils.isEmpty(getDomain())) {
            throw new IllegalAccessError(DOMAIN_IS_EMPTY);
        }
    }

    public void checkOkHttp() {
        if (mOkHttpClient == null) {
            throw new IllegalAccessError(OK_HTTP_CLIENT_IS_NULL);
        }
    }
}
