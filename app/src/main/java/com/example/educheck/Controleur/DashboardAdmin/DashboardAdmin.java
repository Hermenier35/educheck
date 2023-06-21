package com.example.educheck.Controleur.DashboardAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.ClipData;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.autofill.AutofillManager;

import com.example.educheck.Modele.Implementation.DashboardImplementation;
import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.Modele.University;
import com.example.educheck.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class DashboardAdmin extends AppCompatActivity implements AsyncTaskcallback {
    private Toolbar toolbar;
    private static final int NUM_PAGES = 2;
    public static ViewPager2 viewPager;
    public static FragmentStateAdapter pagerAdapter;
    public String token;
    private String request;
    private boolean valide;
    public static FragmentManager fragmentManager;
    public static FragmentActivity fa;
    private DashboardImplementation dashboardRequest;
    private University university;
    private BottomNavigationView bottomNavigationView;
    private static MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.inflateMenu(R.menu.menu_bottom);
        item = bottomNavigationView.getMenu().findItem(R.id.logoNavigation);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        fa = this;

        token = getIntent().getStringExtra("token");
        valide = getIntent().getBooleanExtra("valide", false);
        viewPager = findViewById(R.id.viewpager);
        if(valide){
            dashboardRequest = new DashboardImplementation(this);
            request = "getUniversity";
            dashboardRequest.getUniversity(token);
        }else{
            pagerAdapter = new DashboardAdmin.ScreenSlidePagerAdapter(this, false, token);
            viewPager.setAdapter(pagerAdapter);
        }

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position==0)
                    item.setIcon(R.drawable.baseline_switch_left_white_36dp);
                else
                    item.setIcon(R.drawable.baseline_switch_right_white_36dp);

            }
        });

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
                            response.getString("suffixe_teacher"), Base64.getDecoder().decode(response.getString("image")),
                            response.getString("_id"));
                    pagerAdapter = new DashboardAdmin.ScreenSlidePagerAdapter(this,true ,token, university);
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
            item.setIcon(R.drawable.baseline_switch_left_white_36dp);
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            item.setIcon(R.drawable.baseline_switch_right_white_36dp);
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    public static class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        Boolean valide;
        String token;
        University university;
        public ScreenSlidePagerAdapter(FragmentActivity fa, Boolean valide, String token) {
            super(fa);
            this.valide = valide;
            this.token = token;
        }

        public ScreenSlidePagerAdapter(FragmentActivity fa, Boolean valide, String token, University university) {
            super(fa);
            this.valide = valide;
            this.token = token;
            this.university = university;
        }

        @Override
        public Fragment createFragment(int position) {
            if(valide) {
                if (position == 0) {
                    return ManagerUniversityFragment.newInstance(token, university);
                } else {
                    return ManagerAcademicBackgroundsFragment.newInstance(token, university);
                }
            }else{
                if (position == 0) {
                    return AddUniversityFragment.newInstance(token, "");
                } else {
                    return DefaultPageFragment.newInstance(token,"");
                }
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}