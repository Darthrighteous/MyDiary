package com.example.android.mydiary.addentry;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

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
            actionBar.setTitle("add new Entry");
            mPresenter = new AddEntryPresenter(userUId, fragment);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
