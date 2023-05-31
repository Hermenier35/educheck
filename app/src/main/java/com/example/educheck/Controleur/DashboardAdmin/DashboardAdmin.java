package com.example.educheck.Controleur.DashboardAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.autofill.AutofillManager;

import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class DashboardAdmin extends AppCompatActivity implements AsyncTaskcallback {
    private Toolbar toolbar;
    private static final int NUM_PAGES = 2;
    public static ViewPager2 viewPager;
    public static FragmentStateAdapter pagerAdapter;
    private String token;
    private String request;
    private boolean valide;
    public static FragmentManager fragmentManager;
    public static FragmentActivity fa;
    private DashboardImplementation dashboardRequest;
    private University university;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        fa = this;

        token = getIntent().getStringExtra("token");
        valide = getIntent().getBooleanExtra("valide", false);
        viewPager = findViewById(R.id.viewpager);
        System.out.println("valide: " + valide);
        if(valide){
            dashboardRequest = new DashboardImplementation(this);
            request = "getUniversity";
            dashboardRequest.getUniversity(token);
        }else{
            pagerAdapter = new DashboardAdmin.ScreenSlidePagerAdapter(this, AddUniversityFragment.newInstance(token, "p2"),
                    DefaultPageFragment.newInstance(token, "p2"));
            viewPager.setAdapter(pagerAdapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {
        if(!items.getJSONObject(0).has("code_retour"))
            switch (request){
                case "getUniversity":
                    JSONObject response = items.getJSONObject(0);
                    university = new University(response.getString("name"), response.getString("suffixe_student"),
                            response.getString("suffixe_teacher"), Base64.getDecoder().decode(response.getString("image")));
                    pagerAdapter = new DashboardAdmin.ScreenSlidePagerAdapter(this,
                            ManagerUniversityFragment.newInstance(token, university), ManagerAcademicBackgroundsFragment.newInstance(token, university));
                    viewPager.setAdapter(pagerAdapter);
                    break;
                default: System.err.println("Request not find");
            }else
                System.err.println("code_retour: " + items.getJSONObject(0).getString("code_retour") + " " +
                        "in task DasbordAdmin");
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        private Fragment firstPage, secondPage;
        public ScreenSlidePagerAdapter(FragmentActivity fa, Fragment page1, Fragment page2) {
            super(fa);
            firstPage = page1;
            secondPage = page2;
        }

        @Override
        public Fragment createFragment(int position) {
            if(position==0)
                return firstPage;
            else
                return secondPage;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}