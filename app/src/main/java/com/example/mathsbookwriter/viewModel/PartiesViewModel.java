package com.example.mathsbookwriter.viewModel;

import android.app.Application;
import android.util.Log;

import com.example.mathsbookwriter.liveData.FirebaseQueryLiveData;
import com.example.mathsbookwriter.model.PartiesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class PartiesViewModel extends AndroidViewModel {

    private static final String TAG = PartiesViewModel.class.getSimpleName();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MediatorLiveData<List<PartiesModel>> partiesModelLiveData;
    private MutableLiveData<PartiesModel> partyLiveData;
    
    private FirebaseQueryLiveData liveData;

    private String partyName= null;
    private PartiesModel party;

    public PartiesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PartiesModel>> getListOfParty() {
        if (partiesModelLiveData==null){
            partiesModelLiveData = new MediatorLiveData<>();
            loadModelLiveData();
        }
        return partiesModelLiveData;
    }

    private void loadModelLiveData() {

        //to load cache..its trail ..lets see it will work good or not
//        Source source = Source.CACHE;
        //trying to implement livedata listener
        Query query = db.collection("mainCollection").document("partyList")
                .collection("parties");
         liveData = new FirebaseQueryLiveData(query);
        partiesModelLiveData.addSource(liveData, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots!= null){
                    List<PartiesModel> partiesModels = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                        PartiesModel model = documentSnapshot.toObject(PartiesModel.class);
                        partiesModels.add(model);
                        Log.i(TAG, "the name of party is "+documentSnapshot.getString("partyName"));
//                        Log.i(TAG, "the name of party is "+model.getPartyName());
                    }
                    partiesModelLiveData.setValue(partiesModels);
                }else {
                    Log.i(TAG, "Error getting documents");
                }
            }
        });

    }

    //no use
    public LiveData<PartiesModel> getPartyDetails(String partyName) {
        partyLiveData = new MutableLiveData<>();
        db.collection("mainCollection").document("partyList")
                .collection("parties").document(partyName)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        PartiesModel model = documentSnapshot.toObject(PartiesModel.class);
                        partyLiveData.setValue(model);
                    }
                });
        return partyLiveData;

    }

    public String getPartyNameForSale(){
        Log.i(TAG, "party name in get method is "+partyName);
        return partyName;
    }

    public void setPartyNameforSale(String partyName) {
        Log.i(TAG, "party name is set method is "+partyName);
        this.partyName = partyName;
    }

    public void setParty(PartiesModel party) {
        this.party = party;
    }

    public PartiesModel getParty() {
        return party;
    }

    public void clearPartyNameForSale() {
        this.partyName = null;
    }
}


