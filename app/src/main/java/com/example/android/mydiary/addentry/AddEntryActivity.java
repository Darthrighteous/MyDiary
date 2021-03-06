package com.example.android.mydiary.addentry;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.mydiary.R;
import com.example.android.mydiary.journal.JournalContract;

public class AddEntryActivity extends AppCompatActivity {
    //constant definitions
    //request constants
    public static final int REQUEST_ADD_ENTRY = 101;
    public static final int REQUEST_EDIT_ENTRY = 102;

    //result constant for entry deletion
    public static final int RESULT_DELETED = 201;

    private AddEntryPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        // Setup actionbar to show up
        ActionBar actionBar = getSupportActionBar();
        try{
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        //setup fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddEntryFragment fragment = (AddEntryFragment)
                fragmentManager.findFragmentById(R.id.frame_content);

        if(fragment == null) {
            fragment = AddEntryFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.frame_content, fragment)
                .commit();

        String userUId = getIntent().getStringExtra(JournalContract.ARGUMENT_UNIQUE_USER_ID);

        if (getIntent().hasExtra(JournalContract.ARGUMENT_UNIQUE_ENTRY_ID)) {
            //edit entry case

            //get the bundle from intent and get the entry parameters
            Bundle bundle = getIntent().getBundleExtra(JournalContract.BUNDLE_EDIT_ENTRY);
            String entryUId = bundle.getString(JournalContract.ARGUMENT_UNIQUE_ENTRY_ID);
            String entryTitle = bundle.getString(JournalContract.ARGUMENT_ENTRY_TITLE);
            String entryBody = bundle.getString(JournalContract.ARGUMENT_ENTRY_BODY);
            String entryDateCreated = bundle.getString(JournalContract.ARGUMENT_ENTRY_DATE_CREATED);

            try{
                //set the action bar to edit entry
                actionBar.setTitle("Edit Entry");
            } catch (NullPointerException e){
                e.printStackTrace();
            }

            //initialize presenter for edit entry
            mPresenter = new AddEntryPresenter(userUId, entryUId, entryTitle, entryBody, entryDateCreated,  fragment);
        } else {
            //add entry case
            try{
                //set action bar title to new entry title
                actionBar.setTitle("New Entry");
            } catch (NullPointerException e){
                e.printStackTrace();
            }

            //initialize presenter for new entry
            mPresenter = new AddEntryPresenter(userUId, fragment);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_entry, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            //call presenter method to delete entry
            mPresenter.deleteEntry();
            //set activity result to RESULT_DELETED and finish
            setResult(RESULT_DELETED);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
