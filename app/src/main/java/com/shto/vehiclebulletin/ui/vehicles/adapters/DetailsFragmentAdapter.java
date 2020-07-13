package com.shto.vehiclebulletin.ui.vehicles.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.shto.vehiclebulletin.ui.vehicles.DocumentsFragment;
import com.shto.vehiclebulletin.ui.vehicles.GeneralFragment;
import com.shto.vehiclebulletin.ui.vehicles.OverhaulsFragment;

public class DetailsFragmentAdapter extends FragmentStateAdapter {

    public DetailsFragmentAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new GeneralFragment();
            case 1:
                return new DocumentsFragment();
            case 2:
                return new OverhaulsFragment();
        }
        return new GeneralFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}