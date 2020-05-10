package com.SevenDigITs.Solutions.justTap.user.apps;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.SevenDigITs.Solutions.justTap.R;

public class App1 extends Fragment {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.activity_apps, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);



        // Passing the column number 1 to show online one column in each row.
        recyclerViewLayoutManager = new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        adapter = new AppsAdapter(getActivity(), new ApkInfoExtractor(getActivity()).GetAllInstalledApkInfo());

        recyclerView.setAdapter(adapter);
        return rootView;

    }
}
