package com.shto.vehiclebulletin.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shto.vehiclebulletin.R;


public class RegisterFragment extends Fragment {

    private static final String TAG = "register";
    // Members variables
    EditText mEmail;
    EditText mPassword;
    EditText mRecheckPassword;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        mEmail = root.findViewById(R.id.email_create_input);
        mPassword = root.findViewById(R.id.password_create_input);
        mRecheckPassword = root.findViewById(R.id.password_recheck_input);

        // REGISTER button
        Button registerButton = root.findViewById(R.id.register_button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(mEmail.getText().toString(), mPassword.getText().toString(), mRecheckPassword.getText().toString());
            }
        });

        return root;
    }

    private void register(String email, String password, String recheckPassword) {

        Log.d(TAG, "register: " + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPassword.setError("Required.");
            valid = false;
        } else {
            mPassword.setError(null);
        }

        String recheckPassword = mRecheckPassword.getText().toString();
        if (TextUtils.isEmpty(recheckPassword)) {
            mRecheckPassword.setError("Required.");
            valid = false;
        } else {
            mRecheckPassword.setError(null);
        }

        if (!password.equals(recheckPassword)) {
            mPassword.setError("nu sunt asemanatoare");
            mRecheckPassword.setError("nu sunt asemanatoare");
            valid = false;
        } else {
            mPassword.setError(null);
            mRecheckPassword.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_navigation_vehicles);

            // Show Bottom Navigation Bar
            BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
            navView.setVisibility(View.VISIBLE);
        } else {
            mEmail.setText(null);
            mPassword.setText(null);
            mRecheckPassword.setText(null);
        }
    }
}