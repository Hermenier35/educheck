package com.example.educheck.Controleur.DashboardAdmin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddUniversityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddUniversityFragment extends Fragment implements AsyncTaskcallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TOKEN = "token";
    private static final String ARG_PARAM2 = "suffixeTeacher";
    private final int STORAGE_PERMISSION_CODE = 23;
    private final int GALLERY_REQUEST_CODE=24;
    private Button gallery, addUni;
    private EditText suffixeStudent;
    private EditText suffixeTeacher;
    private EditText nameUniv;
    private ImageView image;
    private DashboardImplementation request;
    private ActivityResultLauncher<Void> galleryLauncher;

    // TODO: Rename and change types of parameters
    private String token;
    private String param2;

    public AddUniversityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param token Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManagerUniversityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddUniversityFragment newInstance(String token, String param2) {
        AddUniversityFragment fragment = new AddUniversityFragment();
        Bundle args = new Bundle();
        args.putString(TOKEN, token);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(getActivity(),new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        if (getArguments() != null) {
            token = getArguments().getString(TOKEN);
            param2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewInflate = inflater.inflate(R.layout.fragment_add_university, container, false);
        gallery = viewInflate.findViewById(R.id.btnGallery);
        addUni = viewInflate.findViewById(R.id.addUniv);
        suffixeStudent = viewInflate.findViewById(R.id.suffixe_student);
        suffixeTeacher = viewInflate.findViewById(R.id.suffixe_teacher);
        nameUniv = viewInflate.findViewById(R.id.name_univ);
        image = viewInflate.findViewById(R.id.img_uni);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryLauncher.launch(null);
            }
        });
        addUni.setOnClickListener(v->{saveToBDD();});
        galleryLauncher = registerForActivityResult(new GalleryActivityResultContract(), result -> {
            if (result != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = requireContext().getContentResolver().query((Uri) result, filePathColumn, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String imgDecodableString = cursor.getString(columnIndex);
                        image.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    }
                    cursor.close();
                }
            }
        });
        return viewInflate;
    }

    public void saveToBDD(){
        University university;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = null;
        Drawable drawable = image.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] logo = byteArrayOutputStream.toByteArray();
        university = new University(nameUniv.getText().toString(), suffixeStudent.getText().toString(),
                suffixeTeacher.getText().toString(), logo);
        request = new DashboardImplementation(this);
        request.postUniversity(this.token, university);
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        if(!items.getJSONObject(0).has("code_retour")) {
            JSONObject response = items.getJSONObject(0);
            University university = new University(response.getString("name"), response.getString("suffixe_student"),
                    response.getString("suffixe_teacher"), Base64.getDecoder().decode(response.getString("image")));
            replaceFragment(ManagerUniversityFragment.newInstance(this.token, university));
        }else
            System.err.println("code_retour: " + items.getJSONObject(0).getString("code_retour") + " " +
                    "please check university save in the data base");
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = DashboardAdmin.fragmentManager;
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.scrollView,fragment);
        fragmentTransaction.commit();
    }
}