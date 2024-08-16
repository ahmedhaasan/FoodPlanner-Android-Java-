package com.example.foodplanneritiandroidjava.view.login_signUp.signUp.view;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.foodplanneritiandroidjava.R;
import com.example.foodplanneritiandroidjava.view.login_signUp.signUp.presenter.RegisterPresenter;
import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;


public class signUp_fragment extends Fragment implements RegisterView {

    Dialog loadingDialog;
    CircleImageView profileImage ;
    ImageButton editProfileImage ;
    EditText userName , email,password ;
    MaterialButton signUpButton ;
    View v ;

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

    //

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view;
        profileImage = view.findViewById(R.id.profile_image);
        editProfileImage = view.findViewById(R.id.edit_signUpImageButton);
        userName = view.findViewById(R.id.userNameField);
        email = view.findViewById(R.id.emailField);
        password = view.findViewById(R.id.passwordField);
        signUpButton =view.findViewById(R.id.signUpButton);
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        //
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailText = email.getText().toString().trim();
                String passwordText = password.getText().toString().trim();
                loadingDialog.show(); // Show loading dialog when login starts
                registerPresenter.handleUserRegistration(emailText,passwordText);
            }
        });
    }

    
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