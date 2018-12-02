package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.SaleAddAdapter;
import com.example.mathsbookwriter.model.BillModel;
import com.example.mathsbookwriter.model.ProductModel;
import com.example.mathsbookwriter.viewModel.BillViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

public class BillDetails extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = BillDetails.class.getSimpleName();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String BILL_NUMBER ="bill_number";

    private BillViewModel mBillViewModel;
    private Context context;

    private TextView txtPartyName;
    private TextView txtDate;
    private TextView txtBillNumber;
    private TextView txtTotalPrice;
    private TextView txtStatus;
    private Button btnSubmit;
    private Spinner spinner_status;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SaleAddAdapter mAdapter;

    private String billNumber;
    private BillModel bill;
    private Boolean isSpinnerTouched= false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBillViewModel = ViewModelProviders.of(getActivity()).get(BillViewModel.class);
//        billNumber = getArguments().getString(BILL_NUMBER);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bill_detail, container, false);
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
        txtStatus = view.findViewById(R.id.txtStatus);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        spinner_status = view.findViewById(R.id.spinner_status);

        recyclerView = view.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new SaleAddAdapter();
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        bill = mBillViewModel.getSelectedBill();
        if (bill!= null){
            loadData(bill);
        }


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack(R.id.billDetails, true);
            }
        });

        //array list for spinner
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context,
                R.array.bill_Status, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(spinnerAdapter);
        spinner_status.setOnItemSelectedListener(this);

        //if i not use this one..automatically snipper selects the first item automatically
        //so restricting it until be select the item from spinner.
        spinner_status.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
            }
        });

    }

    private void loadData(BillModel billModel) {
        billNumber= billModel.getBillNumber();
        txtPartyName.setText(billModel.getPartyName());
        txtBillNumber.setText(billModel.getBillNumber());
        txtDate.setText(billModel.getBillingDate());
        txtTotalPrice.setText(String.valueOf(billModel.getTotalPrice()));
        txtStatus.setText(billModel.getStatus());
        List<ProductModel> model = billModel.getProductModels();
        for (int i=0; i<model.size();i++){
            Log.i(TAG, "product list is "+model.get(i).getProductName());

        }
        mAdapter.setProductItem(billModel.getProductModels());
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String status = parent.getItemAtPosition(position).toString();
        if (!isSpinnerTouched)return;
            db.collection("mainCollection").document("BillDocument")
                    .collection("BillCollection")
                    .document(billNumber)
                    .update("status",status)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "Order status changed to "+status, Toast.LENGTH_SHORT).show();
                            txtStatus.setText(status);
//                            oldStatus = status;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "unable to update the status", Toast.LENGTH_SHORT).show();
                        }

                    });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
