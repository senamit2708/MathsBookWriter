package com.example.mathsbookwriter.viewModel;

import android.app.Application;
import android.util.Log;

import com.example.mathsbookwriter.liveData.FirebaseQueryLiveData;
import com.example.mathsbookwriter.model.BillModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
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

public class BillViewModel extends AndroidViewModel {

    private static final String TAG = BillViewModel.class.getSimpleName();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private BillModel mBillDetail;
    private BillModel bill;

    private FirebaseQueryLiveData mLiveData;
    private MediatorLiveData<List<BillModel>> mBillList;
    Query billListQuery;


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

    public LiveData<List<BillModel>> getCurrentBillList(String billStatus) {
        Log.i(TAG, "inside loadcurrentbilllist 1");
        loadCurrentBillList(billStatus);
        return mBillList;
    }

    private void loadCurrentBillList(String billStatus) {
        if (billStatus.equals("All")){
            billListQuery = db.collection("mainCollection").document("BillDocument")
                    .collection("BillCollection");
        }else {
            billListQuery = db.collection("mainCollection").document("BillDocument")
                    .collection("BillCollection").whereEqualTo("status",billStatus);
        }

        mLiveData = new FirebaseQueryLiveData(billListQuery);
        mBillList = new MediatorLiveData<>();
        Log.i(TAG, "inside loadcurrentbilllist 2");
        mBillList.addSource(mLiveData, new Observer<QuerySnapshot>() {
            @Override
            public void onChanged(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots!= null){
                    Log.i(TAG, "inside loadcurrentbilllist 3");
                    List<BillModel> billModels = new ArrayList<>();
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    BillModel bill = documentSnapshot.toObject(BillModel.class);
                    Log.i(TAG, "bill number is "+bill.getBillNumber());
                    billModels.add(bill);
                    }
                    mBillList.setValue(billModels);
                }else {
                    Log.i(TAG, "inside loadcurrentbilllist 4");
                    mBillList.setValue(null);
                }
            }
        });
    }

    public void setSelectedBill(BillModel bill) {
        this.bill = bill;
    }
    public BillModel getSelectedBill(){
        return bill;
    }
}
