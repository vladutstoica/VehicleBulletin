package com.shto.vehiclebulletin.ui.vehicles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shto.vehiclebulletin.R;
import com.shto.vehiclebulletin.ui.vehicles.adapters.DetailsFragmentAdapter;

public class DetailsFragment extends Fragment {
    private String[] titles;

    DetailsFragmentAdapter detailsFragmentAdapter;
    ViewPager2 viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        detailsFragmentAdapter = new DetailsFragmentAdapter(this);
        viewPager = view.findViewById(R.id.view_details_pager);
        viewPager.setAdapter(detailsFragmentAdapter);

        titles = getResources().getStringArray(R.array.tab_layout);

        TabLayout tabLayout = view.findViewById(R.id.tab_details_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(titles[position]);
                    }
                }
        ).attach();
    }
}