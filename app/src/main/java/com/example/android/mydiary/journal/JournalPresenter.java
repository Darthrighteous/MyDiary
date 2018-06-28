package com.example.android.mydiary.journal;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Jalil on 26/06/2018.
 */

public class JournalPresenter implements JournalContract.Presenter{

    private JournalContract.View mJournalView;

    private DatabaseReference mJournalEntriesDatabaseReference;

    public JournalPresenter(JournalContract.View journalView) {
        mJournalEntriesDatabaseReference = FirebaseDatabase.getInstance().getReference().child("entries");

        mJournalView = journalView;

        mJournalView.setPresenter(this);
    }

    @Override
    public void loadEntries() {
        Query query = mJournalEntriesDatabaseReference.orderByKey();
        mJournalView.showJournalEntries(query);
    }

    @Override
    public void openEntryDetails() {
        //TODO diary entry selected functionality

    }

    @Override
    public void openSettings() {
        //TODO settings selected functionality

    }

    @Override
    public void logout() {
        //TODO logout selected functionality

    }

    @Override
    public void addNewEntry() {
        mJournalView.showAddEntry();
    }

    @Override
    public void start() {

    }
}
