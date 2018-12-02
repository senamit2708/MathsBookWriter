package com.example.mathsbookwriter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.interfaces.MainInventoryInterface;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BillMainAdapter extends RecyclerView.Adapter<BillMainAdapter.ViewHolder> {

    private static final String TAG = BillMainAdapter.class.getSimpleName();
    private Context context;

    private List<String> mStatus;
    private MainInventoryInterface mInterface;

    public BillMainAdapter(Context context, MainInventoryInterface mInterface) {
        this.context = context;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_bill_main, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtRecyclerTitle.setText(mStatus.get(position));
    }

    @Override
    public int getItemCount() {
        if (mStatus!= null){
            return mStatus.size();
        }else {
            return 0;
        }
    }

    public void setBillStatus(List<String> status) {
        mStatus = status;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtRecyclerTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRecyclerTitle = itemView.findViewById(R.id.txtRecyclerTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String status = mStatus.get(adapterPosition);
            mInterface.funLoadTopic(status);
        }
    }
}
