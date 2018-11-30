package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.model.PartiesModel;
import com.example.mathsbookwriter.viewModel.PartiesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class PartyDetialFragment extends Fragment {

    private static final String TAG = PartyDetialFragment.class.getSimpleName();
    private static final String PARTY_NAME = "party_name";
    private static final String REGISTRATION_NUMBER ="registration";
    private static final String ADDRESS = "address";
    private static final String MOBILE_NUMBER = "mobile_number";
    private static final String CHECK_DETAILS = "check_details";

    private Context context;
    private PartiesViewModel mViewModel;
    private String partyName;
    private PartiesModel model;

    private TextView txtPartyName;
    private TextView txtRegistrationNumber;
    private TextView txtAddress;
    private TextView txtMobileNumber;
    private FloatingActionButton btnEditParty;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(PartiesViewModel.class);
//        partyName = getArguments().getString(PARTY_NAME);
//        Log.i(TAG, "the party name is "+partyName);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_party_detail, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtPartyName = view.findViewById(R.id.txtPartyName);
        txtRegistrationNumber = view.findViewById(R.id.txtRegistrationNumber);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtMobileNumber = view.findViewById(R.id.txtMobileNumber);
        btnEditParty = view.findViewById(R.id.btnEditParty);

       model = mViewModel.getParty();
       loadData();

        btnEditParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(PARTY_NAME, model.getPartyName());
                bundle.putString(REGISTRATION_NUMBER, model.getRegistrationNumber());
                bundle.putString(ADDRESS, model.getAddress());
                bundle.putString(MOBILE_NUMBER, model.getMobileNumber());
                bundle.putBoolean(CHECK_DETAILS, true);
                Navigation.findNavController(v).navigate(R.id.action_partyDetialFragment_to_partiesAddFragment, bundle);
            }
        });

    }

    private void loadData() {
        if (model!=null){
            txtPartyName.setText(model.getPartyName());
            txtRegistrationNumber.setText(model.getRegistrationNumber());
            txtAddress.setText(model.getAddress());
            txtMobileNumber.setText(model.getMobileNumber());
        }
    }
}
