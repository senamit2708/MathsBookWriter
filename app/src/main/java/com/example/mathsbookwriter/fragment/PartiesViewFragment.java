package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.PartiesViewAdapter;
import com.example.mathsbookwriter.model.PartiesModel;
import com.example.mathsbookwriter.viewModel.PartiesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PartiesViewFragment extends Fragment {

    private static final String TAG = PartiesViewFragment.class.getSimpleName();
    private static final String CHECK_DETAILS = "check_details";

    private Context context;
    private PartiesViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PartiesViewAdapter mAdapter;


    private FloatingActionButton fabAddParties;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(PartiesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        View view= inflater.inflate(R.layout.activity_parties_view, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fabAddParties = view.findViewById(R.id.fabAddParties);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(context);
        mAdapter = new PartiesViewAdapter(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mViewModel.getListOfParty().observe(this, new Observer<List<PartiesModel>>() {
            @Override
            public void onChanged(List<PartiesModel> partiesModels) {
                if (partiesModels!= null){
                    mAdapter.setPartyList(partiesModels);
                }
            }
        });

        fabAddParties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean(CHECK_DETAILS, false);
                Navigation.findNavController(v).navigate(R.id.action_partiesViewFragment_to_partiesAddFragment, bundle);
            }
        });

    }
}
