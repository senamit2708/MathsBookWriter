package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.SaleAddAdapter;
import com.example.mathsbookwriter.model.BillModel;
import com.example.mathsbookwriter.model.OrderedProductModel;
import com.example.mathsbookwriter.model.ProductModel;
import com.example.mathsbookwriter.viewModel.BillViewModel;
import com.example.mathsbookwriter.viewModel.PartiesViewModel;
import com.example.mathsbookwriter.viewModel.ProductViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SaleAddFragment extends Fragment {

    private static final String TAG = SaleAddFragment.class.getSimpleName();
    private static final String STATUS = "status";
    private static final String BILL_STATUS = "Initial";
    private static final String PARTY_NAME = "party_name";
    private static final String BILL_NUMBER ="bill_number";
    private PartiesViewModel mViewModel;
    private ProductViewModel mProductViewModel;
    private BillViewModel mBillViewModel;
    private Context context;
    private List<ProductModel> productModelList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView txtPartyName;
    private ImageButton btnSearch;
    private TextView txtDate;
    private EditText txtBillNumber;
    private TextView txtTotalPrice;
    private Button btnAddItem;
    private Button btnSubmit;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SaleAddAdapter mAdapter;

    private int status=0;
    private String partyName=null;
    private float totalPrice=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(PartiesViewModel.class);
        mProductViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
        //no need to check status, as i m not taking partyname data from the navigation
//        status = getArguments().getInt(STATUS);
//        if (status==2){
//            partyName = getArguments().getString(PARTY_NAME);
//        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sale_add, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtPartyName = view.findViewById(R.id.txtPartyName);
        txtDate = view.findViewById(R.id.txtDate);
        txtBillNumber = view.findViewById(R.id.txtBillNumber);
        txtTotalPrice = view.findViewById(R.id.txtTotalPrice);
        btnSearch = view.findViewById(R.id.btnSearch);
        btnAddItem = view.findViewById(R.id.btnAddItem);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        recyclerView = view.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new SaleAddAdapter();
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        productModelList= mProductViewModel.getProductItem();
        mAdapter.setProductItem(productModelList);
        totalPrice = calculateTotalPrice(productModelList);


        String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new Date());
        txtDate.setText("Date: "+date);
        txtPartyName.setText(mViewModel.getPartyNameForSale());
        txtTotalPrice.setText(String.valueOf(totalPrice));

        Log.i(TAG, "party name is "+txtPartyName.getText().toString());


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_saleAddFragment_to_partyNameListFragment);
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_saleAddFragment_to_saleAddProduct);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "inside onsubmit method 1");
                btnSubmit.setEnabled(false);
                loadDatatoFirestore();

            }
        });
    }

    private float calculateTotalPrice(List<ProductModel> productModelList) {
        float totalprice=0;
        for (int i=0; i<productModelList.size();i++){
           float price = productModelList.get(i).getSellingPrice();
           int quantity = productModelList.get(i).getOrderedQuantity();
           totalprice = totalprice +(price*quantity);
//           Log.i(TAG, "price of product is "+totalprice);
        }
        return totalprice;
    }


    private void loadDatatoFirestore() {
        Log.i(TAG, "inside onsubmit method 2");
        final String billNumber = txtBillNumber.getText().toString();
        partyName = txtPartyName.getText().toString();
        String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new Date());
        //product details entry
        final List<OrderedProductModel> newProductModel = new ArrayList<>();
        for (int i=0; i<productModelList.size(); i++){
            OrderedProductModel model = new OrderedProductModel(productModelList.get(i).getProductNumber(),
                    productModelList.get(i).getProductName(),
                    productModelList.get(i).getOrderedQuantity(),
                    productModelList.get(i).getSellingPrice());
            newProductModel.add(model);
        }

        //bill details entry
        Map<String, Object> product = new HashMap<>();
        product.put("billNumber",billNumber);
        product.put("partyName",partyName);
        product.put("billingDate",date);
        product.put("totalPrice",totalPrice);
        product.put("status",BILL_STATUS);

        db.collection("mainCollection").document("BillDocument")
                .collection("BillCollection")
                .document(billNumber)
                .set(product, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "inside onsubmit method 3");
                        Map<String, List<OrderedProductModel>> mapList = new HashMap<>();
                        mapList.put("productList",newProductModel);
                        db.collection("mainCollection").document("BillDocument")
                                .collection("BillCollection")
                                .document(billNumber)
                                .set(mapList, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.i(TAG, "inside onsubmit method 4");
                                Toast.makeText(context, "Bill updated successfully", Toast.LENGTH_SHORT).show();
                                updateProductQuantity(billNumber);
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Some error occured", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void goToBillDetails(String billNumber) {
        Log.i(TAG, "inside onsubmit method 6");
        Bundle bundle = new Bundle();
        bundle.putString(BILL_NUMBER, billNumber);
        mProductViewModel.clearProductItem();
        mViewModel.clearPartyNameForSale();
        txtPartyName.setText("");
        btnSubmit.setEnabled(true);
        Navigation.findNavController(getActivity(), R.id.btnSubmit).navigate(R.id.action_saleAddFragment_to_newBillDetail,bundle);

    }

    private void updateProductQuantity(String billNumber) {
        Log.i(TAG, "inside onsubmit method 5");
        Log.i(TAG, "inside on update product quantity");
        for (int i=0; i<productModelList.size();i++){
            final String productNumber = productModelList.get(i).getProductNumber();
            final int availableQuantity = (productModelList.get(i).getAvailableQuantity())-(productModelList.get(i).getOrderedQuantity());
            db.collection("mainCollection").document("productList")
                    .collection("productCollection")
                    .document(productNumber)
                    .update("availableQuantity",availableQuantity).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.i(TAG, "succedd in changing quantity "+productNumber);
                    Log.i(TAG, "succedd in changing quantity "+availableQuantity);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i(TAG, "failed in loading the product quantity");
                        }
                    });
        }
        goToBillDetails(billNumber);
    }
}
