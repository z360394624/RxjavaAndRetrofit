package com.tec.lucius;

import android.app.Activity;
import android.os.Bundle;

import com.tec.lucius.request.RetrofitRequest;
import com.tec.lucius.response.ResponseSubscriber;

import java.util.HashMap;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SimpleRequest().method(RetrofitRequest.POST).execute("", new HashMap<String, Object>(), new ResponseSubscriber(){
            @Override
            public void onNext(Object o) {
                super.onNext(o);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }

            @Override
            public void onComplete() {
                super.onComplete();
            }
        });
    }
}
