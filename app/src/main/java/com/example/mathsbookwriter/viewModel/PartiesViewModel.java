package com.example.mathsbookwriter.viewModel;

import android.app.Application;
import android.util.Log;

import com.example.mathsbookwriter.model.PartiesModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class PartiesViewModel extends AndroidViewModel {

    private static final String TAG = PartiesViewModel.class.getSimpleName();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MediatorLiveData<List<PartiesModel>> partiesModelLiveData;
    private MutableLiveData<PartiesModel> partyLiveData;

    public PartiesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<PartiesModel>> getListOfParty() {

        partiesModelLiveData = new MediatorLiveData<>();
        final List<PartiesModel> partiesModels = new ArrayList<>();
        //to load cache..its trail ..lets see it will work good or not
        Source source = Source.CACHE;

        db.collection("mainCollection").document("partyList")
                .collection("parties")
                .get(source)
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                PartiesModel model = documentSnapshot.toObject(PartiesModel.class);
                                partiesModels.add(model);

//                                Log.i(TAG, "the name of party is "+documentSnapshot.getString("partyName"));
                                Log.i(TAG, "the name of party is "+model.getPartyName());

                            }
                            partiesModelLiveData.setValue(partiesModels);
                        }else {
                            Log.i(TAG, "Error getting documents");
                        }
                    }
                });
        return partiesModelLiveData;
    }

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
}
