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

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shto.vehiclebulletin.R;

public class LoginFragment extends Fragment {

    // TODO: unfocus when touch outside the EditText
    // TODO: unfocus on login failed
    // TODO: remove loginfragment from backstack so when you successfully login and press back, user wont go back to login fragment

    // Members variables
    EditText mEmail;
    EditText mPassword;

    private static final String TAG = "login";

    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        /* This callback will only be called when LoginFragment is at least Started.
        https://developer.android.com/guide/navigation/navigation-custom-back
        Prevent the back button to navigate to navigation start page (navigation_vehicles)*/
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Toast.makeText(getContext(), "backbutton pressed.", Toast.LENGTH_SHORT).show();
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        mEmail = root.findViewById(R.id.email_input);
        mPassword = root.findViewById(R.id.password_input);

        // LOGIN button
        Button loginButton = root.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(mEmail.getText().toString(), mPassword.getText().toString());
            }
        });

        // GoToRegister button
        Button goToRegister = root.findViewById(R.id.register_goto_button);
        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_navigation_vehicles);
                Navigation.findNavController(requireView()).navigate(R.id.registerFragment);
            }
        });


        // TODO: set in on onCreate or finish it
        BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
        navView.setVisibility(View.GONE);

        return root;
    }


    private void login(String email, String password) {

        Log.d(TAG, "signIn: " + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
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

        return valid;
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            requireActivity().getSupportFragmentManager().popBackStack(R.id.loginFragment, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_navigation_vehicles);

            // Show Bottom Navigation Bar
            BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
            navView.setVisibility(View.VISIBLE);
        } else {
            mEmail.setText(null);
            mPassword.setText(null);
        }
    }
}