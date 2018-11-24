package com.example.mathsbookwriter.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.interfaces.MainInventoryInterface;
import com.example.mathsbookwriter.model.TopicMain;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class AdminMainAdapter extends RecyclerView.Adapter<AdminMainAdapter.ViewHolder> {

    private static final String TAG = AdminMainAdapter.class.getSimpleName();
    private static final String TOPIC_KEY = "topicKey";
    private Context context;
    //no use of using interface here, just i dont delete it, i will do it later.
    private MainInventoryInterface mInterface;

    private List<TopicMain> topicName;

    public AdminMainAdapter(Context context, MainInventoryInterface mInterface) {
        this.context = context;
        this.mInterface = mInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_admin_main, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String topic = topicName.get(adapterPosition).getTopicName();
            Log.i(TAG, "adapter position selected is "+adapterPosition);
            Bundle bundle = new Bundle();
            bundle.putString(TOPIC_KEY, topic);
//            mInterface.funLoadTopic(topic);
            if (topic.equals("Products")){
                Navigation.findNavController(view).navigate(R.id.action_adminMainFragment_to_adminProductListView, bundle);
            }else if (topic.equals("Collection 2")){

            }else if (topic.equals("Collection 3")){

            }else if (topic.equals("Collection 4")){

            }else {
                Log.i(TAG, "No option selected from given list");
            }
        }
    }
}
