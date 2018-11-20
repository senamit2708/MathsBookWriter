package com.example.mathsbookwriter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.model.TopicMain;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragmentInventoryAdapter extends RecyclerView.Adapter<MainFragmentInventoryAdapter.ViewHolder> {

    private Context context;
    private List<TopicMain> topicName;

    public MainFragmentInventoryAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_main_fragment_inventory,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (topicName!= null){
            holder.txtTitle.setText(topicName.get(position).getTopicName());
            holder.txtImageView.setImageResource(topicName.get(position).getImage());
        }
    }

    @Override
    public int getItemCount() {
        if (topicName!= null){
            return topicName.size();
        }else{
            return 0;
        }
    }

    public void setTopic(List<TopicMain> topicName) {
            this.topicName = topicName;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTitle;
        ImageView txtImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtRecyclerTitle);
            txtImageView = itemView.findViewById(R.id.txtImageView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
