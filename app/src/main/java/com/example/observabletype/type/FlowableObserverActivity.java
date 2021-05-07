
package com.example.observabletype.type;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.observabletype.Note;
import com.example.observabletype.R;

import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
import kotlinx.coroutines.flow.Flow;

public class FlowableObserverActivity extends AppCompatActivity {
    private static final String TAG = ObservableObserverActivity.class.getSimpleName();
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowable_observer);
        Flowable<Integer> integerFlowable = getFlowableObservable();
        SingleObserver<Integer> flowableObserver = getFlowaaleObserver();
        integerFlowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .reduce(0, new BiFunction<Integer, Integer, Integer>() {
                    @NonNull
                    @Override
                    public Integer apply(@NonNull Integer result, @NonNull Integer number) throws Exception {
                        return result + number;
                    }
                }).subscribe(flowableObserver);
    }
    private Flowable<Integer> getFlowableObservable(){
        return Flowable.range(1,100);
    }
    private SingleObserver<Integer> getFlowaaleObserver(){
        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onSuccess(@NonNull Integer integer) {
                Log.d(TAG, "onSuccess: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}