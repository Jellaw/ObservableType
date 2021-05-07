package com.example.observabletype.type;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.observabletype.Note;
import com.example.observabletype.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ObservableObserverActivity extends AppCompatActivity {
    private static final String TAG = ObservableObserverActivity.class.getSimpleName();
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable_observer);
        Observable<Note> noteObservable = getNoteObservable();
        Observer<Note> noteObserver = getNoteObserver();
        noteObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(noteObserver);

    }
    private Observable<Note> getNoteObservable(){
        final List<Note> notes = initNote();
        return Observable.create(emitter -> {
            for(Note note:notes){
                if (!emitter.isDisposed()){
                    emitter.onNext(note);
                }
            }
            if (!emitter.isDisposed()){
                emitter.onComplete();
            }
        });
    }
    private Observer<Note> getNoteObserver(){
        return  new Observer<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(@NonNull Note note) {
                Log.d(TAG, "onNext: "+note.getNote());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: "+ e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
    }
    private List<Note> initNote(){
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "Buy tooth paste!"));
        notes.add(new Note(2, "Call brother!"));
        notes.add(new Note(3, "Watch Narcos tonight!"));
        notes.add(new Note(4, "Pay power bill!"));
        return notes;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}