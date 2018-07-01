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
    public static final int REQUEST_ADD_ENTRY = 101;
    public static final int REQUEST_EDIT_ENTRY = 102;

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
                fragmentManager.findFragmentById(R.id.contentFrame);

        if(fragment == null) {
            fragment = AddEntryFragment.newInstance();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.contentFrame, fragment)
                .commit();

        String userUId = getIntent().getStringExtra(JournalContract.UNIQUE_USER_ID);

        if (getIntent().hasExtra(JournalContract.UNIQUE_ENTRY_ID)) {
            //edit entry case
            Bundle bundle = getIntent().getBundleExtra(JournalContract.EDIT_ENTRY_BUNDLE);

            String entryUId = bundle.getString(JournalContract.UNIQUE_ENTRY_ID);
            String entryTitle = bundle.getString(JournalContract.ENTRY_TITLE);
            String entryBody = bundle.getString(JournalContract.ENTRY_BODY);
            String entryDateCreated = bundle.getString(JournalContract.ENTRY_DATE_CREATED);

            try{
                actionBar.setTitle("Edit Entry");
            } catch (NullPointerException e){
                e.printStackTrace();
            }

            mPresenter = new AddEntryPresenter(userUId, entryUId, entryTitle, entryBody, entryDateCreated,  fragment);
        } else {
            //add entry case
            try{
                actionBar.setTitle("New Entry");
            } catch (NullPointerException e){
                e.printStackTrace();
            }

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
            //delete entry
            mPresenter.deleteEntry();
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
