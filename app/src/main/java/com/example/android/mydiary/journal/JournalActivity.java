package com.example.android.mydiary.journal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.mydiary.R;
import com.example.android.mydiary.data.JournalEntry;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JournalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private JournalPresenter mPresenter;

    private DrawerLayout mDrawer;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mJournalEntriesDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);


        //initialize firebase database stuff
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mJournalEntriesDatabaseReference = mFirebaseDatabase.getReference().child("entries");


        //initialize the fragment and add it to the layout
        FragmentManager fragmentManager = getSupportFragmentManager();
        JournalFragment journalFragment = (JournalFragment) fragmentManager
                .findFragmentById(R.id.contentFrame);

        if (journalFragment == null) {
            //Create the journal fragment
            journalFragment = JournalFragment.newInstance();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.contentFrame, journalFragment);
            transaction.commit();
        }

        //create the presenter
        mPresenter = new JournalPresenter(mJournalEntriesDatabaseReference, journalFragment);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO remove test code. testing the database
                JournalEntry entry = new JournalEntry("test title", "test body");
                mJournalEntriesDatabaseReference.push().setValue(entry);
                mPresenter.addNewEntry();
        }
        });

        mPresenter.loadEntries();

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.journal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            mPresenter.openSettings();
            return true;
        } else if (id == R.id.action_logout){
            mPresenter.logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
