package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.PartyNameListAdapter;
import com.example.mathsbookwriter.interfaces.PartyNameInterface;
import com.example.mathsbookwriter.model.PartiesModel;
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

public class PartyNameListFragment extends Fragment implements PartyNameInterface {

    private static final String TAG = PartyNameListFragment.class.getSimpleName();
    private static final String STATUS = "status";
    private static final String PARTY_NAME = "party_name";
    private Context context;
    private PartiesViewModel mViewModel;


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManger;
    private PartyNameListAdapter mAdapter;

    private Button btnSubmit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(PartiesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_party_name_list, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSubmit = view.findViewById(R.id.btnSubmit);
        recyclerView = view.findViewById(R.id.recyclerview);
        mLayoutManger = new LinearLayoutManager(context);
        mAdapter = new PartyNameListAdapter(context, this);
        recyclerView.setLayoutManager(mLayoutManger);
        recyclerView.setAdapter(mAdapter);

       mViewModel.getListOfParty().observe(this, new Observer<List<PartiesModel>>() {
           @Override
           public void onChanged(List<PartiesModel> partiesModels) {
               mAdapter.setPartyName(partiesModels);
           }
       });

       btnSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

    }

    @Override
    public void funPartyName(String partyName) {
        Bundle bundle = new Bundle();
        bundle.putInt(STATUS, 2);
        bundle.putString(PARTY_NAME, partyName);
        mViewModel.setPartyNameforSale(partyName);
        Navigation.findNavController(getActivity(),R.id.btnSubmit).popBackStack();
    }
}
