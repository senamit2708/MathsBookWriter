package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.SaleAddProductAdapter;
import com.example.mathsbookwriter.interfaces.ProductNameInterface;
import com.example.mathsbookwriter.model.ProductModel;
import com.example.mathsbookwriter.viewModel.ProductViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SaleAddProduct extends Fragment implements ProductNameInterface {

    private static final String TAG = SaleAddProduct.class.getSimpleName();
    private Context context;
    private ProductViewModel mViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SaleAddProductAdapter mAdapter;
    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sale_add_product, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSubmit = view.findViewById(R.id.btnSubmit);
        recyclerView = view.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new SaleAddProductAdapter(context, this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        mViewModel.getProductList().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                mAdapter.setProductList(productModels);
            }
        });
    }

    @Override
    public void funProductNumber(String productNumber, String productName, int quantity, float price) {
        mViewModel.setProductNumberForSale(productNumber, productName, quantity, price);
//        Navigation.findNavController(getActivity(), R.id.btnSubmit).popBackStack(R.id.saleAddProduct, true);
    }

    @Override
    public void funProductDetails(ProductModel model) {
        mViewModel.setProdDetailsForSale(model);
        Navigation.findNavController(getActivity(),R.id.btnSubmit).navigate(R.id.action_saleAddProduct_to_saleAddItem);
    }
}
