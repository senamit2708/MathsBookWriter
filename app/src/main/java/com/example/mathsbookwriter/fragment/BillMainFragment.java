package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.BillMainAdapter;
import com.example.mathsbookwriter.interfaces.MainInventoryInterface;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BillMainFragment extends Fragment implements MainInventoryInterface {

    private static final String TAG = BillMainFragment.class.getSimpleName();
    private static final String BILL_STATUS = "bill_status";
    private Context context;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private BillMainAdapter mAdapter;

    private Button btnHide;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_bill_main, container, false);
       context = container.getContext();
       return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(context,2);
        mAdapter = new BillMainAdapter(context, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        btnHide = view.findViewById(R.id.btnHide);

        List<String> status = new ArrayList<>();
        status.add("Initial");
        status.add("Confirmed");
        status.add("Shipped");
        status.add("Complete");
        status.add("Closed");
        status.add("All");
        mAdapter.setBillStatus(status);

    }

    @Override
    public void funLoadTopic(String topic) {
        Bundle bundle = new Bundle();
        bundle.putString(BILL_STATUS, topic);
        Navigation.findNavController(getActivity(), R.id.btnHide).navigate(R.id.action_billMainFragment_to_billListFragment, bundle);
    }
}
