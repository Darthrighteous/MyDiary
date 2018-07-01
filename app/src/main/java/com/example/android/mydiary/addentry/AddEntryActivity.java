package com.example.android.mydiary.addentry;

import android.app.Activity;
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

    private AddEntryPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_detail);

        // Setup actionbar to show up
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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
            String entryUId = getIntent().getStringExtra(JournalContract.UNIQUE_ENTRY_ID);
            String entryTitle = getIntent().getStringExtra(JournalContract.ENTRY_TITLE);
            String entryBody = getIntent().getStringExtra(JournalContract.ENTRY_BODY);
            actionBar.setTitle("Edit Entry");
            mPresenter = new AddEntryPresenter(userUId, entryUId, entryTitle, entryBody,  fragment);
        } else {
            //add entry case
            actionBar.setTitle("New Entry");
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
            setResult(Activity.RESULT_CANCELED);
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
