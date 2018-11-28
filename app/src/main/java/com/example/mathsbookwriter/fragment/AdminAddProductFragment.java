package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.model.ProductModel;
import com.example.mathsbookwriter.viewModel.ProductViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class AdminAddProductFragment extends Fragment {

    private static final String TAG = AdminAddProductFragment.class.getSimpleName();
    private static final String CHECK_DETAILS = "check_details";

    private Context context;
    private ProductViewModel mViewModel;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText txtProductNumber;
    private EditText txtProductName;
    private EditText txtCategory;
    private EditText txtBrand;
    private EditText txtQuantity;
    private EditText txtPurchasePrice;
    private EditText txtSellPriceOne;
    private EditText txtSellPriceTwo;
    private EditText txtSellPriceThree;
    private EditText txtDescription;
    private Button btnSubmit;

    private String productNumber;
    private String productName;
    private String category;
    private String brand;
    private int availableQuantity;
    private float purchasePrice;
    private float sellPriceOne;
    private float sellPriceTwo;
    private float sellPriceThree;
    private String description;
    private Boolean check;
    private ProductModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        check = getArguments().getBoolean(CHECK_DETAILS);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
        if (check){
            model = mViewModel.getProductDetailForEdit();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin_add_product, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtProductNumber = view.findViewById(R.id.txtProductNumber);
        txtProductName = view.findViewById(R.id.txtProductName);
        txtCategory = view.findViewById(R.id.txtCategory);
        txtBrand = view.findViewById(R.id.txtBrand);
        txtPurchasePrice = view.findViewById(R.id.txtPurchasePrice);
        txtQuantity = view.findViewById(R.id.txtQuantity);
        txtSellPriceOne = view.findViewById(R.id.txtSellPriceOne);
        txtSellPriceTwo = view.findViewById(R.id.txtSellPriceTwo);
        txtSellPriceThree = view.findViewById(R.id.txtSellPriceThree);
        txtDescription = view.findViewById(R.id.txtDescription);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        if (check){
            insertDetails();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.setEnabled(false);
              int check = getDetails();
              if (check>0){
                  submitDetails();
              }else {
                  btnSubmit.setEnabled(true);
                  Toast.makeText(context, "please enter required detials", Toast.LENGTH_SHORT).show();
              }
            }
        });
    }

    private void insertDetails() {
        txtProductNumber.setText(model.getProductNumber());
        txtProductName.setText(model.getProductName());
        txtCategory.setText(model.getCategory());
        txtBrand.setText(model.getBrand());
        txtQuantity.setText(String.valueOf(model.getAvailableQuantity()));
        txtPurchasePrice.setText(String.valueOf(model.getPurchasePrice()));
        txtSellPriceOne.setText(String.valueOf(model.getSellPriceOne()));
        txtSellPriceTwo.setText(String.valueOf(model.getSellPriceTwo()));
        txtSellPriceThree.setText(String.valueOf(model.getSellPriceThree()));
        txtDescription.setText(model.getDescription());
    }

    private void submitDetails() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        ProductModel model = new ProductModel(productNumber, productName, category, brand, availableQuantity,
                purchasePrice, sellPriceOne, sellPriceTwo, sellPriceThree, description);

        db.collection("mainCollection").document("productList").collection("productCollection")
                .document(productNumber).set(model)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Product added successfully", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getActivity(), R.id.btnSubmit).popBackStack(R.id.adminAddProductFragment, true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "unable to load the product..", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private int getDetails() {
        int check = 1;
        productNumber = txtProductNumber.getText().toString();
        productName = txtProductName.getText().toString();
        category = txtCategory.getText().toString();
        brand = txtBrand.getText().toString();
        if (!TextUtils.isEmpty(txtQuantity.getText().toString())){
            availableQuantity = Integer.parseInt(txtQuantity.getText().toString());
        }
        if (!TextUtils.isEmpty(txtPurchasePrice.getText().toString())){
            purchasePrice = Float.parseFloat(txtPurchasePrice.getText().toString());
        }
        if (!TextUtils.isEmpty(txtSellPriceOne.getText().toString())){
            sellPriceOne = Float.parseFloat(txtSellPriceOne.getText().toString());
        }
        if (!TextUtils.isEmpty(txtSellPriceTwo.getText().toString())){
            sellPriceTwo = Float.parseFloat(txtSellPriceTwo.getText().toString());
        }
        if (!TextUtils.isEmpty(txtSellPriceThree.getText().toString())){
            sellPriceThree = Float.parseFloat(txtSellPriceThree.getText().toString());
        }
        description = txtDescription.getText().toString();

        if (TextUtils.isEmpty(productNumber)){
            txtProductNumber.setError("required");
            check=-1;
        }
        if (TextUtils.isEmpty(productName)){
            txtProductName.setError("required");
            check=-1;
        }
        return check;

    }
}
