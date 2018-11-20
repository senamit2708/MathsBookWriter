package com.example.mathsbookwriter.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mathsbookwriter.R;
import com.example.mathsbookwriter.adapter.MainFragmentInventoryAdapter;
import com.example.mathsbookwriter.model.TopicMain;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainFragmentInventory extends Fragment {

    private static final String TAG = MainFragmentInventory.class.getSimpleName();
    private Context context;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainFragmentInventoryAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main_fragment_inventory, container, false);
        context = container.getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mRecyclerView = view.findViewById(R.id.recyclerView);
        mLayoutManager = new GridLayoutManager(context,2);
        mAdapter = new MainFragmentInventoryAdapter(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        List<TopicMain> topicName = new ArrayList<>();
        topicName.add(new TopicMain("Parties",R.drawable.parties_one));
        topicName.add(new TopicMain("Sales",R.drawable.sale_one));
        topicName.add(new TopicMain("Items",R.drawable.items_one));
        topicName.add(new TopicMain("Dashboard",R.drawable.dashboard_one));

        mAdapter.setTopic(topicName);
    }
}
