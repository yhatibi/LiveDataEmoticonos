package com.example.livedata;

import androidx.lifecycle.LiveData;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Emoticono {

    LiveData<String> emocionLiveData = new LiveData<String>() {
        @Override
        protected void onActive() {
            super.onActive();

            iniciarEmociones(new EmoticonoListener() {
                @Override
                public void cuandoCambieLaEmocion(String emocion) {
                    postValue(emocion);
                }
            });
        }

        @Override
        protected void onInactive() {
            super.onInactive();

            pararEntrenamiento();
        }
    };




    interface EmoticonoListener {
        void cuandoCambieLaEmocion(String orden);
    }

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    ScheduledFuture<?> emocionandose;

    void iniciarEmociones(final EmoticonoListener emoticonoListener) {
        if (emocionandose == null || emocionandose.isCancelled()) {

            emocionandose = scheduler.scheduleAtFixedRate(new Runnable() {
                int emocion;

                @Override
                public void run() {
                    emoticonoListener.cuandoCambieLaEmocion("EMOCION" + emocion);
                    emocion++;
                    if(emocion == 4) emocion = 0;
                }
            }, 0, 1, SECONDS);
        }
    }

    void pararEntrenamiento() {
        if (emocionandose != null) {
            emocionandose.cancel(true);
        }
    }
}