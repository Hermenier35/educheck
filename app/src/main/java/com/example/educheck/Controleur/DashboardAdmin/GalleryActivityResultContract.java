package com.example.educheck.Controleur.DashboardAdmin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GalleryActivityResultContract extends ActivityResultContract {

    Uri uri;
    String intent;

    public GalleryActivityResultContract(Uri uri, String intent) {
        this.uri = uri;
        this.intent = intent;
    }

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, Object input) {
        Intent intent = new Intent(this.intent, this.uri);
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    @Override
    public Uri parseResult(int resultCode, @Nullable Intent intent) {
        if (resultCode == Activity.RESULT_OK && intent != null) {
            return intent.getData();
        }
        System.out.println("Resultat : null");
        return null;
    }
}
