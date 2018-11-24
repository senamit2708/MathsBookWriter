package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.model.PartiesModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class PartiesAddFragment extends Fragment{

    private static final String TAG = PartiesAddFragment.class.getSimpleName();
    private static final String PARTY_NAME = "party_name";
    private static final String REGISTRATION_NUMBER ="registration";
    private static final String ADDRESS = "address";
    private static final String MOBILE_NUMBER = "mobile_number";
    private static final String CHECK_DETAILS = "check_details";

    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private EditText txtPartyName;
    private EditText txtRegistrationNumber;
    private EditText txtAddress;
    private EditText txtPhoneNumber;
    private EditText txtDate;
    private Button btnSubmit;

    private String partyName;
    private String registrationNumber;
    private String address;
    private String mobileNumber;
    private Boolean checkDetail;
    private String date;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkDetail= getArguments().getBoolean(CHECK_DETAILS);
        if (checkDetail){
            partyName = getArguments().getString(PARTY_NAME);
            registrationNumber = getArguments().getString(REGISTRATION_NUMBER);
            address = getArguments().getString(ADDRESS);
            mobileNumber = getArguments().getString(MOBILE_NUMBER);

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = container.getContext();
        View view = inflater.inflate(R.layout.activity_parties_add, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtPartyName = view.findViewById(R.id.txtPartyName);
        txtRegistrationNumber = view.findViewById(R.id.txtRegistrationNumber);
        txtAddress = view.findViewById(R.id.txtAddress);
        txtPhoneNumber = view.findViewById(R.id.txtPhoneNumber);
        txtDate = view.findViewById(R.id.txtDate);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        if (checkDetail){
            txtPartyName.setText(partyName);
            txtRegistrationNumber.setText(registrationNumber);
            txtPhoneNumber.setText(mobileNumber);
            txtAddress.setText(address);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDetails();
                entryDetailsToFirebase(view);
            }
        });

    }

    private void entryDetailsToFirebase(final View view) {
        Log.i(TAG, "the db connection is "+db);
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());

        PartiesModel party = new PartiesModel(partyName, registrationNumber, address,mobileNumber,date);
        db.collection("mainCollection").document("partyList")
                .collection("parties").document(partyName).set(party)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Party Entry Successful", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack(R.id.partiesAddFragment,true);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Party Entry Failed",Toast.LENGTH_SHORT).show();
            }
        });
//        db.collection("mainCollection").document(partyName).set(party);
    }

    private void getDetails() {
        partyName = txtPartyName.getText().toString();
        registrationNumber = txtRegistrationNumber.getText().toString();
        address = txtAddress.getText().toString();
        mobileNumber = txtPhoneNumber.getText().toString();
        date = txtDate.getText().toString();
    }

}
