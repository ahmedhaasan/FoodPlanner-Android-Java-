package com.example.foodplanneritiandroidjava.view.login_signUp.signUp.view;


import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.view.login_signUp.signUp.presenter.RegisterPresenter;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class signUp_fragment extends Fragment implements RegisterView {

    Dialog loadingDialog;
    CircleImageView profileImage ;
    ImageButton editProfileImage ;
    EditText userName , email, password ;
    MaterialButton signUpButton ;
    View v ;
    // for password toggle visability
    private ImageButton togglePasswordVisibility;
    private boolean isPasswordVisible = false;
    //  instance from register presenter
    private RegisterPresenter registerPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPresenter = new RegisterPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view;
        profileImage = view.findViewById(R.id.profile_image);
        editProfileImage = view.findViewById(R.id.edit_signUpImageButton);
        userName = view.findViewById(R.id.userNameField);
        email = view.findViewById(R.id.emailField);
        password = view.findViewById(R.id.passwordField);
        signUpButton = view.findViewById(R.id.signUpButton);
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // Real-time email validation
        setupEmailValidation();

        // Real-time password validation
        setupPasswordValidation();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();

                // Perform validation before proceeding
                if (!validateEmail(emailText)) {
                    Toast.makeText(getContext(), "Invalid email format.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!validatePassword(passwordText)) {
                    Toast.makeText(getContext(), "Password must be at least 8 characters long, " +
                            "and contain one uppercase letter, one lowercase letter, one number, and one special character.", Toast.LENGTH_LONG).show();
                    return;
                }

                loadingDialog.show(); // Show loading dialog when login starts
                registerPresenter.handleUserRegistration(emailText, passwordText);
            }
        });

/*    // Set onClickListener for the toggle button
        togglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide password
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    togglePasswordVisibility.setImageResource(R.drawable.ic_visibility_off); // Replace with your 'eye-off' icon
                } else {
                    // Show password
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    togglePasswordVisibility.setImageResource(R.drawable.ic_visibility_on); // Replace with your 'eye-on' icon
                }
                // Move cursor to the end
                password.setSelection(password.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });*/
    }

    // Email validation setup
    private void setupEmailValidation() {
        email.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validateEmail(s.toString())) {
                    email.setError(null);
                } else {
                    email.setError("Invalid email format.");
                }
            }
        });
    }

    // Password validation setup
    private void setupPasswordValidation() {
        password.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validatePassword(s.toString())) {
                    password.setError(null);
                } else {
                    password.setError("Password must be 8 characters, including uppercase, lowercase, number, and special character.");
                }
            }
        });
    }

    // Email validation method
    private boolean validateEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailPattern);
    }

    // Password validation method
    private boolean validatePassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }

    // SimpleTextWatcher to avoid boilerplate
    abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}
    }

    // Implement the rest of the RegisterView methods...




    // implemented from Register View 
    @Override
    public void onRegisterSuccess() {
        loadingDialog.dismiss(); // Dismiss the loading dialog on success

        Toast.makeText(getContext(), "Sign UP Done Successfully ", Toast.LENGTH_SHORT).show();
        Navigation.findNavController(v).navigate(R.id.action_signUp_fragment_to_login_fragment);

    }

    @Override
    public void onRegisterFailure(String errorMessage) {
        loadingDialog.dismiss(); // Dismiss the loading dialog on success

        if (errorMessage != null && errorMessage.contains("provider is disabled")) {
            Toast.makeText(getContext(), "Sign-up failed: Email/Password sign-in method is disabled.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error Signing Up: " + errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

}