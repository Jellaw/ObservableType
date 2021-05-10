package com.example.observabletype.type;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.observabletype.Note;
import com.example.observabletype.R;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SingleSingleObserverActivity extends AppCompatActivity {
    private static final String TAG = SingleSingleObserverActivity.class.getSimpleName();
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_single_observer);
        Single<Note> singleNote = getSingleNoteObservable();
        SingleObserver<Note> singleObserver = getSingleNoteObserver();
        singleNote.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(singleObserver);
    }
   private Single<Note> getSingleNoteObservable(){
        return Single.create(new SingleOnSubscribe<Note>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Note> emitter) throws Exception {
                Note note = new Note(1, "Note");
                emitter.onSuccess(note);
            }
        });
   }
   private SingleObserver<Note> getSingleNoteObserver(){
        return new SingleObserver<Note>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onSuccess(@NonNull Note note) {
                Log.e(TAG, "onSuccess: " + note.getNote());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }
        };
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}