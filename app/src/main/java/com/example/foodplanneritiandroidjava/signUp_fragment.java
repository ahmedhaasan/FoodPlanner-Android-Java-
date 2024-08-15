package com.example.foodplanneritiandroidjava;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

import de.hdodenhof.circleimageview.CircleImageView;


public class signUp_fragment extends Fragment {

    CircleImageView profileImage ;
    ImageButton editProfileImage ;
    EditText userName , email,password ;
    MaterialButton signUpButton ;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        profileImage = view.findViewById(R.id.profile_image);
        editProfileImage = view.findViewById(R.id.profile_image);
        userName = view.findViewById(R.id.userNameField);
        email = view.findViewById(R.id.emailField);
        password = view.findViewById(R.id.passwordField);
        signUpButton =view.findViewById(R.id.signUpButton);

        //
    }
}