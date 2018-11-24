package com.example.mathsbookwriter.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.interfaces.ProductDetailInterface;
import com.example.mathsbookwriter.model.ProductModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.ViewHolder> {

    private static final String TAG = AdminProductAdapter.class.getSimpleName();
    private static final String PRODUCT_NUMBER = "product_number";
    private Context context;
    private ProductDetailInterface mInterface;

    private List<ProductModel> productList;

    public AdminProductAdapter(Context context) {
        this.context = context;
    }

    public AdminProductAdapter(Context context, ProductDetailInterface mInterface) {
        this.context = context;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_admin_product, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (productList!= null){
            holder.txtProductNumber.setText(productList.get(position).getProductNumber());
            holder.txtProductName.setText(productList.get(position).getProductName());
            holder.txtPrice.setText(String.valueOf(productList.get(position).getSellPriceOne()));
            holder.txtQuantity.setText(String.valueOf(productList.get(position).getAvailableQuantity()));
        }
    }

    @Override
    public int getItemCount() {
        if (productList!= null){
            return productList.size();
        }else {
            return 0;
        }
    }

    public void setProductList(List<ProductModel> productList){
        this.productList = productList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtProductNumber;
        TextView txtProductName;
        TextView txtQuantity;
        TextView txtPrice;
        EditText txtEditQuantity;
        Button btnAddQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductNumber = itemView.findViewById(R.id.txtProductNumber);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtEditQuantity = itemView.findViewById(R.id.txtEditQuantity);
            btnAddQuantity = itemView.findViewById(R.id.btnAddQuantity);

            itemView.setOnClickListener(this);
            btnAddQuantity.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String product_number = productList.get(adapterPosition).getProductNumber();

            if (view.getId()==itemView.getId()){
                Bundle bundle = new Bundle();
                bundle.putString(PRODUCT_NUMBER, product_number);

                Navigation.findNavController(view).navigate(R.id.action_adminProductListView_to_productDetails, bundle);

            }

            if (view.getId()==R.id.btnAddQuantity){
                int availableQuantity= productList.get(adapterPosition).getAvailableQuantity();
                String quantity = txtEditQuantity.getText().toString();
                mInterface.funAddQuantity(quantity,availableQuantity, product_number);
            }
        }
    }
}
