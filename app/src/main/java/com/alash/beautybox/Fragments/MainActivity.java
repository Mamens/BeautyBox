package com.alash.beautybox.Fragments;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.alash.beautybox.Fragments.FirstFragments.Fragment_kategory_tovars1;
import com.alash.beautybox.R;

public class MainActivity extends AppCompatActivity implements Fragments {

    FragmentTransaction transaction;
    FragmentManager manager;
    private static final String SELECTED_ITEM = "arg_selected_item";
    private BottomNavigationView mBottomNav;
    private int mSelectedItem;
    Fragment_profile frag1;
    android.app.FragmentTransaction fTrans;
    Fragment_profile fragment_profile;
    //Fragment_kategory_tovars1 fragment_kategory_tovars;

    //private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        fragment_profile = new Fragment_profile();
        fragment_profile.setFragment(this);
        frag1 = fragment_profile;
        // fragment_kategory_tovars.setFragment(this);
        mBottomNav = (BottomNavigationView) findViewById(R.id.navigation);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });

        MenuItem selectedItem;
        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = mBottomNav.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = mBottomNav.getMenu().getItem(0);
        }
        selectFragment(selectedItem);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(SELECTED_ITEM, mSelectedItem);
        super.onSaveInstanceState(outState);
    }



    @Override
    public void onBackPressed() {
        MenuItem homeItem = mBottomNav.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
        } else {
            super.onBackPressed();
        }
    }


    public void selectFragment(MenuItem item) {
        //fTrans = getFragmentManager().beginTransaction();
        Fragment frag = null;
        // init corresponding fragment
//        case R.id.menu_home:
//        fTrans.add(R.id.frgmCont, frag1);
//        break;
        switch (item.getItemId()) {
            case R.id.menu_home:
                //frag = new Fragment_profile();
//                frag = MenuFragment.newInstance(getString(R.string.main),
//                        getColorFromRes(R.color.color_home));
                Fragment_kategory_tovars1 fragment_kategory_tovars = Fragment_kategory_tovars1.newInstance();

                fragment_kategory_tovars.setFragment(this);
                frag = fragment_kategory_tovars;


//                Fragment_tovars_brends3 fragment_tovars_brends = Fragment_tovars_brends3.newInstance();
//                fragment_find_brend.setFragment(this);
//                frag = fragment_tovars_brends;

                break;
            case R.id.menu_search:
                frag = MenuFragment.newInstance(getString(R.string.basket),
                        getColorFromRes(R.color.color_notifications));
//                  Fragment_kategory_tovars1 fragment_kategory_tovars = new Fragment_kategory_tovars1();
//                  //fragment_kategory_tovars.setFragment(this);
//                frag = fragment_kategory_tovars;
                break;
            case R.id.menu_notifications:
                Fragment_profile fragment_profile = new Fragment_profile();
                fragment_profile.setFragment(this);
                frag = fragment_profile;


//                frag = MenuFragment.newInstance(getString(R.string.profile),
//                        getColorFromRes(R.color.color_search));
                break;
        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i < mBottomNav.getMenu().size(); i++) {
            MenuItem menuItem = mBottomNav.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }

        updateToolbarText(item.getTitle());

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
        }
//        if (frag1 != null) {
//            FragmentTransaction Ftrans = getSupportFragmentManager().beginTransaction();
//            Ftrans.add(R.id.container, frag1, frag1.getTag());
//            Ftrans.commit();
//        }

    }

    private void updateToolbarText(CharSequence text) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(text);
        }
    }

    private int getColorFromRes(@ColorRes int resId) {
        return ContextCompat.getColor(this, resId);
    }



    @Override
    public void show(Fragment data) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.container, data);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
