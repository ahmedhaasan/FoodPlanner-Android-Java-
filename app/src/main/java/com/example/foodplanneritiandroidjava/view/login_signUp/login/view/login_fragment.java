package com.example.foodplanneritiandroidjava.view.login_signUp.login.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanneritiandroidjava.networkStatus.NetworkUtils;
import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.SomeContstants;
import com.example.foodplanneritiandroidjava.firebase.FireBasePresenter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;


public class login_fragment extends Fragment implements LoginView {


    TextView registerText;
    View v1;
    MaterialButton sign_in_button;
    CircleImageView signWithGoogleButton;
    MaterialButton go_as_Guest;
    /*
        TextInputLayout email_layout, password_layout;
    */
    EditText email_edit_text, password_editText;
    Dialog loadingDialog;

    private GoogleSignInClient googleSignInClient;
    private static final int RC_SIGN_IN = 30;
    private FireBasePresenter fireBasePresenter;
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
        fireBasePresenter = new FireBasePresenter(this);

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
        go_as_Guest = view.findViewById(R.id.go_as_Guest);

        // Initialize TextInputLayout and TextInputEditText
        TextInputLayout emailInputLayout = view.findViewById(R.id.emailInputLayout);
        TextInputLayout passwordInputLayout = view.findViewById(R.id.passwordInputLayout);
        TextInputEditText email_edit_text = view.findViewById(R.id.emailField);
        TextInputEditText password_editText = view.findViewById(R.id.passwordField);

        // Setup loading dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Check if user is already signed in
        // Handle button clicks for sign in, Google sign in, etc.
        sign_in_button.setOnClickListener(v -> {
            String email = email_edit_text.getText().toString().trim();
            String password = password_editText.getText().toString().trim();

            if (NetworkUtils.isConnectedToInternet(getContext())) {
                if (email.isEmpty()) {
                    emailInputLayout.setError("Email cannot be empty");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {  // this line to check the email pattern
                    emailInputLayout.setError("Invalid email address");
                } else if (password.isEmpty()) {
                    passwordInputLayout.setError("Password cannot be empty");  // set the password error with red to the ser
                } else {
                    emailInputLayout.setError(null);
                    passwordInputLayout.setError(null);
                    loadingDialog.show();               // then make the load dialog untill response in on succsses
                    fireBasePresenter.handleEmailPasswordLogin(email, password);
                }
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("No Internet Connection")
                        .setMessage("Please check your internet connection and try again.")
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
            }
            addUserSharedState(false);

        });

        // Handle Google Sign-In
        signWithGoogleButton.setOnClickListener(v -> {
            if (NetworkUtils.isConnectedToInternet(getContext())) {
                loadingDialog.show();
                signIn();
                addUserSharedState(false);
            } else {
                Toast.makeText(getContext(), "Please Check the Network First", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle navigation to sign up
        registerText.setOnClickListener(view1 -> {
            if (NetworkUtils.isConnectedToInternet(getContext())) {


                Navigation.findNavController(v1).navigate(R.id.action_login_fragment_to_signUp_fragment);
            } else {
                Toast.makeText(getContext(), "Please Check the Network First", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle guest login
        go_as_Guest.setOnClickListener(view1 -> {
            SharedPreferences guestUser = getActivity().getSharedPreferences(SomeContstants.USERSTATE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = guestUser.edit();
            editor.putBoolean(SomeContstants.ISGUEST, true);
            editor.putString("name", SomeContstants.GUESTUSER);
            editor.apply();
            navigateToHome();
        });

    }




    void addUserSharedState(boolean state){
        SharedPreferences guestUser = getActivity().getSharedPreferences(SomeContstants.USERSTATE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = guestUser.edit();
        editor.putBoolean(SomeContstants.ISGUEST, state);
        editor.apply();
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
                fireBasePresenter.handleGoogleLogin(account.getIdToken());
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
        // store user to check with firebase and shared
        requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE).edit().putString("newUser", "new").apply();
        navigateToHome();

        // change that the user is not a gurst user
        SharedPreferences guestUser = getActivity().getSharedPreferences(SomeContstants.GUESTUSER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = guestUser.edit();
        editor.putBoolean(SomeContstants.ISGUEST, false);
        editor.apply();
    }

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