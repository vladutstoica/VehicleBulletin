package com.shto.vehiclebulletin.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shto.vehiclebulletin.R;

public class ProfileFragment extends Fragment {

    private static final String TAG = "profile";
    private ProfileViewModel mProfileViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        final TextView textView = root.findViewById(R.id.text_notifications);

        mProfileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        mProfileViewModel.getProfileName().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button signOutButton = root.findViewById(R.id.signout_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(requireView()).navigate(R.id.loginFragment);

            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            boolean emailVerified = user.isEmailVerified();
            if (emailVerified) {
                Toast.makeText(getContext(), "email verified", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "email unverified", Toast.LENGTH_SHORT).show();
                // TODO: make snackbar work and open email for email verification
                // Snackbar.make(getActivity().getCurrentFocus(), "Email Unverified", Snackbar.LENGTH_SHORT).show();
            }
        }

        return root;
    }
}