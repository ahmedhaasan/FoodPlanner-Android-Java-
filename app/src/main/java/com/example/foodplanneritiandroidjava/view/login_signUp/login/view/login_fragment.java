package com.example.foodplanneritiandroidjava.view.login_signUp.login.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.view.login_signUp.login.presenter.LoginPresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class login_fragment extends Fragment implements LoginView {
    TextView registerText ;
    View v1;
    MaterialButton sign_in_button;
    SignInButton signWithGoogleButton;
    TextInputLayout email_layout, password_layout;
    TextInputEditText email_edit_text, password_editText;
    Dialog loadingDialog;

    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 30;
    private LoginPresenter loginPresenter;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Ensure you have this ID in your strings.xml
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        loginPresenter = new LoginPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v1 = view;
        registerText = view.findViewById(R.id.notHaveAcountText);
        sign_in_button = view.findViewById(R.id.signInButton);
        signWithGoogleButton = view.findViewById(R.id.signInWithGoogleButton);
        email_layout = view.findViewById(R.id.emailInputLayout);
        password_layout = view.findViewById(R.id.passwordInputLayout);
        email_edit_text = view.findViewById(R.id.emailEditText);
        password_editText = view.findViewById(R.id.passwordEditText);
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        // Check if user is already signed in
        if (firebaseAuth.getCurrentUser() != null) {
            navigateToHome(); // take care view here may be null
        }
        // action on button sign in
        sign_in_button.setOnClickListener(v -> {
            String email = email_edit_text.getText().toString().trim();
            String password = password_editText.getText().toString().trim();
            loadingDialog.show(); // Show loading dialog when login starts
            loginPresenter.handleEmailPasswordLogin(email, password);
        });

        // action on sign in with google
        signWithGoogleButton.setOnClickListener(v -> {
            loadingDialog.show(); // Show loading dialog when Google Sign-In starts
            signIn();
        });

        registerText.setOnClickListener(v ->
                Navigation.findNavController(v1).navigate(R.id.action_login_fragment_to_signUp_fragment)
        );
    }

    private void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                loginPresenter.handleGoogleLogin(account.getIdToken());
            } catch (ApiException e) {
                loadingDialog.dismiss(); // Dismiss the loading dialog on failure

                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLoginSuccess() {
        loadingDialog.dismiss(); // Dismiss the loading dialog on success
        Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
        navigateToHome();    }

    @Override
    public void onLoginFailure(String errorMessage) {
        loadingDialog.dismiss(); // Dismiss the loading dialog on failure
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void navigateToHome() {
        Navigation.findNavController(v1).navigate(R.id.action_login_fragment_to_homeActivity);
        getActivity().finish(); // Close LoginActivity
    }
}