package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.AdminProductAdapter;
import com.example.mathsbookwriter.interfaces.ProductDetailInterface;
import com.example.mathsbookwriter.model.ProductModel;
import com.example.mathsbookwriter.viewModel.ProductViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminProductListView extends Fragment implements ProductDetailInterface {

    private static final String TAG = AdminProductListView.class.getSimpleName();
    private static final String CHECK_DETAILS = "check_details";

    private Context context;

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdminProductAdapter mAdapter;
    private ProductViewModel mViewModel;

    private FloatingActionButton btnAddProduct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin_product_listview, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnAddProduct = view.findViewById(R.id.btnAddProduct);
        mRecycler = view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new AdminProductAdapter(context,this);
        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(mAdapter);

        mViewModel.getProductList().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                mAdapter.setProductList(productModels);
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(CHECK_DETAILS, false);
                Navigation.findNavController(v).navigate(R.id.action_adminProductListView_to_adminAddProductFragment);
            }
        });
    }

    @Override
    public void funAddQuantity(String quantity, int availableQuantity, String productNumber) {
        if (TextUtils.isEmpty(quantity)){
            Toast.makeText(context, "Please enter quantity before add", Toast.LENGTH_SHORT).show();
        }else {
             FirebaseFirestore db = FirebaseFirestore.getInstance();
             int totalQuantity = (Integer.parseInt(quantity))+availableQuantity;
            db.collection("mainCollection").document("productList").collection("productCollection")
                    .document(productNumber)
                    .update("availableQuantity", totalQuantity)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Quantity added to product successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Some error occured", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }
}
