package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.model.ProductModel;
import com.example.mathsbookwriter.viewModel.ProductViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class SaleAddItem extends Fragment {

    private static final String TAG = SaleAddItem.class.getSimpleName();
    private Context context;
    private ProductViewModel mViewModel;
    private ProductModel model;

    private TextView txtProductName;
    private TextView txtAvailableQuantity;
    private EditText txtOrderedQuantity;
    private EditText txtPrice;
//    private ImageButton btnSearch;
    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sale_add_item, container, false);
       context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtProductName = view.findViewById(R.id.txtProductName);
        txtAvailableQuantity = view.findViewById(R.id.txtAvailableQuantity);
        txtOrderedQuantity = view.findViewById(R.id.txtOrderedQuantity);
        txtPrice = view.findViewById(R.id.txtPrice);
//        btnSearch = view.findViewById(R.id.btnSearch);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        model = mViewModel.getProductNumberForSale();
        if (model!= null){
            Log.i(TAG, "the model is "+model.getProductName());
            txtProductName.setText(model.getProductName());
            txtAvailableQuantity.setText(String
                    .valueOf(model.getAvailableQuantity()));
            txtPrice.setText(String.valueOf(model.getSellPriceOne()));
        }

//        btnSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(txtOrderedQuantity.getText())){
                    ProductModel newItem = new ProductModel(model.getProductNumber(),
                            model.getProductName(),
                            model.getAvailableQuantity(),
                            Integer.parseInt(txtOrderedQuantity.getText().toString()),
                            model.getSellPriceOne(),
                            Float.valueOf(txtPrice.getText().toString()));
                    mViewModel.setProductItem(newItem);
                    Navigation.findNavController(v).popBackStack(R.id.saleAddProduct,true);

                }else {
                    Toast.makeText(context, "Please enter quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
