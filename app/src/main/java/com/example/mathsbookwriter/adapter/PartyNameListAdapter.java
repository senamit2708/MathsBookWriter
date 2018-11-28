package com.example.mathsbookwriter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.interfaces.PartyNameInterface;
import com.example.mathsbookwriter.model.PartiesModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PartyNameListAdapter extends RecyclerView.Adapter<PartyNameListAdapter.ViewHolder> {

    private static final String TAG = PartyNameListAdapter.class.getSimpleName();


    private Context context;
    private PartyNameInterface mInterface;



    private List<PartiesModel> partiesModels;

    public PartyNameListAdapter(Context context) {
        this.context = context;
    }

    public PartyNameListAdapter(Context context, PartyNameInterface mInterface) {
        this.context = context;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_party_name_list, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (partiesModels!= null){
            holder.txtPartyName.setText(partiesModels.get(position).getPartyName());
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

    public void setPartyName(List<PartiesModel> partiesModels) {
        this.partiesModels = partiesModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtPartyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPartyName = itemView.findViewById(R.id.txtPartyName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String partyName = partiesModels.get(adapterPosition).getPartyName();
            mInterface.funPartyName(partyName);


        }
    }
}
