package com.example.ab0034.fileshare;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.ab0034.fileshare.Fragment.MusicFragment;
import com.example.ab0034.fileshare.Fragment.PicsFragment;
import com.example.ab0034.fileshare.Fragment.VideoFragment;

import java.util.ArrayList;

import eu.long1.spacetablayout.SpaceTabLayout;

public class FilesActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    SpaceTabLayout tabLayout;
    ImageView img_back,img_forward;
    ViewPager viewPager;
    ArrayList<Fragment> TabNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        tabLayout = ( SpaceTabLayout) findViewById(R.id.ContentTab);
        img_back =(ImageView)findViewById(R.id.img_back);
        img_forward =(ImageView)findViewById(R.id.img_forward);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        TabNames = new ArrayList<>();
//        TabNames.add("Music");
//        TabNames.add("Video");
//        TabNames.add("Picture");
        TabNames.add(new MusicFragment());
        TabNames.add(new VideoFragment());
        TabNames.add(new PicsFragment());
        tabLayout.initialize(viewPager, getSupportFragmentManager(),
                TabNames, savedInstanceState);
//        Pager adapter = new Pager(getSupportFragmentManager(),tabLayout.getCurrentPosition());
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(3);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilesActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        img_forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FilesActivity.this, WiFiConnectivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    public class Pager extends FragmentStatePagerAdapter {
        int tabCount;

        public Pager(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MusicFragment();
                case 1:
                    return new VideoFragment();
                case 2:
                    return  new PicsFragment();
                default:
                    return new MusicFragment();
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }
}


