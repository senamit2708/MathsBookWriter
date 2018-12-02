package com.example.mathsbookwriter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.interfaces.BillListInterface;
import com.example.mathsbookwriter.model.BillModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.ViewHolder> {

    private static final String TAG = BillListAdapter.class.getSimpleName();
    private Context context;

    private List<BillModel> mBillModels;
    private BillListInterface mInterface;

    public BillListAdapter(Context context, BillListInterface mInterface) {
        this.context = context;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bill_list, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mBillModels!= null){
            holder.txtPartyName.setText(mBillModels.get(position).getPartyName());
            holder.txtBillNumber.setText(mBillModels.get(position).getBillNumber());
            holder.txtDate.setText(mBillModels.get(position).getBillingDate());
            holder.txtStatus.setText(String.valueOf(mBillModels.get(position).getStatus()));
        }else {
            Toast.makeText(context, "Unable to upload details ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if (mBillModels!= null){
            return mBillModels.size();
        }else {
            return 0;
        }

    }

    public void setBillModels(List<BillModel> billModels) {
        mBillModels= billModels;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtPartyName;
        TextView txtDate;
        TextView txtBillNumber;
        TextView txtStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPartyName = itemView.findViewById(R.id.txtPartyName);
            txtBillNumber = itemView.findViewById(R.id.txtBillNumber);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            BillModel bill = mBillModels.get(adapterPosition);
            mInterface.funLoadBillModel(bill);

        }
    }
}
