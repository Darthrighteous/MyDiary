package com.example.android.mydiary.journal;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by Jalil on 26/06/2018.
 */

public class JournalPresenter implements JournalPresenterContract {

    private JournalViewContract mJournalViewContract;

    private DatabaseReference mJournalEntriesDatabaseReference;

    public JournalPresenter(DatabaseReference databaseReference, JournalViewContract journalViewContract) {
        mJournalEntriesDatabaseReference = databaseReference;

        mJournalViewContract = journalViewContract;

        mJournalViewContract.setPresenter(this);
    }

    @Override
    public void loadEntries() {
        Query query = mJournalEntriesDatabaseReference.limitToLast(50);
        mJournalViewContract.showJournalEntries(query);
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

    }
}
