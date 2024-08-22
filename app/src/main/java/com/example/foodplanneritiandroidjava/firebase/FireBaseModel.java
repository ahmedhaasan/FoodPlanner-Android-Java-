package com.example.foodplanneritiandroidjava.firebase;

import android.content.Context;

interface  FireBaseModel {

        void downloadDataFromFirebase(String userId, DataDownloadCallback callback, Context context);
        void uploadDataToFirebase(String userId, DataUploadCallback callback, Context context);

}
