package com.example.ab0034.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeImageTransform;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yarolegovich.slidingrootnav.SlidingRootNav;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    SlidingRootNav slidingRootNavBuilder;
    ImageView img;
    LinearLayout animlayout;
    Animation animZoomout, animZoomIn, move, moveleft;
    Boolean CanTransit = true;
    ListView List;
    ArrayList<MenuDto> menuDtoArrayList;
    ViewGroup transitionsContainer;
    DrawerLayout drawerLayout;
    TextView TxtMenu;
    LinearLayout Navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);
        animlayout = (LinearLayout) findViewById(R.id.animlayout);
        List = (ListView) findViewById(R.id.List);
        transitionsContainer = (ViewGroup) findViewById(R.id.transitions_container);
        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        TxtMenu=(TextView)findViewById(R.id.TxtMenu);
        Navigation=(LinearLayout) findViewById(R.id.Navigation);
//        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Slide");
//        setSupportActionBar(toolbar);
//        slidingRootNavBuilder = new SlidingRootNavBuilder(this)
//                .withMenuOpened(false)
//                .withToolbarMenuToggle(toolbar)
//                .withMenuLayout(R.layout.menu)
//                .inject();
        animZoomout = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
        animZoomIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        move = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        moveleft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.moveleft);
        img.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (CanTransit) {
                    Changetransition();
                    img.setImageResource(R.drawable.ic_menu);
                    AnimationSet s = new AnimationSet(false);
                    s.addAnimation(animZoomout);
                    s.addAnimation(move);
                    animlayout.startAnimation(s);
                    s.setFillAfter(true);
                    CanTransit = false;
                } else if (!CanTransit) {
                    Changetransition();
                    img.setImageResource(R.drawable.ic_left_arrow);
                    AnimationSet s = new AnimationSet(false);
                    s.addAnimation(animZoomIn);
                    s.addAnimation(moveleft);
                    animlayout.startAnimation(s);
                    s.setFillAfter(true);
                    CanTransit = true;
                }
            }
        });
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
////                    int position = 0;
////                    if (v.getTag() instanceof Integer) {
////                        position = (Integer) v.getTag();
////                    }
////                String g = v.getTag().toString();
//                if (!CanTransit ) {
//                    AnimationSet s = new AnimationSet(false);
//                    s.addAnimation(animZoomIn);
//                    s.addAnimation(moveleft);
//                    animlayout.startAnimation(s);
//                    s.setFillAfter(true);
//                    CanTransit = true;
//                }
//
//                }catch (Exception e){
//
//                }
//            }
//        });
//        animlayout.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                return false;
//            }
//        });
//
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(Navigation)) {
//                    drawerLayout.closeDrawer(Navigation);
//                }
//                else {
//                    drawerLayout.openDrawer(Navigation);
//                }
//            }
//        });
        menu();
        List.setAdapter(new MenuAdapter(this, menuDtoArrayList));
        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.TxtMenu);
                Toast.makeText(MainActivity.this, textView.getText().toString(), Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {


                        TransitionManager.beginDelayedTransition(transitionsContainer,
                                new TransitionSet().addTransition(new ChangeImageTransform()));
                        img.setImageResource(R.drawable.ic_left_arrow);
                        AnimationSet s = new AnimationSet(false);
                        s.addAnimation(animZoomIn);
                        s.addAnimation(moveleft);
                        animlayout.clearAnimation();
                        // animlayout.startAnimation(s);
                        // s.setFillAfter(true);
                        CanTransit = true;
                    }
                }, 100);
            }
        });



        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                                           @Override
                                           public void onDrawerSlide(View drawerView, float slideOffset) {
                                               TxtMenu.setVisibility(slideOffset > 0 ? View.VISIBLE : View.GONE);

                                               // Scale the View based on current slide offset
                                               final float diffScaledOffset = slideOffset * (1 - 0.7f );
                                               final float offsetScale = 1 - diffScaledOffset;
                                               transitionsContainer.setScaleX(offsetScale);
                                               transitionsContainer.setScaleY(offsetScale);

                                               // Translate the View, accounting for the scaled width
                                               final float xOffset = drawerView.getWidth() * slideOffset;
                                               final float xOffsetDiff = transitionsContainer.getWidth() * diffScaledOffset / 2;
                                               final float xTranslation = xOffset - xOffsetDiff;
                                               transitionsContainer.setTranslationX(xTranslation);
                                           }

                                           @Override
                                           public void onDrawerClosed(View drawerView) {
                                               TxtMenu.setVisibility(View.GONE);
                                           }
                                       }
        );
//        SwipeEvents.detect(animlayout, new SwipeEvents.SwipeCallback() {
//            @Override
//            public void onSwipeTop() {
//                // showToast("Swiped - detectTop");
//                Toast.makeText(MainActivity.this, "SwipeTop", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSwipeRight() {
//                Toast.makeText(MainActivity.this, "SwipeRight", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSwipeBottom() {
//                Toast.makeText(MainActivity.this, "SwipeBottom", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSwipeLeft() {
//                Toast.makeText(MainActivity.this, "SwipeLeft", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Changetransition() {
        TransitionManager.beginDelayedTransition(transitionsContainer,
                new TransitionSet().addTransition(new ChangeImageTransform()));
    }

    public void menu() {
        menuDtoArrayList = new ArrayList<>();
        MenuDto dto;
        dto = new MenuDto();
        dto.Name = "Demo";
        dto.image = getResources().getDrawable(R.drawable.ic_back);
        menuDtoArrayList.add(dto);
        dto = new MenuDto();
        dto.Name = "Setting";
        dto.image = getResources().getDrawable(R.drawable.ic_back);
        menuDtoArrayList.add(dto);
    }


    public class MenuAdapter extends ArrayAdapter<ArrayList<MenuDto>> {
        ArrayList<MenuDto> dtoArrayList;
        Context context;

        public MenuAdapter(Context context, ArrayList<MenuDto> dtoArrayList) {
            super(context, R.layout.menu);
            this.context = context;
            this.dtoArrayList = dtoArrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.menu, null);
            MenuDto p = dtoArrayList.get(position);

            TextView Txtmenu = (TextView) view.findViewById(R.id.TxtMenu);
            ImageView ImgMenu = (ImageView) view.findViewById(R.id.ImgMenu);
            Txtmenu.setText(p.Name);
            ImgMenu.setImageDrawable(p.image);
            ImgMenu.setVisibility(View.INVISIBLE);
            return view;
        }

        @Override
        public int getCount() {
            return dtoArrayList.size();
        }
    }
}

class MenuDto {
    String Name;
    Drawable image;
}
