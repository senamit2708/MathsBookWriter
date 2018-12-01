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

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.SaleAddAdapter;
import com.example.mathsbookwriter.model.BillModel;
import com.example.mathsbookwriter.model.ProductModel;
import com.example.mathsbookwriter.viewModel.BillViewModel;
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

public class NewBillDetail extends Fragment {

    private static final String TAG = NewBillDetail.class.getSimpleName();
    private static final String BILL_NUMBER ="bill_number";

    private BillViewModel mBillViewModel;
    private Context context;

    private TextView txtPartyName;
    private TextView txtDate;
    private TextView txtBillNumber;
    private TextView txtTotalPrice;
    private Button btnSubmit;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SaleAddAdapter mAdapter;

    private String billNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBillViewModel = ViewModelProviders.of(getActivity()).get(BillViewModel.class);
        billNumber = getArguments().getString(BILL_NUMBER);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_new_bill_details, container, false);
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
        btnSubmit = view.findViewById(R.id.btnSubmit);

        recyclerView = view.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new SaleAddAdapter();
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        mBillViewModel.getNewBillDetail(billNumber).observe(this, new Observer<BillModel>() {
            @Override
            public void onChanged(BillModel billModel) {
                if (billModel!= null){
                    Log.i(TAG, "bill model is not  null "+billModel.getBillNumber());
                    loadData(billModel);
                }else {
                    Log.i(TAG,"bill model is null");
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack(R.id.mainFragmentInventory, false);
            }
        });

    }

    private void loadData(BillModel billModel) {
        txtPartyName.setText(billModel.getPartyName());
        txtBillNumber.setText(billModel.getBillNumber());
        txtDate.setText(billModel.getBillingDate());
        txtTotalPrice.setText(String.valueOf(billModel.getTotalPrice()));
        List<ProductModel> model = billModel.getProductModels();
        for (int i=0; i<model.size();i++){
            Log.i(TAG, "product list is "+model.get(i).getProductName());

        }
        mAdapter.setProductItem(billModel.getProductModels());
    }
}
