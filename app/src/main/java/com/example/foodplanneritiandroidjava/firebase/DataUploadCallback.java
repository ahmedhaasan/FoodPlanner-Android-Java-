package com.example.foodplanneritiandroidjava.firebase;

public interface DataUploadCallback {

        void onUploadSuccess();
        void onUploadFailure(String errorMessage);

    }
