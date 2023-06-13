package com.example.educheck.Controleur.DashbardTeacher;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.widget.ImageView;

import com.example.educheck.Controleur.Dashboard.FragMessages1;
import com.example.educheck.Controleur.Dashboard.Schedule;
import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class DashBoardTeacher extends AppCompatActivity implements AsyncTaskcallback {

    private static final int NUM_PAGES = 4;
    private String token;
    private Toolbar toolbar;
    public static ViewPager2 viewPager;
    public static FragmentStateAdapter pagerAdapter;
    public static FragmentActivity fa;
    private DashboardImplementation dashboardImplementation;
    private ImageView imgAddStudent, imgAddCourse, imgMessenger, imgPresent,imgSchedule;
    private String request;
    private final String GET_UNIVERSITY = "getUniversity";
    private University university;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_prof);

        token = getIntent().getStringExtra("token");
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewPagerteacher);
        imgAddStudent = findViewById(R.id.imgAddStu);
        imgAddCourse = findViewById(R.id.imgAddCou);
        imgMessenger = findViewById(R.id.imgMess);
        imgPresent = findViewById(R.id.imgPre);
        imgSchedule = findViewById(R.id.imgSched);

        setSupportActionBar(toolbar);
        fa = this;
        dashboardImplementation = new DashboardImplementation(this);

        imgAddStudent.setOnClickListener(v -> viewPager.setCurrentItem(0));
        imgAddCourse.setOnClickListener(v -> viewPager.setCurrentItem(1));
        imgMessenger.setOnClickListener(v -> viewPager.setCurrentItem(2));
        imgPresent.setOnClickListener(v -> viewPager.setCurrentItem(3));
        imgSchedule.setOnClickListener(v->viewPager.setCurrentItem(4));

        sendRequest(GET_UNIVERSITY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        switch (request){
            case GET_UNIVERSITY:
                JSONObject response = items.getJSONObject(0);
                this.university = new University(response.getString("name"), response.getString("suffixe_student"),
                        response.getString("suffixe_teacher"), Base64.getDecoder().decode(response.getString("image")),
                        response.getString("_id"));
                initialisation();
                break;
        }
    }

    private void sendRequest(String name){
        request = name;
        switch(request){
            case GET_UNIVERSITY:
                dashboardImplementation.getUniversity(token);
                break;
        }

    }

    private void initialisation(){
        Fragment addStudent = AddStudentFragment.newInstance(token, this.university);
        Fragment addCourse = AddCoursesFragment.newInstance(token, this.university);
        Fragment present = PresentFragment.newInstance(token, this.university);
        Fragment schedule = new Schedule();
        Fragment messenger = new FragMessages1();
        pagerAdapter = new ScreenSlidePagerAdapter(fa, addStudent,addCourse, messenger, present, schedule);
        viewPager.setAdapter(pagerAdapter);
    }

    public static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private Fragment addStudentFragment, addCourseFragment, messengerFragment, presentFragment, schedule;
        public ScreenSlidePagerAdapter(FragmentActivity fa, Fragment page1, Fragment page2, Fragment page3, Fragment page4, Fragment page5) {
            super(fa);
            addStudentFragment = page1;
            addCourseFragment = page2;
            messengerFragment = page3;
            presentFragment = page4;
            schedule = page5;
        }

        @Override
        public Fragment createFragment(int position) {
            switch(position){
                case 0: return addStudentFragment;
                case 1: return addCourseFragment;
                case 2: return messengerFragment;
                case 3: return presentFragment;
                default: return schedule;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}