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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shto.vehiclebulletin.R;

import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment {

    // TODO: verification email send and take back to app after verified completed
    // TODO: log out user if his account is deleted from another device

    private static final String TAG = "register";
    // Members variables
    EditText mName;
    EditText mEmail;
    EditText mPassword;
    EditText mRecheckPassword;
    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Declare an instance of FirebaseFirestore
        db = FirebaseFirestore.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_register, container, false);

        mName = root.findViewById(R.id.input_register_name);
        mEmail = root.findViewById(R.id.input_register_email);
        mPassword = root.findViewById(R.id.input_register_password);
        mRecheckPassword = root.findViewById(R.id.input_register_passwordcheck);

        // REGISTER button
        Button registerButton = root.findViewById(R.id.button_register_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(mEmail.getText().toString(), mPassword.getText().toString());
            }
        });

        return root;
    }

    private void register(String email, String password) {

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
                            sendEmailVerification(user);
                            updateDB(user);
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

        String name = mName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mName.setError("Required.");
            valid = false;
        } else {
            mName.setError(null);
        }

        String email = mEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmail.setError("Required.");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        String password = mPassword.getText().toString();
        String recheckPassword = mRecheckPassword.getText().toString();
        if (!password.equals(recheckPassword)) {
            mPassword.setError("Passwords do not match.");
            mRecheckPassword.setError("Passwords do not match.");
            valid = false;
        } else if (TextUtils.isEmpty(password) || TextUtils.isEmpty(recheckPassword)) {
            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Required.");
                valid = false;
            }
            if (TextUtils.isEmpty(recheckPassword)) {
                mRecheckPassword.setError("Required.");
                valid = false;
            }
        } else {
            mPassword.setError(null);
            mRecheckPassword.setError(null);
        }

        return valid;
    }

    private void sendEmailVerification(FirebaseUser user) {
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG, "Email sent.");
                    Toast.makeText(getContext(), "Email sent.",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_navigation_vehicles);

            // Show Bottom Navigation Bar
            BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
            navView.setVisibility(View.VISIBLE);
        } else {
            mName.setText(null);
            mEmail.setText(null);
            mPassword.setText(null);
            mRecheckPassword.setText(null);
        }
    }

    private void updateDB(FirebaseUser user) {
        Map<String, Object> userName = new HashMap<>();
        userName.put("name", mName.getText().toString());

        if (user != null) {
            db.collection("users")
                    .document(user.getUid())
                    .set(userName)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }
    }
}