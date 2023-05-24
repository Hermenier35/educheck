package com.example.educheck.Controleur.DashboardAdmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;

import com.example.educheck.Modele.Interface.AsyncTaskcallback;
import com.example.educheck.R;

import org.json.JSONArray;
import org.json.JSONException;

public class DashboardAdmin extends AppCompatActivity implements AsyncTaskcallback {
    private Toolbar toolbar;
    private static final int NUM_PAGES = 2;
    private ViewPager2 viewPager;
    private FragmentStateAdapter pagerAdapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_admin);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        token = getIntent().getStringExtra("token");
        viewPager = findViewById(R.id.viewpager);
        pagerAdapter = new DashboardAdmin.ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onTaskCompleted(JSONArray items) throws JSONException {

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

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        Fragment managerUniversity, managerAcademicBackgrounds;

        public ScreenSlidePagerAdapter(FragmentActivity fa) {
            super(fa);
            managerUniversity = ManagerUniversityFragment.newInstance(token,"p2");
            managerAcademicBackgrounds = ManagerAcademicBackgroundsFragment.newInstance(token, "p2");
        }

        @Override
        public Fragment createFragment(int position) {
            if(position==0)
                return managerUniversity;
            else
                return managerAcademicBackgrounds;
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}