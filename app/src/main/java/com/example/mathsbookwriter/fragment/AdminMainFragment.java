package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.AdminMainAdapter;
import com.example.mathsbookwriter.interfaces.MainInventoryInterface;
import com.example.mathsbookwriter.model.TopicMain;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminMainFragment extends Fragment implements MainInventoryInterface {

    private static final String TAG = AdminMainFragment.class.getSimpleName();
    private Context context;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AdminMainAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_admin_main_fragment, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(context,2);
        mAdapter = new AdminMainAdapter(context, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //adding the list for the recycler view manually
        List<TopicMain> topicName = new ArrayList<>();
        topicName.add(new TopicMain("Products",R.drawable.ic_product_two));
        topicName.add(new TopicMain("Collection 2",R.drawable.sale_one));
        topicName.add(new TopicMain("Collection 3",R.drawable.items_one));
        topicName.add(new TopicMain("Collection 4",R.drawable.dashboard_one));

        mAdapter.setTopic(topicName);
    }

    @Override
    public void funLoadTopic(String topic) {

    }
}
