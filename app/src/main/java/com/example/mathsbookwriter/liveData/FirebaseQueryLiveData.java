package com.example.mathsbookwriter.liveData;

import android.os.Handler;

//import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import javax.annotation.Nullable;

import androidx.lifecycle.LiveData;

public class FirebaseQueryLiveData extends LiveData<QuerySnapshot> {

    private MyValueEventListener listener = new MyValueEventListener();
    private Query query;
    Source source = Source.CACHE;

    private boolean listenerRemovePending = false;
    private ListenerRegistration registration;
    private Handler handler = new Handler();
    private final Runnable removeListener = new Runnable() {
        @Override
        public void run() {
            registration.remove();
            listenerRemovePending = false;

        }
    };

    public FirebaseQueryLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (listenerRemovePending){
            handler.removeCallbacks(removeListener);
        }else {
          registration=  query.addSnapshotListener(listener);
        }
        listenerRemovePending= false;
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        handler.postDelayed(removeListener, 2000);
        listenerRemovePending=true;
    }

    private class MyValueEventListener implements EventListener<QuerySnapshot> {
        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
            setValue(queryDocumentSnapshots);
        }
    }
}
