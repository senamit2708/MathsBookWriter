package com.example.mathsbookwriter.viewModel;

import android.app.Application;
import android.util.Log;

import com.example.mathsbookwriter.model.BillModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class BillViewModel extends AndroidViewModel {

    private static final String TAG = BillViewModel.class.getSimpleName();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private BillModel mBillDetail;


    public BillViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<BillModel> getNewBillDetail(String billNumber) {
        final MutableLiveData<BillModel> mLiveData = new MutableLiveData<>();

        Query query = db.collection("mainCollection").document("BillDocument")
                .collection("BillCollection");
          ((CollectionReference) query).document(billNumber).get()
                  .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                      @Override
                      public void onSuccess(DocumentSnapshot documentSnapshot) {
                          if (documentSnapshot!= null){

                              mBillDetail = documentSnapshot.toObject(BillModel.class);
                              Log.i(TAG, "inside onsucess method of getNewbillmodel "+mBillDetail.getBillNumber());
                              mLiveData.setValue(mBillDetail);
                          }else {
                              Log.i(TAG, "inside else block of onsucess");
                              mLiveData.setValue(null);
                          }
                      }
                  });


          return mLiveData;

    }
}
