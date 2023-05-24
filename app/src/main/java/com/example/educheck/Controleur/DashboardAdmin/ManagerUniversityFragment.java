package com.example.educheck.Controleur.DashboardAdmin;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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

import java.io.ByteArrayOutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManagerUniversityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManagerUniversityFragment extends Fragment implements AsyncTaskcallback {

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

    // TODO: Rename and change types of parameters
    private String token;
    private String param2;

    public ManagerUniversityFragment() {
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
    public static ManagerUniversityFragment newInstance(String token, String param2) {
        ManagerUniversityFragment fragment = new ManagerUniversityFragment();
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
        View viewInflate = inflater.inflate(R.layout.fragment_manager_university, container, false);
        gallery = viewInflate.findViewById(R.id.btnGallery);
        addUni = viewInflate.findViewById(R.id.addUniv);
        suffixeStudent = viewInflate.findViewById(R.id.suffixe_student);
        suffixeTeacher = viewInflate.findViewById(R.id.suffixe_teacher);
        nameUniv = viewInflate.findViewById(R.id.name_univ);
        image = viewInflate.findViewById(R.id.img_uni);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png","image/jpg"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                startActivityForResult(intent,GALLERY_REQUEST_CODE);
            }
        });
        addUni.setOnClickListener(v->{saveToBDD();});
        return viewInflate;
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data) {
        //Le code de résultat est RESULT_OK uniquement si l'utilisateur sélectionne une image.
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    //data.getData renvoie l'URI de contenu pour l'image sélectionnée
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    // Obtenez le curseur
                    Cursor cursor = getContext().getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    // Déplacer vers la première ligne
                    cursor.moveToFirst();
                    //Obtenir l'index de colonne de MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Obtient la valeur de chaîne dans la colonne
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    //Définir l'image dans ImageView après le décodage de la chaîne
                    image.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                    break;
            }
    }

    public void saveToBDD(){
        University university;
        DashboardImplementation request;
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

    }
}