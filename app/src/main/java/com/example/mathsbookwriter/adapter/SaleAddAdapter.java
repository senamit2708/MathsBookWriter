package com.example.mathsbookwriter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.model.ProductModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SaleAddAdapter extends RecyclerView.Adapter<SaleAddAdapter.ViewHolder> {

    private static final String TAG = SaleAddAdapter.class.getSimpleName();
    private Context context;

    private List<ProductModel> productItem;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_sale_add, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (productItem!= null){
            holder.txtProductName.setText(productItem.get(position).getProductName());
            holder.txtQuantity.setText(String.valueOf(productItem.get(position).getOrderedQuantity()));
            holder.txtPrice.setText(String.valueOf(productItem.get(position).getSellingPrice()));
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

    public void setProductItem(List<ProductModel> productItem) {
        this.productItem = productItem;
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
