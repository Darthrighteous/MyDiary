package com.example.android.mydiary.addentry;

import com.example.android.mydiary.data.JournalEntry;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Jalil on 28/06/2018.
 */

public class AddEntryPresenter implements AddEntryContract.Presenter {

    private AddEntryContract.View mAddEntryView;

    private DatabaseReference mDatabaseReference;

    public AddEntryPresenter(DatabaseReference databaseReference, AddEntryContract.View addEntryView) {
        mDatabaseReference = databaseReference;

        mAddEntryView = addEntryView;
        mAddEntryView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void saveEntry(String title, String body) {
        JournalEntry newEntry = new JournalEntry(title, body);
        mDatabaseReference.push().setValue(newEntry);
        mAddEntryView.showEntries();
    }

    @Override
    public void showExistingEntry() {

    }
}
