package com.example.educheck.Controleur.DashboardAdmin;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagerUniversityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagerUniversityFragment extends Fragment implements AsyncTaskcallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TOKEN = "token";
    private static final String UNIVERSITY = "university";

    // TODO: Rename and change types of parameters
    private String token;
    private ImageView LogoUniv;
    private TextView suffixStudent;
    private  TextView suffixTeacher;
    private EditText NewName ;
    private EditText NewSuffixStudent;
    private EditText NewSuffixTeacher;
    private ImageView NewLogo;
    private Button btnSave;
    private Button btnImportImage;
    private TextView NameOfUniversity;

    private ActivityResultLauncher<Void> galleryLauncher;
    private University university;
    private MenuItem item;

    public ManagerUniversityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param token Token de session.
     * @param university University of Admin.
     * @return A new instance of fragment ManagerUniversityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManagerUniversityFragment newInstance(String token, University university) {
        ManagerUniversityFragment fragment = new ManagerUniversityFragment();
        Bundle args = new Bundle();
        args.putString(TOKEN, token);
        args.putSerializable(UNIVERSITY, university);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            token = getArguments().getString(TOKEN);
            university = (University) getArguments().getSerializable(UNIVERSITY);
        }

    }

    @SuppressLint("WrongThread")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewInflate = inflater.inflate(R.layout.fragment_manager_university, container, false);
        suffixStudent =viewInflate.findViewById(R.id.sufStudent);
        NameOfUniversity=viewInflate.findViewById(R.id.Name);
        suffixTeacher=viewInflate.findViewById(R.id.sufTeacher);
        LogoUniv =viewInflate.findViewById(R.id.LogoUniv);
        NewName = viewInflate.findViewById(R.id.NewName);
        NewSuffixStudent=viewInflate.findViewById(R.id.NewSuffixStudent);
        NewSuffixTeacher=viewInflate.findViewById(R.id.NewSuffixTeacher);
        NewLogo=viewInflate.findViewById(R.id.NewLogo);
        btnSave=viewInflate.findViewById(R.id.btnSave);
        btnImportImage=viewInflate.findViewById(R.id.btnImportImage);
        btnSave.setEnabled(false);
        NewSuffixTeacher.addTextChangedListener(NameWatcher);
        NewSuffixStudent.addTextChangedListener(NameWatcher);
        NewName.addTextChangedListener(NameWatcher);


        btnSave.setOnClickListener(v->{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap bitmap = null;
            Drawable drawable = NewLogo.getDrawable();
            if (drawable instanceof BitmapDrawable) {
                bitmap = ((BitmapDrawable) drawable).getBitmap();
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] logo = byteArrayOutputStream.toByteArray();
            university.setUniName(NewName.getText().toString());
            university.setSuffixeStudent(NewSuffixStudent.getText().toString());
            university.setSuffixeTeacher(NewSuffixTeacher.getText().toString());
            university.setImage(logo);
        });

        suffixStudent.setText( "Suffixe Student : " +university.getSuffixeStudent() + ".");
        suffixTeacher.setText( "Suffixe Teacher : " +university.getSuffixeTeacher() +".");
        NameOfUniversity.setText("Name of university : " + university.getUniName() +".");
        byte[] imageBytes =university.getImage();
        LogoUniv.setImageBitmap(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length));
        btnImportImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryLauncher.launch(null);
            }
        });
        galleryLauncher = registerForActivityResult(new GalleryActivityResultContract(), result -> {
            if (result != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = requireContext().getContentResolver().query((Uri) result, filePathColumn, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String imgDecodableString = cursor.getString(columnIndex);
                        NewLogo.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    }
                    cursor.close();
                }
            }
        });
        return viewInflate;
    }
    private final TextWatcher  NameWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            btnSave.setEnabled(NewSuffixStudent.getText().length() !=0
                    || NewSuffixTeacher.getText().length()!=0 || NewName.getText().length()!=0 || NewLogo.getDrawable()!=null
            );
        }
    };
    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {

    }

    public void setUniversity(University university) {
        this.university = university;
    }
}