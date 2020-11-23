package com.example.livedata;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class EntrenadorViewModel extends AndroidViewModel {
    Emoticono emoticono;

    LiveData<Integer> caritaLiveData;

    public EntrenadorViewModel(@NonNull Application application) {
        super(application);

        emoticono = new Emoticono();

        caritaLiveData = Transformations.switchMap(emoticono.emocionLiveData, new Function<String, LiveData<Integer>>() {
            @Override
            public LiveData<Integer> apply(String emocion) {
                int image;

                switch (emocion){
                    case "EMOCION1":
                    default:
                        image = R.drawable.contento;
                        break;
                }
                return new MutableLiveData<>(image);
            }
        });
    }
}