package com.example.android.mydiary.journal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mydiary.R;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class JournalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawer;

    private NavigationView mNavigationView;

    private JournalFragment mJournalFragment;

    //Firebase instance variables
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //authentication providers
    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build() );

    private JournalPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        //setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setup Drawer
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        //set up Navigation view
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        ImageView imageUserPhoto = mNavigationView.getHeaderView(0).findViewById(R.id.image_user_photo);
        imageUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Feature not available yet",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //fab setup
        FloatingActionButton fab = findViewById(R.id.fab_open_new_entry);
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJournalFragment.showAddEntryActivity();
            }
        });

        //initialize drawer logout button
        ImageView image_logout = findViewById(R.id.image_logout);
        image_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance().signOut(getApplicationContext());
            }
        });

        //initialize drawer settings button
        ImageView image_settings = findViewById(R.id.image_settings);
        image_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Feature not available yet",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //initialize the fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        mJournalFragment = (JournalFragment) fragmentManager
                .findFragmentById(R.id.contentFrame);
        if (mJournalFragment == null) {
            //Create the journal fragment
            mJournalFragment = JournalFragment.newInstance();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.contentFrame, mJournalFragment);
            transaction.commit();
        }

        //create the presenter
        mPresenter = new JournalPresenter(mJournalFragment);

        //initialize firebase auth stuff
        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //signed in
                    onSignedInInitialize(user);
                } else {
                    //signed out
                    onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            JournalContract.RC_SIGN_IN);

                }
            }
        };
    }



    private void onSignedInInitialize(FirebaseUser user) {
        //display nav bar user info
        TextView username = mNavigationView.getHeaderView(0).
                findViewById(R.id.text_username);
        username.setText(user.getDisplayName());
        TextView email = mNavigationView.getHeaderView(0).
                findViewById(R.id.text_email_address);
        email.setText(user.getEmail());

        mPresenter.setUser(user);
    }

    private void onSignedOutCleanup() {
        //TODO cleanup
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)){
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            AuthUI.getInstance().signOut(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.nav_all) {
            // TODO Handle the all action
        } else if (id == R.id.nav_private) {
            // Handle the private action
            Toast.makeText(this, "Feature not available yet",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_archive) {
            //TODO Handle the archive action
            Toast.makeText(this, "Feature not available yet",
                    Toast.LENGTH_SHORT).show();
        }
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == JournalContract.RC_SIGN_IN) {
            if(resultCode == Activity.RESULT_OK){
                Snackbar.make(findViewById(R.id.fab_open_new_entry),
                        "You are now Signed In! Welcome to your diary",
                        Snackbar.LENGTH_LONG).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        mPresenter.stop();
    }
}
