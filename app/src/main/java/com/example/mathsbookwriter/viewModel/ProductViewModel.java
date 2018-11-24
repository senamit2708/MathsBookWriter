package com.example.mathsbookwriter.viewModel;

import android.app.Application;
import android.util.Log;

import com.example.mathsbookwriter.model.ProductModel;
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

public class ProductViewModel extends AndroidViewModel {

    private static final String TAG = ProductViewModel.class.getSimpleName();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private MediatorLiveData<List<ProductModel>> productListLiveData;
    private MutableLiveData<ProductModel> productDetailLiveData;

    private ProductModel model;

    public ProductViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<List<ProductModel>> getProductList() {
        productListLiveData = new MediatorLiveData<>();
        completeProductList();
        return productListLiveData;
    }

    private void completeProductList() {
        final List<ProductModel> productModelList = new ArrayList<>();
        Source source = Source.CACHE;

        db.collection("mainCollection").document("productList")
                .collection("productCollection")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                ProductModel model = documentSnapshot.toObject(ProductModel.class);
                                productModelList.add(model);
                                Log.i(TAG, "the product name is "+model);
                            }productListLiveData.setValue(productModelList);
                        }
                    }
                });

    }

    public LiveData<ProductModel> getProductDetail(String productNumber) {
        productDetailLiveData = new MutableLiveData<>();
        db.collection("mainCollection").document("productList")
                .collection("productCollection")
                .document(productNumber)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                         model = documentSnapshot.toObject(ProductModel.class);
                        productDetailLiveData.setValue(model);
                    }
                });
        return productDetailLiveData;
    }

    public ProductModel getProductDetailForEdit() {
        return model;
    }
}
