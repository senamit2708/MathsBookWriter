package com.example.mathsbookwriter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.model.OrderedProductModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BillProductShowAdapter extends RecyclerView.Adapter<BillProductShowAdapter.ViewHolder> {

    private static final String TAG = BillProductShowAdapter.class.getSimpleName();
    private Context context;
    List<OrderedProductModel> productItem;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_sale_add, parent, false);
        context = parent.getContext();
        return new BillProductShowAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (productItem!= null){
            holder.txtProductName.setText(productItem.get(position).getProductName());
            holder.txtQuantity.setText(String.valueOf(productItem.get(position).getOrderedQuantity()));
            holder.txtPrice.setText(String.valueOf(productItem.get(position).getSellingPrice()));
            Log.i(TAG, "product name is "+productItem.get(position).getProductName());
            Log.i(TAG, "quantity is "+productItem.get(position).getOrderedQuantity());
        }
    }

    @Override
    public int getItemCount() {
        if (productItem!= null){
            return productItem.size();
        }else {
            return 0;
        }
    }
    public void setProductItem(List<OrderedProductModel> productItem) {
        this.productItem = productItem;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName;
        TextView txtQuantity;
        TextView txtPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
}
