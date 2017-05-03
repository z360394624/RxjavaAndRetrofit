package com.tec.lucius;

import com.tec.lucius.request.BaseRequest;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by z3603 on 2017/5/2.
 */

public class SimpleRequest extends BaseRequest {


    @Override
    protected Scheduler getObserveScheduler() {
//        return AndroidSchedulers.mainThread();
        return Schedulers.io();
    }
}
