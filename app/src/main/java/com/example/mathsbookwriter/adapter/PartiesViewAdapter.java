package com.example.mathsbookwriter.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.interfaces.PartyInterface;
import com.example.mathsbookwriter.model.PartiesModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class PartiesViewAdapter extends RecyclerView.Adapter<PartiesViewAdapter.ViewHolder> {

    private static final String TAG = PartiesViewAdapter.class.getSimpleName();
    private static final String PARTY_NAME = "party_name";
    private Context context;
    private PartyInterface mInterface;

    private List<PartiesModel> partiesModels;

    public PartiesViewAdapter(Context context, PartyInterface mInterface) {
        this.context = context;
        this.mInterface = mInterface;
    }

    public PartiesViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_parties_view, parent, false);
      context = parent.getContext();
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (partiesModels!=null){
            holder.txtPartyName.setText(partiesModels.get(position).getPartyName());
            holder.txtMobileNumber.setText(partiesModels.get(position).getMobileNumber());
        }
    }

    @Override
    public int getItemCount() {
        if (partiesModels!= null){
            return partiesModels.size();
        }else {
            return 0;
        }
    }

    public void setPartyList(List<PartiesModel> partiesModels) {
        this.partiesModels = partiesModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtPartyName;
        TextView txtMobileNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPartyName = itemView.findViewById(R.id.txtPartyName);
            txtMobileNumber = itemView.findViewById(R.id.txtMobileNumber);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            PartiesModel party = partiesModels.get(adapterPosition);
           mInterface.funPartyDetails(party);
        }
    }
}
