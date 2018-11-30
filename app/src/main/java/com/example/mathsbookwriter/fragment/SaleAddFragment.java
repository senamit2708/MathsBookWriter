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
import com.example.mathsbookwriter.model.ProductModel;
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
    private static final String PARTY_NAME = "party_name";
    private PartiesViewModel mViewModel;
    private ProductViewModel mProductViewModel;
    private Context context;
    private List<ProductModel> productModelList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private TextView txtPartyName;
    private ImageButton btnSearch;
    private TextView txtDate;
    private EditText txtBillNumber;
    private Button btnAddItem;
    private Button btnSubmit;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SaleAddAdapter mAdapter;

    private int status=0;
    private String partyName=null;

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


        String date = new SimpleDateFormat("dd-MM-YYYY", Locale.getDefault()).format(new Date());
        txtDate.setText("Date: "+date);
        txtPartyName.setText(mViewModel.getPartyNameForSale());

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
                loadDatatoFirestore();
//                testData();
            }
        });
    }

    private void testData() {
        Map<String, Object> docData = new HashMap<>();
        docData.put("stringExample", "Hello world!");
        docData.put("booleanExample", true);
        docData.put("numberExample", 3.14159265);
        docData.put("dateExample", new Date());
        docData.put("listExample", Arrays.asList(productModelList));
        docData.put("nullExample", null);

        Map<String, Object> nestedData = new HashMap<>();
        nestedData.put("a", 5);
        nestedData.put("b", true);
//        Map<String, Object> nestedData1 = new HashMap<>();
//        nestedData1.put("")

        docData.put("objectExample", nestedData);

        db.collection("data").document("one")
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(context, "Bill updated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Some error occured", Toast.LENGTH_SHORT).show();
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    private void loadDatatoFirestore() {
        final String billNumber = txtBillNumber.getText().toString();
        int size = productModelList.size();

        for (int i=0; i<size;i++){

        }
        Map<String, Object> product = new HashMap<>();
        ProductModel productModel = productModelList.get(0);
//        BillModel model = new BillModel("1232","amit", Arrays.asList("name1","name2","name3"));
        product.put("billNumber",billNumber);
        product.put("partyName",partyName);
//        product.putAll(Collections.<String, Object>emptyMap());

        List<ProductModel> list;
        Map<String, ProductModel> map = new HashMap<String, ProductModel>();
        for (ProductModel i : productModelList)
            map.put(i.toString(), i);



        db.collection("mainCollection").document("BillDocument")
                .collection("BillCollection")
                .document(billNumber)
                .set(product, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Map<String, List<ProductModel>> mapList = new HashMap<>();
                        mapList.put("productList",productModelList);
                        db.collection("mainCollection").document("BillDocument")
                                .collection("BillCollection")
                                .document(billNumber)
                                .set(mapList, SetOptions.merge());
                        Toast.makeText(context, "Bill updated successfully", Toast.LENGTH_SHORT).show();
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
