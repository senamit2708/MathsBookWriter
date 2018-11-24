package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.model.ProductModel;
import com.example.mathsbookwriter.viewModel.ProductViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class ProductDetails extends Fragment {

    private static final String TAG = ProductDetails.class.getSimpleName();
    private static final String PRODUCT_NUMBER = "product_number";
    private static final String CHECK_DETAILS = "check_details";


    private Context context;
    private ProductViewModel mViewModel;

    private TextView txtProductNumber;
    private TextView txtProductName;
    private TextView txtCategory;
    private TextView txtBrand;
    private TextView txtQuantity;
    private TextView txtPurchasePrice;
    private TextView txtSellPriceOne;
    private TextView txtSellPriceTwo;
    private TextView txtSellPriceThree;
    private TextView txtDescription;
    private Button btnSubmit;

    private String productNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productNumber = getArguments().getString(PRODUCT_NUMBER);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_product_details, container, false);
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

        mViewModel.getProductDetail(productNumber).observe(this, new Observer<ProductModel>() {
            @Override
            public void onChanged(ProductModel model) {
                setDetails(model);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(CHECK_DETAILS, true);
                Navigation.findNavController(v).navigate(R.id.action_productDetails_to_adminAddProductFragment, bundle);
            }
        });
    }

    private void setDetails(ProductModel model) {
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
}
