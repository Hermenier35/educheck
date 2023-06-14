package com.example.educheck.Controleur.Dashboard;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.educheck.Controleur.DashboardAdmin.GalleryActivityResultContract;
import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FragJustification2 extends Fragment implements AsyncTaskcallback {

    private static final String TOKEN = "token";
    private static final String ARG_JUST = "id_justification";
    private static final String PROF_NAME = "profName";
    private static final String NAME_COURS = "nameCours";
    private static final int STORAGE_PERMISSION_CODE = 24;
    private static final int REQUEST_CODE_PERMISSION = 23;

    private View view;
    private Button btnSelectFile;
    private Button btnUploadFile;
    private ImageView image;

    private DashboardImplementation request;

    private ActivityResultLauncher<Void> galleryLauncher;
    String id_j;
    String token;

    String profName;
    String nameCours;

    String mail;

    String selectedPdfName;
    Uri selectedPdfUri;

    byte[] pdfBytearray;

    public FragJustification2() {
    }

    public static FragJustification2 newInstance(String token, String id_justification, String profName, String nameCours) {
        FragJustification2 fragment = new FragJustification2();
        Bundle args = new Bundle();
        args.putString(TOKEN, token);
        args.putString(ARG_JUST, id_justification);
        args.putString(PROF_NAME, profName);
        args.putString(NAME_COURS, nameCours);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(getActivity(), new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        if (getArguments() != null) {
            token = getArguments().getString(TOKEN);
            id_j = getArguments().getString(ARG_JUST);
            profName = getArguments().getString(PROF_NAME);
            nameCours = getArguments().getString(NAME_COURS);
            System.out.println("token " + token + " id_j: " + id_j + " profName: " + profName + " nameCours: " + nameCours);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_justification, container, false);

        btnSelectFile = view.findViewById(R.id.btnSelectFile);
        image = view.findViewById(R.id.imgJ);
        btnUploadFile = view.findViewById(R.id.btnUpload); // Initialize the btnUploadFile button

        btnSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilePicker();
            }
        });

        btnUploadFile.setOnClickListener(v -> {
            saveToBDD();
        });

        Button btnOpenPdf = view.findViewById(R.id.btnOpenPdf);
        btnOpenPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPdfFile(pdfBytearray);
            }
        });


        galleryLauncher = registerForActivityResult(new GalleryActivityResultContract(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Intent.ACTION_PICK), result -> {
            if (result != null) {
                String[] filePathColumn = {MediaStore.Downloads.DATA};
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

        return view;
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(intent, "Select PDF"), REQUEST_CODE_PERMISSION);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PERMISSION && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            selectedPdfUri = data.getData();
            String filePath = getFilePathFromUri(selectedPdfUri);
            if (filePath != null) {
                image.setVisibility(View.GONE);
                selectedPdfName = getFileNameFromPath(filePath);
                // Display the selected PDF name wherever you want
            }
        }
    }


    private String getFilePathFromUri(Uri uri) {
        String filePath = null;
        Cursor cursor = null;
        try {
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return filePath;
    }

    public void saveToBDD() {
        Justificatif justif;
        byte[] pdfByteArray = null;
        if (selectedPdfUri != null) {
            try {
                InputStream inputStream = requireContext().getContentResolver().openInputStream(selectedPdfUri);
                if (inputStream != null) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                    }

                    pdfByteArray = byteArrayOutputStream.toByteArray();

                    inputStream.close();
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        request = new DashboardImplementation(this);
        pdfBytearray=pdfByteArray;
        request.justify(id_j, token, mail, profName, pdfByteArray);
        // request.postJustificatif(this.token, justif);  a faire
    }

    private String getFileNameFromPath(String filePath) {
        if (filePath != null) {
            int slashIndex = filePath.lastIndexOf('/');
            if (slashIndex >= 0 && slashIndex < filePath.length() - 1) {
                return filePath.substring(slashIndex + 1);
            }
        }
        return null;
    }


    private void openPdfFile(byte[] pdfByteArray) {
        try {
            // Create a temporary file to save the PDF
            File pdfFile = File.createTempFile("temp", ".pdf", getActivity().getCacheDir());

            // Write the byte array to the temporary file
            FileOutputStream fos = new FileOutputStream(pdfFile);
            fos.write(pdfByteArray);
            fos.close();

            // Generate a content URI for the file using FileProvider
            Uri contentUri = FileProvider.getUriForFile(requireContext(), requireContext().getPackageName() + ".fileprovider", pdfFile);

            // Create an intent to open the PDF file
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(contentUri, "application/pdf");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try {
                // Grant read permissions to the receiving app
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Attempt to open the PDF file using an external PDF viewer app
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // If no PDF viewer app is available, show an error message
                Toast.makeText(getActivity(), "No PDF viewer app found.", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        if (!items.getJSONObject(0).has("code_retour")) {
            JSONObject response = items.getJSONObject(0);
            System.out.println(response.toString());
        }
    }
}
