package com.example.android.mydiary.journal;

import com.example.android.mydiary.data.JournalEntry;
import com.example.android.mydiary.data.source.FirebaseContract;
import com.example.android.mydiary.data.source.FirebaseRepository;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Created by Jalil on 26/06/2018.
 */

public class JournalPresenter implements JournalContract.Presenter{

    private JournalContract.View mJournalView;

    private FirebaseUser mUser;

    private FirebaseRepository mFirebaseRepository;


    JournalPresenter(JournalContract.View journalView) {

        mJournalView = journalView;

        mJournalView.setPresenter(this);
    }

    @Override
    public void start() {
        mFirebaseRepository.attachDatabaseReadListener();
    }

    @Override
    public void stop() {
        if(mFirebaseRepository != null) {
            mFirebaseRepository.detachDatabaseReadListener();
        }

    }

    @Override
    public void setUser(FirebaseUser user) {
        mUser = user;

        mFirebaseRepository = new FirebaseRepository(user.getUid(), this);

        start();
    }

    @Override
    public String getUId() {
        return mUser.getUid();
    }

    @Override
    public void loadEntries() {
        mJournalView.setLoadingIndicator(true);
        if(mFirebaseRepository == null) {
            mJournalView.setLoadingIndicator(false);
            return;
        }
        mFirebaseRepository.getEntries(new FirebaseContract.GetEntriesCallback() {
            @Override
            public void onEntriesLoaded(List<JournalEntry> entries) {
                processEntries(entries);
            }

            @Override
            public void onLoadComplete() {
                //
            }
        });
    }

    @Override
    public void processEntries(List<JournalEntry> entries) {
        mJournalView.setLoadingIndicator(false);
        if(entries.size() == 0) {
            mJournalView.clear();
            mJournalView.showNoEntriesMessage(true);
        } else {
            mJournalView.showNoEntriesMessage(false);
            mJournalView.showJournalEntries(entries);
        }

    }

    @Override
    public void openSettings() {
        //TODO settings selected functionality

    }
}
