package com.example.mathsbookwriter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.interfaces.ProductNameInterface;
import com.example.mathsbookwriter.model.ProductModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SaleAddProductAdapter extends RecyclerView.Adapter<SaleAddProductAdapter.ViewHolder> {

    private static final String TAG = SaleAddProductAdapter.class.getSimpleName();
    private Context context;
    private ProductNameInterface mInterface;
    private List<ProductModel> productModels;

    public SaleAddProductAdapter(Context context, ProductNameInterface mInterface) {
        this.context = context;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_sale_add_product, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (productModels!=null){
            holder.txtProductName.setText(productModels.get(position).getProductName());
            holder.txtQuantity.setText(String.valueOf(productModels.get(position).getAvailableQuantity()));
            holder.txtPrice.setText(String.valueOf(productModels.get(position).getSellPriceOne()));
        }
    }

    @Override
    public int getItemCount() {
        if (productModels!= null){
            Log.i(TAG, "the size of productmodels is "+productModels.size());
            return productModels.size();

        }else {
            return 0;
        }
    }

    public void setProductList(List<ProductModel> productModels) {
        this.productModels = productModels;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtProductName;
        TextView txtQuantity;
        TextView txtPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String productNumber = productModels.get(adapterPosition).getProductNumber();
            String productName = productModels.get(adapterPosition).getProductName();
            int quantity = productModels.get(adapterPosition).getAvailableQuantity();
            float price = productModels.get(adapterPosition).getSellPriceOne();
            mInterface.funProductNumber(productNumber, productName, quantity, price);
            ProductModel model = new ProductModel(productNumber, productName, quantity, price);
            mInterface.funProductDetails(model);

        }
    }
}
