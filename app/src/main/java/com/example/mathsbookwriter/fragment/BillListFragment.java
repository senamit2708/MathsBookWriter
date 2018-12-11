package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.BillListAdapter;
import com.example.mathsbookwriter.interfaces.BillListInterface;
import com.example.mathsbookwriter.model.BillModel;
import com.example.mathsbookwriter.viewModel.BillViewModel;
import com.example.mathsbookwriter.viewModel.PartiesViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BillListFragment extends Fragment implements BillListInterface {

    private static final String TAG = BillListFragment.class.getSimpleName();
    private static final String BILL_STATUS = "bill_status";

    private Context context;
    private BillViewModel mViewModel;
    private PartiesViewModel mPartyViewModel;

    private RecyclerView mRecyclerview;
    private RecyclerView.LayoutManager mLayoutManager;
    private BillListAdapter mAdapter;

    private Button btnHide;
//    private Button btnPartyName;

    private String billStatus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        billStatus = getArguments().getString(BILL_STATUS);
        mViewModel = ViewModelProviders.of(getActivity()).get(BillViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_bill_list, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerview = view.findViewById(R.id.recyclerview);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new BillListAdapter(context, this);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setAdapter(mAdapter);
        btnHide = view.findViewById(R.id.btnHide);
//        btnPartyName = view.findViewById(R.id.btnPartyName);

        mViewModel.getCurrentBillList(billStatus).observe(this, new Observer<List<BillModel>>() {
            @Override
            public void onChanged(List<BillModel> billModels) {
                if (billModels!= null){
                    Log.i(TAG, "inside getcurrentbilllist "+billModels.size());
                    mAdapter.setBillModels(billModels);
                }
            }
        });

//        btnPartyName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectPartyName();
//            }
//        });


    }

//    private void selectPartyName() {
//        Navigation.findNavController(getActivity(),R.id.btnPartyName).navigate(R.id.action_billListFragment_to_partyNameListFragment);
//    }

    @Override
    public void funLoadBillModel(BillModel bill) {
        mViewModel.setSelectedBill(bill);
        Navigation.findNavController(getActivity(),R.id.btnHide).navigate(R.id.action_billListFragment_to_billDetails);
    }
}
