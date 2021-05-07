package com.example.observabletype.type;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.observabletype.Note;
import com.example.observabletype.R;

import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MaybeMaybeObserverActivity extends AppCompatActivity {
    private static final String TAG = ObservableObserverActivity.class.getSimpleName();
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maybe_maybe_observer);
        Maybe<Note> noteMaybeObservable = getMaybeNoteObservable();
        MaybeObserver<Note> noteMaybeObserver = getMaybeObserver();
        noteMaybeObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(noteMaybeObserver);
    }
    private Maybe<Note> getMaybeNoteObservable(){
        return Maybe.create(new MaybeOnSubscribe<Note>() {
            @Override
            public void subscribe(@NonNull MaybeEmitter<Note> emitter) throws Exception {
                Note note = new Note(1, "Call brother!");
                if (!emitter.isDisposed()) {
                    emitter.onSuccess(note);
                }
            }
        });
    }
    private MaybeObserver<Note> getMaybeObserver(){
        return new MaybeObserver<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(@NonNull Note note) {
                Log.d(TAG,"onSuccess: "+ note.getNote());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"onError: "+ e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"conComplete");
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}